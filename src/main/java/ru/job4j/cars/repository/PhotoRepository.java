package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PhotoRepository {

    private final CrudRepository crudRepository;

    public Photo save(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    public void delete(int id) {
        crudRepository.run(
                "DELETE from Photo WHERE id = :id",
                Map.of("id", id)
        );
    }

    public Optional<Photo> findById(int id) {
        return crudRepository.optional(
                "from Photo WHERE id = :id",
                Photo.class,
                Map.of("id", id)
        );
    }

}
