package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(int id);

    List<Post> findAllForLastDay();

    List<Post> findAllWithPhoto();

    List<Post> findByCarMark(String model);

    void changeStatus(int id, boolean status);

}
