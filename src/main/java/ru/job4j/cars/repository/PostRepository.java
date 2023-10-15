package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    public List<Post> findAllForLastDay() {
        return crudRepository.query(
                "from Post WHERE created = current_date",
                Post.class
        );
    }

    public List<Post> findAllWithPhoto() {
        return crudRepository.query(
                "from Post WHERE car.photo > 0",
                Post.class
        );
    }

    public List<Post> findByCarMark(String name) {
        return crudRepository.query(
                "from Post WHERE car.name = :name",
                Post.class,
                Map.of("name", name)
        );
    }

}
