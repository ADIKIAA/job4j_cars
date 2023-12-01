package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {

    private static SessionFactory sessionFactory;

    private static CarRepository carRepository;

    private static final Engine ENGINE = new Engine();

    private static final User USER = new User();

    private static final Owner OWNER = new Owner();

    @BeforeAll
    public static void setup() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            CrudRepository crudRepository = new CrudRepository(sessionFactory);
            carRepository = new CarRepository(crudRepository);
            EngineRepository engineRepository = new EngineRepository(crudRepository);
            OwnerRepository ownerRepository = new OwnerRepository(crudRepository);
            UserRepository userRepository = new UserRepository(crudRepository);

            ENGINE.setName("engine");
            engineRepository.create(ENGINE);

            USER.setLogin("test_login");
            USER.setPassword("password");
            userRepository.create(USER);

            OWNER.setName("owner");
            OWNER.setUser(USER);
            ownerRepository.create(OWNER);
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @AfterAll
    public static void tear() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from Owner").executeUpdate();
        session.createQuery("DELETE from Engine").executeUpdate();
        session.createQuery("DELETE from User").executeUpdate();
        session.createQuery("DELETE from Photo").executeUpdate();
        session.getTransaction().commit();
        sessionFactory.close();
    }

    @AfterEach
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from Car").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void saveCarThenFindIt() {
        Car car = new Car();
        car.setName("testName");
        car.setEngine(ENGINE);
        car.setOwner(OWNER);

        carRepository.create(car);
        var rsl = carRepository.findById(car.getId()).get();

        assertThat(rsl).isEqualTo(car);
    }

    @Test
    public void saveSomeCarThenDeleteOneAndFindHim() {
        Car car1 = new Car();
        car1.setName("testName_1");
        car1.setEngine(ENGINE);
        car1.setOwner(OWNER);
        Car car2 = new Car();
        car2.setName("testName_2");
        car2.setEngine(ENGINE);
        car2.setOwner(OWNER);

        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.delete(car1.getId());
        var rsl1 = carRepository.findById(car1.getId());
        var rsl2 = carRepository.findById(car2.getId()).get();

        assertThat(rsl1).isEmpty();
        assertThat(rsl2).isEqualTo(car2);
    }

    @Test
    public void saveSomeCarThenUpdateOneAndFindHim() {
        Car car = new Car();
        car.setName("testName");
        car.setEngine(ENGINE);
        car.setOwner(OWNER);
        String newName = "newName";

        carRepository.create(car);

        car.setName(newName);
        carRepository.update(car);

        var updatedCar = carRepository.findById(car.getId()).get();

        assertThat(updatedCar.getName()).isEqualTo(newName);
    }

}