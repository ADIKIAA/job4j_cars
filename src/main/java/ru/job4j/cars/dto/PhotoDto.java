package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoDto {

    private String path;

    private byte[] content;

}
