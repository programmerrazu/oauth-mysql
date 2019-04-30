package org.razu.services.impl;

import java.util.Optional;
import org.razu.entity.User;
import org.razu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.razu.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUserName(String uName) {
        return userRepository.findByUsername(uName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(User user) {
        userRepository.delete(user);
        return !userRepository.findById(user.getId()).isPresent();
    }
}
