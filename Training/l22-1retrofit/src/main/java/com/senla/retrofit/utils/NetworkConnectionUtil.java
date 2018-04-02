package com.senla.retrofit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senla.retrofit.App;
import com.senla.retrofit.R;
import com.senla.retrofit.adapters.DateTypeAdapter;
import com.senla.retrofit.api.NetworkService;
import com.senla.retrofit.models.requests.RequestLogin;
import com.senla.retrofit.models.requests.RequestProfile;
import com.senla.retrofit.models.responses.ResponseLogin;
import com.senla.retrofit.models.responses.ResponseProfile;

import java.io.IOException;
import java.util.Date;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnectionUtil {

    private static final NetworkService serviceInstanse;

    static {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.self.getString(R.string.base_url))
                .addConverterFactory(gsonConverterFactory)
                .build();
        serviceInstanse = retrofit.create(NetworkService.class);
    }

    public static ResponseLogin sendLoginRequest(RequestLogin request) {
        ResponseLogin responseLogin = null;
        try {
            Response<ResponseLogin> response = serviceInstanse.login(request).execute();
            if (response.isSuccessful()) {
                responseLogin = response.body();
            } else {
                response.errorBody().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseLogin;
    }

    public static ResponseProfile sendProfileRequest(RequestProfile request) {
        ResponseProfile responseProfile = null;
        try {
            Response<ResponseProfile> response = serviceInstanse.getProfile(request).execute();
            if (response.isSuccessful()) {
                responseProfile = response.body();
            } else {
                response.errorBody().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseProfile;
    }
}



