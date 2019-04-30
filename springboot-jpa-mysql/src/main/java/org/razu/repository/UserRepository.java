package org.razu.repository;

import java.util.Optional;
import org.razu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String userName);

}
