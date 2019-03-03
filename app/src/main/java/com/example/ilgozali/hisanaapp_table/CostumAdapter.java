package com.example.ilgozali.hisanaapp_table;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ilgozali.hisanaapp_table.response.DataTableRespond;

import java.util.List;

public class CostumAdapter extends RecyclerView.Adapter<CostumAdapter.CustomViewHolder> {

    private List<DataTableRespond> dataList;
    private Context context;


    public CostumAdapter(Context context, List<DataTableRespond> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvcustno, tvstart, tvfinish, tvstatus;
        CardView parentLayout;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvcustno = mView.findViewById(R.id.tx_customer);
            tvstart = mView.findViewById(R.id.tx_mulai);
            tvfinish = mView.findViewById(R.id.tx_selesai);
            tvstatus = mView.findViewById(R.id.tx_status);
            parentLayout = mView.findViewById(R.id.parent_layout);


        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.activity_costum_adapter, parent, false);
//        return new CustomViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_costum_adapter, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.tvcustno.setText(dataList.get(position).getCustno());
        holder.tvstart.setText(dataList.get(position).getStart());
        holder.tvfinish.setText(dataList.get(position).getFinish());
        holder.tvstatus.setText(dataList.get(position).getStatus());

        
        Log.d("Mainactivity", "data:" + dataList.size());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail.class);
                Toast.makeText(context, "Status : " + dataList.get(position).getStatus(), Toast.LENGTH_SHORT).show();
                intent.putExtra("costno", dataList.get(position).getCustno());
                intent.putExtra("start", dataList.get(position).getStart());
                intent.putExtra("finish", dataList.get(position).getFinish());
                intent.putExtra("status", dataList.get(position).getStatus());
                context.startActivity(intent);


            }
        });


//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getThumbnailUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }
}