package com.onlyedu.newclassesschedule.dao;

import com.onlyedu.newclassesschedule.model.ScheduleMate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Andy
 * @date 2018/11/22 10:40
 */
@Mapper
public interface ScheduleMateDao {

    List<ScheduleMate> findAll();

    ScheduleMate findByPrimaryKey(@Param("id")int id);

}
