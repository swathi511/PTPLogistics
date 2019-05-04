package com.hjsoftware.ptplogistics.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hjsoftware.ptplogistics.R;

public class ViewImageActivity extends AppCompatActivity {

    SimpleDraweeView sv;
    Bundle b;
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(android.R.layout.activity_list_item);
        setContentView(R.layout.activity_view_image);

        sv=(SimpleDraweeView)findViewById(R.id.avi_iv_img);
        iv=(ImageView)findViewById(R.id.avi_iv_back);

        b=getIntent().getExtras();

        sv.setImageURI(b.getString("img"));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

    }
}
