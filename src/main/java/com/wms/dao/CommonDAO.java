package com.wms.dao;

import com.wms.dto.SysEmailPending;
import com.wms.jobs.SendMailJob;
import com.wms.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duyot on 5/8/2017.
 */
public class CommonDAO {
    public static final String getPendingMailSQL = "SELECT * FROM sys_mail_pending where status = 1 ";
    public static final String deletePendingMailSQL = "delete from sys_mail_pending where id in (?);";
    public static Logger log = LoggerFactory.getLogger(SendMailJob.class);

    public static String getINCondition(List<SysEmailPending> lstPendingMail){
        StringBuilder stringBuilder = new StringBuilder();
        for (SysEmailPending i: lstPendingMail) {
            stringBuilder.append(",").append(i.getId());
        }

        return stringBuilder.toString().replaceFirst(",","");
    }

    private static String createQuery(int length) {
        String query = "delete from sys_mail_pending where id in ( ";
        StringBuilder queryBuilder = new StringBuilder(query);
        for( int i = 0; i< length; i++){
            queryBuilder.append(" ?");
            if(i != length -1) queryBuilder.append(",");
        }
        queryBuilder.append(" ) ");
        return queryBuilder.toString();
    }


    public static boolean deleteListPendingMail(List<SysEmailPending> lstPendingMail){

        Connection con = DBUtils.getConnection();
        PreparedStatement statement = null;

        if(con == null){
            log.info("Cannot get connection");
            return false;
        }

        try {
            statement = con.prepareStatement(createQuery(lstPendingMail.size()));
            for(int i = 0; i < lstPendingMail.size();i++){
                statement.setInt(i+1,Integer.parseInt(lstPendingMail.get(i).getId()));
            }
            int count = statement.executeUpdate();
            log.info("Deleted "+ count +" items...");
        } catch (Exception e) {
            log.error("Error caused: "+ e.toString());
            e.printStackTrace();
            return false;
        }finally {
            DBUtils.closeConnection(statement,con);
        }

        return true;
    }

    public static List<SysEmailPending> getListPendingMail(){
            List<SysEmailPending> lstPendingMail = new ArrayList<SysEmailPending>();

            Connection con = DBUtils.getConnection();
            ResultSet rs = null;
            Statement statement = null;

            if(con == null){
                log.info("Cannot get connection");
                return lstPendingMail;
            }

            try {
                statement = con.createStatement();
                rs = statement.executeQuery(getPendingMailSQL);
                while(rs.next()){
                    SysEmailPending temp = new SysEmailPending();

                    temp.setId(nvl(rs.getString("ID")));
                    temp.setCreatedDate(nvl(rs.getString("created_date")));
                    temp.setFromAddr(nvl(rs.getString("from_addr")));
                    temp.setToAddr(nvl(rs.getString("to_addr")));
                    temp.setSubject(nvl(rs.getString("subject")));
                    temp.setContent(nvl(rs.getString("content")));
                    temp.setAttachPath(nvl(rs.getString("attach_path")));
                    temp.setSchedule_time(nvl(rs.getString("schedule_time")));
                    temp.setStatus(nvl(rs.getString("status")));

                    lstPendingMail.add(temp);
                }

            } catch (Exception e) {
                log.error("Error caused: "+ e.toString());
                e.printStackTrace();
            }finally {
                DBUtils.closeConnection(rs,statement,con);
            }

        return lstPendingMail;
    }

    public static String nvl(String value){
        return value == null ? "":value;
    }

    public static void main(String[] args) {
       int a = 1;
       abc(++a,a++);
       abc(a++,++a);
        System.out.println(a);
    }

    public static void abc(int a,int b) {
        System.out.println(a + " "+ b + " ");
    }


}
