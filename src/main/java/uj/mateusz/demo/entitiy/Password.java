package uj.mateusz.demo.entitiy;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "password")
public class Password {
    @Id
    private Integer id;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
