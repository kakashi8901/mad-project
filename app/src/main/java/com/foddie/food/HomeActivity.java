package com.foddie.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.foddie.food.models.RestrauntModel;
import com.foddie.food.models.UserInfoModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {


    RecyclerView mRestrauntListView;
    TextView mNoRestrauntData;
    DBManager dbManager;
    String signInUserId;
    UserInfoModel userInfoModel;
    List<RestrauntModel> mRestrauntModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbManager = new DBManager(this);
        dbManager.open();

        setTitle(getString(R.string.dahboard));
        signInUserId = SharedPreferenceManager.readString(SharedPreferenceManager.SIGNIN_USER, "");
        userInfoModel = dbManager.getLoginCredintials(signInUserId);
        mRestrauntModelList = dbManager.getAllRestraunts();
        
        mRestrauntListView = findViewById(R.id.restraunt_list);
        mNoRestrauntData = findViewById(R.id.no_restraunt);


        if (mRestrauntModelList != null && mRestrauntModelList.size() > 0)
            setAdapter();
        else {
            mRestrauntListView.setVisibility(View.GONE);
            mNoRestrauntData.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter() {
        RestrauntAdapter restroAdapter = new RestrauntAdapter(HomeActivity.this, userInfoModel, mRestrauntModelList);
        mRestrauntListView.setAdapter(restroAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);

        if (userInfoModel.getmUserType().equals("0") || userInfoModel.getmUserType().equals("2")) {
            menu.findItem(R.id.menu_add_restraunt).setVisible(false);
            menu.findItem(R.id.menu_show_all_user).setVisible(false);
        } else {
            menu.findItem(R.id.menu_add_restraunt).setVisible(true);
            menu.findItem(R.id.menu_show_all_user).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_log_out:
                SharedPreferenceManager.writeBoolean(SharedPreferenceManager.IS_SIGNIN, false);
                SharedPreferenceManager.writeString(SharedPreferenceManager.SIGNIN_USER, "");
                startActivity(new Intent(HomeActivity.this, SignInScreen.class));
                finish();
                break;
            case R.id.menu_add_restraunt:
                startActivity(new Intent(HomeActivity.this, AddNewRestrauntScreen.class));
                break;
            case R.id.menu_show_all_user:
                startActivity(new Intent(HomeActivity.this, ShowUsersDetail.class));
                break;
            case R.id.menu_view_profile:
                Intent intent = new Intent(HomeActivity.this, UsersProfileActivity.class);
                intent.putExtra("UserProfile", SharedPreferenceManager.readString(SharedPreferenceManager.SIGNIN_USER, ""));
                startActivity(intent);
                break;
        }
        return true;

    }
}