package com.mobicloud.amf2014;


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


public class TeacherLazyAdapter extends BaseAdapter {
	static public class LazyItemData {
		public String mImageUrl;
		public String mName;
		public String mJobTitle;
		public LazyItemData(String name, String jobTitle,String imageUrl) {
			mImageUrl = imageUrl;
			mName = name;
			mJobTitle = jobTitle;
		}
	}
    
    private Activity mActivity;
    private LazyItemData[] mData;
    private static LayoutInflater sInflater=null;
    public ImageLoader mImageLoader; 
    
    public TeacherLazyAdapter(Activity a, LazyItemData[] d) {
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
        if(convertView==null) {
            //vi = sInflater.inflate(R.layout.row_listview_item, null);
        	vi = sInflater.inflate(R.layout.teacher_lazy_list_item, null);
        }
        TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        
        LazyItemData data = mData[position];
        text.setText(data.mName +"\n"+ data.mJobTitle);
        mImageLoader.DisplayImage(mData[position].mImageUrl, image);
        return vi;
    }
}