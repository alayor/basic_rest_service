package com.alayor.api_service;

import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.DownloadPdfEmailRQ;
import com.alayor.api_service.model.requests.SendEmailRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static java.util.Optional.empty;
import static javax.mail.Transport.send;

/**
 * Service used to process email notifications and PDF files.
 */
@Component
public class NotificationService
{
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Sends an email to the user with the ticket information.
     * @param sendEmailRQ with the information to be sent.
     * @return Success or failure depending on the case.
     */
    public ServiceResult<Optional> sendEmailToUser(SendEmailRQ sendEmailRQ)
    {
        Optional<User> userById = userRepository.getUserById(sendEmailRQ.getUserId());
        if (!userById.isPresent())
        {
            return new ServiceResult<>(false, "An error occurred and email was not sent :(", empty());
        }
        try
        {
            sendEmail(userById.get().getEmail(), createTicketInformationTable(sendEmailRQ));
        }
        catch (MessagingException e)
        {
            return new ServiceResult<>(false, "An error occurred and email was not sent :(", empty());
        }
        return new ServiceResult<>(true, "Email was successfully sent.", empty());
    }

     void sendEmail(String to, String body) throws MessagingException
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("lealpoints@gmail.com", "aayala88");
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("lealpoints@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Here is your ticket buying information.");
        message.setContent(body, "text/html; charset=utf-8");
        send(message);
    }

    private String createTicketInformationTable(SendEmailRQ sendEmailRQ)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body>");
        sb.append("<table>");
        appendHeaders(sb);
        appendTickerInformation(sendEmailRQ, sb);
        sb.append("<table>");
        sb.append("</body></html>");
        return sb.toString();
    }

    private void appendHeaders(StringBuilder sb)
    {
        sb.append("<thead>");
        sb.append("<th>Amount</th>");
        sb.append("<th>From</th>");
        sb.append("<th>To</th>");
        sb.append("<th>Quantity</th>");
        sb.append("</thead>");
    }

    private void appendTickerInformation(SendEmailRQ sendEmailRQ, StringBuilder sb)
    {
        sb.append("<tbody>");
        sb.append("<td>").append(sendEmailRQ.getCurrency()).append(" ").append(sendEmailRQ.getAmount()).append("</td>");
        sb.append("<td>").append(sendEmailRQ.getFrom()).append("</td>");
        sb.append("<td>").append(sendEmailRQ.getTo()).append("</td>");
        sb.append("<td>").append(sendEmailRQ.getQuantity()).append("</td>");
        sb.append("</tbody>");
    }

    /**
     * Creates a PDF file using the provided information.
     * @param filename of the new PDF file.
     * @param downloadPdfEmailRQ Contains the information that the PDF file will have.
     * @throws DocumentException is thrown in case of any error with the PDF file generation.
     * @throws IOException is thrown in case with any error with the creation of the file.
     */
    public void createPdf(String filename, DownloadPdfEmailRQ downloadPdfEmailRQ) throws DocumentException, IOException
    {
        Document document = new Document();
        FileOutputStream os = new FileOutputStream(filename);
        getInstance(document, os);
        document.open();
        document.add(new Paragraph("Amount: " + downloadPdfEmailRQ.getCurrency() + " " + downloadPdfEmailRQ.getAmount()));
        document.add(new Paragraph("From: " + downloadPdfEmailRQ.getFrom()));
        document.add(new Paragraph("To: " + downloadPdfEmailRQ.getTo()));
        document.add(new Paragraph("Quantity: " + downloadPdfEmailRQ.getQuantity()));
        document.close();
        os.flush();
    }
}
