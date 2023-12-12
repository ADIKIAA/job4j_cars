package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EngineService {

    private final EngineRepository engineRepository;

    public Engine create(Engine engine) {
        return engineRepository.create(engine);
    }

    public void update(Engine engine) {
        engineRepository.update(engine);
    }

    public void delete(int id) {
        engineRepository.delete(id);
    }

    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

}
