package com.senla.retrofit.api;

import com.senla.retrofit.models.requests.RequestLogin;
import com.senla.retrofit.models.requests.RequestProfile;
import com.senla.retrofit.models.responses.ResponseLogin;
import com.senla.retrofit.models.responses.ResponseProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("lesson-21.php?method=login")
    Call<ResponseLogin> login(@Body RequestLogin request);

    @POST("lesson-21.php?method=profile")
    Call<ResponseProfile> getProfile(@Body RequestProfile request);
}
