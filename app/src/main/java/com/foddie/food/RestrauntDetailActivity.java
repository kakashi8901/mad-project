package com.foddie.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foddie.food.models.RestrauntModel;
import com.foddie.food.models.ReviewModel;
import com.foddie.food.models.UserInfoModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RestrauntDetailActivity extends AppCompatActivity {

    UserInfoModel mUserInfoModel;
    RestrauntModel restrauntModel;
    Button mAddReviewBtn, mViewReview, mBookResarvation;
    TextView mRestroDesc;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restraunt_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dbManager = new DBManager(this);
        dbManager.open();
        mRestroDesc = findViewById(R.id.restraunt_des);
        mAddReviewBtn = findViewById(R.id.add_review_btn);
        mViewReview = findViewById(R.id.view_review_btn);
        mBookResarvation = findViewById(R.id.reservation_btn);

        restrauntModel = getIntent().getParcelableExtra("RestrauntInfo");
        mUserInfoModel = getIntent().getParcelableExtra("userInfo");
        setButtonVisibility();
        mRestroDesc.setText(restrauntModel.getmDesc());

        setTitle(restrauntModel.getmName()+" Restraunt");
        mAddReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReviewDialog();
            }
        });
        mBookResarvation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.opentable.com/s/?covers=2&dateTime=" + date + "&metroId=72&regionIds=5316&pinnedRids%5B0%5D=87967&enableSimpleCuisines=true&includeTicketedAvailability=true&pageType=0"));
                startActivity(i);
            }
        });
        mViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestrauntDetailActivity.this, ShowReviewActivity.class);
                intent.putExtra("RestroDetail", restrauntModel);
                intent.putExtra("UserInfo", mUserInfoModel);
                startActivity(intent);
            }
        });
    }

    private void showAddReviewDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RestrauntDetailActivity.this);
        alertDialog.setTitle("Add Review");

        final EditText input = new EditText(RestrauntDetailActivity.this);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addReview(input.getText().toString().trim());
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void addReview(String review) {
        if (!TextUtils.isEmpty(review)) {
            saveReview(review);
        } else
            Toast.makeText(RestrauntDetailActivity.this, "Please enter Review", Toast.LENGTH_LONG).show();
    }

    private void saveReview(String review) {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setRestrauntId(restrauntModel.getmRestrauntId());
        reviewModel.setReviewDate(Calendar.getInstance().getTimeInMillis() + "");
        reviewModel.setReviewGivenByEmail(mUserInfoModel.getmUserEmailId());
        reviewModel.setReviewGivenBy(mUserInfoModel.getmUserName());
        reviewModel.setReviewComment(review);
        reviewModel.setReviewId(Calendar.getInstance().getTimeInMillis() + "_" + review);
        dbManager.insertReview(reviewModel);
    }

    private void setButtonVisibility() {
        if (mUserInfoModel.getmUserType().equals("0")) {
        } else if (mUserInfoModel.getmUserType().equals("1")) {
            mViewReview.setVisibility(View.GONE);
            mBookResarvation.setVisibility(View.GONE);
            mAddReviewBtn.setVisibility(View.GONE);
        } else {
            mViewReview.setVisibility(View.GONE);
            mBookResarvation.setVisibility(View.GONE);
        }
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
}