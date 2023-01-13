package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;;
import com.my.logic.Service;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDTO user = Service.getInstance().findUser(login, password);
        if (user == null) {
            return "controller?command=goToFailedLogRegPage";
        }
        String userStatus = user.getUserStatus();
        if ("blocked".equals(userStatus)) {
            return "controller?command=goToAccessDeniedPage";
        }
        req.getSession().setAttribute("currentUser", user);
        return "controller?command=goToStartingPage";
    }
}
