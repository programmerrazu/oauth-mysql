package org.razu.services;

import java.util.Optional;
import org.razu.entity.User;

public interface UserService {

    Iterable<User> findAllUser();

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByUserName(String uName);

    User save(User user);

    User update(User user);

    Boolean delete(User user);

}
