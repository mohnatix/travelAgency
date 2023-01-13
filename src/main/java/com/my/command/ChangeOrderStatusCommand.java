package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.db.dto.UserOrderDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeOrderStatusCommand implements Command {

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
        int userOrderId = Integer.parseInt(req.getParameter("userOrderId"));
        UserOrderDTO userOrder = Service.getInstance().findOrderById(userOrderId);
        if (user.getId() == userOrder.getUserId()) {
            return "controller?command=goToAccessDeniedPage";
        }
        String orderStatusSelect = req.getParameter("orderStatusSelect");
        Service.getInstance().changeOrderStatus(userOrderId, orderStatusSelect);
        return "controller?command=showListOfOrders";
    }
}
