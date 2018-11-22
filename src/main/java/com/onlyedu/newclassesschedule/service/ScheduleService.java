package com.onlyedu.newclassesschedule.service;

import com.onlyedu.newclassesschedule.dao.ScheduleMateDao;
import com.onlyedu.newclassesschedule.model.ScheduleMate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andy
 * @date 2018/11/22 12:39
 */
@Service
public class ScheduleService {

    @Autowired
    private ScheduleMateDao scheduleMateDao;
    @Autowired
    private QuartzManager quartzManager;

    public void startAll(){

        List<ScheduleMate> list =  scheduleMateDao.findAll();

        for(ScheduleMate scheduleMate:list){
            quartzManager.addOrUpdateJob(scheduleMate);
        }
    }

    public void startScheduleById(int id){

        ScheduleMate scheduleMate = scheduleMateDao.findByPrimaryKey(id);
        quartzManager.addOrUpdateJob(scheduleMate);
    }

    public boolean deleteScheduleById(int id){
        ScheduleMate scheduleMate = scheduleMateDao.findByPrimaryKey(id);
        quartzManager.pauseJob(scheduleMate.getJobName(),scheduleMate.getJobGroup());
        return quartzManager.deleteJob(scheduleMate.getJobName(),scheduleMate.getJobGroup());
    }

    public void pauseScheduleById(int id){
        ScheduleMate scheduleMate = scheduleMateDao.findByPrimaryKey(id);
        quartzManager.pauseJob(scheduleMate.getJobName(),scheduleMate.getJobGroup());
    }

    public void resumeScheduleById(int id){
        ScheduleMate scheduleMate = scheduleMateDao.findByPrimaryKey(id);
        quartzManager.resumeJob(scheduleMate.getJobName(),scheduleMate.getJobGroup());
    }
}
