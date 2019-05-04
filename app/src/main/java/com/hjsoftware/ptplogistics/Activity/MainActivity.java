package com.hjsoftware.ptplogistics.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.Pojo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText et_userName,et_password;
    Button bt_Login;
    String st_username,st_password,stProfileid,stTime;
    SessionManager session;
    HashMap<String, String> user;
    API REST_CLIENT;
    ProgressDialog progressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        session=new SessionManager(getApplicationContext());
        user = session.getUserDetails();
        REST_CLIENT= RestClient.get();
        et_userName=(EditText)findViewById(R.id.al_et_uname);
        et_password=(EditText)findViewById(R.id.al_et_pwd);
        bt_Login=(Button)findViewById(R.id.al_bt_login);
        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        checkIfLoggedIn();

        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                st_username=et_userName.getText().toString();
                st_password=et_password.getText().toString();
                if(st_username.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter login name!", Toast.LENGTH_SHORT).show();
                }
                else if(st_password.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter Pwd!", Toast.LENGTH_SHORT).show();

                }
                else{

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    JsonObject v=new JsonObject();
                    v.addProperty("username",st_username );
                    v.addProperty("password",st_password);

                    System.out.println("details "+st_username+"@@"+st_password);

                   Call<Pojo>call=REST_CLIENT.sendLoginDetails(v);
                   call.enqueue(new Callback<Pojo>() {
                       @Override
                       public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                           Pojo data;
                           String s;
                           if(response.isSuccessful()){
                               progressDialog.dismiss();
                               data=response.body();
                               s=data.getMessage();


                               if(s.split("\\+").length==3) {
                                   stProfileid = s.split("\\+")[0];

                                   stTime = s.split("\\+")[2];
                                   editor.putString("logintime", stTime);
                                   editor.commit();

                                   String msg = response.message();
                                   System.out.println("########123msg@@@@@@@@@@@@@@@" + msg);

                                   session.createLoginSession(st_username, st_password, stProfileid);

                                   System.out.println("details " + st_username + "@@" + st_password + "@@" + stTime + "@@" + stProfileid);

                                   Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                   startActivity(i);
                                   finish();
                               }
                               else {

                                   Toast.makeText(MainActivity.this,"Error: "+s,Toast.LENGTH_SHORT).show();
                               }

                           }
                           else {
                               String msg=response.message();
                              System.out.println("########msg@@@@@@@@@@@@@@@" +msg);
                              progressDialog.dismiss();
                               et_userName.setText("");
                               et_password.setText("");
                               Toast.makeText(MainActivity.this,"Invalid credentials!!",Toast.LENGTH_SHORT).show();

                           }
                       }

                       @Override
                       public void onFailure(Call<Pojo> call, Throwable t) {
                        progressDialog.dismiss();
                           Toast.makeText(MainActivity.this,"Please check Internet connection!",Toast.LENGTH_SHORT).show();
                           et_userName.setText("");
                           et_password.setText("");
                           t.printStackTrace();
                           System.out.println("@@@@@@@@@@msg"+t.getMessage());
                       }
                   });
                }


            }
        });
    }


    public void checkIfLoggedIn()
    {

        //System.out.println("session.checkLogin" + session.checkLogin());

        if(isNetworkAvailable()) {

            if (session.checkLogin()) {

                st_username = user.get(SessionManager.KEY_NAME);
                st_password = user.get(SessionManager.KEY_PWD);
                stProfileid = user.get(SessionManager.KEY_PROFILE_ID);

                System.out.println("*****" + st_username + st_password + stProfileid);

                session.createLoginSession(st_username, st_password, stProfileid);

                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();


            }
        }
        else {

            Toast.makeText(MainActivity.this,"Please check Internet connection!",Toast.LENGTH_SHORT).show();
            finish();
        }



    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
