package com.vartyr.distort_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

        Retrofit retrofit;
        APIService apiService;
        EndpointConfigs configs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initializeAPI();


    }

    public void initializeAPI(){

        configs = new EndpointConfigs();

        retrofit = new Retrofit.Builder()
                .baseUrl(configs.getTestUrlEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // create an instance of the ApiService
        apiService = retrofit.create(APIService.class);


    }



    public void getUpdate(View view){

        Call call = apiService.getDistortSession("1234");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("API", "onResponse");
                if (response.body() != null){
                    Log.d("API", "Response string: "+((DistortSessionModel) response.body()).broadcastText);

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("API", "onFailure with: " + t.getLocalizedMessage());
            }
        });
    }


}
