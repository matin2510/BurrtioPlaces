package mustafizurmatin.com.burritoplaces;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesRepository {
    private String KEY;
    private Location local;
    private LocationManager locationManager;
    private BurritoApi burritoApi;
    private CompositeDisposable disposable = new CompositeDisposable();
    private String TAG = PlacesRepository.class.getSimpleName();

    public PlacesRepository(MainActivity context, PlacesCallback callback){
        setKEY(context.getResources().getString(R.string.APP_KEY));
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        String URL = "https://maps.googleapis.com/maps/api/place/textsearch/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        burritoApi = retrofit.create(BurritoApi.class);
        getLocationPermission(context, callback);


    }

    private void getBurritoPlaces(final PlacesCallback callback) {
        int radius = 80467;
        disposable.add(burritoApi.placesList(KEY,"Burrito",local.getLatitude()+"," + local.getLongitude(), radius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(callback::onSuccess, Throwable::printStackTrace));
    }

    private void getLocationPermission(MainActivity context, PlacesCallback callback){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            final LocationListener mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    local = location;
                    Log.d(TAG, "onLocationChanged: im here" + local.getLongitude());
                    getBurritoPlaces(callback);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7,
                    10, mLocationListener);

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            this.getBurritoPlaces(callback);
        }

    }


    public String getKEY() {
        return KEY;
    }

    private void setKEY(String KEY) {
        this.KEY = KEY;
    }
    public void dispose(){
        disposable.dispose();
    }

}
