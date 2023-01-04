package de.markusfisch.android.barcodescannerviewdemo.activity.service;

import de.markusfisch.android.barcodescannerviewdemo.activity.api.Scanner_API;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static Retrofit getRetrofit()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://eticketing20221126151145.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static Scanner_API getTicketApiService()
    {
        Scanner_API barcode_api = getRetrofit().create( Scanner_API.class);
        return barcode_api;
    }
}
