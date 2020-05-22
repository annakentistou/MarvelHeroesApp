package com.codehub.marvelheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<HeroesInfo> heroes;

    public Adapter(Context ctx, List<HeroesInfo> heroes) {
        this.inflater = LayoutInflater.from(ctx);
        this.heroes = heroes;
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
        /*holder.subTitle.setText(heroes.get(position).getExtension());*/
        /*Picasso.get().load(heroes.get(position).getPath()).into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heroesName;
        /*TextView subTitle;*/
        /*ImageView thumbnail;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heroesName = itemView.findViewById(R.id.heroesName);
            /*subTitle = itemView.findViewById(R.id.subTitle);*/
            /*thumbnail = itemView.findViewById(R.id.thumbnail);*/
        }

    }
}
