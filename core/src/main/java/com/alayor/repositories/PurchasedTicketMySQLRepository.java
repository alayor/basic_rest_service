package com.alayor.repositories;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.responses.PurchasedTicketWithUser;
import com.alayor.api_service.support.PurchasedTicketRepository;
import com.alayor.db.query_agent.DbBuilder;
import com.alayor.db.query_agent.QueryAgent;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

/**
 * Repository used to interact with the purchased_tickets table.
 */
@Component
public class PurchasedTicketMySQLRepository extends BaseRepository implements PurchasedTicketRepository
{
    public PurchasedTicketMySQLRepository()
    {
    }

    public PurchasedTicketMySQLRepository(QueryAgent queryAgent)
    {
        super(queryAgent);
    }

    /**
     * Insert a purchased ticket in the table.
     * @param purchasedTicket to be inserted.
     * @return the id of the new purchased ticket
     */
    @Override
    public long insert(PurchasedTicket purchasedTicket)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO purchased_tickets (user_id, amount, currency, origin, destination) VALUES (");
        appendValuesForInsertion(purchasedTicket, sql);
        sql.append(")");
        try
        {
            return getQueryAgent().executeInsert(sql.toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not save user into database", e);
        }
    }

    /**
     * Retrieves all purchased tickets from the database.
     * @return All the purchased tickets.
     */
    @Override
    public List<Optional<PurchasedTicketWithUser>> getAllPurchasedTicketsByUser()
    {
        try
        {
            return getQueryAgent().selectList(new DbBuilder<PurchasedTicketWithUser>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM purchased_tickets INNER JOIN users USING(user_id)";
                }

                @Override public Object[] values()
                {
                    return new Object[0];
                }

                @Override public Optional<PurchasedTicketWithUser> build(ResultSet resultSet) throws SQLException
                {
                    PurchasedTicket purchasedTicket = new PurchasedTicket(
                            resultSet.getString("user_id"),
                            resultSet.getString("amount"),
                            resultSet.getString("currency"),
                            resultSet.getString("origin"),
                            resultSet.getString("destination")
                    );

                    User user = new User(
                            resultSet.getLong("user_id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("active"),
                            resultSet.getString("api_key"),
                            resultSet.getBoolean("is_admin"),
                            resultSet.getString("account_id"));

                    PurchasedTicketWithUser purchasedTicketWithUser = new PurchasedTicketWithUser(user, purchasedTicket);
                    return of(purchasedTicketWithUser);
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get list of purchased tickets");
        }
    }

    private void appendValuesForInsertion(PurchasedTicket purchasedTicket, StringBuilder sql)
    {
        sql.append(purchasedTicket.getUserId()).append(", ");
        sql.append("'").append(purchasedTicket.getAmount()).append("'").append(", ");
        sql.append("'").append(purchasedTicket.getCurrency()).append("'").append(", ");
        sql.append("'").append(purchasedTicket.getFrom()).append("'").append(", ");
        sql.append("'").append(purchasedTicket.getTo()).append("'");
    }
}
