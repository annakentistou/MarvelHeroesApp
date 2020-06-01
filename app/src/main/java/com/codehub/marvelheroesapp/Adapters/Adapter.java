package com.codehub.marvelheroesapp.Adapters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.Fragments.FirstFragment;
import com.codehub.marvelheroesapp.ItemDetails;
import com.codehub.marvelheroesapp.MainActivity;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.HeroesModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<HeroesModel> heroes;
    private List<HeroesModel> heroesListFull; //for search

    CallbackManager callbackManager;
    ShareDialog shareDialog;

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

        holder.Name.setText(title);
        /*holder.subtitle.setText(description);*/
        Picasso.get().load(image).into(holder.image);

        //set onClickListener to item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Intent
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);//sending title of "custom of list view"
                if (description != null) {
                    intent.putExtra("subtitle", "DESCRIPTION : " + description);
                } else
                    intent.putExtra("subtitle", "There is No description");
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

                Intent share_intent = new Intent(Intent.ACTION_SEND);
                /*Picasso.get().load(image).into(target);*/
                /*share_intent.setType("application/vnd.android.package-archive");*/
                share_intent.setType("image/jpeg");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, title); //for subject take the Heros' name
                share_intent.putExtra(Intent.EXTRA_TEXT, image); //for body take the image url
                v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));

            }
        });
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }
    //Create ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, subtitle;
        ImageView image;
        ImageButton add_to_fav, share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subTitle);
            image = itemView.findViewById(R.id.thumbnail);
            add_to_fav = itemView.findViewById(R.id.heart);
            share = itemView.findViewById(R.id.share);
        }
    }

    //31/5/2020 Used For Searching...
    public void filterList(List<HeroesModel> filteredList) {
        heroes = filteredList;
        notifyDataSetChanged();
    }
}
