package com.my.command;

import com.my.AppException;
import com.my.db.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePageSizeCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, AppException {
        HttpSession session = req.getSession();
        String pageSize = req.getParameter("pageSize");
        session.setAttribute("currentPageSize", pageSize);
        return "controller?command=goToStartingPage";
    }
}
