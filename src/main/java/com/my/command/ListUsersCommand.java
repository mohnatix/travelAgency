package com.my.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

public class ListUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<UserDTO> users = Service.getInstance().findAllUsers();
        req.getSession().setAttribute("users", users);
        return "list_users.jsp";
    }
}
