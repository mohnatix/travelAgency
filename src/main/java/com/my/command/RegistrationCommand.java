package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (!password.equals(passwordRepeat)) {
            return "controller?command=goToFailedLogRegPage";
        }
        UserDTO user = Service.getInstance().createUser(login, password, email, name, surname);
        if (user == null) {
            return "controller?command=goToFailedLogRegPage";
        }
        session.setAttribute("currentUser", user);
        return "controller?command=goToStartingPage";
    }
}
