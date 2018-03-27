package com.senla.server_client.utils;

import android.net.Uri;

import com.senla.server_client.App;
import com.senla.server_client.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerConnectionUtil {

    public static Response sendRequest(String param) throws InterruptedException, IOException {
        Request request = new Request.Builder()
                .url(App.self.getString(R.string.url) + encodeText(param))
                .build();
        TimeUnit.SECONDS.sleep(3);
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    private static String encodeText(String src) {
       return Uri.encode(src);
    }
}
