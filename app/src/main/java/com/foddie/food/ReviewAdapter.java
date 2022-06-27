package com.foddie.food;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foddie.food.models.ReviewModel;
import com.foddie.food.models.UserInfoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    List<ReviewModel> mReviewList;
    UserInfoModel userInfoModel;
    Activity mActivity;
    IUpdateReviewRating IUpdateReviewRating;

    public ReviewAdapter(Activity mActivity, UserInfoModel userInfoModel, List<ReviewModel> mReviewList, IUpdateReviewRating IUpdateReviewRating) {
        this.mReviewList = mReviewList;
        this.mActivity = mActivity;
        this.userInfoModel = userInfoModel;
        this.IUpdateReviewRating = IUpdateReviewRating;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_review_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mRestrauntComment.setText(mReviewList.get(position).getReviewComment());
        holder.mRestrauntName.setText(mReviewList.get(position).getReviewGivenBy());
        Date date = new Date(Long.parseLong(mReviewList.get(position).getReviewDate()));
        holder.mRestrauntDate.setText(new SimpleDateFormat("dd-MM-yyyy HH:MM a").format(date));
        if (!TextUtils.isEmpty(mReviewList.get(position).getReviewRating()))
            holder.mRatingBar.setRating(Float.parseFloat(mReviewList.get(position).getReviewRating()));
        if (userInfoModel.getmUserType().equals("0"))
            holder.mRatingBar.setEnabled(true);
        else
            holder.mRatingBar.setEnabled(false);
        holder.mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                holder.mUpdateRating.setVisibility(View.VISIBLE);
            }
        });
        holder.mUpdateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewModel reviewModel = mReviewList.get(position);
                reviewModel.setReviewRating(holder.mRatingBar.getRating() + "");
                IUpdateReviewRating.updateReviewRating(reviewModel);
            }
        });
        holder.mRestrauntName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, UsersProfileActivity.class);
                intent.putExtra("UserProfile", mReviewList.get(position).getReviewGivenByEmail());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView mRestrauntName, mRestrauntComment, mRestrauntDate;
        RatingBar mRatingBar;
        Button mUpdateRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestrauntName = itemView.findViewById(R.id.restro_name_item);
            mRestrauntComment = itemView.findViewById(R.id.restro_desc_item);
            mRestrauntDate = itemView.findViewById(R.id.restro_time_item);
            mRatingBar = itemView.findViewById(R.id.rating_bar);
            mUpdateRating = itemView.findViewById(R.id.update_rating_btn);
        }
    }
}
