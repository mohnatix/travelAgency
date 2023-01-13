package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserOrderDTO;
import com.my.db.entity.OrderStatus;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowListOfOrdersCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<UserOrderDTO> userOrders = Service.getInstance().findAllUserOrders();
        List<OrderStatus> orderStatuses = Service.getInstance().findAllOrderStatuses();
        req.getSession().setAttribute("userOrders", userOrders);
        req.getSession().setAttribute("orderStatuses", orderStatuses);
        return "WEB-INF/jsp/list_user_orders.jsp";
    }
}
