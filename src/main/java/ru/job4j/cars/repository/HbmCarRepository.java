package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DElETE from Car WHERE id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car WHERE id = :id",
                Car.class,
                Map.of("id", id)
        );
    }

    @Override
    public Car getCarWithPhoto(int id) {
        return crudRepository.optional("""
                SELECT c FROM Car c
                LEFT JOIN FETCH c.photo
                WHERE c.id = :id
                """,
                Car.class,
                Map.of("id", id)
        ).get();
    }

}
