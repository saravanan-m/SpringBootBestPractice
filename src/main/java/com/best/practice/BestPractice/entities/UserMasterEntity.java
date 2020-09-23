package com.best.practice.BestPractice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_master")
@Getter
@Setter
public class UserMasterEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String desc;

    @OneToMany(mappedBy="userMasterEntity")
    private Set<AccountEntity> accounts;
}
