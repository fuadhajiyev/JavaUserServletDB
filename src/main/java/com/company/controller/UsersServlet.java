package com.company.controller;


import com.company.dao.impl.UserDaoImpl;
import com.company.dao.inter.UserDao;
import com.company.model.Filter;
import com.company.model.User;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Users", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    UserDao userDao;

    public void init() {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // user repository


        List<User> usersPagination = null;
        String firstname = request.getParameter("firstname");
        String age = request.getParameter("age");
        String page = request.getParameter("page");

        int parsedPageId = 1;

        if (page != null) {
            parsedPageId = Integer.parseInt(page);
        }

        Filter filter = new Filter();
        filter.setFirstname(firstname);
        filter.setAge(age);

        usersPagination = userDao.getUsers(filter,parsedPageId);


        Gson gson = new Gson();
        String userJSON = gson.toJson(usersPagination);
        PrintWriter printWriter = response.getWriter();

        printWriter.write(userJSON);

        printWriter.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            String request = getAllDataFromRequest(req);
            Gson gson = new Gson();
            User user = gson.fromJson(request, User.class);
            User savedUser = userDao.addUser(user);

            String result = gson.toJson(savedUser);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result);
            printWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getAllDataFromRequest(HttpServletRequest request) throws Exception {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();

        return body;
    }

    public void destroy() {
    }


}