package com.codehub.marvelheroesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.json.ComicsModel;
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder>{

    LayoutInflater inflater;
    private List<ComicsModel> comics;
    private List<CreatorsNameModel> creators;

    public ComicsAdapter(Context ctx, List<ComicsModel> comics) {
        this.inflater = LayoutInflater.from(ctx);
        this.comics = comics;
    }

    @NonNull
    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new ComicsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(comics.get(position).getTitle());
        holder.subtitle.setText(comics.get(position).getDescription());
        Picasso.get().load(comics.get(position).getThumbnail().getPath() + ".jpg").into(holder.image);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, subtitle;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name);
            subtitle = itemView.findViewById(R.id.subTitle);
            image = itemView.findViewById(R.id.thumbnail);
        }
    }
}
