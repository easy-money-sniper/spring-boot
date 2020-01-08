package com.github.xl.controller;

import com.github.xl.access.quartz.HelloJob;
import com.github.xl.access.quartz.HelloJobListener;
import com.github.xl.access.quartz.HelloSchedulerListener;
import com.github.xl.access.quartz.HelloTriggerListener;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/09/27 14:29
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public QuartzController(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @RequestMapping("update")
    public void updateJobCron(@RequestParam(value = "cron") String cron) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        ListenerManager listenerManager = scheduler.getListenerManager();

        if (null == listenerManager.getJobListener(HelloJobListener.class.getName())) {
            listenerManager.addJobListener(new HelloJobListener());
        }
        List<SchedulerListener> schedulerListeners = listenerManager.getSchedulerListeners();

        if (CollectionUtils.isEmpty(schedulerListeners)
                || schedulerListeners.stream().anyMatch(schedulerListener -> StringUtils.equals(schedulerListener.getClass().getName(), HelloSchedulerListener.class.getName()))) {
            listenerManager.addJobListener(new HelloJobListener());
        }
        if (null == listenerManager.getTriggerListener(HelloTriggerListener.class.getName())) {
            listenerManager.addTriggerListener(new HelloTriggerListener());
        }

        Trigger trigger = scheduler.getTrigger(new TriggerKey("triggerName", "group"));
        JobDetail jobDetail;
        if (trigger == null) {
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey("triggerName", "group"))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            jobDetail = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("jobName", "group")
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey("triggerName", "group"))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.rescheduleJob(new TriggerKey("triggerName", "group"), trigger);
        }
    }
}
