package com.foddie.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.foddie.food.models.UserInfoModel;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    Button mRegButton;
    EditText mUserName, mEmailEt, mUserId, mPasswordEt;
    Spinner mUserType;
    String[] userTypesArray;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.registration_page));

        userTypesArray = getResources().getStringArray(R.array.userTypesArray);
        mUserName = findViewById(R.id.reg_first_name_et);
        mEmailEt = findViewById(R.id.reg_email_et);
        mPasswordEt = findViewById(R.id.reg_password_et);
        mUserId = findViewById(R.id.reg_username_et);
        mRegButton = findViewById(R.id.register_button);
        mUserType = findViewById(R.id.reg_reg_type_sp);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, userTypesArray);
        mUserType.setAdapter(mAdapter);
        mUserType.setSelection(0);
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisteration();
            }
        });
        dbManager = new DBManager(this);
        dbManager.open();

    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    private void startRegisteration() {
        if (TextUtils.isEmpty(mUserName.getText().toString().trim())) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.empty_firstName));
        } else if (TextUtils.isEmpty(mUserId.getText().toString().trim())) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.empty_userName));
        } else if (TextUtils.isEmpty(mEmailEt.getText().toString().trim())) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.empty_username));
        } else if (!Util.isValidEmail(mEmailEt.getText().toString().trim())) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(mPasswordEt.getText().toString().trim())) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.empty_password));
        } else if (mPasswordEt.getText().toString().trim().length() < 5) {
            Util.showErrorMessage(RegistrationActivity.this,getString(R.string.password_length));
        } else
            saveData();
    }

    private void saveData() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setmUserName(mUserName.getText().toString().trim());
        userInfoModel.setmUserEmailId(mEmailEt.getText().toString().trim());
        userInfoModel.setmUserPassWord(mPasswordEt.getText().toString().trim());
        userInfoModel.setUserID(mUserId.getText().toString().trim());
        userInfoModel.setmUserType(""+mUserType.getSelectedItemPosition());
        userInfoModel.setmUserId(mUserId.getText().toString().trim() + "_" + Calendar.getInstance().getTimeInMillis());
        dbManager.insert(userInfoModel);
        Util.showErrorMessage(RegistrationActivity.this,getString(R.string.registration_success));
        finish();
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