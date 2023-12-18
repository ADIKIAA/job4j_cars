package ru.job4j.cars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimplePostServiceTest {

    private PostService postService;

    private PostRepository postRepository;

    @BeforeEach
    public void initRepository() {
        postRepository = mock(PostRepository.class);
        postService = new SimplePostService(postRepository);
    }

    @Test
    public void savePostThenCheckResult() {
        Post post = new Post();
        post.setId(1);
        post.setDescription("description");
        when(postRepository.save(any())).thenReturn(post);

        var rsl = postService.save(new Post());

        assertThat(rsl).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    public void savePostThenFindById() {
        Post post = new Post();
        post.setId(1);
        post.setDescription("description");
        when(postRepository.save(any())).thenReturn(post);
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));

        var savesPost = postService.save(new Post());
        var findPost = postService.findById(1);

        assertThat(savesPost).usingRecursiveComparison().isEqualTo(post);
        assertThat(findPost).usingRecursiveComparison().isEqualTo(Optional.of(post));
    }

    @Test
    public void testFindAllMethod() {
        Post post1 = new Post();
        post1.setId(1);
        post1.setDescription("description1");
        Post post2 = new Post();
        post2.setId(2);
        post2.setDescription("description2");
        var expected = List.of(post1, post2);
        when(postRepository.findAll()).thenReturn(expected);

        var findPosts = postService.findAll();

        assertThat(findPosts).isEqualTo(expected);
    }

}