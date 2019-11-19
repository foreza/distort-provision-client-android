package com.vartyr.distort_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        String tempProvisionText;


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


    public String utility_fetchSessionIDFromInput(){
        EditText editText = findViewById(R.id.inputProvisioningSessionID);
        return editText.getText().toString();
    }

    public void utility_setTextToSharedSecret(String s){
        TextView tv = findViewById(R.id.provisionPasswordText);
        tv.setText(s);
    }

    public void utility_clearTextToSharedScret(){
        TextView tv = findViewById(R.id.provisionPasswordText);
        tv.setText("");
    }


    public void getUpdate(View view){

        // TODO: Support the proper pin implementation
        if (utility_fetchSessionIDFromInput() != "") {

            Call call = apiService.getDistortSession(utility_fetchSessionIDFromInput());

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Log.d("API", "onResponse");
                    if (response.body() != null) {
                        Log.d("API", "Response acquired: ");
                        tempProvisionText = ((DistortSessionModel) response.body()).broadcastText;
                        utility_setTextToSharedSecret(tempProvisionText);
                    }


                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d("API", "onFailure with: " + t.getLocalizedMessage());
                }
            });

        }
    }



    public void addToClipboardAndOpenWifi(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("provision", tempProvisionText);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"Added provision text to clipboard: " + tempProvisionText,Toast.LENGTH_SHORT);
        utility_clearTextToSharedScret();

        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));


    }


}
