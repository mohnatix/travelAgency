package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeTourStatusCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("currentUser");
        if (user == null) {
            return "controller?command=goToAccessDeniedPage";
        }
        String role = user.getRole();
        if ("customer".equals(role)) {
            return "controller?command=goToAccessDeniedPage";
        }
        String userStatus = user.getUserStatus();
        if ("blocked".equals(userStatus)) {
            return "controller?command=goToAccessDeniedPage";
        }
        String tourStatusSelect = req.getParameter("tourStatusSelect");
        int tourId = Integer.parseInt(req.getParameter("tourId"));
        Service.getInstance().changeTourStatus(tourId, tourStatusSelect);
        return "controller?command=goToStartingPage";
    }
}
