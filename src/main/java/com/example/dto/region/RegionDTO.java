package com.example.dto.region;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDTO {
    private Integer id;
    @NotNull(message = "name uz is empty")
    private String nameUz;
    @NotNull(message = "name ru is empty")
    private String nameRu;
    @NotNull(message = "name en is empty")
    private String nameEn;
}
