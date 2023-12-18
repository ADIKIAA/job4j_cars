package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.Optional;

public interface OwnerService {

    Owner create(Owner owner);

    void update(Owner owner);

    void delete(int id);

    Optional<Owner> findById(int id);

}
