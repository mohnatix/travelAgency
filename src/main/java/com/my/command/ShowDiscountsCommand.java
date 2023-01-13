package com.my.command;

import com.my.db.DBException;
import com.my.db.entity.Discount;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowDiscountsCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<Discount> discounts = Service.getInstance().findAllDiscounts();
        req.getSession().setAttribute("discounts", discounts);
        return "WEB-INF/jsp/discount.jsp";
    }
}
