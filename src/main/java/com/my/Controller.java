package com.my;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.command.Command;
import com.my.command.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@MultipartConfig
@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    final Logger log = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "controller?command=goToErrorPage";
        String commandName = req.getParameter("command");
        log.info("commandName = " + commandName);
        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            req.getSession().setAttribute("ex", ex);
            log.error("Error during command execution", ex);
        }
        log.info("address = " + address);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "controller?command=goToErrorPage";
        String commandName = req.getParameter("command");
        log.info("commandName = " + commandName);
        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            req.getSession().setAttribute("ex", ex);
            log.error("Error during command execution", ex);
        }
        log.info("address = " + address);
        resp.sendRedirect(address);
    }
}




