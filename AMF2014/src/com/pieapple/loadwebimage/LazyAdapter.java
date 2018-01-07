package com.pieapple.loadwebimage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pieapple.loadwebimage.imageutils.ImageLoader;
import com.mobicloud.amf2014.*;

public class LazyAdapter extends BaseAdapter {
    
    private Activity mActivity;
    private String[] mData;
    private static LayoutInflater sInflater=null;
    public ImageLoader mImageLoader; 
    
    public LazyAdapter(Activity a, String[] d) {
        mActivity = a;
        mData=d;
        sInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader=new ImageLoader(mActivity.getApplicationContext());
    }

    public int getCount() {
        return mData.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = sInflater.inflate(R.layout.row_listview_item, null);

        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText("item "+position);
        mImageLoader.DisplayImage(mData[position], image);
        return vi;
    }
}