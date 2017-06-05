package com.wms.jobs;

import com.vivas.mail.SendMailTLS;
import com.wms.applistener.AppListener;
import com.wms.dao.CommonDAO;
import com.wms.dto.SysEmailPending;
import com.wms.utils.DBUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.wms.dao.CommonDAO.deleteListPendingMail;

/**
 * Created by duyot on 5/7/2017.
 */
public class SendMailJob implements Job {
    Logger log = LoggerFactory.getLogger(SendMailJob.class);
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("----------------------------------------------------");
        log.info("Starting job...");
        List<SysEmailPending> lstPendingMail = CommonDAO.getListPendingMail();
        if(lstPendingMail == null || lstPendingMail.size() == 0){
            return;
        }
        //
        log.info("Sending: "+ lstPendingMail.size() + " emails.");
        for (SysEmailPending i: lstPendingMail) {
            SendMailTLS.send(i.getFromAddr(), i.getToAddr(), i.getSubject(), i.getContent());
        }
        log.info("Finished send emai...");
        //
        boolean isDeleteSuccess= deleteListPendingMail(lstPendingMail);
        if(isDeleteSuccess){
            log.info("Deleted sent mail successfully...");
        }else{
            log.info("Deleted sent mail fail...");
        }

        log.info("Finish job...");
    }
}
