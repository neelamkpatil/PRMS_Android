package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import sg.edu.nus.iss.phoenix.user.android.controller.ReviewSelectUserController;
import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class RetrieveUsersDelegate extends AsyncTask<String, Void, String> {
    // Tag for logging
    private static final String TAG = RetrieveUsersDelegate.class.getName();

    private UserController UserController = null;
    private ReviewSelectUserController reviewSelectUserController = null;

    public RetrieveUsersDelegate(UserController userController) {
        this.reviewSelectUserController = null;
        this.UserController= userController;
    }

    public RetrieveUsersDelegate(ReviewSelectUserController reviewSelectProgramController) {
        this.UserController = null;
        this.reviewSelectUserController = reviewSelectProgramController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_USER).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<User> users = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray urArray = reader.getJSONArray("urList");

                for (int i = 0; i < urArray.length(); i++) {
                    JSONObject urJson = urArray.getJSONObject(i);
                    String id = urJson.getString("id");
                    String name = urJson.getString("name");
                    JSONArray roles=urJson.getJSONArray("roles");
                    ArrayList<Role> r=new ArrayList<Role>();
                    for (int j=0;j<roles.length();j++){
                        JSONObject roleJson=roles.getJSONObject(j);
                        String role=roleJson.getString("role");
                        String accessPrivilege="No";
                        if (role.equals("admin")){
                            accessPrivilege="Yes";
                        }
                        Role role1=new Role(role,accessPrivilege);
                        r.add(role1);
                    }
                    users.add(new User(id,"",name, r));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (UserController != null)
            UserController.usersRetrieved(users);
        else if (reviewSelectUserController != null)
            reviewSelectUserController.usersRetrieved(users);
    }
}
