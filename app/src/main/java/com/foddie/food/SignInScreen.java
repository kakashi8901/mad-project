package com.foddie.food;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.foddie.food.models.UserInfoModel;

public class SignInScreen extends AppCompatActivity {

    EditText mUserEmailId, mPasswordEt;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mUserEmailId = findViewById(R.id.signin_email_et);
        mPasswordEt = findViewById(R.id.signin_password_et);

        dbManager = new DBManager(this);
        dbManager.open();

        findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFlow();
            }
        });

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInScreen.this,RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void loginFlow() {
        if (TextUtils.isEmpty(mUserEmailId.getText().toString().trim())) {
            Util.showErrorMessage(SignInScreen.this,getString(R.string.empty_email));
        } else if (!Util.isValidEmail(mUserEmailId.getText().toString().trim())) {
            Util.showErrorMessage(SignInScreen.this,getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(mPasswordEt.getText().toString().trim())){
            Util.showErrorMessage(SignInScreen.this,getString(R.string.empty_password));
        }
        else
            checkCredintials();
    }

    private void checkCredintials() {
        UserInfoModel userInfoModel = dbManager.getLoginCredintials(mUserEmailId.getText().toString().trim());
        if(userInfoModel == null)
            Util.showErrorMessage(SignInScreen.this,getString(R.string.user_not_found));
        else if(!userInfoModel.getmUserEmailId().equals(mUserEmailId.getText().toString().trim()) || !userInfoModel.getmUserPassWord().equals(mPasswordEt.getText().toString().trim()))
            Util.showErrorMessage(SignInScreen.this,getString(R.string.enter_valid_credintials));
        else
        {
            SharedPreferenceManager.writeString(SharedPreferenceManager.SIGNIN_USER,userInfoModel.getmUserEmailId());
            SharedPreferenceManager.writeBoolean(SharedPreferenceManager.IS_SIGNIN,true);

            startActivity(new Intent(SignInScreen.this,HomeActivity.class));
            finish();
        }
    }

}