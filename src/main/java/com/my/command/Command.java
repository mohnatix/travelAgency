package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.AppException;
import com.my.db.DBException;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, AppException;
}
