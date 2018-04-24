package com.example.t186.fragmentcommunication.rest;


import com.example.t186.fragmentcommunication.sqliteentity.Apicall;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by T186 on 4/11/2018.
 */

public interface ApiInterface {
    @GET("example.json")
    Observable<Response<Apicall>> getData();

}
