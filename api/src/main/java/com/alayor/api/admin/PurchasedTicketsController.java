package com.alayor.api.admin;

import com.alayor.api_service.PurchasedTicketsService;
import com.alayor.api_service.model.responses.PurchasedTicketWithUser;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller used by admin users to get purchased tickets
 */
@RestController
@RequestMapping("/admin/purchased_tickets")
public class PurchasedTicketsController
{
    private final PurchasedTicketsService purchasedTicketsService;

    @Autowired
    public PurchasedTicketsController(PurchasedTicketsService purchasedTicketsService)
    {
        this.purchasedTicketsService = purchasedTicketsService;
    }

    /**
     * Retrieves all the tickets purchased by users along with the ticket information.
     * @return All tickets purchased by users
     */
    @RequestMapping(value = "", method = GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<List<PurchasedTicketWithUser>>> getAllPurchasedTicketsWithUsers()
    {
        ServiceResult<List<PurchasedTicketWithUser>> serviceResult = purchasedTicketsService.getAllPurchasedTicketsWithUsers();
        return new ResponseEntity<>(serviceResult, OK);
    }
}
