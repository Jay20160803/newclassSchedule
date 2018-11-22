package com.onlyedu.newclassesschedule.job;

import com.onlyedu.newclassesschedule.model.ScheduleMate;
import com.onlyedu.newclassesschedule.util.HttpUtil;
import com.onlyedu.newclassesschedule.util.JsonUtil;
import com.onlyedu.newclassesschedule.util.RestResult;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author Andy
 * @date 2018/11/22 12:44
 */
public class SimpleJob implements Job {

    private Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    public static final String JOB_DATA_KEY = "scheduleMate";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        RestResult restResult = null;
        String str = "";

        try{
            str = context.getJobDetail().getJobDataMap().getString(JOB_DATA_KEY);
            ScheduleMate scheduleMate = JsonUtil.json2Object(str,ScheduleMate.class);
            if(scheduleMate == null || scheduleMate.getUri() == null || scheduleMate.getServerAddr() == null){
                return;
            }

            if(StringUtils.isEmpty(scheduleMate.getParamJSON())){
                restResult = HttpUtil.get(getUrl(scheduleMate));
            }else {
                restResult = HttpUtil.post(getUrl(scheduleMate),scheduleMate.getParamJSON());
            }

        }catch (Exception e){
            logger.error("SimpleJob.execute 报错:{},str:{},restResult:{}",str,(restResult==null?"":JsonUtil.object2Jon(restResult)));
        }
    }

    private String getUrl(ScheduleMate scheduleMate){

       return scheduleMate.getServerAddr() + scheduleMate.getUri();
    }
}
