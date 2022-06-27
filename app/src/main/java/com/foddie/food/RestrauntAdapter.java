package com.foddie.food;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foddie.food.models.RestrauntModel;
import com.foddie.food.models.UserInfoModel;

import java.util.List;

class RestrauntAdapter extends RecyclerView.Adapter<RestrauntAdapter.MyViewHolder> {
    List<RestrauntModel> mRestrauntModelList;
    UserInfoModel userInfoModel;
    Activity mActivity;
    public RestrauntAdapter(Activity mActivity,UserInfoModel userInfoModel,List<RestrauntModel> mRestrauntModelList) {
        this.mRestrauntModelList = mRestrauntModelList;
        this.mActivity = mActivity;
        this.userInfoModel = userInfoModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_retro_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,final int position) {
        holder.mRestrauntNameTv.setText(mRestrauntModelList.get(position).getmName());
        holder.mRestrauntDescTv.setText(mRestrauntModelList.get(position).getmSmallDesc());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,RestrauntDetailActivity.class);
                intent.putExtra("RestrauntInfo",mRestrauntModelList.get(position)) ;
                intent.putExtra("userInfo",userInfoModel);
                mActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestrauntModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        RelativeLayout mParent;
        TextView mRestrauntNameTv, mRestrauntDescTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestrauntNameTv = itemView.findViewById(R.id.list_restro_item_name_tv);
            mRestrauntDescTv = itemView.findViewById(R.id.list_restro_item_desc_tv);
            mParent = itemView.findViewById(R.id.parent_retro);

        }
    }
}
