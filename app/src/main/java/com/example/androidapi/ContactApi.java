package com.example.androidapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ContactApi {
    @GET("/contacts/{id}")
    Call<Contact> getByID(@Path("id") int id);

    @GET("/contacts")
    Call<List<Contact>> getAll();

    @POST("/contacts")
    Call<Contact>save(@Body Contact contact);

    @DELETE("/contacts/{id}")
    Call<Contact> delete(@Path("id") int id);
}
