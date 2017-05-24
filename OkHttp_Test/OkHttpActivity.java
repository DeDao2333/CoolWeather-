package com.example.java.weatherapps.OkHttp_Test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java.weatherapps.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private Button mRequest;
    private TextView mDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        mDisplay = (TextView) findViewById(R.id.tv_okhttp_display);
        mRequest = (Button) findViewById(R.id.bt_okhttp_request);
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webhttp();
            }
        });
    }

    public void webhttp() {
        HttpUtil.sendOkHttpRequest("http://github.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("wer", response.body().string());
            }
        });
    }

}
