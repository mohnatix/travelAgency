package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.TourDTO;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BookCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("currentUser");
        if (user == null) {
            return "controller?command=goToAccessDeniedPage";
        }
        String userStatus = user.getUserStatus();
        if ("blocked".equals(userStatus)) {
            return "controller?command=goToAccessDeniedPage";
        }
        int tourId = Integer.parseInt(req.getParameter("tourId"));
        TourDTO tour = Service.getInstance().findTourById(tourId);
        if ("archived".equals(tour.getTourStatus())) {
            return "controller?command=goToAccessDeniedPage";
        }
        Service.getInstance().createOrder(user.getId(), tourId);
        return "controller?command=showMyOrders";
    }
}
