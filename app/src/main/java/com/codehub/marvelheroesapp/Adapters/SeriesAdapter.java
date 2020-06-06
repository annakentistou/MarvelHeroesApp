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
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.codehub.marvelheroesapp.json.SeriesModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<SeriesModel> series;
    private List<CreatorsNameModel> creators;

    public SeriesAdapter(Context ctx, List<SeriesModel> series) {
        this.inflater = LayoutInflater.from(ctx);
        this.series = series;
    }

    @NonNull
    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_items_in_comics_series, parent, false);
        return new SeriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String title = series.get(position).getTitle();
        final String description = series.get(position).getDescription();
        final String image = series.get(position).getThumbnail().getPath() + ".jpg";

        holder.Name.setText(title);

        Picasso.get().load(image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            return series.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name, subtitle;
            ImageView image;
            ImageButton add_to_fav,share;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                Name = itemView.findViewById(R.id.title);
                subtitle = itemView.findViewById(R.id.subtitle);
                image = itemView.findViewById(R.id.thumbnail);
                add_to_fav = itemView.findViewById(R.id.heart);
                share = itemView.findViewById(R.id.share);

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        SeriesModel seriesModel = series.get(position);
                        Intent share_intent = new Intent(Intent.ACTION_SEND);
                        share_intent.setType("text/plain");
                        share_intent.putExtra(Intent.EXTRA_TITLE, "Marvel Hero");
                        share_intent.putExtra(Intent.EXTRA_SUBJECT, seriesModel.getTitle());
                        share_intent.putExtra(Intent.EXTRA_TEXT, seriesModel.getThumbnail().getPath() + ".jpg");
                        v.getContext().startActivity(Intent.createChooser(share_intent, "Share"));

                    }
                });
            }
        }
    }