package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {

    private final SessionFactory sf;

    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllOrderById() {
        List<User> list = List.of();
        Session session = sf.openSession();
        try {
            list = session.createQuery("from User ORDER BY id", User.class).list();
        } finally {
            session.close();
        }
        return list;
    }

    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        Optional<User> rsl;
        try {
            rsl = session.createQuery("from User as u WHERE u.id = :id", User.class)
                    .setParameter("id", userId).uniqueResultOptional();
        } finally {
            session.close();
        }
        return rsl;
    }

    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        Query<User> query;
        try {
            query = session.createQuery(
                            "from User as u WHERE u.login LIKE :fKey", User.class)
                    .setParameter("fKey", "%" + key + "%");
        } finally {
            session.close();
        }
        return query.list();
    }

    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> rsl;
        try {
            rsl = session.createQuery(
                            "from User as u WHERE u.login = :login", User.class)
                    .setParameter("login", login).uniqueResultOptional();
        } finally {
            session.close();
        }
        return rsl;
    }

}
