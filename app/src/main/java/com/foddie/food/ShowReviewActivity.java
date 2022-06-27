package com.foddie.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.foddie.food.models.RestrauntModel;
import com.foddie.food.models.ReviewModel;
import com.foddie.food.models.UserInfoModel;

import java.util.List;

public class ShowReviewActivity extends AppCompatActivity implements IUpdateReviewRating {

    RestrauntModel restrauntModel;
    UserInfoModel userInfoModel;
    RecyclerView recyclerView;
    TextView textView;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);
        textView = findViewById(R.id.no_review);
        recyclerView = findViewById(R.id.review_list);
        restrauntModel = getIntent().getParcelableExtra("RestroDetail");
        userInfoModel = getIntent().getParcelableExtra("UserInfo");
        dbManager = new DBManager(this);
        dbManager.open();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(restrauntModel.getmName());

        List<ReviewModel> mReviewList = dbManager.getAllReviews(restrauntModel.getmRestrauntId());
        if (mReviewList != null && mReviewList.size() > 0)
            setAdapter(mReviewList);
        else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter(List<ReviewModel> mReviewList) {
        ReviewAdapter reviewAdapter = new ReviewAdapter(this, userInfoModel, mReviewList,this);
        recyclerView.setAdapter(reviewAdapter);
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
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

    @Override
    public void updateReviewRating(ReviewModel reviewModel) {
        dbManager.updateReviews(reviewModel);
    }
}