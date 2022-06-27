package com.foddie.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foddie.food.models.RestrauntModel;

import java.util.Calendar;

public class AddNewRestrauntScreen extends AppCompatActivity {


    EditText mRestrauntName, mRestrauntSmallDescription, mRestrauntDescription;
    Button mAddRestrauntBtn;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restraunt);

        dbManager = new DBManager(this);
        dbManager.open();
        mRestrauntName = findViewById(R.id.addReg_name_et);
        mRestrauntSmallDescription = findViewById(R.id.addReg_small_desc_et);
        mRestrauntDescription = findViewById(R.id.addReg_des_et);
        mAddRestrauntBtn = findViewById(R.id.addRegister_button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.Add_Restraunt));

        mAddRestrauntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                    saveData();
                else
                    Toast.makeText(AddNewRestrauntScreen.this, "Please enter All details", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveData() {
        RestrauntModel restrauntModel = createRestroModel();
        dbManager.insertRestraunt(restrauntModel);
        Intent intent = new Intent(AddNewRestrauntScreen.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private RestrauntModel createRestroModel() {
        RestrauntModel restrauntModel = new RestrauntModel();
        restrauntModel.setmRestrauntId(Calendar.getInstance().getTimeInMillis() + "_" + mRestrauntName.getText().toString().trim());
        restrauntModel.setmCreatedDate("" + Calendar.getInstance().getTimeInMillis());
        restrauntModel.setmDesc(mRestrauntDescription.getText().toString().trim());
        restrauntModel.setmSmallDesc(mRestrauntSmallDescription.getText().toString().trim());
        restrauntModel.setmName(mRestrauntName.getText().toString().trim());
        restrauntModel.setmRestrauntName(mRestrauntName.getText().toString().trim());
        restrauntModel.setmCreateBy(SharedPreferenceManager.readString(SharedPreferenceManager.SIGNIN_USER, ""));
        return restrauntModel;
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(mRestrauntName.getText().toString().trim()))
            return false;
        else if (TextUtils.isEmpty(mRestrauntSmallDescription.getText().toString().trim()))
            return false;
        else if (TextUtils.isEmpty(mRestrauntDescription.getText().toString().trim()))
            return false;
        else
            return true;
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