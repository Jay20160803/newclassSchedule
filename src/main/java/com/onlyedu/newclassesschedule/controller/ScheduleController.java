package com.onlyedu.newclassesschedule.controller;

import com.onlyedu.newclassesschedule.service.ScheduleService;
import com.onlyedu.newclassesschedule.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andy
 * @date 2018/11/22 12:37
 */
@RestController
public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("startAll")
    public RestResult startAll(){

        try{
            scheduleService.startAll();
            return RestResult.success();
        }catch (Exception e){

            logger.error("ScheduleController.startAll 报错，报错信息：{} ",e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

    @RequestMapping("startById")
    public RestResult startById(@RequestParam("id") Integer id){

        if(id == null){
            return RestResult.fail("id 不能为空");
        }

        try{
            scheduleService.startScheduleById(id);
            return RestResult.success();
        }catch (Exception e){

            logger.error("ScheduleController.startById 报错，报错信息：{} ",e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

    @RequestMapping("deleteScheduleById")
    public RestResult deleteScheduleById(@RequestParam("id") Integer id){

        if(id == null){
            return RestResult.fail("id 不能为空");
        }

        try{
            return RestResult.success(scheduleService.deleteScheduleById(id));
        }catch (Exception e){

            logger.error("ScheduleController.deleteScheduleById 报错，报错信息：{} ",e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

    @RequestMapping("pauseScheduleById")
    public RestResult pauseScheduleById(@RequestParam("id") Integer id){

        if(id == null){
            return RestResult.fail("id 不能为空");
        }

        try{
            scheduleService.pauseScheduleById(id);
            return RestResult.success();
        }catch (Exception e){

            logger.error("ScheduleController.pauseScheduleById 报错，报错信息：{} ",e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

    @RequestMapping("resumeScheduleById")
    public RestResult resumeScheduleById(@RequestParam("id") Integer id){

        if(id == null){
            return RestResult.fail("id 不能为空");
        }

        try{
            scheduleService.resumeScheduleById(id);
            return RestResult.success();
        }catch (Exception e){

            logger.error("ScheduleController.resumeScheduleById 报错，报错信息：{} ",e.fillInStackTrace());
            return RestResult.fail(e.getMessage());
        }
    }

}
