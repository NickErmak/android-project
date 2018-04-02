package com.senla.gson_bolts.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senla.gson_bolts.App;
import com.senla.gson_bolts.R;
import com.senla.gson_bolts.adapters.DateTypeAdapter;

import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnectionUtil {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String url = App.self.getString(R.string.format_url);

    public static <REQ, RESP> RESP sendRequest(String method, REQ reqModel, final Class<RESP> respClass) throws IOException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(reqModel));
        Request request = new Request.Builder()
                .url(String.format(url, method))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return gson.fromJson(response.body().string(), respClass);
    }
}



