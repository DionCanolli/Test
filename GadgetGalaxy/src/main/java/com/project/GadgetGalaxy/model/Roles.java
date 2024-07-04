package com.project.GadgetGalaxy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;

    private String roleName;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private List<Users> usersList;

    public Roles(String roleName, List<Users> usersList) {
        this.roleName = roleName;
        this.usersList = usersList;
    }

    public Roles() {
    }
}
