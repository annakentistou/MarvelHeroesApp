package com.codehub.marvelheroesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.json.HeroesModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    private List<HeroesModel> heroes;
    /*List<HeroesInfo> heroes;*/

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.heroesName.setText(heroes.get(position).getName());
        holder.subtitle.setText(heroes.get(position).getDescription());
        Picasso.get().load(heroes.get(position).getThumbnail().getPath() + ".jpg").into(holder.image);

    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heroesName, subtitle;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heroesName = itemView.findViewById(R.id.heroesName);
            subtitle = itemView.findViewById(R.id.subTitle);
            image = itemView.findViewById(R.id.thumbnail);
        }
    }
}
