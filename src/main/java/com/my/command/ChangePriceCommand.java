package com.my.command;

import com.my.AppException;
import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePriceCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, AppException {
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
        int tourId = Integer.parseInt(req.getParameter("tourId"));
        try {
            double price = Double.parseDouble(req.getParameter("price"));
            Service.getInstance().changePrice(tourId, price);
        } catch (NumberFormatException ex) {
            throw new AppException("Wrong number format, check your input please", ex);
        }
        return "controller?command=goToStartingPage";
    }
}
