package khairy.com.bakingapp.Adapter;

import android.content.Context;
import android.media.Image;
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
import butterknife.OnClick;
import khairy.com.bakingapp.R;
import khairy.com.bakingapp.imgPreview;
import khairy.com.bakingapp.jsonData.ReviewJson;

public class mainPageAdapter extends RecyclerView.Adapter<mainPageAdapter.cardHolder>{

    private List<ReviewJson> reviewJson;
    private imgPreview imgPreview = new imgPreview();
    private Context context;
    final private card_onclickHandler CardClickHandler ;


    public mainPageAdapter(Context cont , card_onclickHandler chandler ,  List<ReviewJson> rj) {
        this.reviewJson = rj;
        this.context = cont;
        this.CardClickHandler = chandler;
    }




    public interface card_onclickHandler{
        void card_handler(int position);
    }

    @NonNull
    @Override
    public cardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int mainPageLayout = R.layout.main_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(mainPageLayout , parent , false);
        cardHolder holder = new cardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull cardHolder holder, int position) {
        holder.mainName.setText(reviewJson.get(position).getName());
        Glide.with(context).load(imgPreview.getImg().get(position)).thumbnail(0.5f).into(holder.preview);
    }

    @Override
    public int getItemCount() {
        return reviewJson.size();
    }

    public class cardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.cover)
        ImageView preview;
        @BindView(R.id.name)
        TextView mainName;

        public cardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            CardClickHandler.card_handler(position);
        }
    }
}
