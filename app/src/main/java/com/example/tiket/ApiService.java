package com.example.tiket;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("insert_tiket.php")
    Call<Void> insertTiket(@Body Tiket tiket);
    @GET("get_tiket.php")
    Call<List <Tiket>> getTiket();

    Call<Void> updateTiket(Tiket tiket);

    Call<Void> deleteTiket(int userId);
}