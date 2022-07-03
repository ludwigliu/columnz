package org.liu.luiz;

import org.apache.commons.lang3.StringUtils;
import org.liu.luiz.demo.User;
import org.liu.luiz.handler.Columnzy;

import java.util.Date;

public class App {
    public static void main(String[] args) {
        User user = new User("admin", 30, "admin@live.com", new Date());
        Columnzy<User> columnzy = new Columnzy<>();
        Object[] cols = columnzy.parse(User.class, user);
        System.out.println(StringUtils.joinWith(",", cols));
    }
}