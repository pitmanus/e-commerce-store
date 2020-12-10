package com.finalproject.ecommercestore.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleNames name;

    @ManyToMany(
            mappedBy="roles"
    )
    private List<User> users = new ArrayList<>();


    public Role() {
    }

    public Role(RoleNames name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleNames getName() {
        return name;
    }

    public void setName(RoleNames name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }

}
