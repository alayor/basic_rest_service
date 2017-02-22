package com.alayor.api;

import com.alayor.api_service.NotificationService;
import com.alayor.api_service.model.requests.SendEmailRQ;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest
{
    private NotificationController notificationController;
    @Mock
    private NotificationService notificationService;

    @Before
    public void setUp() throws Exception
    {
        notificationController = new NotificationController(notificationService);
    }

    @Test
    public void shouldCallNotificationServiceToSendEmail()
    {
        //when
        notificationController.sendEmail(new HttpEntity<>(createSendEmail()));

        //then
        verify(notificationService).sendEmailToUser(any(SendEmailRQ.class));
    }

    private String createSendEmail()
    {
        Map<String, String> map = new HashMap<>();
        map.put("email", "alayor3@gmail.com");
        map.put("userId", "1");
        map.put("amount", "1000");
        map.put("currency", "USD");
        map.put("from", "DFW");
        map.put("to", "MEX");
        map.put("quantity", "2");
        return new JSONObject(map).toJSONString();
    }
}
