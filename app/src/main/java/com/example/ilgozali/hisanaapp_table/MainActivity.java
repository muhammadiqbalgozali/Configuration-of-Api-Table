package com.example.ilgozali.hisanaapp_table;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ilgozali.hisanaapp_table.API.ApiClient;
import com.example.ilgozali.hisanaapp_table.API.ApiEndPoint;
import com.example.ilgozali.hisanaapp_table.Adapter_SlideImage.SliderUtlis;
import com.example.ilgozali.hisanaapp_table.Adapter_SlideImage.ViewPagerAdapter;
import com.example.ilgozali.hisanaapp_table.response.DataTableRespond;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //    private static ViewPager Pager;
//    private static int Halaman = 0;
//    private static int NoHalaman = 0;
//    public static MainActivity activity;
//    private static final String TAG = MainActivity.class.getSimpleName();
//////    private static final String API_KEY = "pesan";
////List<DataTableRespond> DataTable = new ArrayList<>();
////// insert your themoviedb.org API KEY here
//    public static ApiEndPoint apiEndPoint;
//private TextView rvcustomer,rvmulai,rvselsai,rvstatus;
//private List<DataTableRespond> TableRespond;
//private RecyclerView recyclerView;
//private DataTableRespond datatablerespond;
    public static MainActivity ma;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtlis> sliderImg;
    ViewPagerAdapter viewPagerAdapter;
    String request_url = "http://117.53.46.24:3000/matprom";
    private CostumAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    ProgressDialog progressDoalog;
    private final Handler handler = new Handler();


//    private String[] url = new String[]{"https://3.bp.blogspot.com/-ACRCUeBb_sE/WTYfzfgx4JI/AAAAAAAAASg/sp-6TH8wpn063LfufM3q7birLNG9hMlOACLcB/s1600/Menu%2BPaket%2BFried%2BChicken.jpg",
//            "https://pbs.twimg.com/media/C2-urjWVEAA8Oyk.jpg:large", "https://scontent-dfw5-1.cdninstagram.com/vp/7cab70317396d2baf7640916becd7269/5CDA7E99/t51.2885-15/e35/42933316_264091244241367_6718131751756595537_n.jpg?_nc_ht=scontent-dfw5-1.cdninstagram.com&ig_cache_key=MTg4NTYxMTA3MDk5MTY5MDg1Nw%3D%3D.2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        sliderImg = new ArrayList<>();


        viewPager = (ViewPager) findViewById(R.id.Halaman);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        ma = this;

        sendRequest();
        doTheAutoRefresh();
//
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        Calendar kalender = Calendar.getInstance();
        String currentkalender = DateFormat.getDateInstance(DateFormat.FULL).format(kalender.getTime());

        TextView date = findViewById(R.id.tanggal);
        date.setText(currentkalender);


//        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
//
//
//        Call<List<DataTableRespond>> call = apiEndPoint.getDataTable();
//        call.enqueue(new Callback<List<DataTableRespond>>() {
//            @Override
//            public void onResponse(Call<List<DataTableRespond>> call, Response<List<DataTableRespond>> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
//
//
//            }
//
//
//            @Override
//            public void onFailure(Call<List<DataTableRespond>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(MainActivity.this, "Data Tidak masuk", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }

//    private void refresh() {
//        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
//        Call<List<DataTableRespond>> call = apiEndPoint.getDataTable();
//        call.enqueue(new Callback<List<DataTableRespond>>() {
//            @Override
//            public void onResponse(Call<List<DataTableRespond>> call, Response<List<DataTableRespond>> response) {
//                List<DataTableRespond> KontakList = response.body();
//                Log.d("Retrofit Get", "Jumlah data Kontak: " +
//                        String.valueOf(KontakList.size()));
//                mAdapter = new DataTableRespond();
//                recyclerView.setAdapter(mAdapter);
//
//            }
//
//
//            @Override
//            public void onFailure(Call<List<DataTableRespond>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(MainActivity.this, "Data Tidak masuk", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
                Call<List<DataTableRespond>> call = apiEndPoint.getDataTable();
                call.enqueue(new Callback<List<DataTableRespond>>() {
                    @Override
                    public void onResponse(Call<List<DataTableRespond>> call, Response<List<DataTableRespond>> response) {
                        progressDoalog.dismiss();
                        generateDataList(response.body());


                    }


                    @Override
                    public void onFailure(Call<List<DataTableRespond>> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(MainActivity.this, "Data Tidak masuk", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }, 5000);
    }




    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    public void sendRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    SliderUtlis sliderUtlis = new SliderUtlis();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtlis.setSliderImageUrl(jsonObject.getString("url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(sliderUtlis);

                }
                viewPagerAdapter = new ViewPagerAdapter(sliderImg, MainActivity.this);

                viewPager.setAdapter(viewPagerAdapter);

                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {

                    dots[i] = new ImageView(MainActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        rq.add(jsonArrayRequest);
    }

//        init();
//        progressDoalog = new ProgressDialog(MainActivity.this);
//        progressDoalog.setMessage("Loading....");
//        progressDoalog.show();

//        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
//        Call<List<DataTableRespond>> call = apiEndPoint.getDataTable();
//        call.enqueue(new Callback<List<DataTableRespond>>() {
//            @Override
//            public void onResponse(Call<List<DataTableRespond>> call, Response<List<DataTableRespond>> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<DataTableRespond>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(MainActivity.this, "Data Tidak masuk", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//


    //membuat fungsi kalender pada system hardware
//        Calendar kalender = Calendar.getInstance();
//        String currentkalender = DateFormat.getDateInstance(DateFormat.FULL).format(kalender.getTime());
//
//        TextView date = findViewById(R.id.tanggal);
//        date.setText(currentkalender);
//
//    }

    private void generateDataList(List<DataTableRespond> dataTable) {
        recyclerView = findViewById(R.id.recycle);
        adapter = new CostumAdapter(this, dataTable);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


//    private void init() {
//        Pager = (ViewPager) findViewById(R.id.Halaman);
//        Pager.setAdapter(new ViewPagerAdapter(MainActivity.this, url));
//// mengatur indicator untuk slide image dan mendaaptkan tampilan matriks
//        CirclePageIndicator Indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        Indicator.setViewPager(Pager);
//
//        final float density = getResources().getDisplayMetrics().density;
////mengatur radius dari lingkaran
//        Indicator.setRadius(5 * density);
//
//        NoHalaman = url.length;
////memulai otomatis pada view pager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            @Override
//            public void run() {
//                if (Halaman == NoHalaman) {
//                    Halaman = 0;
//                }
//                Pager.setCurrentItem(Halaman++, true);
//            }
//        };
//        Timer swipTimer = new Timer();
//        swipTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//
//            }
//        }, 3000, 3000);
//        Indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Halaman = position;
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        //convert dari get window ke getdecorview
        View coreView = getWindow().getDecorView();
        //mengaktifkan immersive untuk menghilangkan status bar dan navigasi bar pada printai android
        coreView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                //Mengatur Kontent menjadi full screen
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // membuat http connection untuk berkomunikasi url
            urlConnection = (HttpURLConnection) url.openConnection();

            // mengkoneksikan dengan url
            urlConnection.connect();

            // Membaca data dari url
            // Data yang sudah terbaca merupakan sequance data /  byte streams
            iStream = urlConnection.getInputStream();

            //byte stream yang sudah didapat didecode menjadi characters stream menggunakan InputStreamReader
            //BufferedReader akan membaca text dari character-input stream, dan melakukan buffering characters sehenggiga dapat dengan efesien membaca character, array dan perbaris
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            //StringBuffer digunkan untuk memodifikasi character-input stream menjadi String
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) { // jika saat baris dibaca berisi null maka tidak akan diinput ke sb
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
}
