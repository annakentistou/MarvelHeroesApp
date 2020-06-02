package com.codehub.marvelheroesapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.Fragments.FirstFragment;
import com.codehub.marvelheroesapp.ItemDetails;
import com.codehub.marvelheroesapp.MainActivity;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<HeroesModel> heroes;
    private List<HeroesModel> heroesListFull; //for search

    public Adapter(Context ctx, List<HeroesModel> heroes) {
        this.inflater = LayoutInflater.from(ctx);
        this.heroes = heroes;
        heroesListFull = new ArrayList<>(heroes);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String title = heroes.get(position).getName();
        final String description = heroes.get(position).getDescription();
        final String image = heroes.get(position).getThumbnail().getPath() + ".jpg";
        final int numOfComics = heroes.get(position).getComics().getAvailable();
        final int numOfSeries = heroes.get(position).getSeries().getAvailable();

        holder.name.setText(title);
        holder.numOfComics.setText("Comics: " + numOfComics);
        holder.numOfSeries.setText("Series: " + numOfSeries);
        Picasso.get().load(image).into(holder.image);

        //set onClickListener to item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Intent
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);//sending title of "custom of list view"
                intent.putExtra("subtitle", description);
                intent.putExtra("image", image);
                v.getContext().startActivity(intent);
            }
        });

        //set onCLickListener to heart icon
        holder.add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color of heart when you click on it
                holder.add_to_fav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }
        });

        //set onCLickListener to Share icon
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share a LINK in Facebook
                ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag(title).build();
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setShareHashtag(shareHashTag)
                        .setQuote("Marvel Hero")
                        .setContentUrl(Uri.parse(image))
                        .build();

                ShareDialog.show((Activity) v.getContext(),shareLinkContent);

                //share direct Message
               /* Intent share_intent = new Intent(Intent.ACTION_SEND);
                share_intent.setType("image/jpeg");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, title); //for subject take the Heros' name
                share_intent.putExtra(Intent.EXTRA_TEXT, image); //for body take the image url
                v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));*/
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
        ImageButton add_to_fav, share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.thumbnail);
            add_to_fav = itemView.findViewById(R.id.heart);
            share = itemView.findViewById(R.id.share);
            numOfComics = itemView.findViewById(R.id.numOfComics);
            numOfSeries = itemView.findViewById(R.id.numOfSeries);
        }
    }

    //31/5/2020 Used For Searching...
    public void filterList(List<HeroesModel> filteredList) {
        heroes = filteredList;
        notifyDataSetChanged();
    }
}
