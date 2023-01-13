package com.my.command;

import com.my.AppException;
import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeDiscountCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, AppException {
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
        int discountId = Integer.parseInt(req.getParameter("discountId"));
        try {
            int discountStep = Integer.parseInt(req.getParameter("discountStep"));
            int discountMax = Integer.parseInt(req.getParameter("discountMax"));
            Service.getInstance().changeDiscount(discountId, discountStep, discountMax);
        } catch (NumberFormatException ex) {
            throw new AppException("Wrong number format, check your input please", ex);
        }
        return "controller?command=showDiscounts";
    }
}
