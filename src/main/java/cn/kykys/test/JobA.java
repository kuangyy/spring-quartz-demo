package cn.kykys.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by kuangye on 2016/7/22.
 */
public class JobA implements Job{


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {




        System.out.println(" job execute "+new Date());


        //you should update your last execute time

    }
}
