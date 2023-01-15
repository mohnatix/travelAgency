package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnblockUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("currentUser");
        if (user == null) {
            return "controller?command=goToAccessDeniedPage";
        }
        String role = user.getRole();
        if (!"administrator".equals(role)) {
            return "controller?command=goToAccessDeniedPage";
        }
        String userStatus = user.getUserStatus();
        if ("blocked".equals(userStatus)) {
            return "controller?command=goToAccessDeniedPage";
        }
        int userId = Integer.parseInt(req.getParameter("userId"));
        if (user.getId() == userId) {
            return "controller?command=goToAccessDeniedPage";
        }
        Service.getInstance().unblockUser(userId);
        return "controller?command=showListOfUsers";
    }
}
