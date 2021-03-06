package ru.geekbrains;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.geekbrains.base.enums.CategoryType;
import ru.geekbrains.dto.Category;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.base.enums.CategoryType.*;


public class CategoryTests {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    public void getCategoryPositiveFoodTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(FOOD.getId())
                .execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getId()).as("Id is not equal to 1").isEqualTo(1);
        assertThat(response.body().getTitle()).isEqualTo(FOOD.getCategory());
    }

    @Test
    public void getCategoryPositiveElectronicsTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(ELECTRONICS.getId())
                .execute();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getId()).as("Id is not equal to 2").isEqualTo(2);
        assertThat(response.body().getTitle()).isEqualTo(ELECTRONICS.getCategory());
    }

    @Test
    public void getCategoryNegativeTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(3)
                .execute();
        assertThat(response.code()).isEqualTo(404);
    }



}
