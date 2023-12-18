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
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.*;

class OwnerRepositoryTest {

    private static SessionFactory sessionFactory;

    private static OwnerRepository ownerRepository;

    private static final User USER = new User();

    @BeforeAll
    public static void setup() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            CrudRepository crudRepository = new CrudRepository(sessionFactory);
            UserRepository userRepository = new HbmUserRepository(crudRepository);
            ownerRepository = new HbmOwnerRepository(crudRepository);

            USER.setLogin("test_login");
            USER.setPassword("password");
            userRepository.create(USER);
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @AfterAll
    public static void tear() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from User").executeUpdate();
        session.getTransaction().commit();
        sessionFactory.close();
    }

    @AfterEach
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from Owner").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void createOwnerThenFindHim() {
        Owner owner = new Owner();
        owner.setName("owner");
        owner.setUser(USER);
        ownerRepository.create(owner);

        var rsl = ownerRepository.findById(owner.getId()).get();

        assertThat(rsl).isEqualTo(owner);
        assertThat(rsl.getUser()).isEqualTo(USER);
        assertThat(rsl.getName()).isEqualTo("owner");
    }

    @Test
    public void saveSomeOwnersThenDeleteOneAndFindOther() {
        Owner owner1 = new Owner();
        owner1.setName("owner_1");
        owner1.setUser(USER);
        Owner owner2 = new Owner();
        owner2.setName("owner_2");
        owner2.setUser(USER);
        Owner owner3 = new Owner();
        owner3.setName("owner_3");
        owner3.setUser(USER);

        ownerRepository.create(owner1);
        ownerRepository.create(owner2);
        ownerRepository.create(owner3);
        ownerRepository.delete(owner1.getId());

        assertThat(ownerRepository.findById(owner1.getId())).isEmpty();
        assertThat(ownerRepository.findById(owner2.getId()).get()).isEqualTo(owner2);
        assertThat(ownerRepository.findById(owner3.getId()).get()).isEqualTo(owner3);
    }

    @Test
    public void saveOwnerThenUpdateOneAndFindHim() {
        Owner owner = new Owner();
        owner.setName("owner");
        owner.setUser(USER);
        ownerRepository.create(owner);

        String newName = "newName";
        owner.setName(newName);
        ownerRepository.update(owner);

        var rsl = ownerRepository.findById(owner.getId()).get();

        assertThat(rsl.getName()).isEqualTo(newName);
    }

}