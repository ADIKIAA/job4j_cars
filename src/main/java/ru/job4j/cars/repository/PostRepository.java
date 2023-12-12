package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    public List<Post> findAll() {
        return crudRepository.query(
                "from Post",
                Post.class
        );
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post WHERE id = :id",
                Post.class,
                Map.of("id", id)
        );
    }

    public List<Post> findAllForLastDay() {
        return crudRepository.query(
                "from Post WHERE created >= current_date()",
                Post.class
        );
    }

    public List<Post> findAllWithPhoto() {
        return crudRepository.query(
                "from Post WHERE car.photo.size > 0",
                Post.class
        );
    }

    public List<Post> findByCarMark(String model) {
        return crudRepository.query(
                "from Post WHERE car.model = :model",
                Post.class,
                Map.of("model", model)
        );
    }

    public void changeStatus(int id, boolean status) {
        crudRepository.run(
                "UPDATE Post SET status = :status WHERE id = :id",
                Map.of("id", id, "status", !status)
        );
    }

}
