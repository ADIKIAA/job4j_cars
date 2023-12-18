package ru.job4j.cars.repository;

import ru.job4j.cars.model.OwnershipHistory;

import java.util.List;
import java.util.Optional;

public interface OwnershipHistoryRepository {

    OwnershipHistory save(OwnershipHistory ownershipHistory);

    Optional<OwnershipHistory> findById(int id);

    List<OwnershipHistory> findByCarId(int id);

    List<OwnershipHistory> findByOwnerId(int id);

}
