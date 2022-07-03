package org.liu.luiz.demo;

import org.liu.luiz.annotation.Column;

import java.util.Date;


public class User {

    @Column(name = "Name")
    private String name;
    @Column(name = "Age")
    private int age;
    @Column(name = "Email")
    private String email;
    @Column(name = "Birth")
    private Date birth;

    public User(String n, int age, String mail, Date birth) {
        this.name = n;
        this.age = age;
        this.email = mail;
        this.birth = birth;
    }
}
