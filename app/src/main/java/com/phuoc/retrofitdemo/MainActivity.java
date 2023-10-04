package com.phuoc.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phuoc.retrofitdemo.R;
import com.phuoc.retrofitdemo.api.ApiService;
import com.phuoc.retrofitdemo.model.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvTerms, tvSource, tvUsdVnd;
    Button btnCallApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSource = findViewById(R.id.tv_sources);
        tvTerms = findViewById(R.id.tv_terms);
        tvUsdVnd = findViewById(R.id.tv_vnd);
        btnCallApi = findViewById(R.id.btn_call_api);

        btnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi();
            }
        });
    }

    private void clickCallApi() {
        // http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
        ApiService.apiService.convertUsdToVnd("843d4d34ae72b3882e3db642c51e28e6",
                "VND", "USD", 1).enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                Currency currency = response.body();
                if (currency != null && currency.isSuccess()) {
                    tvTerms.setText(currency.getTerms());
                    tvSource.setText(currency.getSource());
                    tvUsdVnd.setText(String.valueOf(currency.getQuotes().getUsdVnd()));
                }
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Loi!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}