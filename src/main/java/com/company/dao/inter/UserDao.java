package com.company.dao.inter;

import com.company.model.Filter;
import com.company.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    List<User> getUsers(Filter filter, int page);

    User addUser(User u);

    User getById(int id);
}
