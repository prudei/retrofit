package ru.geekbrains.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.geekbrains.dto.Product;

public interface ProductService {

    @GET("products")
    Call<ResponseBody> getAllProducts();

    @POST("products")
    Call<Product> createProduct (@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct (@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct (@Body Product createProductRequest);
}
