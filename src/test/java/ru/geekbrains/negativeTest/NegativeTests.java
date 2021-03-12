package ru.geekbrains.negativeTest;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.base.enums.CategoryType;
import ru.geekbrains.dto.Category;
import ru.geekbrains.dto.Product;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.util.DBUtils;
import ru.geekbrains.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class NegativeTests {

    static ProductService productService;
    static CategoryService categoryService;
    static ProductsMapper productsMapper;
    static CategoriesMapper categoriesMapper;
    Product product;
    Faker faker = new Faker();

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        productsMapper = DBUtils.getProductsMapper();
        categoriesMapper = DBUtils.getCategoriesMapper();
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @BeforeEach
    void setUp () {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getCategory())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
    }

        @SneakyThrows
        @Test
        public void postNegativeTest () {
            retrofit2.Response<Product> response = productService
                    .createProduct(product.withId(1))
                    .execute();
            assertThat(response.code()).isEqualTo(400);
            //assertThat(productsMapper.selectByPrimaryKey())
            //ResponseBody resp2 = response.errorBody();
            //ErrorBody errorBod = objectMapper.w
            //assertThat(errorBod.message).isEqualTo("Id must be null for new entity");
        }

    @Test
    public void getCategoryNegativeTest () throws IOException {
        Response<Category> response = categoryService
                .getCategory(0)
                .execute();
        assertThat(response.code()).isEqualTo(404);
        assertThat(categoriesMapper.selectByPrimaryKey(0)).isEqualTo(null);
    }

    @SneakyThrows
    @Test
    void deleteNegativeTest () {
            retrofit2.Response<ResponseBody> response = productService
                    .deleteProduct(0)
                    .execute();
            assertThat(response.code()).isEqualTo(404);
    }

    @SneakyThrows
    @Test
    void putNegativeTest() {
        retrofit2.Response<Product> response = productService
                .modifyProduct(product.withId(null))
                .execute();
        assertThat(response.code()).isEqualTo(400);
    }

    @SneakyThrows
    @Test
    void postProductWithNullCategoryTest() {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withCategoryTitle(null))
                .execute();
        assertThat(response.code()).isEqualTo(403);
        ProductsExample example = new ProductsExample();
        example.createCriteria().andCategory_idEqualTo(null);
        assertThat(productsMapper.countByExample(example)).isEqualTo(0);
    }

    @SneakyThrows
    @Test
    void postProductWithNullTitleTest() {
        retrofit2.Response<Product> response = productService
                .createProduct(product.withCategoryTitle(CategoryType.FOOD.getCategory()).withTitle(null))
                .execute();
        assertThat(response.code()).isEqualTo(403);
        ProductsExample example = new ProductsExample();
        example.createCriteria().andTitleEqualTo(null);
        assertThat(productsMapper.countByExample(example)).isEqualTo(0);
    }
}

