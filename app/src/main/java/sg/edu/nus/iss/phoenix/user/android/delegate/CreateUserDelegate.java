package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;


/**
 * Created by Surbhi on 21/9/18.
 *
 */

public class CreateUserDelegate extends AsyncTask<User, Void, Boolean> {
    // Tag for logging
    private static final String TAG = CreateUserDelegate.class.getName();

    private final UserController userController;

    public CreateUserDelegate(UserController userController) {
        this.userController = userController;
    }

    @Override
    protected Boolean doInBackground(User... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"create").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        JSONArray rolesJson = new JSONArray();
        try {
            json.put("id", params[0].getId());
            json.put("password", params[0].getPassword());
            json.put("name", params[0].getName());
          //  JSONObject role1 = new JSONObject();
            ArrayList<Role> roles = params[0].getRoles();
            for(Role role : roles){
                JSONObject roleObj = new JSONObject();
                roleObj.put("role", role.getRole());
                rolesJson.put(roleObj);
            }

            //role1.put("accessPrivilege", params[0].getRoles().get(0).getAccessPrivilege());

            json.put("roles", rolesJson);
            //json.put("Roles", params[0].getRoles());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v("json",json.toString());
            int result = httpURLConnection.getResponseCode();
            Log.v(TAG, "Http PUT response " + result);
            if (result == 200){
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
    protected void onPostExecute(Boolean result) {
        userController.userCreated(result.booleanValue());
    }
}