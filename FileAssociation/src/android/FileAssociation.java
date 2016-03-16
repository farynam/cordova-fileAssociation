package com.mfitbs.fileassociation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class echoes a string called from JavaScript.
 */
public class FileAssociation extends CordovaPlugin {

    static final String DEFAULT_LOG_TAG = "FileAssociationPlugin";
    Intent resumeIntent;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.v(DEFAULT_LOG_TAG, "entering execute");
        if (action.equals("getAssociatedData")) {
            Log.v("FileAssociationPlugin", "executing");
            getAssociatedData(callbackContext);
            return true;
        }
        return false;
    }

    public void onNewIntent(Intent intent) {
        this.resumeIntent = intent;
        super.onNewIntent(intent);
    }

    private void getAssociatedData(CallbackContext callbackContext) throws JSONException {
        InputStream in = null;
        try {
            //only for local files
            Activity activity = this.cordova.getActivity();

            Uri uri = null;
            //clear data ensure only one time usage
            if (resumeIntent != null) {
                uri = resumeIntent.getData();
                clearData(resumeIntent);
            }

            Uri startIntentData = activity.getIntent().getData();

            if (startIntentData != null) {
                clearData(activity.getIntent());
            }

            uri = uri == null ? startIntentData : uri;

            if (uri == null) {
                callbackContext.success((String)null);
                Log.v(DEFAULT_LOG_TAG, "no uri");
                return;
            }

            Log.v(DEFAULT_LOG_TAG, uri.toString());

            if (Log.isLoggable(DEFAULT_LOG_TAG, LOG.VERBOSE)) {
                Log.v(DEFAULT_LOG_TAG, "uri:" + uri.getPath());
            }

            in = activity.getContentResolver().openInputStream(uri);
            BufferedReader sr = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = sr.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            callbackContext.success(sb.toString());
        } catch (Exception e) {
            handleException(e, callbackContext);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                handleException(e, callbackContext);
            }
        }
    }

    private void handleException(Exception e, CallbackContext callbackContext) throws JSONException {
        callbackContext.error(e.getMessage());
        throw new JSONException(e.getMessage());
    }

    private void clearData(Intent intent) {
        intent.setData(null);
    }
}
