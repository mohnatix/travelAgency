package com.my;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Method initialize log file, load locales and path to images folder
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String logPath = context.getRealPath("/WEB-INF/log4j2.log");
        System.setProperty("logFile", logPath);
        final Logger log = LogManager.getLogger(ContextListener.class);
        log.debug("logPath = " + logPath);
        //String path = context.getContextPath();
        //context.setAttribute("app", path);
        //log.info("app = " + path);
        String localesFileName = context.getInitParameter("locales");
        String localesFileRealPath = context.getRealPath(localesFileName);
        Properties locales = new Properties();
        try {
            //locales.load(new FileInputStream(localesFileRealPath));
            locales.load(Files.newInputStream(Paths.get(localesFileRealPath)));
        } catch (IOException e) {
            log.error("Cannot load locales", e);
        }
        context.setAttribute("locales", locales);
        log.debug("locales = " + locales);
        String imagesFileName = context.getInitParameter("images");
        String imagesFileRealPath = context.getRealPath(imagesFileName);
        Properties images = new Properties();
        String imagesAddress = "";
        try {
            images.load(Files.newInputStream(Paths.get(imagesFileRealPath)));
            imagesAddress = images.getProperty("folder");
        } catch (IOException e) {
            log.error("Cannot load images location", e);
        }
        context.setAttribute("imagesAddress", imagesAddress);
        log.debug("imagesPath = " + imagesAddress);
    }
}
