package ru.job4j.cars.service;

import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;

import java.util.Optional;

public interface PhotoService {

    Photo save(PhotoDto photoDto);

    Optional<PhotoDto> findById(int id);

    void delete(int id);

}
