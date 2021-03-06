package ru.geekbrains.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
    public class ErrorBody {

        @JsonProperty("status")
        public Integer status;
        @JsonProperty("message")
        public String message;
        @JsonProperty("timestamp")
        public String timestamp;

    }

