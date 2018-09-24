package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private static final String TAG = UserAdapter.class.getName();

    public UserAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row_user, parent, false);
        }
        //    Word currentWord = getItem(position);
        User currentUR = getItem(position);
        Log.e("ID", currentUR.getId());
        TextView userID = (TextView) listItemView.findViewById(R.id.maintain_user_id);
        userID.setText(currentUR.getId(), TextView.BufferType.NORMAL);
        //userID.setKeyListener(null); // This disables editing.

        TextView userName = (TextView) listItemView.findViewById(R.id.maintain_user_name);
        userName.setText(currentUR.getName(), TextView.BufferType.NORMAL);
        //userName.setKeyListener(null);

       // Log.d(TAG, "Adapter password is +++++++++++++++ " + currentUR.getPassword());

        TextView userRole = (TextView) listItemView.findViewById(R.id.maintain_user_role);
        ArrayList<Role> roles = currentUR.getRoles();
        String role_show = "";
        for (int i = 0; i < roles.size(); i++) {

            Role r = roles.get(i);
            String role_i = r.getRole();
            role_show += role_i + ":";
            Log.d(TAG, "Adapter role is " + role_i);
        }
        userRole.setText(role_show, TextView.BufferType.NORMAL);
        //userRole.setKeyListener(null);

        return listItemView;
    }
}
