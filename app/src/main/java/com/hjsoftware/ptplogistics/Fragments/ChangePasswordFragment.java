package com.hjsoftware.ptplogistics.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;
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

public class ChangePasswordFragment extends Fragment {
    TextView tv_userName;
    EditText et_oldPass,et_newPass;
    Button bt_save,bt_cancel;
    View v;
    String st_oldpass,st_newpass,stTime;
    String stUname,stpwd,stProfileid;
    SessionManager session;
    HashMap<String, String> user;
    ProgressDialog progressDialog;
    API REST_CLIENT;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
     @Nullable
    @Override
    public View onCreateView(android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater .inflate(R.layout.change_password, container, false);



         REST_CLIENT= RestClient.get();
         session=new SessionManager(getContext());
         user=session.getUserDetails();
         stUname=user.get(SessionManager.KEY_NAME);
         stpwd=user.get(SessionManager.KEY_PWD);
         stProfileid=user.get(SessionManager.KEY_PROFILE_ID);
         pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
         editor = pref.edit();
         stTime=pref.getString("logintime",null);
         String upperString = stUname.substring(0,1).toUpperCase() + stUname.substring(1);
         System.out.println("@##@##Time is"+stTime);
         tv_userName=(TextView)v.findViewById(R.id.cp_tv_user_name);
         et_oldPass=(EditText)v.findViewById(R.id.cp_et_old_pass);
         et_newPass=(EditText)v.findViewById(R.id.cp_et_new_pass);
         bt_save=(Button)v.findViewById(R.id.cp_bt_save);
         bt_cancel=(Button)v.findViewById(R.id.cp_bt_cancel);
         tv_userName.setText(upperString);
         System.out.println("profileId"+stProfileid);
         System.out.println("current password"+stpwd);

         bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_oldpass=et_oldPass.getText().toString();
                st_newpass=et_newPass.getText().toString();


                if(st_oldpass.equals("")||st_newpass.equals(""))
                {
                    Toast.makeText(getActivity(),"Please fill all the details!",Toast.LENGTH_SHORT).show();
                }
                else if(!stpwd.equals(st_oldpass))
                {
                    Toast.makeText(getActivity(),"Old Password doesn't matches with the stored password",Toast.LENGTH_SHORT).show();

                    et_oldPass.setText("");

                }
                else{
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    JsonObject v=new JsonObject();
                    v.addProperty("ProfileID",stProfileid);
                    v.addProperty("oldpassword",st_oldpass);
                    v.addProperty("newpassword",st_newpass);

                    System.out.print(" profileid"+stProfileid);
                    System.out.print(" oldPassword"+st_oldpass);
                    System.out.print(" newpasword"+st_newpass);
                    Call<Pojo>call=REST_CLIENT.changePassword(v);
                    call.enqueue(new Callback<Pojo>() {
                        @Override
                        public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                            Pojo data;
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"Password updated successfully!",Toast.LENGTH_SHORT).show();

                                et_oldPass.setText("");
                                et_newPass.setText("");
                                sendLogoutStatus();

                            }
                        }

                        @Override
                        public void onFailure(Call<Pojo> call, Throwable t) {
                           progressDialog.dismiss();
                            Toast.makeText(getContext(),"Please check Internet connection!",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

     return v;

}
    public void sendLogoutStatus()
    {

        JsonObject v=new JsonObject();
        v.addProperty("ProfileID",stProfileid);
        v.addProperty("logintime",stTime);

        System.out.println("logoutProfileId"+stProfileid);
        System.out.println("logOutlogintime"+stTime);

        Call<Pojo>call=REST_CLIENT.logout(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if(response.isSuccessful()){
                    session.logoutUser();
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getContext(),"Please check Internet connection!",Toast.LENGTH_LONG).show();

            }
        });

    }
}
