package uj.mateusz.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uj.mateusz.demo.entitiy.Password;
import uj.mateusz.demo.entitiy.User;


public interface PasswordRepository extends CrudRepository<Password, Integer> {
}
