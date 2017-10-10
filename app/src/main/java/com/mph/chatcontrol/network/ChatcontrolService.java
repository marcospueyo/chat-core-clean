package com.mph.chatcontrol.network;
/* Created by macmini on 10/10/2017. */

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChatcontrolService {
    @FormUrlEncoded
    @POST("addMessage")
    Call<ResponseBody> sendMessage(@FieldMap Map<String, String> names);
}
