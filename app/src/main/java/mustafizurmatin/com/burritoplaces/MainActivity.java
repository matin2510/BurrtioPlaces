package mustafizurmatin.com.burritoplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    PlacesRepository repository;
    String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        BurritoAdapter adapter = new BurritoAdapter();
        recyclerView.setAdapter(adapter);
        PlacesCallback callback = new PlacesCallback() {
            @Override
            public void onSuccess(Places places) {
                Log.d(TAG, "onSuccess: im here" + places.results.get(0).name);
                adapter.setPlaces(places.results);
            }

            @Override
            public void onFailure() {
                showToast();
            }
        };

        repository = new PlacesRepository(this,callback);




    }

    public void showToast(){

        Toast.makeText(this, "Something when wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.dispose();
    }
}
