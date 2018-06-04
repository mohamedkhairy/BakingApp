package khairy.com.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.Adapter.ingredientAdapter;
import khairy.com.bakingapp.Adapter.mainPageAdapter;
import khairy.com.bakingapp.fragment.Ingredients_fragment;
import khairy.com.bakingapp.fragment.Steps_fragment;
import khairy.com.bakingapp.jsonData.Ingredient;
import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.jsonData.Step;
import khairy.com.bakingapp.widget.IngredientsWidget;
import khairy.com.bakingapp.widget.WidgetService;

public class ingredientActivity extends AppCompatActivity {




    static final String reviewJson_EXTRA = "reviewJson";


    private ReviewJson reviewJsonData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(ingredientActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        reviewJsonData = (ReviewJson) intent.getSerializableExtra(reviewJson_EXTRA);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment Sfragment = Steps_fragment.fragment_Sitter(reviewJsonData);
        Fragment Ifragment = Ingredients_fragment.Ing_Sitter(reviewJsonData);

        fragmentTransaction.replace(R.id.ingred_placeholder, Ifragment, Ingredients_fragment.class.getSimpleName());
        fragmentTransaction.replace(R.id.your_placeholder, Sfragment, Steps_fragment.class.getSimpleName());
        fragmentTransaction.commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_widegt , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.empty:
                prefrance.saveData(ingredientActivity.this, reviewJsonData);
                WidgetService.updateWidget(ingredientActivity.this);
                Toast.makeText(ingredientActivity.this , "added to your widget" , Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

}
