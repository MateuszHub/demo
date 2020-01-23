package uj.mateusz.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uj.mateusz.demo.entitiy.User;


public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE name = ?1")
    User findByName(String name);
}
