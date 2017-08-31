package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import retrofit2.Retrofit;

public class RestClient {

    protected Retrofit retrofit;

    public <T> T getService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
