package com.hjsoftware.ptplogistics.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Adapters.DrawerItemCustomAdapter;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.Fragments.LrEditOneFragment;
import com.hjsoftware.ptplogistics.Fragments.NewLrBookingOneFragment;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.NavigationData;
import com.hjsoftware.ptplogistics.model.Pojo;

import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hjsoft on 19/5/18.
 */
public class LREditActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    DrawerItemCustomAdapter adapter;
    final static int REQUEST_LOCATION = 199;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    TextView tvName,tvMobile;
    RelativeLayout rLayout;
    SessionManager session;
    String stTime,stProfileid;
    API REST_CLIENT;
    HashMap<String, String> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTitle = mDrawerTitle = getTitle();
        //mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_view_drawer);
        tvName=(TextView)findViewById(R.id.ah_tv_name);
        // tvMobile=(TextView)findViewById(R.id.ah_tv_mobile);
        rLayout=(RelativeLayout)findViewById(R.id.left_drawer);

        REST_CLIENT= RestClient.get();

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();
        stProfileid=user.get(SessionManager.KEY_PROFILE_ID);

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        stTime=pref.getString("logintime",null);

        System.out.println("loginntime is@@@@@@@@@@"+stTime);
        System.out.println("profileid @@@@@@@@@@"+stProfileid);


        String myString=pref.getString("name","xxx");

        String upperString = myString.substring(0,1).toUpperCase() + myString.substring(1);

        tvName.setText(upperString);
        // tvMobile.setText(pref.getString("mobile","91xxxxxxxx"));

        setupToolbar();

        NavigationData[] drawerItem = new NavigationData[6];

        drawerItem[0] = new NavigationData(R.drawable.arrow, "LR Booking");
        drawerItem[1] = new NavigationData(R.drawable.arrow, "All Bookings");
        drawerItem[2] = new NavigationData(R.drawable.arrow, "LR Tracking");
        drawerItem[3] = new NavigationData(R.drawable.arrow, "LR POD Update");
        drawerItem[4] = new NavigationData(R.drawable.arrow, "Change Password");
        drawerItem[5] = new NavigationData(R.drawable.arrow, "Logout");

       /* drawerItem[1] = new NavigationData(R.drawable.arrow, "Loading Sheet");
        drawerItem[2] = new NavigationData(R.drawable.arrow, "UnLoading Sheet");
        drawerItem[3] = new NavigationData(R.drawable.arrow, "Goods Delivery");
        drawerItem[4] = new NavigationData(R.drawable.arrow, "LR Cancel");
        drawerItem[5] = new NavigationData(R.drawable.arrow, "LR Tracking");
        drawerItem[6] = new NavigationData(R.drawable.arrow, "Change Password");
        drawerItem[7] = new NavigationData(R.drawable.arrow,"Logout");*/
        /*drawerItem[4] = new NavigationData(R.drawable.arrow,"Rate Card");
        drawerItem[5] = new NavigationData(R.drawable.arrow,"Support");
        drawerItem[6] = new NavigationData(R.drawable.arrow,"Logout");*/


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setupDrawerToggle();

        Fragment fragment=new LrEditOneFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment,"All_Bookings").commit();
        setTitle("Edit LR");
        adapter.setSelectedItem(-1);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {


                if (getSupportFragmentManager().getBackStackEntryCount() >0) {

                    mDrawerToggle.setDrawerIndicatorEnabled(false);//showing back button
                    setTitle("Edit LR");

                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // onBackPressed();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag("All_Bookings"));
                            fragmentManager.popBackStackImmediate();
                        }
                    });
                }
                else
                {

                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    setTitle("Edit LR");

                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //mDrawerLayout.openDrawer(mDrawerList);
                            mDrawerLayout.openDrawer(rLayout);
                        }
                    });
                }
            }
        });
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;
        adapter.setSelectedItem(position);

        switch (position) {
            case 0:

                Intent k=new Intent(LREditActivity.this,HomeActivity.class);
                k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(k);
                finish();

                break;
            case 1:
                Intent r=new Intent(LREditActivity.this,BookingListActivity.class);
                r.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                r.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(r);
                finish();
                break;
            case 2:
                Intent l=new Intent(LREditActivity.this,LRTrackingActivity.class);
                l.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(l);
                finish();
                break;


            case 3:
                Intent m=new Intent(LREditActivity.this,LRPODUpdateActivity.class);
                m.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(m);
                finish();
                break;
            /*case 3 :
                Intent n=new Intent(LREditActivity.this,GoodsDeliveryActivity.class);
                n.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(n);
                finish();
                break;

            case 4:
                Intent o=new Intent(LREditActivity.this,LRCancelActivity.class);
                o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                o.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(o);
                finish();
                break;
             case 5 :
                 Intent p=new Intent(LREditActivity.this,LRTrackingActivity.class);
                p.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(p);
                finish();
                 break;*/
            case 4:
                Intent q=new Intent(LREditActivity.this,ChangePasswordActivity.class);
                q.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                q.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(q);
                finish();
                break;
            case 5:

                sendLogoutStatus();
                break;


            default:
                break;
        }

        if (fragment != null) {

            openFragment(fragment,position);

        } else {
            // Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void openFragment(Fragment fragment, int position){

        Fragment containerFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (containerFragment.getClass().getName().equalsIgnoreCase(fragment.getClass().getName())) {
            mDrawerLayout.closeDrawer(mDrawerList);
            return;
        }

        else{
            /*
           FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            */
            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        /*
        int titleId = getResources().getIdentifier("toolbar", "id", "android");
        TextView abTitle = (TextView) findViewById(titleId);
        abTitle.setTextColor(Color.parseColor("#000000"));*/
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

//sendLogoutStatus();
        super.onBackPressed();

    }

    public void sendLogoutStatus()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LREditActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_logout, null);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        Button btOk=(Button)dialogView.findViewById(R.id.alog_bt_ok);
        Button btCancel=(Button)dialogView.findViewById(R.id.alog_bt_cancel);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("****stProfileid" + stProfileid);
                System.out.println("****logintime" + stTime);

                JsonObject v=new JsonObject();
                v.addProperty("ProfileID",stProfileid);
                v.addProperty("logintime",stTime);
                Call<Pojo>call=REST_CLIENT.logout(v);
                call.enqueue(new Callback<Pojo>() {
                    @Override
                    public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                        if(response.isSuccessful()){
                            alertDialog.dismiss();
                            session.logoutUser();

                            finish();
                            Toast.makeText(LREditActivity.this, "You have successfully Logged Out!", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(LREditActivity.this, "Error"+response.message(), Toast.LENGTH_SHORT).show();
                            System.out.println("****"+response.message()+response.isSuccessful());
                        }
                    }

                    @Override
                    public void onFailure(Call<Pojo> call, Throwable t) {
                        Toast.makeText(LREditActivity.this,"Please check Internet connection!",Toast.LENGTH_LONG).show();

                        alertDialog.dismiss();
                        finish();
                    }
                });

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });
    }





}
