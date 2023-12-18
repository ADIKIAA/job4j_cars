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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {

    private static SessionFactory sessionFactory;

    private static PostRepository postRepository;

    private static final User USER = new User();

    private static final Car CARWITHPHOTO = new Car();

    private static final Car CARWITHOUTPHOTO = new Car();

    @BeforeAll
    public static void setup() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            CrudRepository crudRepository = new CrudRepository(sessionFactory);
            UserRepository userRepository = new HbmUserRepository(crudRepository);
            PhotoRepository photoRepository = new HbmPhotoRepository(crudRepository);
            CarRepository carRepository = new HbmCarRepository(crudRepository);
            EngineRepository engineRepository = new HbmEngineRepository(crudRepository);
            postRepository = new HbmPostRepository(crudRepository);

            Engine engine = new Engine();
            engine.setName("engine");
            engineRepository.create(engine);

            Photo photo = new Photo();
            photo.setPath("path");
            photoRepository.save(photo);

            USER.setLogin("test_login");
            USER.setPassword("password");
            userRepository.create(USER);

            CARWITHPHOTO.getPhoto().add(photo);
            CARWITHPHOTO.setModel("Mark_1");
            CARWITHPHOTO.setEngine(engine);
            carRepository.create(CARWITHPHOTO);

            CARWITHOUTPHOTO.setModel("Mark_2");
            CARWITHOUTPHOTO.setEngine(engine);
            carRepository.create(CARWITHOUTPHOTO);
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @AfterAll
    public static void tear() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from Photo").executeUpdate();
        session.createQuery("DELETE from Car").executeUpdate();
        session.createQuery("DELETE from User").executeUpdate();
        session.getTransaction().commit();
        sessionFactory.close();
    }

    @AfterEach
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE from Post").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void createPostThenFindHimById() {
        Post post = new Post();
        post.setCar(CARWITHOUTPHOTO);
        post.setCreated(LocalDateTime.now());
        post.setDescription("description");

        postRepository.save(post);

        var rsl = postRepository.findById(post.getId()).get();

        assertThat(rsl).isEqualTo(post);
    }

    @Test
    public void saveSomePostsThenFindWithPhoto() {
        Post post1 = new Post();
        post1.setCar(CARWITHPHOTO);
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("description_1");
        Post post2 = new Post();
        post2.setCar(CARWITHOUTPHOTO);
        post2.setCreated(LocalDateTime.now());
        post2.setDescription("description_2");

        List<Post> expected = List.of(post1);

        postRepository.save(post1);
        postRepository.save(post2);

        var rsl = postRepository.findAllWithPhoto();

        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void saveSomePostsThenFindByCarMark() {
        Post post1 = new Post();
        post1.setCar(CARWITHPHOTO);
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("description_1");
        Post post2 = new Post();
        post2.setCar(CARWITHOUTPHOTO);
        post2.setCreated(LocalDateTime.now());
        post2.setDescription("description_2");

        String mark = "Mark_2";
        List<Post> expected = List.of(post2);

        postRepository.save(post1);
        postRepository.save(post2);

        var rsl = postRepository.findByCarMark(mark);

        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    public void saveSomePostsThenFindAllForLastDay() {
        Post post1 = new Post();
        post1.setCar(CARWITHPHOTO);
        post1.setCreated(LocalDateTime.now());
        post1.setDescription("description_1");
        Post post2 = new Post();
        post2.setCar(CARWITHOUTPHOTO);
        post2.setCreated(LocalDateTime.now().minusMonths(3));
        post2.setDescription("description_2");

        List<Post> expected = List.of(post1);

        postRepository.save(post1);
        postRepository.save(post2);

        var rsl = postRepository.findAllForLastDay();

        assertThat(rsl).isEqualTo(expected);
    }

}