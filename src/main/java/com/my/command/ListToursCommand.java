package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.TourDTO;
import com.my.db.entity.Accommodation;
import com.my.db.entity.TourStatus;
import com.my.db.entity.TourType;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ListToursCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        String tourTypeSearch = req.getParameter("tourTypeSearch");
        String personsNumberSearch = req.getParameter("personsNumberSearch");
        String durationSearch = req.getParameter("durationSearch");
        String accommodationSearch = req.getParameter("accommodationSearch");
        String priceSearch = req.getParameter("priceSearch");
        String pageParameter = req.getParameter("page");
        String pageSizeParameter = req.getParameter("pageSize");
        int page = Integer.parseInt(pageParameter);
        int pageSize = Integer.parseInt(pageSizeParameter);
        List<TourType> tourTypes = Service.getInstance().findAllTourTypes();
        List<TourStatus> tourStatuses = Service.getInstance().findAllTourStatuses();
        List<Accommodation> accommodations = Service.getInstance().findAllAccommodations();
        int count = Service.getInstance().countAllTours(tourTypeSearch, accommodationSearch);
        int pageCount = (count != 0 && count % pageSize == 0) ? count / pageSize : count / pageSize + 1;
        if (page > pageCount) {
            page = pageCount;
            pageParameter = String.valueOf(page);
        }
        int offset = pageSize * (page - 1);
        List<TourDTO> tours = Service.getInstance().findAllTours(tourTypeSearch, personsNumberSearch, durationSearch,
                accommodationSearch, priceSearch, pageSize, offset);
        session.setAttribute("tour_types", tourTypes);
        session.setAttribute("tour_statuses", tourStatuses);
        session.setAttribute("accommodations", accommodations);
        session.setAttribute("tours", tours);
        session.setAttribute("currentTourTypeSearch", tourTypeSearch);
        session.setAttribute("currentPersonsNumberSearch", personsNumberSearch);
        session.setAttribute("currentDurationSearch", durationSearch);
        session.setAttribute("currentAccommodationSearch", accommodationSearch);
        session.setAttribute("currentPage", pageParameter);
        session.setAttribute("currentPageSize", pageSizeParameter);
        session.setAttribute("pageCount", pageCount);
        return "WEB-INF/jsp/home.jsp";
    }
}
