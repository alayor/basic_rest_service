package com.alayor.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility used to retrieve properties from a specified file.
 */
public class PropertyManager
{
    private final String propertiesFile;
    private final Properties properties;

    public PropertyManager(String propertiesFileName)
    {
        this.propertiesFile = propertiesFileName;
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties()
    {
        InputStream inputStream = PropertyManager.class.getClassLoader().getResourceAsStream(propertiesFile);
        if (inputStream != null)
        {
            try
            {
                properties.load(inputStream);
            }
            catch (IOException e)
            {
                final String error = "Could not load property file: " + propertiesFile;
                throw new RuntimeException(error);
            }
        }
        else
        {
            final String error = "Could not find property file: " + propertiesFile;
            throw new RuntimeException(error);
        }
    }

    /**
     * Retrieves a specified property.
     * @param key used to retrieve the property.
     * @return the property specified by the key.
     */
    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }
}
