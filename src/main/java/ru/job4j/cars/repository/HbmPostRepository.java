package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query(
                "from Post",
                Post.class
        );
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post WHERE id = :id",
                Post.class,
                Map.of("id", id)
        );
    }

    @Override
    public List<Post> findAllForLastDay() {
        return crudRepository.query(
                "from Post WHERE created >= current_date()",
                Post.class
        );
    }

    @Override
    public List<Post> findAllWithPhoto() {
        return crudRepository.query(
                "from Post WHERE car.photo.size > 0",
                Post.class
        );
    }

    @Override
    public List<Post> findByCarMark(String model) {
        return crudRepository.query(
                "from Post WHERE car.model = :model",
                Post.class,
                Map.of("model", model)
        );
    }

    @Override
    public void changeStatus(int id, boolean status) {
        crudRepository.run(
                "UPDATE Post SET status = :status WHERE id = :id",
                Map.of("id", id, "status", !status)
        );
    }

}
