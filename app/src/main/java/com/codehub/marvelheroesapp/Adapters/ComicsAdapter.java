package com.codehub.marvelheroesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.Activities.ItemDetails;
import com.codehub.marvelheroesapp.R;
import com.codehub.marvelheroesapp.json.ComicsModel;
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ComicsModel> comics;
    private List<CreatorsNameModel> creators;


    public ComicsAdapter(Context ctx, List<ComicsModel> comics) {
        this.inflater = LayoutInflater.from(ctx);
        this.comics = comics;
    }

    @NonNull
    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_items_in_comics_series, parent, false);
        return new ComicsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String title = comics.get(position).getTitle();
        final String description = comics.get(position).getDescription();
        final String image = comics.get(position).getThumbnail().getPath() + ".jpg";
     /*   final String creator = comics.get(position).getCreators().getItems().get(position).getName();

        List<CreatorsNameModel> creators = new ArrayList<>();
        creators = comics.get(position).getCreators().getItems();*/
        /*if(creators.size() !=0) {
            holder.Creator.setText(creator);
        }*/
        holder.Name.setText(title);
        Picasso.get().load(image).into(holder.image);

        //set onClickListener to item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Intent
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);//sending title of "custom of list view"
                if (description != null) {
                    intent.putExtra("subtitle", "DESCRIPTION: " + description);
                } else
                    intent.putExtra("subtitle", "There is No description");
                intent.putExtra("image", image);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Creator;
        ImageView image;
        ImageButton add_to_fav, share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.title);
            Creator = itemView.findViewById(R.id.numOfComics);
            image = itemView.findViewById(R.id.thumbnail);
            add_to_fav = itemView.findViewById(R.id.heart);
            share = itemView.findViewById(R.id.share);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ComicsModel comicsModel = comics.get(position);
                    Intent share_intent = new Intent(Intent.ACTION_SEND);
                    share_intent.setType("image/*");
                    share_intent.putExtra(Intent.EXTRA_TITLE, "Marvel Hero");
                    share_intent.putExtra(Intent.EXTRA_SUBJECT, comicsModel.getTitle());
                    share_intent.putExtra(Intent.EXTRA_TEXT, comicsModel.getThumbnail().getPath() + ".jpg");
                    v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));

                }
            });
        }
    }
}

