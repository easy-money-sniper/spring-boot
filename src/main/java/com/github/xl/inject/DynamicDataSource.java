package com.github.xl.inject;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 10:39
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceHolder;

    static {
        dataSourceHolder = ThreadLocal.withInitial(DBRole.MASTER::name);
    }

    public static void useMaster() {
        dataSourceHolder.set(DBRole.MASTER.name());
    }

    public static void useSlave() {
        dataSourceHolder.set(DBRole.SLAVE.name());
    }

    public static void clear() {
        dataSourceHolder.remove();
    }

    protected Object determineCurrentLookupKey() {
        String role = dataSourceHolder.get();
        if (null == role) {
            return DBRole.MASTER.name();
        }
        return role;
    }
}
