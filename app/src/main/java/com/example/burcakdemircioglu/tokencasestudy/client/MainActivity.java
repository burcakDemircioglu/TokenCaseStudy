package com.example.burcakdemircioglu.tokencasestudy.client;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.burcakdemircioglu.tokencasestudy.R;
import com.example.burcakdemircioglu.tokencasestudy.model.QR;
import com.example.burcakdemircioglu.tokencasestudy.server.PaymentRESTTask;
import com.example.burcakdemircioglu.tokencasestudy.server.RequestQRRESTTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

import static com.example.burcakdemircioglu.tokencasestudy.model.QR.RECEIPT_DATETIME_TAG;
import static com.example.burcakdemircioglu.tokencasestudy.model.QR.TRANSACTION_AMOUNT_TAG;
import static com.example.burcakdemircioglu.tokencasestudy.model.QR.TRANSACTION_CURRENCY_TAG;
import static com.example.burcakdemircioglu.tokencasestudy.model.QR.getCurrencyString;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private View buttonPay;
    private View thick;
    private QR qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mTextViewResult = findViewById(R.id.text_view_result);
        buttonPay = findViewById(R.id.buttonPay);
        buttonPay.setVisibility(View.INVISIBLE);
        thick = findViewById(R.id.thick);
        thick.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
    }

    public void requestQR(View v) throws ExecutionException, InterruptedException {
        thick.setVisibility(View.INVISIBLE);

        final ResponseEntity<String> stringResponseEntity = new RequestQRRESTTask().execute().get();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (stringResponseEntity != null) {
                    String response = stringResponseEntity.getBody();
                    try {
                        JSONObject jsonCell = new JSONObject(response);
                        String qrString = jsonCell.getString("QRdata");
                        qr = new QR(qrString);
                        qr.qrString = qrString;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String stringBuilder = "Date: " +
                            qr.tagsToValues.get(RECEIPT_DATETIME_TAG) + "\n" +
                            "Payment amount: " +
                            qr.tagsToValues.get(TRANSACTION_AMOUNT_TAG) +
                            " " + getCurrencyString(qr.tagsToValues.get(TRANSACTION_CURRENCY_TAG));
                    mTextViewResult.setText(stringBuilder);
                    buttonPay.setVisibility(View.VISIBLE);
                } else {
                    mTextViewResult.setText(R.string.qr_error);
                }
            }
        });
    }

    public void requestPayment(View v) throws ExecutionException, InterruptedException {
        thick.setVisibility(View.INVISIBLE);

        final ResponseEntity<String> stringResponseEntity = new PaymentRESTTask(qr).execute().get();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (stringResponseEntity != null) {

                    HttpStatus statusCode = stringResponseEntity.getStatusCode();
                    if (statusCode == HttpStatus.OK) {
                        thick.setVisibility(View.VISIBLE);
                        mTextViewResult.setText(R.string.payment_success);
                        buttonPay.setVisibility(View.INVISIBLE);
                    } else {
                        mTextViewResult.setText(R.string.payment_error);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
