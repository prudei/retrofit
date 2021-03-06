package ru.geekbrains;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.base.enums.CategoryType;
import ru.geekbrains.dto.ErrorBody;
import ru.geekbrains.dto.Product;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.RetrofitUtils;

import javax.xml.ws.Response;

import java.io.DataInput;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTests {
    Integer productId;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUp () {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getCategory())
                .withPrice((int)(Math.random()*1000+1))
                .withTitle(faker.food().ingredient());
    }

    @SneakyThrows
    @Test
    public void postPositiveTest () {
        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();
        productId = response.body().getId();
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    public void postNegativeTest () {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withId(777))
                .execute();
        if (response.body() != null) {
            productId = response.body().getId();
        }
        assertThat(response.code()).isEqualTo(400);
        //ResponseBody resp2 = response.errorBody();
        //ErrorBody errorBod = objectMapper.w
        //assertThat(errorBod.message).isEqualTo("Id must be null for new entity");
    }

    @SneakyThrows
    @Test
    void getAllProductsPositiveTest() {
        retrofit2.Response<ResponseBody> response = productService
                .getAllProducts()
                .execute();
        assertThat(response.isSuccessful()).isTrue();

    }

    /*@AfterEach
    void tearDown () {
        try {
            retrofit2.Response<ResponseBody> response = productService
                    .deleteProduct(productId)
                    .execute();
            assertThat(response.isSuccessful()).isTrue();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
