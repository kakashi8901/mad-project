package com.foddie.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.foddie.food.models.UserInfoModel;

import java.util.List;

public class ShowUsersDetail extends AppCompatActivity implements IUserInfoUpdate {

    RecyclerView recyclerView;
    TextView noUser;
    DBManager dbManager;
    List<UserInfoModel> mAllUserList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_detail);

        dbManager = new DBManager(this);
        dbManager.open();
        setTitle("Users List");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.user_detail_rv);
        noUser = findViewById(R.id.no_user);

        List<UserInfoModel> mAllUserList = dbManager.getAllUserList("0");
        List<UserInfoModel> mAllCritics = dbManager.getAllUserList("2");
        if (mAllUserList != null)
            mAllUserList.addAll(mAllCritics);
        else if (mAllCritics != null)
            mAllUserList = mAllCritics;

        if (mAllUserList != null && mAllUserList.size() > 0) {
            this.mAllUserList = mAllUserList;
            setAdapter();
        } else {
            noUser.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


    }
    UserInfoAdapter userInfoAdapter;
    private void setAdapter() {
        userInfoAdapter = new UserInfoAdapter(this,mAllUserList,this);
        recyclerView.setAdapter(userInfoAdapter);
    }

    @Override
    public void updateUserInfoToCritics(UserInfoModel userInfoModel, int position) {
        userInfoModel.setmUserType("2");
        userInfoAdapter.updateItem(userInfoModel,position);
        dbManager.updateUserInfo(userInfoModel);
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