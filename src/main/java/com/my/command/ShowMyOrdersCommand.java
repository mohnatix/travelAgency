package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.db.dto.UserOrderDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMyOrdersCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("currentUser");
        if (user == null) {
            return "controller?command=goToAccessDeniedPage";
        }
        List<UserOrderDTO> userOrders = Service.getInstance().findAllUserOrdersForUser(user.getId());
        session.setAttribute("myOrders", userOrders);
        return "WEB-INF/jsp/user_order.jsp";
    }
}
