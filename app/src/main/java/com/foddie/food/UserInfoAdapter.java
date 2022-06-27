package com.foddie.food;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foddie.food.models.UserInfoModel;

import java.util.List;

class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.MyViewHolder> {
    List<UserInfoModel> mUserInfoList;
    Activity mActivity;
    IUserInfoUpdate iUserInfoUpdate;

    public UserInfoAdapter(Activity mActivity, List<UserInfoModel> mRestrauntModelList, IUserInfoUpdate iUserInfoUpdate) {
        this.mUserInfoList = mRestrauntModelList;
        this.mActivity = mActivity;
        this.iUserInfoUpdate = iUserInfoUpdate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_user_detail, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mEmailId.setText(mUserInfoList.get(position).getmUserEmailId());
        holder.mUserName.setText(mUserInfoList.get(position).getmUserName());

        if (mUserInfoList.get(position).getmUserType().equals("0"))
            holder.mMakeCritics.setVisibility(View.VISIBLE);
        else
            holder.mMakeCritics.setVisibility(View.GONE);
        holder.mMakeCritics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iUserInfoUpdate.updateUserInfoToCritics(mUserInfoList.get(position), position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }

    public void updateItem(UserInfoModel userInfoModel, int position) {
        this.mUserInfoList.set(position, userInfoModel);
        notifyItemChanged(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button mMakeCritics;
        TextView mUserId, mUserName, mEmailId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.user_name);
            mEmailId = itemView.findViewById(R.id.user_email);
            mMakeCritics = itemView.findViewById(R.id.user_critics);

        }
    }
}
