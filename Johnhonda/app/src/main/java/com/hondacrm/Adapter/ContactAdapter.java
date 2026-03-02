package com.hondacrm.Adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Fragment.UpdateContact;
import com.hondacrm.Module.ContactResponse;
import com.hondacrm.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    Context context;
    List<ContactResponse> contactResponseList;

    public ContactAdapter(Context context, List<ContactResponse> contactResponseList) {
        this.context = context;
        this.contactResponseList = contactResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ContactResponse contactResponse = contactResponseList.get(position);

        holder.textViews.get(0).setText("" + contactResponseList.get(position).getName());
        holder.textViews.get(1).setText("" + contactResponseList.get(position).getPhone());
        holder.textViews.get(2).setText("" + contactResponseList.get(position).getMailId());
        holder.textViews.get(3).setText("" + contactResponseList.get(position).getAddress());

        holder.imageViews.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateContact updateContact = new UpdateContact();
                Bundle bundle = new Bundle();
                bundle.putString("name", "" + contactResponseList.get(position).getName());
                bundle.putString("phone", "" + contactResponseList.get(position).getPhone());
                bundle.putString("email", "" + contactResponseList.get(position).getMailId());
                bundle.putString("address", "" + contactResponseList.get(position).getAddress());
                bundle.putString("contactType", "" + contactResponseList.get(position).getContactType());
                updateContact.setArguments(bundle);
                ((MainPage) context).loadFragment(updateContact, true);

            }
        });

        holder.imageViews.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // perform click on Email ID
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/html");
                final PackageManager pm = context.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                String className = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                        className = info.activityInfo.name;

                        if (className != null && !className.isEmpty()) {
                            break;
                        }
                    }
                }
                emailIntent.setData(Uri.parse("mailto:" + contactResponseList.get(position).getMailId()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.setClassName("com.google.android.gm", className);
                try {
                    context.startActivity(emailIntent);
                } catch (ActivityNotFoundException ex) {
                    // handle error
                }

            }
        });

        holder.imageViews.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (contactResponseList.get(position).getPhone() != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + contactResponseList.get(position).getPhone()));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "phone number not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.imageViews.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Contact Details");
                    String shareMessage = "\nName : " + contactResponseList.get(position).getName() +
                            "\nPhone Number : " + contactResponseList.get(position).getPhone() +
                            "\nEmail : " + contactResponseList.get(position).getMailId() +
                            "\nAddress : " + contactResponseList.get(position).getAddress();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "Share Here"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return contactResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.name, R.id.contact, R.id.emailId, R.id.address})
        List<TextView> textViews;
        @BindViews({R.id.view, R.id.call, R.id.email, R.id.share})
        List<ImageView> imageViews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
