package com.codehub.marvelheroesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codehub.marvelheroesapp.json.HeroesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<HeroesModel> heroes;
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
                intent.putExtra("subtitle",description);
                intent.putExtra("image",image);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    //ViewHolder creation

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name, subtitle;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subTitle);
            image = itemView.findViewById(R.id.thumbnail);
        }
    }

    //29/5/2020 For Searching...
   /*@Override
   public Filter getFilter() {
       return searchFilter;
   }

  private Filter searchFilter = new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
         List<HeroesModel> filteredList = new ArrayList<>();

         if (constraint == null || constraint.length() == 0){
               filteredList.addAll(heroesListFull);
           } else {
               String filterPattern = constraint.toString().toLowerCase().trim();
              for (HeroesModel item: heroesListFull) {
                   if (item.getName().toLowerCase().contains(filterPattern)){
                      filteredList.add(item);
                   }
               }
           }
           FilterResults results = new FilterResults();
           results.values = filteredList;
           return results;
      }

      @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {
            heroes.clear();
            heroes.addAll((List) results.values);
            notifyDataSetChanged();
       }
   };*/
}
