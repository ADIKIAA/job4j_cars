package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public Owner create(Owner owner) {
        return ownerRepository.create(owner);
    }

    public void update(Owner owner) {
        ownerRepository.update(owner);
    }

    public void delete(int id) {
        ownerRepository.delete(id);
    }

    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

}
