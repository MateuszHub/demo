package uj.mateusz.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uj.mateusz.demo.entitiy.Roles;
import uj.mateusz.demo.entitiy.User;

import java.util.Collection;
import java.util.List;


public interface RolesRepository extends CrudRepository<Roles, Integer> {

    @Query("SELECT r FROM Roles r WHERE user_id = ?1")
    List<Roles> findRolesById(Integer id);
}
