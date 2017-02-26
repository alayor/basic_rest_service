package com.alayor.api_service;

import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@Component
public class OffersService
{
    public ServiceResult<List<OfferRS>> getAvailableOffers()
    {
        List<OfferRS> availableOffers = createOffers();
        return new ServiceResult<>(true, "", availableOffers);
    }

    private List<OfferRS> createOffers() {
        List<OfferRS> list = new ArrayList<>();
        list.add(createOffer("100", "USD", "London", "Madrid", false));
        list.add(createOffer("200", "MXN", "Monterrey", "Cancun", true));
        return list;
    }

    private OfferRS createOffer(String amount, String currency, String from, String to, boolean isPurchased) {
        return new OfferRS(
                amount,
                currency,
                from,
                to,
                isPurchased
        );
    }
}
