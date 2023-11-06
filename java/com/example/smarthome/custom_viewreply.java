package com.example.smarthome;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class custom_viewreply extends BaseAdapter{
    String[] c_id,complaint,reply,date,status;
    private Context context;

    public custom_viewreply(Context appcontext, String[] c_id,String[] complaint,String[] reply,String[] date,String[] status)
    {
        this.context=appcontext;
        this.c_id=c_id;
        this.complaint=complaint;
        this.reply=reply;
        this.date=date;
        this.status=status;
    }
    @Override
    public int getCount() {
        return status.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);

            gridView = inflator.inflate(R.layout.custom_viewreply, null);

        } else {
            gridView = (View) view;

        }
        TextView tvdate = (TextView) gridView.findViewById(R.id.txt_court);
        TextView tvcomplaint = (TextView) gridView.findViewById(R.id.textView24);
        TextView tvstatus = (TextView) gridView.findViewById(R.id.textView44);
        ImageView im = (ImageView) gridView.findViewById(R.id.imageView7);

        tvdate.setText("Posted on:" + date[i]);
        tvcomplaint.setText(complaint[i]);
        tvstatus.setText(reply[i]);


        return gridView;
    }
}



