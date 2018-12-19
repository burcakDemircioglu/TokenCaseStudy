package com.example.burcakdemircioglu.tokencasestudy.server;

import android.os.AsyncTask;

import com.example.burcakdemircioglu.tokencasestudy.BuildConfig;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RequestQRRESTTask extends AsyncTask<String, Void, ResponseEntity<String>> {

    @Override
    protected ResponseEntity<String> doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.set("x-ibm-client-id", BuildConfig.X_IBM_CLIENT_ID);
            headers.set("x-ibm-client-secret", BuildConfig.X_IBM_CLIENT_SECRET);
            String input = "{\"totalReceiptAmount\":100}";
            HttpEntity<String> entity = new HttpEntity<>(input, headers);
            String url = BuildConfig.CAMPAIGN_QR_API_URL + "get_qr_sale";

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

        } catch (Exception ex) {
            String message = ex.getMessage();
            return null;
        }
    }
}
