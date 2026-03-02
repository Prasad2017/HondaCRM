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
import com.hondacrm.Fragment.FollowupDetails;
import com.hondacrm.Module.FollowupResponse;
import com.hondacrm.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class FollowUpAdapter extends RecyclerView.Adapter<FollowUpAdapter.MyViewHolder> {

    Context context;
    List<FollowupResponse> followupResponseList;
    String status, type;

    public FollowUpAdapter(Context context, List<FollowupResponse> followupResponseList, String type) {

        this.context = context;
        this.followupResponseList = followupResponseList;
        this.type = type;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.followup_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FollowupResponse followupResponse = followupResponseList.get(position);

        try {

            if (followupResponseList.get(position).getStatus().equalsIgnoreCase("1")) {
                status = "Open";
                holder.view.setVisibility(View.VISIBLE);
            } else {
                status = "Close";
                holder.view.setVisibility(View.GONE);
            }

            holder.textViews.get(0).setText("" + followupResponseList.get(position).getFollowUp());
            String details = "<b>Feedback: </b> " + followupResponseList.get(position).getFeedback() +
                    "<br/><b>Details: </b> " + followupResponseList.get(position).getDetails() +
                    "<br/><b>Status:  </b> " + status +
                    "<br/><b>Date:  </b> " + followupResponseList.get(position).getDate().substring(0, 10);
            holder.textViews.get(1).setText(Html.fromHtml(details));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainPage) context).removeCurrentFragmentAndMoveBack();
                FollowupDetails followupDetails = new FollowupDetails();
                Bundle bundle = new Bundle();
                bundle.putString("followupId", "" + followupResponseList.get(position).getId());
                bundle.putString("type", "" + type);
                bundle.putString("enquiryId", "" + followupResponseList.get(position).getEnquiryID());
                bundle.putString("quotationId", "" + followupResponseList.get(position).getQuotationID());
                bundle.putString("feedBack", "" + followupResponseList.get(position).getFeedback());
                bundle.putString("followup", "" + followupResponseList.get(position).getFollowUp());
                bundle.putString("details", "" + followupResponseList.get(position).getDetails());
                bundle.putString("status", "" + followupResponseList.get(position).getStatus());
                bundle.putString("date", "" + followupResponseList.get(position).getDate().substring(0, 10));
                followupDetails.setArguments(bundle);
                ((MainPage) context).loadFragment(followupDetails, true);

            }

        });


    }

    @Override
    public int getItemCount() {
        return followupResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.name, R.id.description})
        List<TextView> textViews;
        @BindView(R.id.view)
        ImageView view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
