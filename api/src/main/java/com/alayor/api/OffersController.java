package com.alayor.api;

import com.alayor.api_service.OffersService;
import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller that retrieves available offers from external services
 */
@RestController
@RequestMapping("/offers")
public class OffersController
{
    private final OffersService offersService;

    @Autowired
    public OffersController(OffersService offersService)
    {
        this.offersService = offersService;
    }

    /**
     * Retrieves all available offers.
     * @return All the available offers.
     */
    @RequestMapping(value = "", method = GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<List<OfferRS>>> getAvailableOffers()
    {
        try
        {
            ServiceResult<List<OfferRS>> serviceResult = offersService.getAvailableOffers();
            return new ResponseEntity<>(serviceResult, OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ServiceResult<>(false), INTERNAL_SERVER_ERROR);
        }
    }
}
