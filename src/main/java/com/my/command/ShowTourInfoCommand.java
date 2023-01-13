package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.TourDTO;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowTourInfoCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        int tourId = Integer.parseInt(req.getParameter("tourId"));
        TourDTO tour = Service.getInstance().findTourById(tourId);
        session.setAttribute("tour", tour);
        return "WEB-INF/jsp/tour_info.jsp";
    }
}
