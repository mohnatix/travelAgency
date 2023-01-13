package com.my.command;

import com.my.db.DBException;
import com.my.db.entity.Accommodation;
import com.my.db.entity.TourStatus;
import com.my.db.entity.TourType;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAddToursPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        List<TourType> tourTypes = Service.getInstance().findAllTourTypes();
        List<TourStatus> tourStatuses = Service.getInstance().findAllTourStatuses();
        List<Accommodation> accommodations = Service.getInstance().findAllAccommodations();
        session.setAttribute("tour_types", tourTypes);
        session.setAttribute("tour_statuses", tourStatuses);
        session.setAttribute("accommodations", accommodations);
        return "WEB-INF/jsp/add_tours.jsp";
    }
}
