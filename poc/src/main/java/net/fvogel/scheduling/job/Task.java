package net.fvogel.scheduling.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.Job;

public class Task<T extends Job> {

    private Class<T> clazz;
    private Map<String, Object> parameters = new ConcurrentHashMap<>();

    public Task(Class<T> clazz, Map<String, Object> parameters) {
        this.clazz = clazz;
        this.parameters = parameters;
    }

    public Class<T> getJobClass() {
        return clazz;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

}
