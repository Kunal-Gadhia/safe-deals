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
    public static final String TWILIO_NUMBER = "+15097745141";

    public static void sendSms() {
        System.out.println("Coming to service");
        try {
            System.out.println("In Try");
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "Hello, Thank you for your intrest in SafeDeals,our customer care executive will get back to you soon regarding your query."));
            params.add(new BasicNameValuePair("To", "+919766136110")); //Add real number here
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
            System.out.println("Message :"+message.getSid());
        } catch (TwilioRestException e) {
            System.out.println("In Catch");
            System.out.println(e.getErrorMessage());
        }
        //////////////////////////////////////////////
//        TwilioRestClient client = new TwilioRestClient("YOUR_TWILIO_ACCOUNT_SID", "YOUR_TWILIO_AUTH_TOKEN");
// 
//        Account account = client.getAccount();
// 
//        SmsFactory factory = account.getSmsFactory();
// 
//        HashMap<String, String> message = new HashMap<>();
// 
//        message.put("To", "YOUR_PHONE_NUMBER");
//        message.put("From", "YOUR_TWILIO_PHONE_NUMBER");
//        message.put("Body", "Ahoy from Twilio!");
// 
//        factory.create(message);
        //////////////////////////////////////////////
//        try {
//            System.out.println("In Try");
//            String phoneNumber = "9766136110";
//            String appKey = "153b70e2-4d6e-4a20-ba1c-4fc389b81406";
//            String appSecret = "I4CDKOG4oEizdnfWhmkk7w==";
//            String message = "Hello, world!";
//            URL url = new URL("https://messagingapi.sinch.com/v1/sms/" + phoneNumber);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            String userCredentials = "application\\" + appKey + ":" + appSecret;
//            byte[] encoded = Base64.encodeBase64(userCredentials.getBytes());
//            String basicAuth = "Basic " + new String(encoded);
//            connection.setRequestProperty("Authorization", basicAuth);
//            String postData = "{\"Message\":\"" + message + "\"}";
//            OutputStream os = connection.getOutputStream();
//            os.write(postData.getBytes());
//            StringBuilder response = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                response.append(line);
//            }
//            br.close();
//            os.close();
//            System.out.println("response " + response.toString());
////            return true;
//        } catch (IOException e) {
//            System.out.println("In Catch");
//            e.printStackTrace();
////            return false;
//        }
    }

}
