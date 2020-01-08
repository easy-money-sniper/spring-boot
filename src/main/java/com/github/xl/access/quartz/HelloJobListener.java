package com.github.xl.access.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/09/27 10:23
 */
public class HelloJobListener implements JobListener {
    @Override
    public String getName() {
        return HelloJobListener.class.getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("jobToBeExecuted");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("jobExecutionVetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("jobWasExecuted");
    }
}
