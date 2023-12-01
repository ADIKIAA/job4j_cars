package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.OwnershipHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnershipHistoryRepository {

    private final CrudRepository crudRepository;

    public OwnershipHistory save(OwnershipHistory ownershipHistory) {
        crudRepository.run(session -> session.persist(ownershipHistory));
        return ownershipHistory;
    }

    public Optional<OwnershipHistory> findById(int id) {
        return crudRepository.optional(
                "from OwnershipHistory WHERE id = :id",
                OwnershipHistory.class,
                Map.of("id", id)
        );
    }

    public List<OwnershipHistory> findByCarId(int id) {
        return crudRepository.query(
                "from OwnershipHistory WHERE car_id = id",
                OwnershipHistory.class,
                Map.of("id", id)
        );
    }

    public List<OwnershipHistory> findByOwnerId(int id) {
        return crudRepository.query(
                "from OwnershipHistory WHERE owner_id = id",
                OwnershipHistory.class,
                Map.of("id", id)
        );
    }

}
