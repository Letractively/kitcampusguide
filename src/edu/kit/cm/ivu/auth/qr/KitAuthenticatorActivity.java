package edu.kit.cm.ivu.auth.qr;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.kit.cm.ivu.qrscanner.ObjectDetection;

public class KitAuthenticatorActivity extends Activity {
	
	public static final String TAG = "KIT_AUTHENTICATOR";
	private final int REGISTER_CONFIRMATION_DIALOG = 0;
	
	private boolean startScan;
	private QrMessage tempMessage;
	private int action;
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final Button scanButton = (Button) findViewById(R.id.scanButton);
		scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startScanActivity();
            }
        });
		
		// Run the scanner after the first program start immediately.
		startScan = true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	if (startScan)
    		startScanActivity();
    }
    
    private void startScanActivity() {
    	Intent i = new Intent(ObjectDetection.ACTION_GET_IDENTIFIER);
		i.setType("detector/qr");
		startActivityForResult(i, ObjectDetection.GET_IDENTIFIER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Don't start scanner again.
    	startScan = false;
    	
		// Check if the scanner found and decoded a QR-Code.
		if (resultCode == Activity.RESULT_OK && requestCode == ObjectDetection.GET_IDENTIFIER_REQUEST_CODE) {
			
		    // Get byte data from the QR-Code.
		    byte[] result = data.getByteArrayExtra("SCAN_RESULT_BYTE_SEGMENTS_0");
		    
		    try {
				QrMessage message = QrParser.parse(result);
				// Save message as attribute to have access to it later.
				tempMessage = message;
				switch (message.action()) {
				case REGISTER:
					// Show confirmation dialog.
					action = 0;
					showDialog(REGISTER_CONFIRMATION_DIALOG);
				    break;
				case LOGIN:
					// Directly send login message.
					action = 1;
					sendLoginMessage();
				    break;
				default:
				    break;
				}
		    } catch (QrException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				httpError(e.getMessage());
		    }
		}
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	
    	switch(id) {
    	case REGISTER_CONFIRMATION_DIALOG:
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage("Wollen Sie das Gerät jetzt registrieren?");
    		builder.setCancelable(false);
    		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				sendRegisterMessage();
    			}
    		});
    		builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		dialog = builder.create();
    		break;
    	default:
    		dialog = null;
    		break;
    	}
    	
    	return dialog;
    }
    
    /**
     * Sends a HTTP Post request containing a register message.
     */
    private void sendRegisterMessage() {
    	// Show loading image.
    	displayLoading("Gerät wird registriert...");
    	
    	// Cast the message.
	    QrRegisterMessage regMessage = (QrRegisterMessage) tempMessage;
	    
	    // Prepare the HTTP post request.
	    AsyncHttpTask task = new AsyncHttpTask(this);
	    task.addNameValuePair("action", "1");
	    task.addNameValuePair("userId", regMessage.userId());
	    task.addNameValuePair("deviceId", this.getDeviceId());
	    task.addNameValuePair("deviceName", this.getDeviceName());
	    
	    // Execute the HTTP post request.
	    task.execute(regMessage.responseUrl());
    }
    
    private void sendLoginMessage() {
    	// Cast the message.
    	QrLoginMessage loginMessage = (QrLoginMessage) tempMessage;
    	
    	// Convert provider to string.
    	byte[] val = { loginMessage.provider().value() };
    	String provider = String.valueOf(new BigInteger(val).intValue());
    	
    	// Check if provider is registered.
    	SharedPreferences pref = getPreferences(MODE_PRIVATE);
    	boolean registered = pref.getBoolean(provider + "_registered", false);
    	if (!registered) {
    		// Show error message.
    		displayResult("Fehler beim Login:\n" + "Das Gerät ist bei diesem Provider nicht registriert.");
        	return;
    	}
    	
    	// Retrieve parameters from preferences.
    	String url = pref.getString(provider + "_url", "0");
        String userId = pref.getString(provider + "_userId", "0");
        String secret = pref.getString(provider + "_secret", "0");
        
        if (url.equals("0") || userId.equals("0") || secret.equals("0")) {
        	displayResult("Fehler beim Login:\n" + "Die Registrierungsdaten auf diesem Gerät sind fehlerhaft."
        			+ "Bitte registrieren Sie sich erneut.");
        }
        
        // Calculate the challenge hash.
        byte[] secretBytes = fromHexString(secret);
        byte[] challengeBytes = loginMessage.challengeBytes();
        byte[] challengeHashBytes = this.encrypt(challengeBytes, secretBytes);
        String challengeHash = QrMessage.byteArrayToHexString(challengeHashBytes);
        String challenge = loginMessage.challenge();
	    
	    // Prepare the HTTP post request.
	    AsyncHttpTask task = new AsyncHttpTask(this);
	    task.addNameValuePair("action", "2");
	    task.addNameValuePair("userId", userId);
	    task.addNameValuePair("challenge", challenge);
	    task.addNameValuePair("challengeHash", challengeHash);
	    
	    // Show loading image.
    	displayLoading("Login wird durchgeführt...");
    	
	    // Execute the HTTP post request.
	    task.execute(url);
    }
    
    /**
     * Callback method, called if the HTTP request was successful.
     */
    public void httpSuccess() {
    	// Display success message and save parameters if registration.
    	switch(action) {
    	case 0:
    		saveRegistrationData();
    		displayResult("Gerät erfolgreich registriert.");
    		break;
    	case 1:
    		displayResult("Login erfolgreich durchgeführt.");
    		break;
    	default:
    		displayResult("Error: Invalid action!");
    		break;
    	}
    }
    
    /**
     * Callback method, called if an error occurred during a HTTP request.
     * @param error A string containing the error message.
     */
    public void httpError(String error) {
    	// Display error message.
    	switch(action) {
    	case 0: displayResult("Fehler bei der Registrierung:\n" + error); break;
    	case 1: displayResult("Fehler beim Login:\n" + error); break;
    	default: displayResult("Error: Invalid action!"); break;
    	}
    }
    
    /**
     * Saves the registration data to the internal storage.
     */
    private void saveRegistrationData() {
    	QrRegisterMessage regMessage = (QrRegisterMessage) tempMessage;
    	byte[] val = { regMessage.provider().value() };
    	String provider = String.valueOf(new BigInteger(val).intValue());
    	
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putBoolean(provider + "_registered", true);
        editor.putString(provider + "_url", regMessage.responseUrl());
        editor.putString(provider + "_userId", regMessage.userId());
        editor.putString(provider + "_secret", regMessage.secret());
        editor.commit();
    }
    
    /**
     * This method reads out the device id and returns it.
     * @return The device id.
     */
    private String getDeviceId() {
    	final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceId = "" + tm.getDeviceId();
        return deviceId;
    }
    
    /**
     * Builds a string that contains the device manufacturer and model.
     * @return Device name.
     */
    private String getDeviceName() {
    	String name = Build.MANUFACTURER + " " + Build.MODEL;
    	return name;
    }
    
    private byte[] encrypt(byte[] value, byte[] key) {
    	Mac mac;
    	SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
        } catch (Exception e) {
            return null;
        }
        return mac.doFinal(value);
    }
    
    private byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0)
            throw new IllegalArgumentException("Input string must contain an even number of characters");

        final byte result[] = new byte[encoded.length()/2];
        final char enc[] = encoded.toCharArray();
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);
            result[i/2] = (byte) Integer.parseInt(curr.toString(), 16);
        }
        return result;
    }
    
    private void displayLoading(String msg) {
    	// Hide result text.
    	LinearLayout result = (LinearLayout) findViewById(R.id.result);
    	result.setVisibility(View.GONE);
    	
    	TextView tv = (TextView) findViewById(R.id.loadingHint);
    	tv.setText(msg);
    	
    	// Display loading image and message.
    	LinearLayout loading = (LinearLayout) findViewById(R.id.loading);
    	loading.setVisibility(View.VISIBLE);
    }
    
    private void displayResult(String msg) {
    	// Hide loading image and text.
    	LinearLayout loading = (LinearLayout) findViewById(R.id.loading);
    	loading.setVisibility(View.GONE);
    	
    	TextView tv = (TextView) findViewById(R.id.resultText);
    	tv.setText(msg);
    	
    	// Display result text.
    	LinearLayout result = (LinearLayout) findViewById(R.id.result);
    	result.setVisibility(View.VISIBLE);
    }

}