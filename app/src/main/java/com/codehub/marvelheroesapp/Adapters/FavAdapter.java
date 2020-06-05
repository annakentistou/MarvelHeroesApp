package com.codehub.marvelheroesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.DatabaseFiles.FavDB;
import com.codehub.marvelheroesapp.DatabaseFiles.FavoriteHero;
import com.codehub.marvelheroesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<FavoriteHero> favItemList;
    private Context context;
    FavDB favDB;

    public FavAdapter(Context context, List<FavoriteHero> favItemList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fav_list_item, parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(favItemList.get(position).getItem_title());
        String image = favItemList.get(position).getItem_image();
        Picasso.get().load(image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        AppCompatImageButton bin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.thumbnail);
            bin = itemView.findViewById(R.id.bin);
        }
    }

  /*  private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }*/
}