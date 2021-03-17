package ru.geekbrains.positiveTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.geekbrains.base.enums.CategoryType;
import ru.geekbrains.dto.Category;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.base.enums.CategoryType.*;


public class GetTests {
    static CategoryService categoryService;
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
        RestAssured.filters(new AllureRestAssured());
    }

    @Epic(value = "Положительные тесты")
    @Feature(value = "GET запрос")
    @Step("Проверка, что категория FOOD имеет id=1")
    @Test
    public void getCategoryPositiveFoodTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(FOOD.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 1").isEqualTo(1);
        assertThat(response.body().getTitle()).isEqualTo(FOOD.getCategory());
    }

    @Epic(value = "Положительные тесты")
    @Feature(value = "GET запрос")
    @Step("Проверка, что категория Electronics имеет id=2")
    @Test
    public void getCategoryPositiveElectronicsTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(ELECTRONICS.getId())
                .execute();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getId()).as("Id is not equal to 2").isEqualTo(2);
        assertThat(response.body().getTitle()).isEqualTo(ELECTRONICS.getCategory());
    }

    @Epic(value = "Положительные тесты")
    @Feature(value = "GET запрос")
    @Step("Проверка, что можно получить все продукты")
    @SneakyThrows
    @Test
    void getAllProductsTest() {
        retrofit2.Response<ResponseBody> response = productService
                .getAllProducts()
                .execute();
        assertThat(response.isSuccessful()).isTrue();
    }
}
