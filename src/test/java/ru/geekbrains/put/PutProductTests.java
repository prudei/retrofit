package ru.geekbrains.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.base.enums.CategoryType;
import ru.geekbrains.dto.Product;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class PutProductTests {
    Integer productId;
    Faker faker = new Faker();
    static ProductService productService;
    Product product;
    Product product2;

    String CheckTitle = faker.food().vegetable();
    Integer CheckPrice = ((int) Math.random()*100+1);

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @BeforeEach
    void setUp () {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getCategory())
                .withPrice((int)(Math.random()*1000+1))
                .withTitle(faker.food().ingredient());
        retrofit2.Response<Product> response = productService
                .createProduct(product)
                .execute();

        productId = response.body().getId();
        product2 = new Product()
                .withId(productId)
                .withPrice(CheckPrice)
                .withTitle(CheckTitle)
                .withCategoryTitle(CategoryType.ELECTRONICS.getCategory());
        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void putPositiveTest() {
    retrofit2.Response<Product> response = productService
        .modifyProduct(product2)
        .execute();
    assertThat(response.isSuccessful()).isTrue();
    assertThat(response.body().getCategoryTitle()).isEqualTo(CategoryType.ELECTRONICS.getCategory());
    assertThat(response.body().getPrice()).isEqualTo(CheckPrice);
    assertThat(response.body().getTitle()).isEqualTo(CheckTitle);
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
