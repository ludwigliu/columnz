package org.liu.luiz.demo;

import org.liu.luiz.annotation.Column;
import org.liu.luiz.formatter.DateConverter;

import java.util.Date;


public class User {

    @Column(name = "Name")
    private final String name;
    @Column(name = "Age")
    private final int age;
    @Column(name = "Email")
    private final String email;
    @Column(name = "Birth", pattern = "yyyyMMdd HH:mm:ss", converter = DateConverter.class)
    private final Date birth;

    public User(String n, int age, String mail, Date birth) {
        this.name = n;
        this.age = age;
        this.email = mail;
        this.birth = birth;
    }
}
