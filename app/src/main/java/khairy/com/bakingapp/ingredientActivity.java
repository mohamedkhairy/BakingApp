package khairy.com.bakingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.Adapter.ingredientAdapter;
import khairy.com.bakingapp.Adapter.mainPageAdapter;
import khairy.com.bakingapp.jsonData.Ingredient;
import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.jsonData.Step;

public class ingredientActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ingredientAdapter adapter;
    private LinearLayoutManager layoutManager;
//    static final String ingredient_EXTRA = "ingredients";
//    static final String steps_EXTRA = "steps";
    static final String reviewJson_EXTRA = "reviewJson";

    private Ingredient ingData;
    private Step stepData;
    private ReviewJson reviewJsonData;

    @BindView(R.id.ingredients)
    TextView ingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(ingredientActivity.this);
        Intent intent = getIntent();
        reviewJsonData = (ReviewJson) intent.getSerializableExtra(reviewJson_EXTRA);
//        ingData = (Ingredient) intent.getSerializableExtra(ingredient_EXTRA);
//        stepData = (Step) intent.getSerializableExtra(steps_EXTRA);
//        ingView = (TextView) findViewById(R.id.ingredients);
        setIngredientsView();
        set_view();
    }

    public  void set_view (){
        recyclerView = (RecyclerView) findViewById(R.id.ingredients_recycler_view);
        layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ingredientAdapter(ingredientActivity.this , reviewJsonData);
        recyclerView.setAdapter(adapter);
    }

    public void setIngredientsView(){

//        Handler h = new Handler(this.getMainLooper());
//
//        h.post(new Runnable() {
//            @Override
//            public void run() {
                for (int x = 0; x < reviewJsonData.getIngredients().size(); x++) {
                    ingView.append(reviewJsonData.getIngredients().get(x).getIngredient()+"\n");
                    Log.d("null" , " 33333")  ;
                }
//            }
//        });


    }

}
