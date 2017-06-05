package com.wms.applistener;

import com.vivas.mail.SendMailTLS;
import com.vivas.mail.config.object.ConfigMailTLS;
import com.wms.dao.CommonDAO;
import com.wms.utils.DBUtils;
import com.wms.worker.SendMailWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Array;
import java.sql.Connection;


/**
 * Created by duyot on 5/7/2017.
 */
@WebListener
public class AppListener implements ServletContextListener {
    Logger log = LoggerFactory.getLogger(AppListener.class);
    //
    public static String MAIL_SMTP_USERNAME = "wms.teams@gmail.com";
    public static String MAIL_SMTP_PASSWORD = "wms#2017";
    public static String MAIL_SMTP_AUTH = "true";
    public static String MAIL_SMTP_STARTTLS_ENABLE = "true";
    public static String MAIL_SMTP_HOST = "smtp.gmail.com";
    public static String MAIL_SMTP_PORT = "587";
    public static String MAIL_SMTP_SSL_TRUST = "*";
    public static ConfigMailTLS configMail;
    //
    SendMailWorker sendMailWorker;

    public AppListener() {
        sendMailWorker = new SendMailWorker();
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("-----------------------------------------------------------");
        try {
            Connection connection = DBUtils.getConnection();
            if (connection != null) {
                log.info("Get connection successfully");
            } else {
                log.info("Get connection: null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Get connection fail");
        }
        log.info("Initing app...");
        //
        log.info("Initting mail config...");
        initMailConfig();
        //
        sendMailWorker.start();
        log.info("Read finished, worker is running...");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("-----------------------------------------------------------");
        sendMailWorker.stop();
        log.info("Closing app...");
    }

    private void initMailConfig() {
        configMail = new ConfigMailTLS();
        configMail.setUserName(MAIL_SMTP_USERNAME);
        configMail.setPassword(MAIL_SMTP_PASSWORD);
        configMail.setAuth(MAIL_SMTP_AUTH);
        configMail.setStarttlsEnable(MAIL_SMTP_STARTTLS_ENABLE);
        configMail.setHost(MAIL_SMTP_HOST);
        configMail.setPort(MAIL_SMTP_PORT);
        configMail.setSslTrust(MAIL_SMTP_SSL_TRUST);
        SendMailTLS.setConfig(configMail);
    }

}
