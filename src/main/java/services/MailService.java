package services;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class MailService {

    public void sendMailConfirmedPayment(String name,String userEmail, double price, int idOfCourse){
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "cursago@gmail.com");
        props.put("mail.smtp.password", "cursago1234");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("cursago@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("[Cursago] Payment has been verified!");
            message.setText("Hello " + name +", \n" +
                            "We have registered a payment for the amount of $ " + price+". \n" +
                            "Wait no longer and begin your course! Link: http://localhost:4200/course/" + idOfCourse);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, "cursago@gmail.com", "cursago1234");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
