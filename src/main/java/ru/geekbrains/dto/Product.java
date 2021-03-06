package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


    @Data
    @With
    @AllArgsConstructor
@NoArgsConstructor
    public class Product {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("categoryTitle")
        private String categoryTitle;

    }
