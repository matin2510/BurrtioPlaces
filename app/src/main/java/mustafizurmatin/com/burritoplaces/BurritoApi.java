package mustafizurmatin.com.burritoplaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BurritoApi {

    @GET("json")
    Observable<Places> placesList(@Query("key") String KEY,
                                  @Query("query") String query,
                                  @Query("location") String location,
                                  @Query("radius") int radius
                                  );
}
