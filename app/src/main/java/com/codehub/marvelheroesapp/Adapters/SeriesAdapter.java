package com.codehub.marvelheroesapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
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
import com.codehub.marvelheroesapp.json.CreatorsNameModel;
import com.codehub.marvelheroesapp.json.SeriesModel;
import com.squareup.picasso.Callback;
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
        final String extension = series.get(position).getThumbnail().getExtension();
        final String image = series.get(position).getThumbnail().getPath() + "." + extension;
        final List<CreatorsNameModel> creatorsName = series.get(position).getCreators().getItems();
        Log.i("Creator", creatorsName.toString());
        for (int i = 0; i < creatorsName.size(); i++) {
            position = i;
            if (creatorsName.size() != 0) {
                holder.creator.setText("Creator: " + creatorsName.get(position).getName());
            } else {
                holder.creator.setText("There is no Creator");
            }
        }

        holder.Name.setText(title);

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
            return series.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name, creator;
            ImageView image;
            ImageButton share;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                Name = itemView.findViewById(R.id.title);
                creator = itemView.findViewById(R.id.creatorName);
                image = itemView.findViewById(R.id.thumbnail);
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