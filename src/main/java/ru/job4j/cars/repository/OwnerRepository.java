package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository crudRepository;

    public Owner create(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    public void update(Owner owner) {
        crudRepository.run(session -> session.update(owner));
    }

    public void delete(int id) {
        crudRepository.run(
                "DELETE from Owner WHERE id = :id",
                Map.of("id", id)
        );
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner WHERE id = :id",
                Owner.class,
                Map.of("id", id)
        );
    }

}
