package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


    @Data
    public class Category {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("products")
        private List<Product> products = new ArrayList<Product>();

    }

