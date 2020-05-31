package com.codehub.marvelheroesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.ItemDetails;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.ComicsModel;
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ComicsModel> comics;
    private List<CreatorsNameModel> items;

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
        final String title = comics.get(position).getTitle();
        /*final String subTitle = items.get(position).getName();*/
        final String description = comics.get(position).getDescription();
        final String image = comics.get(position).getThumbnail().getPath() + ".jpg";

        holder.Name.setText(title);
        /*holder.subtitle.setText(subTitle);*/
        Picasso.get().load(image).into(holder.image);

        //set onClickListener to item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Intent
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);//sending title of "custom of list view"
                intent.putExtra("subtitle",description);
                intent.putExtra("image",image);
                v.getContext().startActivity(intent);
            }
        });

        //set onCLickListener to heart icon
     /*   holder.add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, subtitle;
        ImageView image,add_to_fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subTitle);
            image = itemView.findViewById(R.id.thumbnail);
            add_to_fav = itemView.findViewById(R.id.heart);

        }
    }
}
