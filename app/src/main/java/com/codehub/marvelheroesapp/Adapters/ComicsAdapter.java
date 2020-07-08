package com.codehub.marvelheroesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private Context ctx;

    public ComicsAdapter(Context ctx, List<ComicsModel> comics) {
        this.inflater = LayoutInflater.from(ctx);
        this.comics = comics;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_items_in_comics_series, parent, false);
        return new ComicsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ComicsModel comicsItem = comics.get(position);
        final String title = comicsItem.getTitle();
        final String description = comicsItem.getDescription();
        final String extension = comicsItem.getThumbnail().getExtension();
        final String image = comicsItem.getThumbnail().getPath() + "." + extension;
        final List<CreatorsNameModel> creatorsName = comics.get(position).getCreators().getItems();
        Log.i("Creator", creatorsName.toString());

        for (int j = 0; j < creatorsName.size(); j++) {
            if (creatorsName.size() != 0) {
                holder.creator.setText("Creator: " + creatorsName.get(j).getName());
            } else {
                holder.creator.setText("There is no Creator");
            }
        }

        holder.name.setText(title);

        Picasso.get().load(image).placeholder(R.drawable.loading).into(holder.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDetails.class);
                intent.putExtra("title", title);
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
        TextView name, creator;
        ImageView image;
        ImageButton share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            creator = itemView.findViewById(R.id.creatorName);
            image = itemView.findViewById(R.id.thumbnail);
            share = itemView.findViewById(R.id.share);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ComicsModel comicsModel = comics.get(position);
                    Intent share_intent = new Intent(Intent.ACTION_SEND);
                    share_intent.setType("text/plain");
                    share_intent.putExtra(Intent.EXTRA_TITLE, "Marvel Hero");
                    share_intent.putExtra(Intent.EXTRA_SUBJECT, comicsModel.getTitle());
                    share_intent.putExtra(Intent.EXTRA_TEXT, comicsModel.getThumbnail().getPath() + ".jpg");
                    v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));
                }
            });
        }
    }
}
