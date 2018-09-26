package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

/**
 * Created by Neelam on 9/26/2018.
 */

public class UpdateScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {

    private final ScheduleController scheduleController;
    private static final String TAG = UpdateScheduleDelegate.class.getName();

    public UpdateScheduleDelegate(ScheduleController scheduleController){
        this.scheduleController=scheduleController;
    }
    @Override
    protected Boolean doInBackground(ProgramSlot... params) {

        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        System.out.println("PS:"+params[0].toString());
        JSONObject json = new JSONObject();
        try {
            json.put("id", params[0].getId());
            json.put("rpname", params[0].getRadioProgramName());
            json.put("date", params[0].getProgramSlotDate());
            json.put("sttime", params[0].getProgramSlotSttime());
            json.put("duration", params[0].getProgramSlotDuration());
            json.put("presenter", params[0].getProgramSlotPresenter());
            json.put("producer", params[0].getProgramSlotProducer());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(json.toString());
            dos.flush();
            //dos.writeUTF(json.toString());
           // dos.write(256);
            Log.v(TAG, "JSON response " + json.toString());
            int result = httpURLConnection.getResponseCode();
            Log.v(TAG, "Http PUT response " + result);
            if (result == 409 || result == 400) {
                Log.v(TAG, "Http PUT result 409" + result);
                success = false;
            } else {
                Log.v(TAG, "Http PUT result OK" + result);
                success = true;
            }

        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        scheduleController.scheduleCreated(aBoolean);

    }

}
