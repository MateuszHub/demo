package uj.mateusz.demo.entitiy;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String role;

    private Integer user_id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
