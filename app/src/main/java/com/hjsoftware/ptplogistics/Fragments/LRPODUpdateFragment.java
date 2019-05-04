package com.hjsoftware.ptplogistics.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Activity.LRPODUpdateActivity;
import com.hjsoftware.ptplogistics.Activity.ViewImageActivity;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.LRPodUpdatePojo;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class LRPODUpdateFragment extends Fragment{

    View v;
    TextView tvFromLoc,tvToLoc,tvSname,tvRname,tvLrtype,tvPodStat;
    TextView tvShowDetails,tvUploadImg,tvSendImg;
    ImageView iv1,iv2,iv3,iv4;
    API REST_CLIENT;
    int count = 1;
    public static ArrayList<String> filePaths = new ArrayList<String>();
    LinearLayout ll1,ll;
    String lrno,profileId;
    SessionManager session;
    HashMap<String, String> user;
    EditText etLrno;
    SimpleDraweeView sv;
    LRPodUpdatePojo p;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_lr_pod_update,container,false);

        etLrno=(EditText)v.findViewById(R.id.lr_pod_update_et_lr_no);

        tvFromLoc=(TextView)v.findViewById(R.id.lr_pod_update_tv_from_loc);
        tvToLoc=(TextView)v.findViewById(R.id.lr_pod_update_tv_to_loc);
        tvSname=(TextView)v.findViewById(R.id.lr_pod_update_tv_sname);
        tvRname=(TextView)v.findViewById(R.id.lr_pod_update_tv_rname);
        tvLrtype=(TextView)v.findViewById(R.id.lr_pod_update_tv_lr_type);
        tvPodStat=(TextView)v.findViewById(R.id.lr_pod_update_tv_pod_status);

        tvShowDetails=(TextView)v.findViewById(R.id.lr_pod_update_tv_show_details);
        tvUploadImg=(TextView)v.findViewById(R.id.lr_pod_update_tv_upload_img);
        tvSendImg=(TextView)v.findViewById(R.id.lr_pod_update_tv_send_img);

        iv1=(ImageView)v.findViewById(R.id.lr_pod_update_iv1);
        iv2=(ImageView)v.findViewById(R.id.lr_pod_update_iv2);
        iv3=(ImageView)v.findViewById(R.id.lr_pod_update_iv3);
        iv4=(ImageView)v.findViewById(R.id.lr_pod_update_iv4);

        ll1=(LinearLayout)v.findViewById(R.id.lr_pod_update_ll1);
        ll=(LinearLayout)v.findViewById(R.id.lr_pod_update_ll);
        ll1.setVisibility(View.GONE);

        sv=(SimpleDraweeView)v.findViewById(R.id.fb_iv_img);
        sv.setVisibility(View.GONE);

        tvUploadImg.setVisibility(View.GONE);
        ll.setVisibility(View.GONE);
        tvSendImg.setVisibility(View.GONE);

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), ViewImageActivity.class);
                i.putExtra("img",p.getFilepath());
                startActivity(i);
            }
        });

        tvShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lrno=etLrno.getText().toString().trim();

                if(lrno.equals(""))
                {
                    Toast.makeText(getActivity(),"Please enter LrNo!",Toast.LENGTH_SHORT).show();
                }
                else {

                    System.out.println("lrno..."+lrno);

                    retrofit2.Call<List<LRPodUpdatePojo>> call=REST_CLIENT.updatePODStatus(lrno,profileId);
                    call.enqueue(new Callback<List<LRPodUpdatePojo>>() {
                        @Override
                        public void onResponse(retrofit2.Call<List<LRPodUpdatePojo>> call, Response<List<LRPodUpdatePojo>> response) {


                            List<LRPodUpdatePojo> pList;

                            if(response.isSuccessful())
                            {
                                pList=response.body();

                                p=pList.get(0);

                                ll1.setVisibility(View.VISIBLE);

                                tvFromLoc.setText(p.getFromLoc());
                                tvToLoc.setText(p.getToLoc());
                                tvSname.setText(p.getSendername());
                                tvRname.setText(p.getReceiverName());
                                tvLrtype.setText(p.getLrtype());
                                tvPodStat.setText(p.getPODStatus());

                                if(p.getPODStatus().equals("Y"))
                                {
                                    Toast.makeText(getActivity(),"POD Status is already updated!",Toast.LENGTH_SHORT).show();
                                    //tvUploadImg.setVisibility(View.GONE);
                                    ll.setVisibility(View.GONE);
                                    tvSendImg.setVisibility(View.GONE);
                                    sv.setVisibility(View.VISIBLE);
                                    sv.setImageURI(p.getFilepath());
                                }
                                else {

                                    tvUploadImg.setVisibility(View.VISIBLE);
                                    ll.setVisibility(View.VISIBLE);

                                    //tvSendImg.setVisibility(View.VISIBLE);
                                }
                            }
                            else {

                                Toast.makeText(getActivity(),"Error:"+response.message(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<List<LRPodUpdatePojo>> call, Throwable t) {


                            t.printStackTrace();
                        }
                    });

                }
            }
        });






        tvUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 23) {
                    //  //System.out.println("Sdk_int is"+Build.VERSION.SDK_INT);
                    getData();
                } else {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        getData();
                    } else {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            Toast.makeText(getActivity(), "This permission is required for the images to be uploaded!", Toast.LENGTH_LONG).show();
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }
                }
            }
        });
        
        tvSendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                sendtoServer();
            }
        });

        iv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setMessage("Delete image?");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        iv1.setImageDrawable(null);

                        int k=(Integer) iv1.getTag();
                        for(int l=0; l<filePaths.size(); l++)
                        {
                            if(l==k)
                            {
                                filePaths.remove(l);
                            }
                        }
                        count--;

                        if(filePaths.size()<5)
                        {
                            tvUploadImg.setVisibility(View.VISIBLE);
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return false;

            }
        });

        iv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setMessage("Delete image?");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        iv2.setImageDrawable(null);

                        int k=(Integer) iv2.getTag();
                        for(int l=0; l<filePaths.size(); l++)
                        {
                            if(l==k)
                            {
                                filePaths.remove(l);
                            }
                        }
                        count--;

                        if(filePaths.size()<5)
                        {
                            tvUploadImg.setVisibility(View.VISIBLE);
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return false;
            }
        });

        iv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setMessage("Delete image?");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        iv3.setImageDrawable(null);

                        int k=(Integer) iv3.getTag();
                        for(int l=0; l<filePaths.size(); l++)
                        {
                            if(l==k)
                            {
                                filePaths.remove(l);
                            }
                        }
                        count--;

                        if(filePaths.size()<5)
                        {
                            tvUploadImg.setVisibility(View.VISIBLE);
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return false;
            }
        });

        iv4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setMessage("Delete image?");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        iv4.setImageDrawable(null);

                        int k=(Integer) iv4.getTag();
                        for(int l=0; l<filePaths.size(); l++)
                        {
                            if(l==k)
                            {
                                filePaths.remove(l);
                            }
                        }
                        count--;

                        if(filePaths.size()<5)
                        {
                            tvUploadImg.setVisibility(View.VISIBLE);
                        }
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return false;
            }
        });





        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(getContext());

        REST_CLIENT= RestClient.get();

        session=new SessionManager(getContext());
        user=session.getUserDetails();

        //Detail 1

        profileId=user.get(SessionManager.KEY_PROFILE_ID);
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("MM/dd/yyyy").format(cDate);

        System.out.println("date isss"+fDate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getData();
            } else {
                Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void getData() {
        //if (count < 2) {
        if (count == 1) {

            if (tvSendImg.isShown()) {
            } else {
                tvSendImg.setVisibility(View.VISIBLE);
            }

            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            startActivityForResult(galleryIntent, 0);
            tvUploadImg.setVisibility(View.GONE);
            count++;
        } else {
            Toast.makeText(getActivity(), "Cannot Upload further..!", Toast.LENGTH_LONG).show();
            tvUploadImg.setVisibility(View.GONE);
            //ivPaperClip.setEnabled(false);
        }
    }

    public void sendtoServer() {

        Toast.makeText(getActivity(), "Uploading images!!!", Toast.LENGTH_SHORT).show();
        MediaType MEDIA_TYPE_IMG = MediaType.parse("image/jpeg");
        MultipartBody.Builder builder=new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        RequestBody requestBody;
        try {

            for (int i = 0; i < filePaths.size(); i++) {

                File file = new File(filePaths.get(i));
                File compressedImageFile= Compressor.getDefault(getActivity()).compressToFile(file);
                //requestBody=RequestBody.create(MEDIA_TYPE_IMG,file);
                requestBody=RequestBody.create(MEDIA_TYPE_IMG,compressedImageFile);
                // builder.addFormDataPart("photo"+i,file.getName(),requestBody);
                builder.addFormDataPart("photo"+i,compressedImageFile.getName(),requestBody);
            }
            RequestBody finalRequestBody=builder.build();
            REST_CLIENT= RestClient.get();

            // //System.out.println(data.getDslipid()+"*"+data.getDriverid()+"*"+data.getStartdate());

            //JsonObject j=new JsonObject();

            Date cDate = new Date();
            String fDate = new SimpleDateFormat("MM/dd/yyyy").format(cDate);



            retrofit2.Call<List<String>> upload=REST_CLIENT.uploadData(lrno,profileId,fDate,finalRequestBody);
            upload.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(retrofit2.Call<List<String>> call, Response<List<String>> response) {

                    ////System.out.println("response is "+response.isSuccessful()+response.message()+response.code());

                    if(response.isSuccessful())
                    {
                        tvSendImg.setVisibility(View.GONE);
                        tvUploadImg.setVisibility(View.GONE);

                        iv1.setEnabled(false);
                        iv1.setLongClickable(false);
                        iv2.setEnabled(false);
                        iv2.setLongClickable(false);
                        iv3.setEnabled(false);
                        iv3.setLongClickable(false);
                        iv4.setEnabled(false);
                        iv4.setLongClickable(false);

                        Toast.makeText(getActivity(),"File(s) Uploaded !",Toast.LENGTH_SHORT).show();

                        Toast.makeText(getActivity(),"POD is updated successfully!",Toast.LENGTH_SHORT).show();

                        /*Intent i=new Intent(getActivity(), LRPODUpdateActivity.class);
                        startActivity(i);
                        getActivity().finish();*/

                    }
                    else
                    {
                        ////System.out.println("error sending....");
                        // //System.out.println(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<String>> call, Throwable t) {

                    t.printStackTrace();
                    t.getCause();
                    //btOk.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"No Network Connection! Please Retry!",Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == getActivity().RESULT_OK && data != null) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mediaPath = cursor.getString(columnIndex);

            ////System.out.println("mediapath is"+mediaPath);
            // Set the Image in ImageView for Previewing the Media

            if (iv1.getDrawable() == null) {

                String extension = mediaPath.substring(mediaPath.lastIndexOf("."));

                if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                    filePaths.add(mediaPath);
                    iv1.setImageBitmap(decodeSampledBitmapFromResource(mediaPath, 100, 100));
                    iv1.setTag(0);
                } else {
                    Toast.makeText(getActivity(), "Cannot Upload file other than .png or .jpg", Toast.LENGTH_LONG).show();
                }
            } else if (iv2.getDrawable() == null) {
                //  ivImage2.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                String extension = mediaPath.substring(mediaPath.lastIndexOf("."));

                if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                    //add fil to ArayList
                    filePaths.add(mediaPath);
                    iv2.setImageBitmap(decodeSampledBitmapFromResource(mediaPath, 100, 100));
                    iv2.setTag(1);
                } else {
                    Toast.makeText(getActivity(), "Cannot Upload file other than .png or .jpg", Toast.LENGTH_LONG).show();
                }
            } else if (iv3.getDrawable() == null) {
                //ivImage3.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                String extension = mediaPath.substring(mediaPath.lastIndexOf("."));

                if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                    //add fil to ArayList
                    filePaths.add(mediaPath);
                    iv3.setImageBitmap(decodeSampledBitmapFromResource(mediaPath, 100, 100));
                    iv3.setTag(2);
                } else {
                    Toast.makeText(getActivity(), "Cannot Upload file other than .png or .jpg", Toast.LENGTH_LONG).show();
                }
            } else if (iv4.getDrawable() == null) {
                // ivImage4.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

                String extension = mediaPath.substring(mediaPath.lastIndexOf("."));

                if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                    filePaths.add(mediaPath);
                    iv4.setImageBitmap(decodeSampledBitmapFromResource(mediaPath, 100, 100));
                    iv4.setTag(3);
                } else {
                    Toast.makeText(getActivity(), "Cannot Upload file other than .png or .jpg", Toast.LENGTH_LONG).show();
                }
            }
            cursor.close();
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String file, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap b1 = BitmapFactory.decodeFile(file, options);
        // //System.out.println("size of bitmap b1 is " + b1.getByteCount());
        Bitmap b2 = Bitmap.createScaledBitmap(b1, 100, 100, true);

        return b1;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
