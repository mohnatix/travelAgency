package com.my.command;

import com.my.db.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLocaleCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String locale = req.getParameter("locale");
        return "controller?command=goToChangeLocalePage&locale=" + locale;
    }
}
