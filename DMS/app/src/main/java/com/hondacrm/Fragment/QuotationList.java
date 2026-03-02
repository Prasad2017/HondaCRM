package com.hondacrm.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Adapter.QuotationAdapter;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuotationList extends Fragment {

    View view;
    static Activity activity;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noDataFound)
    TextView noDataFound;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    int page = 1, maxPage;
    List<QuotationResponse> quotationResponseList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quotation_list, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Quotation");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .permitNetwork()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        activity = getActivity();

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new Home(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("tag", "onKey Back listener is working!!!");
                    try {
                        ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                        ((MainPage) getActivity()).loadFragment(new Home(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //check condition
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //Increase page size
                page++;
                if (page <= maxPage) {
                    getQuotationList();
                }
            }
        });

        return view;
    }

    @OnClick({R.id.addQuotation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addQuotation:
                ((MainPage) getActivity()).loadFragment(new AddQuotation(), true);
                break;
        }
    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getQuotationList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getQuotationList() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);

        Call<List<QuotationResponse>> call;
        if (Integer.parseInt(MainPage.userId) == 1) {
            call = Api.getClient().getAdminQuotationPaginationList(page, 10);
        } else {
            call = Api.getClient().getQuotationPaginationList(MainPage.userId, page, 10);
        }

        call.enqueue(new Callback<List<QuotationResponse>>() {
            @Override
            public void onResponse(Call<List<QuotationResponse>> call, Response<List<QuotationResponse>> response) {

                if (response.isSuccessful()) {

                    quotationResponseList.addAll(response.body());
                    if (quotationResponseList.size() > 0) {

                        // get headers
                        String headers = response.headers().get("Paging-Headers");
                        try {
                            JSONObject jsonObject = new JSONObject(headers);
                            maxPage = Integer.parseInt(jsonObject.getString("totalPages"));
                            Log.e("totalPages", "" + maxPage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        QuotationAdapter adapter = new QuotationAdapter(getActivity(), quotationResponseList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.notifyItemInserted(quotationResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        noDataFound.setVisibility(View.GONE);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<QuotationResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
                Log.e("quotationError", "" + t.getMessage());
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.error_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                TextView txtYes = dialog.findViewById(R.id.yes);

                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    public static void showSettingsDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private static void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }

}