package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "auto_post")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String description;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> participates = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

}
