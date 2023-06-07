package org.mycode.myweatherapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private CardView cardView;
    private EditText cityEditText;
    private Button searchButton;
    private TextView header;
    private TextView temperature;
    private ImageView myImageView;
    private MainAPIService mainAPIService;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = findViewById(R.id.cardView);
        cityEditText = findViewById(R.id.edit_text_city);
        searchButton = findViewById(R.id.button_search);
        header = findViewById(R.id.headerText);
        temperature = findViewById(R.id.temperature);
        myImageView = findViewById(R.id.myImageView);
        mainAPIService = MainAPIFactory.createAPIService();
        intent = new Intent(MainActivity.this, WeatherDetails.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEditText.getText().toString().trim();
                if (!city.isEmpty()) {
                    getWeather(city);
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void getWeather(String city) {
        mainAPIService.getWeather(city, "f825344b0cf0672c689378549f9868db")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            header.setText(response.body().getCityName());
                            double number_temp = response.body().getMain().getTemp() - 273.15;
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            double rounded = Double.parseDouble(decimalFormat.format(number_temp));
                            String temp = rounded + "°C";
                            temperature.setText(temp);
                            String icon = response.body().getWeather().get(0).getIcon();
                            String link = "http://openweathermap.org/img/w/"+icon+".png";
                            Glide.with(MainActivity.this)
                                    .load(link)
                                    .into(myImageView);
                            cardView.setVisibility(View.VISIBLE);
                            intent.putExtra("city", response.body().getCityName());
                            intent.putExtra("temp", String.valueOf(Double.parseDouble(decimalFormat.format(response.body().getMain().getTemp() - 273.15))));
                            intent.putExtra("feels_like", String.valueOf(Double.parseDouble(decimalFormat.format(response.body().getMain().getFeelsLike() - 273.15))));
                            intent.putExtra("temp_min", String.valueOf(Double.parseDouble(decimalFormat.format(response.body().getMain().getTempMin() - 273.15))));
                            intent.putExtra("temp_max", String.valueOf(Double.parseDouble(decimalFormat.format(response.body().getMain().getTempMax() - 273.15))));
                            intent.putExtra("pressure", String.valueOf(response.body().getMain().getPressure()));
                            intent.putExtra("humidity", String.valueOf(response.body().getMain().getHumidity()));
                            intent.putExtra("link", link);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.v("ERROR", t.getMessage());
                    }
                });
    }
}
