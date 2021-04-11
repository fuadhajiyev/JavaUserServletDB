package com.company.service;


import com.company.model.User;

import java.sql.ResultSet;


public class UserService {

    public User getUser(ResultSet rs) throws Exception {
        Integer id = rs.getInt("id");
        String name = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String age = rs.getString("age");
        String email = rs.getString("email");

        return new User(id, name, lastname, age, email);


    }


}
