package org.mycode.myweatherapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainAPIFactory {

    public static MainAPIService createAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MainAPIService.class);
    }
}
