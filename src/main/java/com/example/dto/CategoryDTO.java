package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    @NotNull(message = "name uz is empty")
    private String nameUz;
    @NotNull(message = "name ru is empty")
    private String nameRu;
    @NotNull(message = "name en is empty")
    private String nameEn;
}
