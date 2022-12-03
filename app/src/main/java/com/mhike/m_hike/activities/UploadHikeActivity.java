package com.mhike.m_hike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.mhike.m_hike.R;
import com.mhike.m_hike.classes.DatabaseHelper;
import com.mhike.m_hike.classes.JsonThread;
import com.mhike.m_hike.utilities.Helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UploadHikeActivity extends AppCompatActivity {
    private final Activity CURRENT_ACTIVITY = UploadHikeActivity.this;
    private DatabaseHelper db;
    private Context context;
    private WebView browser;

    // used for by the Json Thread class
    public WebView getBrowser() {
        return browser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hike);
        setTitle(getString(R.string.upload_hike));
        context = getApplicationContext();
        Helper.getIntentMessage(context, getIntent().getExtras());
        db = DatabaseHelper.getInstance(context);

        browser = (WebView) findViewById(R.id.webkit);
        try {
            URL pageURL = new URL(getString(R.string.web_service_url));
            JsonThread.trustAllHosts();
            HttpURLConnection con =
                    (HttpURLConnection) pageURL.openConnection();
            // this must come from the database
            String jsonString = getString(R.string.json);

            JsonThread myTask = new JsonThread(this, con, jsonString);
            Thread t1 = new Thread(myTask, "JSON Thread");
            t1.start();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}