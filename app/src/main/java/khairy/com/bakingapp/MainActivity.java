package khairy.com.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import khairy.com.bakingapp.Adapter.mainPageAdapter;
import khairy.com.bakingapp.jsonData.Ingredient;
import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.jsonData.Step;
import khairy.com.bakingapp.retro.dataFetcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements mainPageAdapter.card_onclickHandler {



    private String URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofit;
    private ReviewJson reviewJson;
    private RecyclerView recyclerView;
    private mainPageAdapter adapter;
    private GridLayoutManager layoutManager;
    private Ingredient ingredientData;
    private Step spepsData;
    List<ReviewJson> reviewJsons_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMainPageData();
    }

    public void getMainPageData(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataFetcher apiFetcher = retrofit.create(dataFetcher.class);
        Call<List<ReviewJson>> call = apiFetcher.getReviewJson();

        call.enqueue(new Callback<List<ReviewJson>>() {

            @Override
            public void onResponse(Call<List<ReviewJson>> call, Response<List<ReviewJson>> response) {
                reviewJsons_List = response.body();
                set_view();
            }

            @Override
            public void onFailure(Call<List<ReviewJson>> call, Throwable t) {
                Toast.makeText(MainActivity.this , t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                Log.d("MAIN" , t.getMessage() + "\n" + t.getLocalizedMessage());
                t.getStackTrace();
            }
        });
    }

    public  void set_view (){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(MainActivity.this , 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new mainPageAdapter(MainActivity.this , this , reviewJsons_List);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void card_handler(int position) {
        Intent intent = new Intent(MainActivity.this , ingredientActivity.class);
        reviewJson = reviewJsons_List.get(position);

        intent.putExtra(ingredientActivity.reviewJson_EXTRA ,reviewJson);
        startActivity(intent);
    }
}
