package com.foddie.food;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foddie.food.models.UserInfoModel;

public class UsersProfileActivity extends AppCompatActivity {

    Button mUpdateButton;
    EditText mUserName, mEmailEt, mUserId;
    String[] userTypes;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userTypes = getResources().getStringArray(R.array.userTypesArray);
        mUserName = findViewById(R.id.profilefirst_name_et);
        mEmailEt = findViewById(R.id.profileemail_et);
        mUserId = findViewById(R.id.profileusername_et);
        mUpdateButton = findViewById(R.id.profile_update_button);

        dbManager = new DBManager(this);
        dbManager.open();
        String userProfile = getIntent().getStringExtra("UserProfile");
        getLoginUserDetails(userProfile);
    }

    UserInfoModel userInfoModel;
    boolean isSameLoginUser;

    private void getLoginUserDetails(String userProfile) {
        if (userProfile.equals(SharedPreferenceManager.readString(SharedPreferenceManager.SIGNIN_USER, "")))
            isSameLoginUser = true;

        userInfoModel = dbManager.getLoginCredintials(userProfile);
        setData();
        if (isSameLoginUser)
            mUpdateButton.setVisibility(View.VISIBLE);
        else
        {
            mUserName.setEnabled(false);
            mUserId.setEnabled(false);
        }
        setTitle("User Profile");
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    dbManager.updateUserInfo(createModel());
                    Toast.makeText(UsersProfileActivity.this, " Profile has been Updated ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(UsersProfileActivity.this, "Please enter all input values.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private UserInfoModel createModel() {
        userInfoModel.setmUserName(mUserId.getText().toString().trim());
        userInfoModel.setmUserName(mUserName.getText().toString().trim());
        return userInfoModel;
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(mUserName.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(mUserId.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    private void setData() {
        mUserName.setText(userInfoModel.getmUserName());
        mUserId.setText(userInfoModel.getmUserName());
        mEmailEt.setText(userInfoModel.getmUserEmailId());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                break;
        }
        return true;
    }
}