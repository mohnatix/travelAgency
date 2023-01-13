package com.my.command;

import com.my.AppException;
import com.my.db.DBException;
import com.my.db.dto.UserDTO;
import com.my.logic.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AddTourCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, AppException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("currentUser");
        if (user == null) {
            return "controller?command=goToAccessDeniedPage";
        }
        String role = user.getRole();
        if (!"administrator".equals(role)) {
            return "controller?command=goToAccessDeniedPage";
        }
        String userStatus = user.getUserStatus();
        if ("blocked".equals(userStatus)) {
            return "controller?command=goToAccessDeniedPage";
        }
        try {
            Part filePart = req.getPart("imageUpload");
            String fileName = filePart.getSubmittedFileName();
            InputStream is = filePart.getInputStream();
            String imagesAddressTarget = req.getServletContext().getRealPath("/images");
            String imagesAddress = (String) req.getServletContext().getAttribute("imagesAddress");
            //String imagesAddress = "C:Users/User/Desktop/travelAgency/src/main/webapp/images";
            String tourName = req.getParameter("tourName");
            String tourType = req.getParameter("tourType");
            String tourTypeNew = req.getParameter("tourTypeNew");
            int numberOfPersons = Integer.parseInt(req.getParameter("numberOfPersons"));
            int duration = Integer.parseInt(req.getParameter("duration"));
            String accommodation = req.getParameter("accommodation");
            String accommodationNew = req.getParameter("accommodationNew");
            double price = Double.parseDouble(req.getParameter("price"));
            String tourStatus = req.getParameter("tourStatus");
            while (Service.getInstance().isExistingTourImageName(fileName)) {
                fileName = "0" + fileName;
            }
            Files.copy(is, Paths.get(imagesAddress + "/" + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(imagesAddress + "/" + fileName),
                    Paths.get(imagesAddressTarget + "/" + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            Service.getInstance().addTour(tourName, tourType, tourTypeNew, numberOfPersons, duration,
                    accommodation, accommodationNew, price, tourStatus, fileName);
        } catch (IOException | ServletException | NumberFormatException ex) {
            throw new AppException("Check your input", ex);
        }
        return "controller?command=goToStartingPage";
    }
}
