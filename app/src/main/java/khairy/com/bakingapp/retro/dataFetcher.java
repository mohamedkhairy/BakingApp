package khairy.com.bakingapp.retro;


import java.util.List;

import khairy.com.bakingapp.jsonData.ReviewJson;
import retrofit2.Call;
import retrofit2.http.GET;

public interface dataFetcher {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<ReviewJson>> getReviewJson ();
}
