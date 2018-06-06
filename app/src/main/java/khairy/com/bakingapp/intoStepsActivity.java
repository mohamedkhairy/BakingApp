package khairy.com.bakingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import khairy.com.bakingapp.fragment.IntoSteps_fragment;
import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.widget.WidgetService;

public class intoStepsActivity extends AppCompatActivity {

    public static final String stepNUM = "position";
    public static final String jsonData = "json";
    private ReviewJson reviewJsonData;
    private int place;
    private Boolean check = false;
    Fragment intoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_into_steps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        place = intent.getIntExtra(stepNUM , 0);
        reviewJsonData = (ReviewJson) intent.getSerializableExtra(jsonData);

        if (savedInstanceState == null) {
            intoFragment(place);
        } else {
            intoFragment = (IntoSteps_fragment) getSupportFragmentManager()
                    .findFragmentByTag(IntoSteps_fragment.class.getSimpleName());
        }

    }

    public void intoFragment(int position){
        check = true;
        IntoSteps_fragment intoStepsFragment = new IntoSteps_fragment();
         intoFragment = intoStepsFragment.information_Sitter(reviewJsonData , position);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.step_container , intoFragment , IntoSteps_fragment.class.getSimpleName()).commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.empty:
                prefrance.saveData(intoStepsActivity.this, reviewJsonData);
                WidgetService.updateWidget(intoStepsActivity.this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
