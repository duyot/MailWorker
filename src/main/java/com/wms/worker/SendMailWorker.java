package com.wms.worker;

import com.wms.applistener.AppListener;
import com.wms.jobs.SendMailJob;
import com.wms.utils.BundleUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duyot on 5/7/2017.
 */
public class SendMailWorker {
    Scheduler scheduler;
    JobDetail jobDetail;
    Trigger trigger;

    public static final String CRONTAB = BundleUtils.getKey("cron_send_time");
    Logger log = LoggerFactory.getLogger(SendMailWorker.class);

    public SendMailWorker() {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            initJobDetailAndTrigger();
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    private void initJobDetailAndTrigger(){
        jobDetail = JobBuilder.newJob(SendMailJob.class)
                .withIdentity("ExportReport", "Report").build();

        trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("ReportTrigger", "Report")
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule(CRONTAB)).build();
    }

    public void start(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }
    public void stop(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }
}
