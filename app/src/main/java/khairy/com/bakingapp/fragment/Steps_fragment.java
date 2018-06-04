package khairy.com.bakingapp.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import khairy.com.bakingapp.Adapter.ingredientAdapter;
import khairy.com.bakingapp.R;
import khairy.com.bakingapp.ingredientActivity;
import khairy.com.bakingapp.intoStepsActivity;
import khairy.com.bakingapp.jsonData.ReviewJson;

import static android.view.View.INVISIBLE;

public class Steps_fragment extends Fragment implements ingredientAdapter.Step_Selected {

    public static final String Bundle_rjData = "rjData";
    private RecyclerView recyclerView;
    private ingredientAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ReviewJson reviewJsonData;



    public static Steps_fragment fragment_Sitter(ReviewJson rj) {
            Steps_fragment stepsFragment = new Steps_fragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Bundle_rjData, rj);
            stepsFragment.setArguments(bundle);
            return stepsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        reviewJsonData = (ReviewJson) getArguments().getSerializable(Bundle_rjData);
        recyclerView = (RecyclerView) inflater.inflate(R.layout.ingred_rv, container, false);
        recyclerView = set_steps_fragment();

        return recyclerView;
    }

    public  RecyclerView set_steps_fragment (){

        layoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ingredientAdapter(getContext() , reviewJsonData , this);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }


    @Override
    public void steps_handler(int position) {

        FrameLayout fragmentItemDetail = (FrameLayout) getActivity().findViewById(R.id.flDetailContainer);
        if (fragmentItemDetail != null) {
            determinePaneLayout(true , position);
        }
        else {
            determinePaneLayout(false , position);
        }
    }

    private void determinePaneLayout(boolean check , int p) {

        if(check){
            IntoSteps_fragment intoStepsFragment = new IntoSteps_fragment();
            Fragment intoFragment = intoStepsFragment.information_Sitter(reviewJsonData , p);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer , intoFragment , IntoSteps_fragment.class.getSimpleName())
                    .addToBackStack(null).commit();

        }else {
            Intent intent = new Intent(getContext() , intoStepsActivity.class);
            intent.putExtra(intoStepsActivity.jsonData , reviewJsonData);
            intent.putExtra(intoStepsActivity.stepNUM , p);
            startActivity(intent);
        }

    }


}
