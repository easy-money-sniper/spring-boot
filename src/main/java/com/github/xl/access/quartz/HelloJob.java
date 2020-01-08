package com.github.xl.access.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/09/26 10:40
 */
public class HelloJob implements Job {
    private String k;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 自动注入k v
        context.getMergedJobDataMap();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(k);
    }
}
