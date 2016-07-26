package cn.kykys.quartz;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by kuangye on 2016/7/22.
 */
@Service
public class JobScheduler implements IJob {

    //schedulerFactoryBean 由spring创建注入
    @Autowired
   private Scheduler scheduler;

    public boolean addJob(ScheduleJob job) {

        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //不存在，创建一个
            if (null == trigger) {

                Class clzz = Class.forName(job.getJobClass());
                //运行JOB的实现类
                JobDetail jobDetail = JobBuilder.newJob(clzz)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();

                jobDetail.getJobDataMap().put("scheduleJob", job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                        .getCronExpression());
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                        .getCronExpression());
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }


//    public List<JobDetail> getJobs(){
//        try {
//            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
//            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//
//            List<JobDetail> jobDetails = new ArrayList<JobDetail>();
//
//            for (JobKey key : jobKeys) {
//                jobDetails.add(scheduler.getJobDetail(key));
//            }
//            return jobDetails;
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    //获取所有的触发器
    public List<ScheduleJob> getTriggersInfo() {
        try {
            GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup();
            Set<TriggerKey> Keys = scheduler.getTriggerKeys(matcher);
            List<ScheduleJob> triggers = new ArrayList<>();

            for (TriggerKey key : Keys) {
                Trigger trigger = scheduler.getTrigger(key);
                ScheduleJob pageTrigger = new ScheduleJob();
                pageTrigger.setJobName(trigger.getJobKey().getName());
                pageTrigger.setJobGroup(trigger.getJobKey().getGroup());
                pageTrigger.setJobStatus(scheduler.getTriggerState(key) + "");
                if (trigger instanceof SimpleTrigger) {
                    SimpleTrigger simple = (SimpleTrigger) trigger;
                    pageTrigger.setCronExpression("重复次数:" + (simple.getRepeatCount() == -1 ?
                            "无限" : simple.getRepeatCount()) + ",重复间隔:" + (simple.getRepeatInterval() / 1000L)+"(秒)");
                    pageTrigger.setDesc(simple.getDescription());
                }
                if (trigger instanceof CronTrigger) {
                    CronTrigger cron = (CronTrigger) trigger;
                    pageTrigger.setCronExpression(cron.getCronExpression());
                    pageTrigger.setDesc(cron.getDescription());
                }
                triggers.add(pageTrigger);
            }
            return triggers;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }


    //暂停任务
    public void stopJob(ScheduleJob scheduleJob) {
        JobKey key = new JobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //恢复任务
    public void restartJob(ScheduleJob scheduleJob) {
        JobKey key = new JobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //立马执行一次任务
    public void startNowJob(ScheduleJob scheduleJob) {
        try {
            JobKey key = new JobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            JobDetail job = JobBuilder.newJob(
                    scheduler.getJobDetail(key).getJobClass())
                    .storeDurably()
                    .build();
            scheduler.addJob(job, false);
            scheduler.triggerJob(job.getKey());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //删除任务
    public void delJob(ScheduleJob scheduleJob) {
        JobKey key = new JobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //修改触发器时间
    public void modifyTrigger(ScheduleJob scheduleJob) {
        try {
            TriggerKey key = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//            Trigger trigger = scheduler.getTrigger(key);

            CronTrigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(key)
                    .withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()))
                    .build();
            scheduler.rescheduleJob(key, newTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    //暂停调度器
    public void stopScheduler() {
        try {
            if (!scheduler.isInStandbyMode()) {
                scheduler.standby();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
