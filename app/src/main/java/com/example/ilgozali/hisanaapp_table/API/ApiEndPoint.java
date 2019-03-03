package com.example.ilgozali.hisanaapp_table.API;

import com.example.ilgozali.hisanaapp_table.response.DataTableRespond;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndPoint {
    @GET("pesanan")
    Call<List<DataTableRespond>> getDataTable();

    @FormUrlEncoded
    @POST("pesanan")
    Call<DataTableRespond> postDataTable(@Field("custno") String custno,
                                        @Field("start") String start,
                                        @Field("finish") String finish,
                                        @Field("status") String status);
}