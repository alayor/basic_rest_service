package com.alayor.db.datasources;

import com.alayor.tools.PropertyManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Class used to create a DataSource using the properties file.
 */
public class DataSourceFactory
{
    public DriverManagerDataSource createDataSource()
    {
        return create("dev");
    }

    private DriverManagerDataSource create(String env) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        PropertyManager propertyManager = new PropertyManager("core.properties");
        dataSource.setDriverClassName(propertyManager.getProperty(env + ".driver_class"));
        dataSource.setUsername(propertyManager.getProperty(env + ".db_user"));
        dataSource.setPassword(propertyManager.getProperty(env + ".db_password"));
        dataSource.setUrl(propertyManager.getProperty(env + ".db_url") + propertyManager.getProperty(env + ".database"));
        return dataSource;
    }

    public DriverManagerDataSource createDataSourceForTests()
    {
        return create("test");
    }
}
