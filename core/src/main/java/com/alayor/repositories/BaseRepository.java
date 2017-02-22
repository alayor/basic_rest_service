package com.alayor.repositories;

import com.alayor.db.datasources.DataSourceFactory;
import com.alayor.db.query_agent.QueryAgent;

/**
 * Class with basic functionality to use among all Repositories.
 */
public abstract class BaseRepository
{
    private final QueryAgent queryAgent;

    public BaseRepository()
    {
        this.queryAgent = new QueryAgent(new DataSourceFactory().createDataSource());
    }

    public BaseRepository(QueryAgent queryAgent)
    {
        this.queryAgent = queryAgent;
    }

    /**
     * Returns the query agent object that will be used by repositories
     * to interact with the database.
     * @return The query agent object.
     */
    QueryAgent getQueryAgent()
    {
        return queryAgent;
    }
}
