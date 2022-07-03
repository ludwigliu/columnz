package org.liu.luiz.demo;

import org.liu.luiz.annotation.Column;

import java.util.Date;


public class User {

    @Column(name = "Name")
    private final String name;
    @Column(name = "Age")
    private final int age;
    @Column(name = "Email")
    private final String email;
    @Column(name = "Birth", dateFormat = "yyyyMMdd HH:mm:ss")
    private final Date birth;

    public User(String n, int age, String mail, Date birth) {
        this.name = n;
        this.age = age;
        this.email = mail;
        this.birth = birth;
    }
}
