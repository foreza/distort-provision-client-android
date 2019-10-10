package com.vartyr.distort_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("api/distort/{uid}")
    Call<DistortSessionModel> getDistortSession(@Path("uid") String uid);

    // TODO: Remove or make use of this
    @GET("api/distort/")
    Call<List<DistortSessionModel>> getAllDistortSession();

}

