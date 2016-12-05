/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.enquiry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hp
 */
@Service
@Transactional
public class EnquiryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Boolean sendSms() throws IOException {
        logger.info("swap calling");
        // logger.info("client no : ", clientNumber);
        try {
            String phoneNumber = "9766136110";
            String appKey = "153b70e2-4d6e-4a20-ba1c-4fc389b81406";
            String appSecret = "I4CDKOG4oEizdnfWhmkk7w==";
            String message = "Hello, world!";
            URL url = new URL("https://messagingapi.sinch.com/v1/sms/" + phoneNumber);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            String userCredentials = "application\\" + appKey + ":" + appSecret;
            byte[] encoded = Base64.encodeBase64(userCredentials.getBytes());
            String basicAuth = "Basic " + new String(encoded);
            connection.setRequestProperty("Authorization", basicAuth);
            String postData = "{\"Message\":\"" + message + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());
            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            os.close();
            System.out.println("response " + response.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
