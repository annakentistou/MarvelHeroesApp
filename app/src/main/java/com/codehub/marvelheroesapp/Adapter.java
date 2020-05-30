package com.codehub.marvelheroesapp;

import android.content.Context;
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

    LayoutInflater inflater;
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

        holder.Name.setText(heroes.get(position).getName());
        holder.subtitle.setText(heroes.get(position).getDescription());
        Picasso.get().load(heroes.get(position).getThumbnail().getPath() + ".jpg").into(holder.image);

    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    //ViewHolder creation

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, subtitle;
        ImageView image;

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
