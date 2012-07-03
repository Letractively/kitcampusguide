package edu.kit.cm.ivu.auth.qr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpTask extends AsyncTask<String, Void, HttpResponse> {
	
	private String error;
	public static final String TAG = "KIT_AUTHENTICATOR_HTTP";

    private KitAuthenticatorActivity activity;
    private ArrayList<NameValuePair> nameValuePairs;
    
    public AsyncHttpTask(KitAuthenticatorActivity activity) {
        this.activity = activity;
        this.nameValuePairs = new ArrayList<NameValuePair>();
    }
    
    public void addNameValuePair(String name, String value) {
        nameValuePairs.add(new BasicNameValuePair(name, value));
    }
    
    protected HttpResponse doInBackground(String... urls) {
        // Create a new HttpClient and post header.
    	HttpParams parameters = new BasicHttpParams();
    	HttpConnectionParams.setConnectionTimeout(parameters, 10000);
    	HttpConnectionParams.setSoTimeout(parameters, 10000);
        HttpClient httpClient = new DefaultHttpClient(parameters);
        HttpPost httpPost = new HttpPost(urls[0]);
        
        HttpResponse response = null;

        try {
            if (nameValuePairs.size() > 0) {
                // Add data to HTTP request.
            	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request.
                response = httpClient.execute(httpPost);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            error = e.getMessage();
            return null;
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			error = e.getMessage();
            return null;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
			error = e.getMessage();
            return null;
		}
        
        return response;
    }

    protected void onPostExecute(HttpResponse response) {
    	if (response == null) {
    		activity.httpError(error);
    		return;
    	}
    	
    	// Check if HTTP request was successful.
    	if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            activity.httpSuccess();
    	} else {
    		error = "";
			try {
				InputStream content = response.getEntity().getContent();
				
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	            String s = "";
	            while ((s = buffer.readLine()) != null) {
	            	error += s;
	            }
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
            
    		activity.httpError(error);
    	}
        
    }

}
