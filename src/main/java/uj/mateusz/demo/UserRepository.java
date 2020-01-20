package uj.mateusz.demo;

import org.springframework.data.repository.CrudRepository;
import uj.mateusz.demo.User;


public interface UserRepository extends CrudRepository<User, Integer> {

}
