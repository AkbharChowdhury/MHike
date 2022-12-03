package com.mhike.m_hike.classes;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import com.mhike.m_hike.activities.UploadHikeActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class JsonThread implements Runnable {
    private AppCompatActivity activity;
    private HttpURLConnection con;
    private String jsonPayLoad;

    public JsonThread(AppCompatActivity activity,
                      HttpURLConnection con, String jsonPayload) {
        this.activity = activity;
        this.con = con;
        this.jsonPayLoad = jsonPayload;
    }


    @Override
    public void run() {
        String response = prepareConnection() ? postJson() : "Error preparing the connection";
        showResult(response);
    }


    private boolean prepareConnection() {
        try {
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            return true;

        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String postJson() {
        String response = "";
        try {
            String postParameters = "jsonpayload="
                    + URLEncoder.encode(jsonPayLoad, "UTF-8");
            con.setFixedLengthStreamingMode(postParameters.getBytes().length);
            PrintWriter out = new PrintWriter(con.getOutputStream());
            out.print(postParameters);
            out.close();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = readStream(con.getInputStream());
            } else {
                response = "Error contacting server: " + responseCode;
            }
        } catch (Exception e) {
            response = "Error executing code: " + e.getMessage();
        }
        return response;
    }

    private void showResult(String response) {
        activity.runOnUiThread(() -> {
            String page = generatePage(response);
            ((UploadHikeActivity) activity).getBrowser().loadData(page,
                    "text/html", "UTF-8");
        });
    }

    private String generatePage(String content) {
        return "<html><body><p>" + content + "</p></body></html>";
    }

    private String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in))) {
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        @SuppressLint("CustomX509TrustManager") TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}