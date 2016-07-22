package cn.kykys.quartz;

import java.util.List;

/**
 * Created by kuangye on 2016/7/22.
 */
public interface IJob {


//    public List<JobDetail> getJobs();

    // 添加job
    boolean addJob(ScheduleJob job);

    //获取所有的触发器
    List<ScheduleJob> getTriggersInfo();

    //暂停任务
    void stopJob(ScheduleJob scheduleJob);

    //恢复任务
    void restartJob(ScheduleJob scheduleJob);

    //立马执行一次任务
    void startNowJob(ScheduleJob scheduleJob);

    //删除任务
    void delJob(ScheduleJob scheduleJob);

    //修改触发器时间
    void modifyTrigger(ScheduleJob scheduleJob);

    //暂停调度器
    void stopScheduler();
}
