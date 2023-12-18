package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCarServiceTest {

    private CarService carService;

    private CarRepository carRepository;

    @BeforeEach
    public void initRepository() {
        carRepository = mock(CarRepository.class);
        carService = new SimpleCarService(carRepository);
    }

    @Test
    public void saveCarThenCheckResult() {
        Car car = new Car();
        car.setId(1);
        car.setModel("model");
        when(carRepository.create(any())).thenReturn(car);

        var rsl = carService.create(new Car());

        assertThat(rsl).usingRecursiveComparison().isEqualTo(car);
    }

    @Test
    public void saveCarThenFindById() {
        Car car = new Car();
        car.setId(5);
        car.setModel("model");
        when(carRepository.create(any())).thenReturn(car);
        when(carRepository.findById(anyInt())).thenReturn(Optional.of(car));

        var savedCar = carService.create(new Car());
        var findCar = carService.findById(5);

        assertThat(savedCar).usingRecursiveComparison().isEqualTo(car);
        assertThat(findCar).isPresent().isEqualTo(Optional.of(car));
    }

    @Test
    public void findCarWithPhoto() {
        Car car = new Car();
        car.setId(5);
        car.setModel("model");
        Photo photo = mock(Photo.class);
        car.setPhoto(List.of(photo));
        when(carRepository.getCarWithPhoto(anyInt())).thenReturn(car);

        var rsl = carService.getCarWithPhoto(1);

        assertThat(rsl).usingRecursiveComparison().isEqualTo(car);
    }

}