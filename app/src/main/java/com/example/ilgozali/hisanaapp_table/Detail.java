package com.example.ilgozali.hisanaapp_table;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilgozali.hisanaapp_table.API.ApiClient;
import com.example.ilgozali.hisanaapp_table.API.ApiEndPoint;
import com.example.ilgozali.hisanaapp_table.response.DataTableRespond;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private Button btSelesai, btProses, btBatal, btkirim;
    private TextView custno,Start,Finish,Status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Detail Custommer");
        toolbar.setLogo(R.drawable.ic_details_black_24dp);


        Log.d(TAG, "onCreate: started.");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        btProses = findViewById(R.id.bt_prosess);
        btSelesai = findViewById(R.id.bt_selesai);
        btBatal = findViewById(R.id.bt_batal);
        btkirim = findViewById(R.id.bt_update);
        custno = findViewById(R.id.tv_costuno_detail);
        Start = findViewById(R.id.tv_start_detail);
        Finish = findViewById(R.id.tv_finish_detail);
        Status = findViewById(R.id.tv_status_detail);
        Intent mIntent = getIntent();
        custno.setText(mIntent.getStringExtra("costno"));
        custno.setTag(custno.getKeyListener());
        custno.setKeyListener(null);
        Start.setText(mIntent.getStringExtra("start"));
        Finish.setText(mIntent.getStringExtra("finish"));
        Status.setText(mIntent.getStringExtra("status"));


        int width = dm.widthPixels;
        int heigt = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (heigt * .8));

        btSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status.setText("SELESAI");
                Status.setBackgroundResource(R.drawable.background_button_selesai);

            }
        });
        btProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status.setText("DIPROSES");
                Status.setBackgroundResource(R.drawable.background_button_proses);
            }
        });
        btBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status.setText("DIBATALKAN");
                Status.setBackgroundResource(R.drawable.background_button_batal);
            }
        });
        btkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
                final Call<DataTableRespond> updateCall = apiEndPoint.postDataTable(
                       custno.getText().toString(),
                        Start.getText().toString(),
                        Finish.getText().toString(),
                        Status.getText().toString());
                updateCall.enqueue(new Callback<DataTableRespond>() {
                    @Override
                    public void onResponse(Call<DataTableRespond> call, Response<DataTableRespond> response) {
                        MainActivity.ma.doTheAutoRefresh();
                        finish();
                        Log.d("Detail","data"+updateCall.request());
                    }

                    @Override
                    public void onFailure(Call<DataTableRespond> call, Throwable t) {

                    }
                });

//        getIncomingIntent();

    }


//    private void getIncomingIntent() {
//
//        if (getIntent().hasExtra("costno")
//                && getIntent().hasExtra("start")
//                && getIntent().hasExtra("finish")
//                && getIntent().hasExtra("status")) {
//
//            String detailcostno = getIntent().getStringExtra("costno");
//            String detailstart = getIntent().getStringExtra("start");
//            String detailfinish = getIntent().getStringExtra("finish");
//            String detailstatus = getIntent().getStringExtra("status");
//
//            setText(detailcostno,
//                    detailstart,
//                    detailfinish,
//                    detailstatus);
//
//        }
//    }
//
//    private void setText(String detailcostno, String detailstart, String detailfinish, final String detailstatus) {
//

//
//        custno.setText(detailcostno);
//        Start.setText(detailstart);
//        Finish.setText(detailfinish);
//        Status.setText(detailstatus);
//
//        btSelesai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Status.setText("SELESAI");
//            }
//        });
//        btBatal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Status.setText("DIBATALKAN");
//            }
//        });
//        btProses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Status.setText("PROSES");
//            }
//        });
//
//        btkirim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
//    }

});
    }
}
