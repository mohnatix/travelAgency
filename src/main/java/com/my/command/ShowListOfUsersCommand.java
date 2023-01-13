package com.my.command;

import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.db.entity.Role;
import com.my.logic.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowListOfUsersCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        List<UserDTO> users = Service.getInstance().findAllUsers();
        List<Role> roles = Service.getInstance().findAllRoles();
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("roles", roles);
        return "WEB-INF/jsp/list_users.jsp";
    }
}
