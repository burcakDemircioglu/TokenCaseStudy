package com.example.burcakdemircioglu.tokencasestudy.model;

import java.util.HashMap;
import java.util.Map;

public class QR {

    public String qrString;
    public Map<String, String> tagsToValues = new HashMap<>();

    public static final String PAYLOAD_FORMAT_INDICATOR_TAG = "00";
    public static final String TRANSACTION_CURRENCY_TAG = "53";
    public static final String TRANSACTION_AMOUNT_TAG = "54";
    public static final String ARCELIK_QR_VERSION_TAG = "80";
    public static final String TRANSACTION_TYPE_TAG = "81";
    public static final String RECEIPT_DATETIME_TAG = "82";
    public static final String RECEIPT_ID_TAG = "83";
    public static final String VAT_STR_TAG = "86";
    public static final String POS_ID_TAG = "87";
    public static final String BATCH_NUMBER_TAG = "89";
    public static final String SESSION_ID_TAG = "84";
    public static final String SECURE_QR_SIGNATURE_TAG = "88";

    public QR(String qrString) {
        this.qrString = qrString;

        String qrToBeParsed = qrString;
        if (qrString != null)
            while (qrToBeParsed.length() > 0) {
                String tag = qrToBeParsed.substring(0, 2);
                String length = qrToBeParsed.substring(2, 4);
                String value = qrToBeParsed.substring(4, Integer.parseInt(length) + 4);
                tagsToValues.put(tag, value);
                qrToBeParsed = qrToBeParsed.substring(Integer.parseInt(length) + 4);
            }
    }

    public static String getCurrencyString(String value) {
        if (value.equals("949"))
            return "TRY";
        return "";
    }
}