import cn.kykys.quartz.IJob;
import cn.kykys.quartz.JobScheduler;
import cn.kykys.quartz.ScheduleJob;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kuangye on 2016/7/22.
 */
public class aa {


    @Test
    public void aa()    throws Exception{

        ApplicationContext context = new ClassPathXmlApplicationContext("config/spring.xml");

        IJob jobScheduler = (IJob) context.getBean("jobScheduler");

        ScheduleJob scheduleJob = new ScheduleJob();

        scheduleJob.setJobName("aa");
        scheduleJob.setJobClass("cn.kykys.test.JobA");
        scheduleJob.setJobGroup("aa");
        scheduleJob.setCronExpression("/5 * * * * ?");
        scheduleJob.setDesc("s");
        scheduleJob.setJobId("sd");

        jobScheduler.addJob(scheduleJob);


        Thread.sleep(100000L);

    }
}
