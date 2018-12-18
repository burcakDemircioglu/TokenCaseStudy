package com.example.burcakdemircioglu.tokencasestudy;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private String qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mTextViewResult = findViewById(R.id.text_view_result);
        setSupportActionBar(toolbar);

    }

    public void requestQR(View v) throws ExecutionException, InterruptedException {
        final ResponseEntity<String> stringResponseEntity = new RequestQRRESTTask().execute().get();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (stringResponseEntity != null) {
                    String response = stringResponseEntity.getBody();
                    mTextViewResult.setText(response);
                    try {
                        JSONObject jsonCell = new JSONObject(response);
                        qr = jsonCell.getString("QRdata");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void requestPayment(View v) throws ExecutionException, InterruptedException {
        final ResponseEntity<String> stringResponseEntity = new PaymentRESTTask(qr).execute().get();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (stringResponseEntity != null) {
                    String response = stringResponseEntity.getBody();
                    mTextViewResult.setText(response);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
