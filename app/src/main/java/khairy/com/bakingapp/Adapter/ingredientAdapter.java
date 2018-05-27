package khairy.com.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.R;
import khairy.com.bakingapp.imgPreview;
import khairy.com.bakingapp.jsonData.ReviewJson;

public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.cardHolder> {



    private Context context;
    private ReviewJson reviewJson;
    private StringBuffer stringBuffer;
    public ingredientAdapter(Context cont , ReviewJson rJ) {

        this.context = cont;
        this.reviewJson = rJ;
    }



    @NonNull
    @Override
    public ingredientAdapter.cardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int mainPageLayout = R.layout.ingredients_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(mainPageLayout , parent , false);
        ingredientAdapter.cardHolder holder = new ingredientAdapter.cardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientAdapter.cardHolder holder, int position) {
//        stringBuffer.append(reviewJson.getIngredients().get(position).getIngredient());
//        stringBuffer.append(reviewJson.getIngredients().get(position).getMeasure());
//        stringBuffer.append(reviewJson.getIngredients().get(position).getQuantity());
//         holder.ingText.append();
         holder.stepsNum.setText(reviewJson.getSteps().get(position).getId().toString()+"-");
         holder.stepsText.setText(reviewJson.getSteps().get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        int count = reviewJson.getSteps().size();
        return count;
    }

    public class cardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.stepsNUM)
        TextView stepsNum;
        @BindView(R.id.steps)
        TextView stepsText;


        public cardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
