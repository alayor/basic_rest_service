package com.alayor.api;

import com.alayor.api_service.PurchaseService;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Controller used to purchase tickets
 */
@RestController
@RequestMapping("/purchase")
public class PurchaseController
{
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService)
    {
        this.purchaseService = purchaseService;
    }

    /**
     * Runs ticket purchasing process
     * @param entity Contains information about the ticket to be processed
     * @return Success or failure depending on the process result.
     */
    @RequestMapping(value = "", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult> buyTicket(HttpEntity<String> entity)
    {
        try
        {
            ServiceResult serviceResult = purchaseService.buyTicket(new BuyTicketRQ(entity.getBody()));
            return new ResponseEntity<>(serviceResult, OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ServiceResult(false), INTERNAL_SERVER_ERROR);
        }
    }
}
