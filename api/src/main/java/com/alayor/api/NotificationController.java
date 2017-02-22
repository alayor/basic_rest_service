package com.alayor.api;

import com.alayor.api_service.NotificationService;
import com.alayor.api_service.model.requests.DownloadPdfEmailRQ;
import com.alayor.api_service.model.requests.SendEmailRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Controller used to send email notifications or
 * generate and send pdf files with ticket information
 */
@RestController
@RequestMapping("/notification")
public class NotificationController
{

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    /**
     * Sends an email to the user in the entity param with the ticket information.
     * @param entity Contains information about the ticket and the user to send the email to.
     * @return Optional value representing success or failure.
     */
    @RequestMapping(value = "/email", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<Optional>> sendEmail(HttpEntity<String> entity)
    {
        ServiceResult<Optional> optionalServiceResult = notificationService.sendEmailToUser(new SendEmailRQ(entity.getBody()));
        return new ResponseEntity<>(optionalServiceResult, OK);
    }

    /**
     * Created a PDF file with the ticket information.
     * @param entity Contains information about the ticket and the user to generate the PDF file.
     * @return The url to retrieve the generated PDF file.
     */
    @RequestMapping(value = "/generate_pdf", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<String>> generatePdf(HttpEntity<String> entity)
    {
        String name = randomAlphabetic(10);
        try
        {
            notificationService.createPdf("public/" + name + ".pdf", new DownloadPdfEmailRQ(entity.getBody()));
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ServiceResult<>(false, "Error generating PDF file", ""),
                    INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ServiceResult<>(true, "", name), OK);
    }

    /**
     * Retrieves a PDF file generated previously by generatePdf method.
     * @param fileName Name of the file to be retrieved.
     * @param response It's filled with the pdf data in its body.
     */
    @RequestMapping(value = "/pdf/{name}", method = GET)
    public void getPDFFile(@PathVariable("name") String fileName, HttpServletResponse response)
    {
        try
        {
            InputStream is = new FileInputStream("public/" + fileName + ".pdf");
            copy(is, response.getOutputStream());
            response.flushBuffer();
        }
        catch (IOException ex)
        {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }
}
