package org.liu.luiz.demo;

import org.apache.commons.lang3.StringUtils;
import org.liu.luiz.handler.Columnzy;

import java.util.Date;

public class App {
    public static void main(String[] args) {
        User user1 = new User("admin", 30, "admin@live.com", new Date());
        User user2 = new User("jack", 25, "jack@live.com", new Date());

        Columnzy<User> columnzy = new Columnzy<>();
        String[] headers = new String[]{"Name", "Age", "Birth", "Email"};
        columnzy.setHeaders(headers);
        System.out.println(String.join(",", headers));
        columnzy.parse(new User[]{user1, user2}).forEach(c -> System.out.println(StringUtils.joinWith(",", c)));
    }
}