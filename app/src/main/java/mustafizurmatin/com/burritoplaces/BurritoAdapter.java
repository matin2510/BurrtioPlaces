package mustafizurmatin.com.burritoplaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BurritoAdapter extends RecyclerView.Adapter<BurritoViewHolder> {
    private List<Places.Results> places = new ArrayList<>();
    @NonNull
    @Override
    public BurritoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.burrito_item, viewGroup, false);
        return new BurritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BurritoViewHolder burritoViewHolder, int i) {
        burritoViewHolder.setView(places.get(i));
        burritoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(burritoViewHolder.itemView.getContext(), BurritoMap.class);
                map.putExtra("latitude", places.get(i).geometry.location.lat);
                map.putExtra("longitude",places.get(i).geometry.location.lng);
                map.putExtra("name",places.get(i).name);
                burritoViewHolder.itemView.getContext().startActivity(map);
            }
        });
    }

    void setPlaces(List<Places.Results> places){
        if (places != null) {
            this.places.clear();
            this.places.addAll(places);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
