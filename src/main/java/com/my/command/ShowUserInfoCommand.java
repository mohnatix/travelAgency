package com.my.command;

import com.my.db.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserInfoCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        return "WEB-INF/jsp/user_info.jsp";
    }
}
