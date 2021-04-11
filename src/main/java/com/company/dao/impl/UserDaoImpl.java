package com.company.dao.impl;

import com.company.dao.inter.UserDao;
import com.company.dbconnect.ConnectDb;
import com.company.model.Filter;
import com.company.model.User;
import com.company.service.UserService;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl extends ConnectDb implements UserDao {

    @Override
    public List<User> getAll() {

        List<User> result = new ArrayList<>();

        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select * from users");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserService userService = new UserService();
                User u = userService.getUser(rs);
                result.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return result;
    }

    @Override
    public List<User> getUsers(Filter filter, int page) {
        String firstname = filter.getFirstname();
        String age = filter.getAge();

        final int total = 5;
        final int offSet = (page - 1) * total;
        String query = "SELECT * FROM users WHERE 1=1 ";


        if (!StringUtils.isNullOrEmpty(firstname)) {
            query += "AND Firstname LIKE '%" + firstname + "%' ";
        }

        if (!StringUtils.isNullOrEmpty(age)) {

            query += "AND Age < " + age;
        }

        query += " Limit " + offSet + ", " + total;


        List<User> result = new ArrayList<>();

        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserService userService = new UserService();
                User u = userService.getUser(rs);
                result.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);

        }

        return result;

    }

    @Override
    public User getById(int userId) {

        User result = null;

        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select * from users where" + userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Firstname");
                String surname = rs.getString("Lastname");
                String age = rs.getString("Age");
                String email = rs.getString("Email");

                result = new User(id, name, surname, age, email);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return result;
    }

    @Override
    public User addUser(User u) {

        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("insert into users(Firstname,Lastname,Age,Email) values(?,?,?,?)");
            stmt.setString(1, u.getFirstname());
            stmt.setString(2, u.getLastname());
            stmt.setString(3, u.getAge());
            stmt.setString(4, u.getEmail());
            stmt.execute();
            Statement getIdStmt = c.createStatement();
            getIdStmt.execute("select LAST_INSERT_ID()");
            ResultSet rs = getIdStmt.getResultSet();
            rs.next();
            Integer lastInsertedId = rs.getInt(1);
            System.out.println("lastInsertedId == " + lastInsertedId);
            return getById(lastInsertedId);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
