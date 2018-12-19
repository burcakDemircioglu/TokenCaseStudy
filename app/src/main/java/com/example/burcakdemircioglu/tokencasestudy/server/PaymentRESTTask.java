package com.example.burcakdemircioglu.tokencasestudy.server;

import android.os.AsyncTask;

import com.example.burcakdemircioglu.tokencasestudy.BuildConfig;
import com.example.burcakdemircioglu.tokencasestudy.model.QR;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.example.burcakdemircioglu.tokencasestudy.model.QR.TRANSACTION_AMOUNT_TAG;
import static com.example.burcakdemircioglu.tokencasestudy.model.QR.TRANSACTION_CURRENCY_TAG;
import static com.example.burcakdemircioglu.tokencasestudy.model.QR.VAT_STR_TAG;

public class PaymentRESTTask extends AsyncTask<String, Void, ResponseEntity<String>> {

    private QR qr;

    public PaymentRESTTask(QR qr) {
        this.qr = qr;
    }

    @Override
    protected ResponseEntity<String> doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("x-ibm-client-id", BuildConfig.X_IBM_CLIENT_ID);
            headers.set("x-ibm-client-secret", BuildConfig.X_IBM_CLIENT_SECRET);
            String input = "{\"returnCode\":1000," +
                    "\"returnDesc\":\"success\"," +
                    "\"receiptMsgCustomer\":\"beko Campaign\"," +
                    "\"receiptMsgMerchant\":\"beko Campaign Merchant\"," +
                    "\"paymentInfoList\":[{\"paymentProcessorID\":67," +
                    "                       \"paymentActionList\":[{\"paymentType\":3, " +
                    "                                               \"amount\":" + qr.tagsToValues.get(TRANSACTION_AMOUNT_TAG) + "," +
                    "                                               \"currencyID\":" + qr.tagsToValues.get(TRANSACTION_CURRENCY_TAG) + "," +
                    "                                               \"vatRate\":" + qr.tagsToValues.get(VAT_STR_TAG).split("-")[0] + "}]}]," +
                    "\"QRdata\":\"" + qr.qrString + "\"}";

            HttpEntity<String> entity = new HttpEntity<>(input, headers);
            String url = BuildConfig.CAMPAIGN_QR_API_URL + "payment";

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
