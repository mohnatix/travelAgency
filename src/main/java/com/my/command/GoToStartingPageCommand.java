package com.my.command;

import com.my.db.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToStartingPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession();
        String tourTypeSearch = req.getParameter("tourTypeSearch");
        String currentTourTypeSearch = (String) session.getAttribute("currentTourTypeSearch");
        String defaultTourTypeSearch = "all";
        String personsNumberSearch = req.getParameter("personsNumberSearch");
        String currentPersonsNumberSearch = (String) session.getAttribute("currentPersonsNumberSearch");
        String defaultPersonsNumberSearch = "NONE";
        String durationSearch = req.getParameter("durationSearch");
        String currentDurationSearch = (String) session.getAttribute("currentDurationSearch");
        String defaultDurationSearch = "NONE";
        String accommodationSearch = req.getParameter("accommodationSearch");
        String currentAccommodationSearch = (String) session.getAttribute("currentAccommodationSearch");
        String defaultAccommodationSearch = "all";
        String priceSearch = req.getParameter("priceSearch");
        String currentPriceSearch = (String) session.getAttribute("currentPriceSearch");
        String defaultPriceSearch = "NONE";
        String page = req.getParameter("page");
        String currentPage = (String) session.getAttribute("currentPage");
        String defaultPage = "1";
        String pageSize = req.getParameter("pageSize");
        String currentPageSize = (String) session.getAttribute("currentPageSize");
        String defaultPageSize = "15";

        if (tourTypeSearch == null && currentTourTypeSearch == null) {
            tourTypeSearch = defaultTourTypeSearch;
        } else if (tourTypeSearch == null) {
            tourTypeSearch = currentTourTypeSearch;
        }

        if (personsNumberSearch == null && currentPersonsNumberSearch == null) {
            personsNumberSearch = defaultPersonsNumberSearch;
        } else if (personsNumberSearch == null) {
            personsNumberSearch = currentPersonsNumberSearch;
        }

        if (durationSearch == null && currentDurationSearch == null) {
            durationSearch = defaultDurationSearch;
        } else if (durationSearch == null) {
            durationSearch = currentDurationSearch;
        }

        if (accommodationSearch == null && currentAccommodationSearch == null) {
            accommodationSearch = defaultAccommodationSearch;
        } else if (accommodationSearch == null) {
            accommodationSearch = currentAccommodationSearch;
        }

        if (priceSearch == null && currentPriceSearch == null) {
            priceSearch = defaultPriceSearch;
        } else if (priceSearch == null) {
            priceSearch = currentPriceSearch;
        }

        if (page == null && currentPage == null) {
            page = defaultPage;
        } else if (page == null) {
            page = currentPage;
        }

        if (pageSize == null && currentPageSize == null) {
            pageSize = defaultPageSize;
        } else if (pageSize == null) {
            pageSize = currentPageSize;
        }

        return "controller?command=listTours&tourTypeSearch=" + tourTypeSearch +
                "&personsNumberSearch=" + personsNumberSearch + "&durationSearch=" + durationSearch +
                "&accommodationSearch=" + accommodationSearch + "&priceSearch=" + priceSearch +
                "&page=" + page + "&pageSize=" + pageSize;
    }
}
