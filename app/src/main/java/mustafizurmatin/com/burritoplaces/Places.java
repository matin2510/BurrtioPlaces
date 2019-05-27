package mustafizurmatin.com.burritoplaces;

import java.util.List;

class Places {
    Places(){}
    List<Results> results;

    public class Results {
        String formatted_address;
        String vicinity;
        String name;
        List<String> types;
        Geometry geometry;

        class Geometry {
            Local location;
            class Local {
                double lat;
                double lng;
            }
        }
        Opening opening_hours;
        double rating;
        int price_level;

        class Opening {
            boolean open_now;
        }
    }
    String status;


}
