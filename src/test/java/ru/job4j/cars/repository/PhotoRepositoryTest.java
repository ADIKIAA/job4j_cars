package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import static org.assertj.core.api.Assertions.*;

class PhotoRepositoryTest {

    private static SessionFactory sessionFactory;

    private static CrudRepository crudRepository;

    private static PhotoRepository photoRepository;

    @BeforeAll
    public static void setup() {
        try {

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("test-hibernate.cfg.xml").build();

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();

            crudRepository = new CrudRepository(sessionFactory);
            photoRepository = new HbmPhotoRepository(crudRepository);

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
        var photos = crudRepository.query(
                "from Photo",
                Photo.class
        );
        for (Photo photo : photos) {
            photoRepository.delete(photo.getId());
        }
    }

    @Test
    public void savePhotoThenFindById() {
        Photo photo = new Photo();
        photo.setPath("path");

        var rsl = photoRepository.save(photo);

        assertThat(rsl).isEqualTo(photo);
    }

    @Test
    public void saveSomePhotoThenDeleteOneAndFindById() {
        Photo photo1 = new Photo();
        photo1.setPath("path1");
        Photo photo2 = new Photo();
        photo2.setPath("path2");
        Photo photo3 = new Photo();
        photo3.setPath("path3");

        photoRepository.save(photo1);
        photoRepository.save(photo2);
        photoRepository.save(photo3);
        photoRepository.delete(photo2.getId());

        assertThat(photoRepository.findById(photo1.getId()).get()).usingRecursiveComparison().isEqualTo(photo1);
        assertThat(photoRepository.findById(photo2.getId())).isEmpty();
        assertThat(photoRepository.findById(photo3.getId()).get()).usingRecursiveComparison().isEqualTo(photo3);
    }

}