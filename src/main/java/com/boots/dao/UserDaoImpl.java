package com.boots.dao;

import com.boots.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT user FROM User user join fetch  user.roles WHERE user.email =:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {

        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {

        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {

        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long id) {

        entityManager.remove(entityManager.getReference(User.class, id));
    }

    @Override
    public List<User> listUsers() {

        return entityManager.createQuery("From User", User.class).getResultList();
    }
}
