package com.hondacrm.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.hondacrm.Extra.Common;
import com.hondacrm.Fragment.ContactList;
import com.hondacrm.Fragment.EnquiryList;
import com.hondacrm.Fragment.Home;
import com.hondacrm.Fragment.QuotationList;
import com.hondacrm.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainPage extends AppCompatActivity {

    public static ImageView menu, back, filter;
    public static DrawerLayout drawerLayout;
    public static TextView title, userNameTxt, userMobileNumberTxt, developedCompany, appVersion;
    public static String userId, userName, userNumber, currency = "₹", userBranchOffice, userHeadOffice;
    public static LinearLayout toolbarContainer;
    public static NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarContainer = findViewById(R.id.toolbar_container);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        initViews();

        try {
            userId = Common.getSavedUserData(MainPage.this, "userId");
            userName = Common.getSavedUserData(MainPage.this, "userName");
            userNumber = Common.getSavedUserData(MainPage.this, "userMobile");
            userBranchOffice = Common.getSavedUserData(MainPage.this, "userBranchOffice");
            userHeadOffice = Common.getSavedUserData(MainPage.this, "userHeadOffice");
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadFragment(new Home(), false);

        navigationView = findViewById(R.id.navigationView);
        View header = navigationView.getHeaderView(0);
        userNameTxt = header.findViewById(R.id.userName);
        userMobileNumberTxt = header.findViewById(R.id.userMobileNumber);

        userNameTxt.setText(userName);
        userMobileNumberTxt.setText(userNumber);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText("v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        loadFragment(new Home(), false);
                        break;

                    case R.id.enquiry:
                        loadFragment(new EnquiryList(), true);
                        break;

                    case R.id.quotation:
                        loadFragment(new QuotationList(), true);
                        break;

                    case R.id.contact:
                        loadFragment(new ContactList(), true);
                        break;

                    case R.id.support:
                        call();
                        break;

                    case R.id.aboutUs:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.landmarksol.com/"));
                        startActivity(browserIntent);
                        break;

                    case R.id.logout:
                        logout();
                        break;

                }

                return false;
            }
        });

    }

    private void logout() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainPage.this);
        // Setting Dialog Title
        alertDialog.setTitle("");
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to logout?");
        // On pressing Settings button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Common.saveUserData(MainPage.this, "userId", "");
                File file1 = new File("data/data/"+getPackageName()+"/shared_prefs/user.xml");
                if (file1.exists()) {
                    file1.delete();
                }

                Intent intent = new Intent(MainPage.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();

            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }


    @SuppressLint("CutPasteId")
    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        title = findViewById(R.id.title);
        menu = findViewById(R.id.menu);
        back = findViewById(R.id.back);
        filter = findViewById(R.id.filter);
        appVersion = findViewById(R.id.appVersion);
        developedCompany = findViewById(R.id.developedCompany);

    }

    @SuppressLint("RtlHardcoded")
    @OnClick({R.id.menu, R.id.back, R.id.developedCompany})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                if (!MainPage.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    MainPage.drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;

            case R.id.back:
                removeCurrentFragmentAndMoveBack();
                break;

            case R.id.developedCompany:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.landmarksol.com/"));
                startActivity(browserIntent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        // double press to exit
        if (menu.getVisibility() == View.VISIBLE) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        } else {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toasty.normal(MainPage.this, "Press back once more to exit", Toasty.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void lockUnlockDrawer(int lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
        if (lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            menu.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
        }

    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getResources().getString(R.string.contactNo)));
        startActivity(intent);
    }

    public void removeCurrentFragmentAndMoveBack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}