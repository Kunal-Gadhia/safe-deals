/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.guidelines;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hp-pc
 */
@Service
@Transactional
public class GuidelinesService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Boolean sendBudgetReportEmail(String name, String email, String cashInHand, String loanEligibility, String grossBudget,
            String emiEligibility, String eligiblePropertyValue) throws AddressException, MessagingException {

        System.out.println("SERVICE name" + name);
        System.out.println("SERVICE email" + email);
        System.out.println("SERVICE cashInHand" + cashInHand);
        System.out.println("SERVICE loanEligibility" + loanEligibility);

        final String username = "sender@gmail.com";
        final String password = "senderspassword";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sender@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Safedeals Budget Report");
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText
                    = " <div style='text-align : center'>\n"
                    + "  </p><img src=\"cid:image\"> "
                    + " <h3> Budget Report </h3>"
                    + "  </div>"
                    + "<p>Dear <strong> " + name + "</strong>, thanks for using our system to know your disposable budget especially for buying property.</p> \n"
                    + "  <p> We at Safe Deals are committed to offer you genuine, unbiased and transparent information based on your input. Please go though the valuable guidelines in order to draw maximum benefits.\n"
                    + "  Based on your Annual Income, assests and liabilities here is a summary of your overall budget :\n"
                    + "<p><strong>Cash in Hand for spending on OC (Own Contribution) : Rs.</strong> " + cashInHand + "</p>\n"
                    + "<p><strong>Loan Eligibility through selected bank SBI : Rs.</strong>: " + loanEligibility + " </p>\n"
                    + "<p><strong>EMI for Loan Repayment for a period of 20 Yrs  Rs. </strong> " + emiEligibility + " </p>\n"
                    + "<p><strong>Budget for Property Value : Rs. </strong>  " + eligiblePropertyValue + "</p>\n"
                     //"                    <p><strong>Budget for Overheads </strong>  _______________</p>\n" +
                    + "<p><strong>Gross Budget including all resources : Rs. </strong> " + grossBudget + "</p>\n"
                     + "<div>\n"
                    + "<p class=\" bullet-point\"><i class=\"fa fa-circle fa-fw \"></i> <strong class=\"bullet-center\">Cautions :</strong></p>\n"
                    + "<p>These calculations are made on standard guidelines preveiling in lending policy of various banks and you must adhere to these calculation in order to avoid over spending.</p>\n"
                    + "<p>Do not put your entire money into one purchase untill unless it is properly planned and effectivly managed.</p>\n"
                    + "<p>Be practical while entering values for liquidable assests. Try always to account for 20% less value for calculation where market value is subjected to change.</p>\n"
                    + "<p>Take into consideration about planned expenditure and emergency funds.</p>\n"
                    + "</div>"
                    + "<div>\n"
                    + "<p class=\" bullet-point\"><i class=\"fa fa-circle fa-fw\"></i> <strong class=\"bullet-center\">Options available :</strong></p>\n"
                    + "<p> Now, look for various options available in your chosen locations based on your budget, type & size of property, livable amenities, design, specification, occupancy and offered price.\n"
                    + "</p>\n"
                    + "</div>"
                     + "<div>\n"
                    + "<p class=\" bullet-point\"><i class=\"fa fa-circle fa-fw\"></i> <strong class=\"bullet-center\">DO`s and DON`TS</strong></p>\n"
                    + "<strong>Know the DO`s and DON`TS in order to identify the best suitable property;</strong>\n"
                    + "<p> At this stage you know that these are short listed properties and you need to finalise or choose the best suitable one based on our guidelines.</p>\n"
                    + "\n"
                    + "<strong>DO`s :-</strong>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>    Select top two or three properties, meeting your requirements and prepare a comparison chart under various heads in order to choose the best one, like; size of property in terms of usable area, offered price to know whether it is realistic or not, attractive design and specifications to make your property look exclusive, livable amenities to enjoy your routine life, occupancy and supportive neighborhood to give you clear idea of development potential. Look out for amenities those are of actual use and not becomes a burden lateron.</p>\n"
                    + "\n"
                    + "<strong>DON`TS :-</strong>\n"
                    + "<p>Also, adhere to vital point check list to minimize risk and ensure overall success, like; </p>\n"
                    + "<strong>Never buy a property in the following circumstances</strong>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Without registered agreement to sale or sale deed. It assures you of legal and technical perfection.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Where property is not eligible for loan from nationalize bank.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For location if it is detached from the main city, especially for residential properties.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For location where no work places are nearby.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For location where source of basic amenities are doubtful, like; transportation, shopping, medical, school/college, parks etc.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For project not having clear and legal approach road.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   If approach road is not safe to travel by kids and ladies in odd hours.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For projects where unsold dwelling units are lying vacant for more than 2 years.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For ready project where occupancy is very low.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   For areas where there is a risk for health hazards like; low areas, near chemical/medicine industries, power plants, filthy lakes and areas prone to crimes.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Don’t buy overpriced property. Buy below market price or at least at realistic price.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Don’t just rely on verbal promises, ensure creditability of seller.</p>\n"
                    + "\n"
                    + "<strong>Process of finalizing the deal;</strong>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   At this point you must be clear about which property is most suitable for you. There comes the process of finalizing and it is better if it is done through real estate professional.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   You need to prepare, check list to cross check legal and technical marketability of property / project, prepare list of terms and conditions to be entered into conveyance document and valid points for price negotiation before finalizing the deals.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Above all you need to know what price to pay and what can be the acceptable variations.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   Keep in mind that your decision taken today is going to decide your future success or failure. There is no point of cursing your luck later if you do not pay intention to the guidelines today.</p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>   These guidelines are applicable universally, but if you have any specific enquiry  and you need any sort of assistance to take informed decision for buying property in and around Nagpur, feel free to contact us at 88888 43 880, 7755 92 3368. </p>\n"
                    + "<p><i class=\"fa fa-caret-right\"></i>     We offer free service to help you to identify suitable property and gets third party genuine information related to property buying.</p>\n"
                    + "<p> Created for public interest by Safe Deals, Nagpur.</p>\n"
                    + "<p>Wish you a successful buying.</p>\n"
                    + "<p>Good Luck!</p>\n"
                    + "</div>";
                    
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("/home/hp-pc/NetbeansProject/safe-deals/src/main/webapp/images/safedeals_email.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Done Email sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
