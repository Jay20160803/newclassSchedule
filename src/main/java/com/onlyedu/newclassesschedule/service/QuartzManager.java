package com.onlyedu.newclassesschedule.service;


import com.onlyedu.newclassesschedule.job.SimpleJob;
import com.onlyedu.newclassesschedule.model.ScheduleMate;
import com.onlyedu.newclassesschedule.util.JsonUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.onlyedu.newclassesschedule.job.SimpleJob.JOB_DATA_KEY;


/**
 * @author Andy
 * @date 2018/11/22 9:25
 */
@Service
public class QuartzManager {

    private Logger logger = LoggerFactory.getLogger(QuartzManager.class);

    @Autowired
    private Scheduler scheduler;



    private void addJob(ScheduleMate scheduleMate){
        try{

            JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity(scheduleMate.getJobName(),scheduleMate.getJobGroup())
                    .usingJobData(JOB_DATA_KEY, JsonUtil.object2Jon(scheduleMate))
                    .build();


            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleMate.getJobName(),scheduleMate.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleMate.getCronExp()))
                    .startNow()
                    .build();

            scheduler.scheduleJob(jobDetail,trigger);

            if(scheduler.isShutdown()){
                scheduler.start();
            }

        }catch (Exception e){
            logger.error("QuartzManager.addJob 报错,scheduleMate:{},报错信息：{}",JsonUtil.object2Jon(scheduleMate),e.fillInStackTrace());
        }
    }

    public void addOrUpdateJob(ScheduleMate scheduleMate){

        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleMate.getJobName(),scheduleMate.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if(trigger == null){
                addJob(scheduleMate);
            }else {

                if(trigger.getCronExpression().equals(scheduleMate.getCronExp())){
                    return;
                }

                updateJob(scheduleMate);
            }
        }catch (Exception e){
            logger.error("QuartzManager.addOrUpdateJob 报错,scheduleMate:{},报错信息：{}",JsonUtil.object2Jon(scheduleMate),e.fillInStackTrace());
        }
    }


    private void updateJob(ScheduleMate scheduleMate){

        try{

            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleMate.getJobName(),scheduleMate.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleMate.getCronExp()))
                    .build();

            scheduler.rescheduleJob(triggerKey,trigger);

        }catch (Exception e){
            logger.error("QuartzManager.addOrUpdateJob 报错,scheduleMate:{},报错信息：{}",JsonUtil.object2Jon(scheduleMate),e.fillInStackTrace());
        }
    }

    public boolean deleteJob(String jobName,String jobGroupName){

        boolean result = false;
        try{

            result = scheduler.deleteJob(new JobKey(jobName,jobGroupName));
        }catch (Exception e){
            logger.error("QuartzManager.deleteJob 报错,jobName:{},jobGroupName:{},报错信息：{}",jobName,jobGroupName,e.fillInStackTrace());
        }
        return result;
    }

    public void pauseJob(String jobName,String jobGroupName){

        try{

            scheduler.pauseJob(new JobKey(jobName,jobGroupName));
        }catch (Exception e){
            logger.error("QuartzManager.pauseJob 报错,jobName:{},jobGroupName:{},报错信息：{}",jobName,jobGroupName,e.fillInStackTrace());
        }
    }

    public void resumeJob(String jobName,String jobGroupName){

        try{

            scheduler.resumeJob(new JobKey(jobName,jobGroupName));
        }catch (Exception e){
            logger.error("QuartzManager.pauseJob 报错,jobName:{},jobGroupName:{},报错信息：{}",jobName,jobGroupName,e.fillInStackTrace());
        }
    }

}
