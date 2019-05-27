package mustafizurmatin.com.burritoplaces;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class BurritoViewHolder extends RecyclerView.ViewHolder {
    private TextView placeName;
    private TextView address;
    private TextView text;
    public BurritoViewHolder(@NonNull View itemView) {
        super(itemView);
        placeName = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        text = itemView.findViewById(R.id.small_print);
    }

    public void setView(Places.Results place){
        placeName.setText(place.name);
        address.setText(place.formatted_address);
        String rating = "unlisted";
        switch (place.price_level) {
            case 0: rating = "free"; break;
            case 1: rating = "$"; break;
            case 2: rating = "$$"; break;
            case 3: rating = "$$$"; break;
            case 4: rating = "$$$$"; break;
        }
        String type = "";
        if (place.types.size() > 0) {
            type = place.types.get(0);
        }
        String print = rating + " \u2022 " + type;
        text.setText(print);
    }
}
