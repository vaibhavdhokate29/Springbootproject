package com.example.automation.Controller;




import com.example.automation.Model.User1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user")
public class User1Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // In-memory list to simulate a database
    private static List<User1> userList = new ArrayList<>();
    private static int userId = 1;

    // Read operation (Display all users)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User1 user = getUserById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else if (action != null && action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            deleteUser(id);
            response.sendRedirect("/user");
        } else {
            request.setAttribute("users", userList);
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        }
    }

    // Create operation (Add a new user)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User1 user = new User1(userId++, name, email);
        userList.add(user);
        response.sendRedirect("/user");
    }

    // Update operation
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User1 user = getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
        }
        response.sendRedirect("/user");
    }

    // Helper methods
    private User1 getUserById(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    private void deleteUser(int id) {
        userList.removeIf(user -> user.getId() == id);
    }
}


