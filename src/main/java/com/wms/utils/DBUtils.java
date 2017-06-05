package com.wms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by duyot on 5/7/2017.
 */
public class DBUtils {
    public static Logger log = LoggerFactory.getLogger(BundleUtils.class);

    public static Connection getConnection(){
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/ApplicationPool");
            return ds.getConnection();
        } catch (Exception e) {
            log.error("Get connection fail caused: "+ e.toString());
            e.printStackTrace();
        }
        return null;
    }
    public static void closeConnection(ResultSet rs, CallableStatement callableStatement, Connection connection){
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                log.error("Error: ", ex);
            }
        }
        //
        if(callableStatement != null){
            try {
                callableStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection(ResultSet rs, Statement statement, Connection connection){
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                log.error("Error: ", ex);
            }
        }
        //
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeConnection(Statement statement, Connection connection){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
