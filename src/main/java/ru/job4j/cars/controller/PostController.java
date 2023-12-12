package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    private final CarService carService;

    private final EngineService engineService;

    private final PhotoService photoService;

    private final OwnerService ownerService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts/list";
    }

    @GetMapping("/withPhoto")
    public String getOnlyWithPhoto(Model model) {
        model.addAttribute("posts", postService.findAllWithPhoto());
        return "posts/list";
    }

    @GetMapping("/lastDay")
    public String getOnlyForLastDay(Model model) {
        model.addAttribute("posts", postService.findAllForLastDay());
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("brands", Brand.values());
        model.addAttribute("bodyworks", Bodywork.values());
        return "posts/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post,
                             @ModelAttribute Car car,
                             @ModelAttribute Engine engine,
                             @RequestParam String ownerName,
                             @SessionAttribute User user,
                             @RequestParam MultipartFile[] files) throws IOException {
        post.setUser(user);
        post.setCreated(LocalDateTime.now());
        Owner owner = new Owner();
        if (ownerName.isEmpty()) {
            owner.setName(user.getLogin());
        } else {
            owner.setName(ownerName);
        }
        owner.setUser(user);
        ownerService.create(owner);
        engineService.create(engine);
        List<Photo> photo = new ArrayList<>();
        if (!files[0].isEmpty()) {
            for (MultipartFile file : files) {
                String path = file.getOriginalFilename();
                byte[] content = file.getBytes();
                photo.add(photoService.save(new PhotoDto(path, content)));
            }
        }
        car.setOwner(owner);
        car.setPhoto(photo);
        car.setEngine(engine);
        carService.create(car);
        post.setCar(car);
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String getOne(Model model, @PathVariable int id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("message", "Объявление с указанныи id не найдено");
            return "errors/404";
        }
        Car car = carService.getCarWithPhoto(optionalPost.get().getCar().getId());
        if (car.getPhoto().size() == 0) {
            model.addAttribute("messagePhoto", "У этой машины нет фотографий");
        }
        model.addAttribute("car", car);
        model.addAttribute("post", optionalPost.get());
        return "posts/one";
    }

    @PostMapping("/changeStatus")
    public String changeStatus(Model model, @RequestParam int id, @RequestParam boolean status) {
        postService.changeStatus(id, status);
        return "redirect:/posts";
    }

}
