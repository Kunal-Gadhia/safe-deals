/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.enquiry;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author hp
 */
@Service
@Transactional
public class EnquiryService {

    public static final String ACCOUNT_SID = "AC96ce189faa1c77694353b230987c8d82";
    public static final String AUTH_TOKEN = "e9bf1ef76e1ec2c780d665c045fac01e";
    public static final String TWILIO_NUMBER = "+12672142839";

    public static void sendSms() {
        System.out.println("Coming to service");
        try {
            System.out.println("In Try");
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "Hello, Thank you for your intrest in SafeDeals,our customer care executive will get back to you soon regarding your query."));
            params.add(new BasicNameValuePair("To", "+91 XXXXXXXXXX"));
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
            System.out.println("Message :" + message.getSid());
        } catch (TwilioRestException e) {
            System.out.println("In Catch");
            System.out.println(e.getErrorMessage());
        }
    }

}
