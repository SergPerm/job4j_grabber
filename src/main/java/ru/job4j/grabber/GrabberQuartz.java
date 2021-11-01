package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class GrabberQuartz implements Grab {

    private static String url = "https://www.sql.ru/forum/job-offers";

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler)
            throws SchedulerException {
        scheduler.start();
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabberQuartzJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(40)
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Grab grabber = new GrabberQuartz();
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        Store store = new MemStore();
        Parse parse = new SqlRuParse(dateTimeParser);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        grabber.init(parse, store, scheduler);
        Thread.sleep(120000);
        scheduler.shutdown();
        for (Post tmp : store.getAll()) {
            System.out.println(tmp);
        }
    }

    public static class GrabberQuartzJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            Parse parse = (Parse) context.getJobDetail().getJobDataMap().get("parse");
            Store store = (Store) context.getJobDetail().getJobDataMap().get(("store"));
            List<Post> list = parse.list(url);
            for (Post post : list) {
                store.save(post);
            }
        }
    }
}
