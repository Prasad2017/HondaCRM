package com.hondacrm.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
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
import com.hondacrm.Fragment.QuotationFollowUpList;
import com.hondacrm.Fragment.QuotationList;
import com.hondacrm.Fragment.UpdateQuotation;
import com.hondacrm.Invoice.QuotationPDF;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;


@SuppressLint("SetTextI18n, IntentReset")
public class QuotationAdapter extends RecyclerView.Adapter<QuotationAdapter.MyViewHolder> {

    Context context;
    List<QuotationResponse> quotationResponseList;

    public QuotationAdapter(Context context, List<QuotationResponse> quotationResponseList) {
        this.context = context;
        this.quotationResponseList = quotationResponseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quotation_item_list, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        QuotationResponse quotationResponse = quotationResponseList.get(position);

        holder.textViews.get(0).setText(quotationResponseList.get(position).getName());
        holder.textViews.get(1).setText(quotationResponseList.get(position).getQuotationN());
        try {
            if (quotationResponseList.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.textViews.get(2).setText("Customer Name : " + quotationResponseList.get(position).getName() +
                        "\nCustomer Mobile Number: " + quotationResponseList.get(position).getPhone() +
                        "\nModel Category : " + quotationResponseList.get(position).getModelCategory() +
                        "\nModel Name : " + quotationResponseList.get(position).getModelName() +
                        "\nModel Variant : " + quotationResponseList.get(position).getModelvarient() +
                        "\nStatus : Open" +
                        "\nStage : " + quotationResponseList.get(position).getStage() +
                        "\nSource : " + quotationResponseList.get(position).getSource() +
                        "\nDate : " + quotationResponseList.get(position).getDate().substring(0, 10));
            } else if (quotationResponseList.get(position).getStatus().equalsIgnoreCase("2")) {
                holder.textViews.get(2).setText("Customer Name : " + quotationResponseList.get(position).getName() +
                        "\nCustomer Mobile Number: " + quotationResponseList.get(position).getPhone() +
                        "\nModel Category : " + quotationResponseList.get(position).getModelCategory() +
                        "\nModel Name : " + quotationResponseList.get(position).getModelName() +
                        "\nModel Variant : " + quotationResponseList.get(position).getModelvarient() +
                        "\nStatus : Close" +
                        "\nStage : " + quotationResponseList.get(position).getStage() +
                        "\nSource : " + quotationResponseList.get(position).getSource() +
                        "\nDate : " + quotationResponseList.get(position).getDate().substring(0, 10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.textViews.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quotationResponseList.get(position).getPhone() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + quotationResponseList.get(position).getPhone()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "phone number not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.imageViews.get(2).setOnClickListener(new View.OnClickListener() {
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
                emailIntent.setData(Uri.parse("mailto:" + quotationResponseList.get(position).getStatus()));
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
                if (quotationResponseList.get(position).getPhone() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + quotationResponseList.get(position).getPhone()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "phone number not found", Toast.LENGTH_SHORT).show();
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

                QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                Bundle bundle = new Bundle();
                bundle.putString("quotationId", quotationResponseList.get(position).getId());
                quotationFollowUpList.setArguments(bundle);
                ((MainPage) context).loadFragment(quotationFollowUpList, true);

            }
        });

        holder.textViews.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                Bundle bundle = new Bundle();
                bundle.putString("quotationId", quotationResponseList.get(position).getId());
                quotationFollowUpList.setArguments(bundle);
                ((MainPage) context).loadFragment(quotationFollowUpList, true);

            }
        });

        holder.textViews.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateQuotation updateQuotation = new UpdateQuotation();
                Bundle bundle = new Bundle();
                bundle.putString("quotationId", quotationResponseList.get(position).getId());
                updateQuotation.setArguments(bundle);
                ((MainPage) context).loadFragment(updateQuotation, true);

            }
        });

        holder.imageViews.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 31) {
                    requestNewPermission(quotationResponse);
                } else {
                    requestPermission(quotationResponse);
                }

            }
        });

    }

    public void requestNewPermission(QuotationResponse quotationResponse) {

        Dexter.withContext(context)
                .withPermissions(Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_AUDIO,
                        Manifest.permission.READ_MEDIA_VIDEO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            QuotationPDF quotationPDF = new QuotationPDF();
                            quotationPDF.generatePDF(context, quotationResponse);

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    public void requestPermission(QuotationResponse quotationResponse) {

        Dexter.withContext(context)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            QuotationPDF quotationPDF = new QuotationPDF();
                            quotationPDF.generatePDF(context, quotationResponse);

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    @Override
    public int getItemCount() {
        return quotationResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindViews({R.id.name, R.id.number, R.id.description, R.id.contactNow, R.id.followup, R.id.update})
        List<TextView> textViews;
        @BindViews({R.id.view, R.id.call, R.id.email, R.id.share, R.id.pdfView})
        List<ImageView> imageViews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}