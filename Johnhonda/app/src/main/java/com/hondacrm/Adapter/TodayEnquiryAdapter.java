package com.hondacrm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Fragment.EnquiryDetails;
import com.hondacrm.Fragment.EnquiryFollowUpList;
import com.hondacrm.Module.TodayEnquiryResponse;
import com.hondacrm.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class TodayEnquiryAdapter extends RecyclerView.Adapter<TodayEnquiryAdapter.MyViewHolder> {

    Context context;
    List<TodayEnquiryResponse> todayEnquiryResponseList;

    public TodayEnquiryAdapter(Context context, List<TodayEnquiryResponse> todayEnquiryResponseList) {
        this.context = context;
        this.todayEnquiryResponseList = todayEnquiryResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.today_enquiry_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TodayEnquiryResponse todayEnquiryResponse = todayEnquiryResponseList.get(position);

        String followUp = "<b>Follow Up: </b>" + todayEnquiryResponseList.get(position).getFollowUp();
        String feedback = "<b>Feedback: </b>" + todayEnquiryResponseList.get(position).getFeedback();
        String date = "<b>Follow Up: </b>" + todayEnquiryResponseList.get(position).getDate().substring(0, 10);

        holder.textViews.get(0).setText(Html.fromHtml(followUp));
        holder.textViews.get(1).setText(Html.fromHtml(feedback));
        holder.textViews.get(2).setText(Html.fromHtml(date));

        holder.imageViews.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EnquiryDetails enquiryDetails = new EnquiryDetails();
                    Bundle bundle = new Bundle();
                    bundle.putString("enquiryId", "" + todayEnquiryResponseList.get(position).getEnquiryID());
                    enquiryDetails.setArguments(bundle);
                    ((MainPage) context).loadFragment(enquiryDetails, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.imageViews.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EnquiryFollowUpList enquiryFollowUpList = new EnquiryFollowUpList();
                    Bundle bundle = new Bundle();
                    bundle.putString("enquiryId", "" + todayEnquiryResponseList.get(position).getEnquiryID());
                    enquiryFollowUpList.setArguments(bundle);
                    ((MainPage) context).loadFragment(enquiryFollowUpList, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return todayEnquiryResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.followup, R.id.feedback, R.id.date})
        List<TextView> textViews;
        @BindViews({R.id.view, R.id.follow})
        List<ImageView> imageViews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
