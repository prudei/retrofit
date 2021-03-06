package ru.geekbrains.positiveTest;

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

public class PostProductTests {
    Integer productId;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    Integer CheckPrice = (int)(Math.random()*1000+1);
    String CheckTitle = faker.food().ingredient();

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUp () {
        product = new Product()
                .withPrice(CheckPrice)
                .withTitle(CheckTitle);
    }

    @SneakyThrows
    @Test
    public void postCheckCreated201FoodTest () {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withCategoryTitle(CategoryType.FOOD.getCategory()))
                .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assertThat(response.body().getCategoryTitle()).isEqualTo(CategoryType.FOOD.getCategory());
        assertThat(response.body().getTitle()).isEqualTo(CheckTitle);
    }

    @SneakyThrows
    @Test
    public void postCheckCreated201ElectronicTest () {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withCategoryTitle(CategoryType.ELECTRONICS.getCategory()))
                .execute();
        productId = response.body().getId();
        assertThat(response.code()).isEqualTo(201);
        assertThat(response.body().getCategoryTitle()).isEqualTo(CategoryType.ELECTRONICS.getCategory());
        assertThat(response.body().getPrice()).isEqualTo(CheckPrice);
    }



    @AfterEach
    void tearDown () {
        try {
            retrofit2.Response<ResponseBody> response = productService
                    .deleteProduct(productId)
                    .execute();
            assertThat(response.isSuccessful()).isTrue();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
