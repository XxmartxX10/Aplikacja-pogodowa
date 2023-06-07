package org.mycode.myweatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WeatherDetails extends AppCompatActivity {

    private ImageView imageTextView;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView feelsLikeTextView;
    private TextView tempMinTextView;
    private TextView tempMaxTextView;
    private TextView pressureTextView;
    private TextView humidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        imageTextView = findViewById(R.id.imageDetails);
        cityTextView = findViewById(R.id.city_);
        tempTextView = findViewById(R.id.temp);
        feelsLikeTextView = findViewById(R.id.feels_like);
        tempMinTextView = findViewById(R.id.temp_min);
        tempMaxTextView = findViewById(R.id.temp_max);
        pressureTextView = findViewById(R.id.pressure);
        humidityTextView = findViewById(R.id.humidity);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        String link = intent.getStringExtra("link");
        String temp = intent.getStringExtra("temp");
        String feelsLike = intent.getStringExtra("feels_like");
        String tempMin = intent.getStringExtra("temp_min");
        String tempMax = intent.getStringExtra("temp_max");
        String pressure = intent.getStringExtra("pressure");
        String humidity = intent.getStringExtra("humidity");

        cityTextView.setText(city);
        tempTextView.setText(temp + "°C");
        feelsLikeTextView.setText(feelsLike + "°C");
        tempMinTextView.setText(tempMin + "°C");
        tempMaxTextView.setText(tempMax + "°C");
        pressureTextView.setText(pressure + "hPa");
        humidityTextView.setText(humidity + "%");

        Glide.with(WeatherDetails.this)
                .load(link)
                .into(imageTextView);

    }
}