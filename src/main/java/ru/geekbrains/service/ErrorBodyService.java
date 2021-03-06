package ru.geekbrains.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.dto.Category;

public interface ErrorBodyService {
    @GET("categories/{id}")
    Call<Category> getErrorBody(@Path("id") Integer id);
}
