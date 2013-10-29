package ru.dtlbox.chatsecure.api;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class RequestHandler {

	public final static String LOG_TAG = "RequestHandler";

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

	protected String executePostRequest(final String url, JSONObject parameters) {

		Log.i(LOG_TAG + "/executePostRequest", "url: " + url);
		Log.i(LOG_TAG + "/executePostRequest", "parameters: " + parameters.toString());
        String result = null;
        try {

            HttpClient httpClient = new DefaultHttpClient();
			HttpPost postUrl = new HttpPost(url);

			postUrl.setHeader("Content-type", "application/json");

            StringEntity content = new StringEntity(parameters.toString(), HTTP.UTF_8);
            postUrl.setEntity(content);

            HttpResponse response = httpClient.execute(postUrl);

            result = convertStreamToString( response.getEntity().getContent() );

    	} catch (org.apache.http.client.ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
    	} catch (Exception e) {
			e.printStackTrace();
		}

        return result;
    }
}
