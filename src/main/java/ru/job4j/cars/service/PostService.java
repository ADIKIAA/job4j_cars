package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(int id);

    List<Post> findAllForLastDay();

    List<Post> findAllWithPhoto();

    List<Post> findByCarMark(String name);

    void changeStatus(int id, boolean status);

}
