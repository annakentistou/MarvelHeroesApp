package com.codehub.marvelheroesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codehub.marvelheroesapp.Activities.ItemDetails;
import com.codehub.marvelheroesapp.DatabaseFiles.FavDB;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<HeroesModel> heroes;
    private Context ctx;
    private FavDB favDB;

    public Adapter(Context ctx, List<HeroesModel> heroes) {
        this.inflater = LayoutInflater.from(ctx);
        this.heroes = heroes;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(ctx);
        SharedPreferences prefs = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final HeroesModel heroItem = heroes.get(position);
        final String title = heroItem.getName();
        final String description = heroItem.getDescription();
        final String extension = heroItem.getThumbnail().getExtension();
        final String image = heroItem.getThumbnail().getPath() + "." + extension;
        final int numOfComics = heroItem.getComics().getAvailable();
        final int numOfSeries = heroItem.getSeries().getAvailable();

        holder.name.setText(title);
        holder.numOfComics.setText("Comics: " + numOfComics);
        holder.numOfSeries.setText("Series: " + numOfSeries);

        Picasso.get().load(image).into(holder.image);
        Log.i("image", image);

        readCursorData(heroItem, holder);

        //set onClickListener to item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Intent
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);
                intent.putExtra("subtitle", description);
                intent.putExtra("image", image);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    //Create ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, numOfComics, numOfSeries;
        ImageView image;
        AppCompatImageButton add_to_fav, share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.thumbnail);
            add_to_fav = itemView.findViewById(R.id.heart);
            share = itemView.findViewById(R.id.share);
            numOfComics = itemView.findViewById(R.id.numOfComics);
            numOfSeries = itemView.findViewById(R.id.numOfSeries);

            //set onCLickListener to heart icon
            add_to_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    HeroesModel heroItem = heroes.get(position);
                    String extension = heroItem.getThumbnail().getExtension();

                    if (heroItem.getFavStatus() == 0) {
                        heroItem.setFavStatus(1);
                        favDB.insertIntoTheDatabase(heroItem.getName(), heroItem.getThumbnail().getPath() + "." + extension,
                                heroItem.getId(), heroItem.getFavStatus());
                        add_to_fav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    } else {
                        heroItem.setFavStatus(0);
                        favDB.remove_fav(heroItem.getId());
                        add_to_fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                }
            });
            //set onCLickListener to share icon
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    HeroesModel heroesItem = heroes.get(position);
                    String extension = heroesItem.getThumbnail().getExtension();
                    Intent share_intent = new Intent(Intent.ACTION_SEND);
                    share_intent.setType("text/plain");
                    share_intent.putExtra(Intent.EXTRA_TITLE, "Marvel Hero");
                    share_intent.putExtra(Intent.EXTRA_SUBJECT, heroesItem.getName());
                    share_intent.putExtra(Intent.EXTRA_TEXT, heroesItem.getThumbnail().getPath() + "." + extension);
                    v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));

                   /* ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag(heroesModel.getName()).build();
                    ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                            .setShareHashtag(shareHashTag)
                            .setQuote("Marvel Hero")
                            .setContentUrl(Uri.parse(heroesModel.getThumbnail().getPath() + ".jpg"))
                            .build();

                    ShareDialog.show((Activity) v.getContext(), shareLinkContent);*/
                }
            });
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(@NonNull HeroesModel heroItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(heroItem.getId());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                int item_fav_status = cursor.getInt(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                heroItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status == 1) {
                    viewHolder.add_to_fav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                } else if (item_fav_status == 0) {
                    viewHolder.add_to_fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    viewHolder.add_to_fav.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }

    //31/5/2020 Used For Searching...
    public void filterList(List<HeroesModel> filteredList) {
        heroes = filteredList;
        notifyDataSetChanged();
    }
}
