package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    public void delete(int id) {
        crudRepository.run(
                "DElETE from Car WHERE id = :id",
                Map.of("id", id)
        );
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car WHERE id = :id",
                Car.class,
                Map.of("id", id)
        );
    }

}
