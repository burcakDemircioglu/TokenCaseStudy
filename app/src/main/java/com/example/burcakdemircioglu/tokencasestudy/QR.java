package com.example.burcakdemircioglu.tokencasestudy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QR {

    Map<String, String> tagsToValues = new HashMap<>();

    public QR() {
        tagsToValues.put("00", "");
        tagsToValues.put("53", "");
        tagsToValues.put("54", "");
        tagsToValues.put("80", "");
        tagsToValues.put("81", "");
        tagsToValues.put("82", "");
        tagsToValues.put("83", "");
        tagsToValues.put("86", "");
        tagsToValues.put("87", "");
        tagsToValues.put("89", "");
        tagsToValues.put("84", "");
        tagsToValues.put("88", "");
    }

    public static QR parseQR(String qrString) {
        List<String> list = new ArrayList<>();

        QR qr = new QR();
        String qrToBeParsed = qrString;
        if (qrString != null)
            while (qrToBeParsed.length() > 0) {
                String tag = qrToBeParsed.substring(0, 2);
                String length = qrToBeParsed.substring(2, 4);
                String value = qrToBeParsed.substring(4, Integer.parseInt(length) + 4);
                qr.tagsToValues.put(tag, value);
                qrToBeParsed = qrToBeParsed.substring(Integer.parseInt(length) + 4);
            }
        return qr;
    }
}
