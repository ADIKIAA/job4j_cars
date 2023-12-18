package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleEngineServiceTest {

    private EngineService engineService;

    private EngineRepository engineRepository;

    @BeforeEach
    public void initRepository() {
        engineRepository = mock(EngineRepository.class);
        engineService = new SimpleEngineService(engineRepository);
    }

    @Test
    public void createEngineThenCheckResult() {
        Engine engine = new Engine();
        engine.setId(1);
        engine.setName("name");
        when(engineRepository.create(any())).thenReturn(engine);

        var rsl = engineService.create(new Engine());

        assertThat(rsl).usingRecursiveComparison().isEqualTo(engine);
    }

    @Test
    public void saveEngineThenFindById() {
        Engine engine = new Engine();
        engine.setId(1);
        engine.setName("name");
        when(engineRepository.create(any())).thenReturn(engine);
        when(engineRepository.findById(anyInt())).thenReturn(Optional.of(engine));

        var savedEngine = engineService.create(new Engine());
        var findEngine = engineService.findById(1);

        assertThat(engine).usingRecursiveComparison().isEqualTo(savedEngine);
        assertThat(findEngine).isPresent().isEqualTo(Optional.of(engine));
    }

}