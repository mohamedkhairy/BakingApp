package khairy.com.bakingapp.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.R;
import khairy.com.bakingapp.ingredientActivity;
import khairy.com.bakingapp.jsonData.ReviewJson;

public class Ingredients_fragment extends Fragment {

    private static final String Ing_Data = "ingredients_Data";
    @BindView(R.id.ingredients)
    TextView ingView;
    private ReviewJson reviewJson;

    public static Ingredients_fragment Ing_Sitter(ReviewJson rj) {
        Ingredients_fragment ingredients_fragment = new Ingredients_fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Ing_Data, rj);
        ingredients_fragment.setArguments(bundle);
        return ingredients_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this , view);
        setIngredientsView();
        return view;
    }

    public void setIngredientsView(){
        reviewJson = (ReviewJson) getArguments().getSerializable(Ing_Data);
        for (int x = 0; x < reviewJson.getIngredients().size(); x++) {
            ingView.append(reviewJson.getIngredients().get(x).getIngredient()+"\n");
        }
    }
}
