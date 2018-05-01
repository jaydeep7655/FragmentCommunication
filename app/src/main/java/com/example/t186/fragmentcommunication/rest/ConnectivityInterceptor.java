package com.example.t186.fragmentcommunication.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.t186.fragmentcommunication.utility.UDF;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by T186 on 4/11/2018.
 */

public class ConnectivityInterceptor implements Interceptor {
    private Context context;
    ConnectivityInterceptor(Context context) {
        this.context = context;
    }
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        boolean isNetworkActive = UDF.isOnline(context);
        if (!isNetworkActive) {
            throw new NoConnectivityException();
        } else {
            return chain.proceed(chain.request());
        }
    }
    public static class NoConnectivityException extends IOException {
        @Override
        public String getMessage() {
            return "No network available, please check your WiFi or Data connection";
        }
    }
}
