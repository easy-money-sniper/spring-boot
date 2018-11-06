package com.github.xl.inject;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 10:39
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceHolder;

    static {
        dataSourceHolder = ThreadLocal.withInitial(DBRole.MASTER::name);
    }

    public DynamicDataSource(final DataSource master, final DataSource slave) {
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>() {{
            put(DBRole.MASTER, master);
            put(DBRole.SLAVE, slave);
        }};
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(master);
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

    public enum DBRole {
        MASTER,
        SLAVE
    }
}
