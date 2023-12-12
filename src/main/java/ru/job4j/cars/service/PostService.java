package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllForLastDay() {
        return postRepository.findAllForLastDay();
    }

    public List<Post> findAllWithPhoto() {
        return postRepository.findAllWithPhoto();
    }

    public List<Post> findByCarMark(String name) {
        return postRepository.findByCarMark(name);
    }

    public void changeStatus(int id, boolean status) {
        postRepository.changeStatus(id, status);
    }

}
