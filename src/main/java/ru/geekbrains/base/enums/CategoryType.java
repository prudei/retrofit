package ru.geekbrains.base.enums;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food"),
    ELECTRONICS(2, "Electronic");

    @Getter
    private final String category;
    @Getter
    private final Integer id;

    CategoryType(int id, String category) {
        this.id = id;
        this.category = category;
    }
}
