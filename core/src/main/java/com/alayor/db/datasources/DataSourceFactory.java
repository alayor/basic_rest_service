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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        PropertyManager propertyManager = new PropertyManager("core.properties");
        dataSource.setDriverClassName(propertyManager.getProperty("dev" + ".driver_class"));
        dataSource.setUsername(propertyManager.getProperty("dev" + ".db_user"));
        dataSource.setPassword(propertyManager.getProperty("dev" + ".db_password"));
        dataSource.setUrl(propertyManager.getProperty("db_url") + propertyManager.getProperty("dev" + ".database"));
        return dataSource;
    }
}
