package cn.kykys.quartz;

import java.util.Date;

/**
 * Created by kuangye on 2016/7/22.
 */
public class ScheduleJob {

    /**
     * 任务id
     **/
    private String jobId;
    /**
     * 任务名称
     **/
    private String jobName;
    /**
     * 任务Class 全称 （包括包）
     **/
    private String jobClass;

    /**
     * 任务分组
     **/
    private String jobGroup;
    /**
     * 任务状态 0禁用 1启用 2删除
     **/
    private String jobStatus;
    /**
     * 任务运行时间表达式
     **/
    private String cronExpression;
    /**
     * 任务描述
     **/
    private String desc;


    private Date executeTime;


    private Date lastExecuteTime;



    public ScheduleJob() {
    }

    public ScheduleJob(String jobId, String jobName, String jobClass, String cronExpression, String desc) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobClass = jobClass;
        this.cronExpression = cronExpression;
        this.desc = desc;

        this.jobGroup = "DEFAULT_GROUP";
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
