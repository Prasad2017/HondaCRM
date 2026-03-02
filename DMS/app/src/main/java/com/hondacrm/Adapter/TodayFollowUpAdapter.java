package com.hondacrm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Fragment.EnquiryFollowUpList;
import com.hondacrm.Fragment.QuotationFollowUpList;
import com.hondacrm.Fragment.TodayFollowupDetails;
import com.hondacrm.Module.TodayFollowupResponse;
import com.hondacrm.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class TodayFollowUpAdapter extends RecyclerView.Adapter<TodayFollowUpAdapter.MyViewHolder> {

    Context context;
    List<TodayFollowupResponse> todayFollowupResponseList;
    String type;

    public TodayFollowUpAdapter(Context context, List<TodayFollowupResponse> todayFollowupResponseList, String type) {

        this.context = context;
        this.todayFollowupResponseList = todayFollowupResponseList;
        this.type = type;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.today_followup_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        TodayFollowupResponse TodayFollowupResponse = todayFollowupResponseList.get(position);

        try {

            if (type.equalsIgnoreCase("enquiry")) {
                holder.textViews.get(0).setText("" + todayFollowupResponseList.get(position).getEnquiryN());
            } else {
                holder.textViews.get(0).setText("" + todayFollowupResponseList.get(position).getQuotationN());
            }
            holder.textViews.get(1).setText("" + todayFollowupResponseList.get(position).getFeedback());
            holder.textViews.get(2).setText("" + todayFollowupResponseList.get(position).getName());
            holder.textViews.get(3).setText("" + todayFollowupResponseList.get(position).getMobileNumber());
            holder.textViews.get(4).setText("" + todayFollowupResponseList.get(position).getModelCategory());
            holder.textViews.get(5).setText("" + todayFollowupResponseList.get(position).getModelName());
            holder.textViews.get(6).setText("" + todayFollowupResponseList.get(position).getModelvarient());
            holder.textViews.get(7).setText("" + todayFollowupResponseList.get(position).getDate().substring(0, 10));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainPage) context).removeCurrentFragmentAndMoveBack();
                TodayFollowupDetails todayFollowupDetails = new TodayFollowupDetails();
                Bundle bundle = new Bundle();
                bundle.putString("followupId", "" + todayFollowupResponseList.get(position).getId());
                bundle.putString("type", "" + type);
                bundle.putString("enquiryId", "" + todayFollowupResponseList.get(position).getEnquiryID());
                bundle.putString("quotationId", "" + todayFollowupResponseList.get(position).getQuotationID());
                bundle.putString("feedBack", "" + todayFollowupResponseList.get(position).getFeedback());
                bundle.putString("followup", "" + todayFollowupResponseList.get(position).getFollowUp());
                bundle.putString("details", "" + todayFollowupResponseList.get(position).getDetails());
                bundle.putString("status", "" + todayFollowupResponseList.get(position).getStatus());
                bundle.putString("date", "" + todayFollowupResponseList.get(position).getDate().substring(0, 10));
                todayFollowupDetails.setArguments(bundle);
                ((MainPage) context).loadFragment(todayFollowupDetails, true);

            }
        });

        holder.followupHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todayFollowupResponseList.get(position).getEnquiryID() != null) {
                    EnquiryFollowUpList enquiryFollowUpList = new EnquiryFollowUpList();
                    Bundle bundle = new Bundle();
                    bundle.putString("enquiryId", "" + todayFollowupResponseList.get(position).getEnquiryID());
                    enquiryFollowUpList.setArguments(bundle);
                    ((MainPage) context).loadFragment(enquiryFollowUpList, true);
                } else if (todayFollowupResponseList.get(position).getQuotationID() != null) {
                    QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                    Bundle bundle = new Bundle();
                    bundle.putString("quotationId", "" + todayFollowupResponseList.get(position).getQuotationID());
                    quotationFollowUpList.setArguments(bundle);
                    ((MainPage) context).loadFragment(quotationFollowUpList, true);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return todayFollowupResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.number, R.id.feedback, R.id.clientName, R.id.mobileNumber, R.id.modelCategory, R.id.modelName,
                R.id.modelVariant, R.id.date})
        List<TextView> textViews;
        @BindView(R.id.reschedule)
        TextView reschedule;
        @BindView(R.id.followupHistory)
        TextView followupHistory;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
