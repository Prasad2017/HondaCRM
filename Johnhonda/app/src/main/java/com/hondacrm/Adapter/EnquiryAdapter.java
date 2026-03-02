package com.hondacrm.Adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hondacrm.Activity.MainPage;
import com.hondacrm.Fragment.EnquiryDetails;
import com.hondacrm.Fragment.EnquiryFollowUpList;
import com.hondacrm.Fragment.EnquiryList;
import com.hondacrm.Fragment.UpdateEnquiry;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.EnquiryResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.MyViewHolder> {

    Context context;
    List<EnquiryResponse> enquiryResponseList;
    List<CategoryResponse> categoryResponseList;
    String[] closerIdList, closerNameList;
    String closerId;

    public EnquiryAdapter(Context context, List<EnquiryResponse> enquiryResponseList, List<CategoryResponse> categoryResponseList) {
        this.context = context;
        this.enquiryResponseList = enquiryResponseList;
        this.categoryResponseList = categoryResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.enquiry_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        EnquiryResponse enquiryResponse = enquiryResponseList.get(position);

        holder.textViews.get(0).setText("" + enquiryResponseList.get(position).getName());
        holder.textViews.get(1).setText("" + enquiryResponseList.get(position).getEnquiryN());
        try {
            if (enquiryResponseList.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.textViews.get(2).setText("Customer Name : " + enquiryResponseList.get(position).getName() +
                        "\nCustomer Mobile Number: " + enquiryResponseList.get(position).getPhone() +
                        "\nModel Category : " + enquiryResponseList.get(position).getModelCategory() +
                        "\nModel Name : " + enquiryResponseList.get(position).getModelName() +
                        "\nModel Variant : " + enquiryResponseList.get(position).getModelvarient() +
                        "\nStatus : Open" +
                        "\nStage : " + enquiryResponseList.get(position).getStage() +
                        "\nSource : " + enquiryResponseList.get(position).getSource() +
                        "\nDate : " + enquiryResponseList.get(position).getDate().substring(0, 10));
            } else if (enquiryResponseList.get(position).getStatus().equalsIgnoreCase("2")) {
                holder.textViews.get(2).setText("Customer Name : " + enquiryResponseList.get(position).getName() +
                        "\nCustomer Mobile Number: " + enquiryResponseList.get(position).getPhone() +
                        "\nModel Category : " + enquiryResponseList.get(position).getModelCategory() +
                        "\nModel Name : " + enquiryResponseList.get(position).getModelName() +
                        "\nModel Variant : " + enquiryResponseList.get(position).getModelvarient() +
                        "\nStatus : Close" +
                        "\nStage : " + enquiryResponseList.get(position).getStage() +
                        "\nSource : " + enquiryResponseList.get(position).getSource() +
                        "\nDate : " + enquiryResponseList.get(position).getDate().substring(0, 10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            if (enquiryResponseList.get(position).getType() == 0) {

                holder.imageViews.get(0).setVisibility(View.VISIBLE);
                holder.imageViews.get(1).setVisibility(View.VISIBLE);
                holder.imageViews.get(2).setVisibility(View.VISIBLE);
                holder.imageViews.get(3).setVisibility(View.VISIBLE);
                holder.imageViews.get(4).setVisibility(View.GONE);

                holder.textViews.get(4).setVisibility(View.VISIBLE);
                holder.textViews.get(5).setVisibility(View.VISIBLE);

            } else if (enquiryResponseList.get(position).getType() == 1) {

                holder.imageViews.get(0).setVisibility(View.VISIBLE);
                holder.imageViews.get(1).setVisibility(View.VISIBLE);
                holder.imageViews.get(2).setVisibility(View.VISIBLE);
                holder.imageViews.get(3).setVisibility(View.VISIBLE);
                holder.imageViews.get(4).setVisibility(View.GONE);

                holder.textViews.get(4).setVisibility(View.VISIBLE);
                holder.textViews.get(5).setVisibility(View.GONE);

            } else if (enquiryResponseList.get(position).getType() == 2) {

                holder.imageViews.get(0).setVisibility(View.GONE);
                holder.imageViews.get(1).setVisibility(View.GONE);
                holder.imageViews.get(2).setVisibility(View.GONE);
                holder.imageViews.get(3).setVisibility(View.GONE);
                holder.imageViews.get(4).setVisibility(View.GONE);

                holder.textViews.get(4).setVisibility(View.GONE);
                holder.textViews.get(5).setVisibility(View.GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.textViews.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (enquiryResponseList.get(position).getPhone() != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + enquiryResponseList.get(position).getPhone()));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "phone number not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.imageViews.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // perform click on Email ID
                /*Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.setType("text/html");
                emailIntent.setData(Uri.parse("mailto: " + enquiryResponseList.get(position).getStatus()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                try {
                    context.startActivity(emailIntent);
                } catch (ActivityNotFoundException ex) {
                    // handle error
                    ex.toString();
                }*/

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
                emailIntent.setData(Uri.parse("mailto:" + enquiryResponseList.get(position).getStatus()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.setClassName("com.google.android.gm", className);
                try {
                    context.startActivity(emailIntent);
                } catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });

        holder.imageViews.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (enquiryResponseList.get(position).getPhone() != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + enquiryResponseList.get(position).getPhone()));
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
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry Details");
                    String shareMessage = holder.textViews.get(2).getText().toString();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "Share Here"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        holder.imageViews.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EnquiryFollowUpList enquiryFollowUpList = new EnquiryFollowUpList();
                Bundle bundle = new Bundle();
                bundle.putString("enquiryId", "" + enquiryResponseList.get(position).getId());
                enquiryFollowUpList.setArguments(bundle);
                ((MainPage) context).loadFragment(enquiryFollowUpList, true);

            }
        });

        holder.imageViews.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnquiryDetails enquiryDetails = new EnquiryDetails();
                Bundle bundle = new Bundle();
                bundle.putString("enquiryId", "" + enquiryResponseList.get(position).getId());
                enquiryDetails.setArguments(bundle);
                ((MainPage) context).loadFragment(enquiryDetails, true);
            }
        });

        holder.textViews.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnquiryFollowUpList enquiryFollowUpList = new EnquiryFollowUpList();
                Bundle bundle = new Bundle();
                bundle.putString("enquiryId", "" + enquiryResponseList.get(position).getId());
                enquiryFollowUpList.setArguments(bundle);
                ((MainPage) context).loadFragment(enquiryFollowUpList, true);
            }
        });

        holder.textViews.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog).create();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.lost_enquiry_dialog, null);
                builder.setView(dialogView);
                builder.setCancelable(true);

                TextView submit = dialogView.findViewById(R.id.submit);
                MaterialSpinner reasonSpinner = dialogView.findViewById(R.id.reasonSpinner);
                ImageView close = dialogView.findViewById(R.id.close);

                try {
                    Log.e("categoryResponseList", "" + categoryResponseList.size());
                    if (categoryResponseList.size() > 0) {
                        try {
                            closerIdList = new String[categoryResponseList.size()];
                            closerNameList = new String[categoryResponseList.size()];

                            for (int i = 0; i < categoryResponseList.size(); i++) {
                                closerIdList[i] = categoryResponseList.get(i).getId();
                                closerNameList[i] = categoryResponseList.get(i).getClosure();
                            }

                            final ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, closerNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            reasonSpinner.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                reasonSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        closerId = closerIdList[position];
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (closerId != null) {
                            submitReason(closerId, enquiryResponseList.get(position).getId(), builder);
                        } else {
                            Toast.makeText(context, "please select closer", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                builder.setView(dialogView);
                builder.show();

            }
        });

        holder.textViews.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enquiryResponseList.get(position).getType() == 0) {
                    UpdateEnquiry updateEnquiry = new UpdateEnquiry();
                    Bundle bundle = new Bundle();
                    bundle.putString("enquiryId", "" + enquiryResponseList.get(position).getId());
                    updateEnquiry.setArguments(bundle);
                    ((MainPage) context).loadFragment(updateEnquiry, true);
                } else if (enquiryResponseList.get(position).getType() == 1) {
                    UpdateEnquiry updateEnquiry = new UpdateEnquiry();
                    Bundle bundle = new Bundle();
                    bundle.putString("enquiryId", "" + enquiryResponseList.get(position).getId());
                    updateEnquiry.setArguments(bundle);
                    ((MainPage) context).loadFragment(updateEnquiry, true);
                } else {
                    Toast.makeText(context, "Enquiry is closed now", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void submitReason(String closerId, String enquiryId, AlertDialog builder) {

        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        Call<String> call = Api.getClient().submitLostReason(closerId, enquiryId, MainPage.userId, formattedDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(context, "" + response.body(), Toast.LENGTH_SHORT).show();
                        ((MainPage) context).removeCurrentFragmentAndMoveBack();
                        ((MainPage) context).loadFragment(new EnquiryList(), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pDialog.dismiss();
                builder.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("reasonError", "" + t.getMessage());
                builder.dismiss();
                pDialog.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        return enquiryResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.name, R.id.number, R.id.description, R.id.contactNow, R.id.followup, R.id.lostEnquiry, R.id.update})
        List<TextView> textViews;
        @BindViews({R.id.view, R.id.call, R.id.email, R.id.share, R.id.moveQuotation})
        List<ImageView> imageViews;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
