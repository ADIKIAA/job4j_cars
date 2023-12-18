package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import static org.assertj.core.api.Assertions.*;

class EngineRepositoryTest {

    private static SessionFactory sessionFactory;

    private static CrudRepository crudRepository;

    private static EngineRepository engineRepository;

    @BeforeAll
    public static void setup() {
        try {

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            crudRepository = new CrudRepository(sessionFactory);
            engineRepository = new HbmEngineRepository(crudRepository);

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @AfterAll
    public static void tear() {
        sessionFactory.close();
    }

    @AfterEach
    public void deleteAll() {
        var engines = crudRepository.query(
                "from Engine",
                Engine.class
        );
        for (Engine engine : engines) {
            engineRepository.delete(engine.getId());
        }
    }

    @Test
    public void createEngineThenFindHimById() {
        Engine engine = new Engine();
        String name = "testName";
        engine.setName(name);

        Engine savedEngine = engineRepository.create(engine);
        var rsl = engineRepository.findById(savedEngine.getId()).get();

        assertThat(rsl.getName()).isEqualTo(name);
    }

    @Test
    public void saveSomeEngineThenDeleteOneAndFindHimById() {
        Engine engine1 = new Engine();
        engine1.setName("testName1");
        Engine engine2 = new Engine();
        engine1.setName("testName2");
        Engine engine3 = new Engine();
        engine1.setName("testName3");

        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.create(engine3);
        engineRepository.delete(engine2.getId());
        var rsl = engineRepository.findById(engine3.getId()).get();

        assertThat(engineRepository.findById(engine2.getId())).isEmpty();
        assertThat(rsl).isEqualTo(engine3);
    }

}