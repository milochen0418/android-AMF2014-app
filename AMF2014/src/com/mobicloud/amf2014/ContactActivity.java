package com.mobicloud.amf2014;

import com.mobicloud.amf2014.TeacherLazyAdapter.LazyItemData;
import com.pieapple.loadwebimage.imageutils.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
public class ContactActivity extends Activity implements OnClickListener, ListView.OnItemClickListener {
	/*
	 * 通訊錄 ，看起來是透過這個地方要資料 
	 * member api 與會者資料 (要確認一下，是否用在通訊錄的頁面上)
	 * http://www.amf.com.tw/api/en/member/index.php
	 * http://www.amf.com.tw/api/zh/member/index.php
	 * 
	 * 然後以ListView show 出來, 點進item 後，會show 該員的資料，下方就有發送信件與加到我的聯絡人這兩個button，
	 * 記得實作出該功能就對了
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		findViewById(R.id.back_btn).setOnClickListener(this);
		loadListView(0);
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		}		
	}

	
	public ListView mListView;
	public ArrayAdapter mAdapter;
	public ContactAdapter mContactAdapter;
	public String[] mStrs;
	public ItemData[] mData;

	public void loadListView(int tabIdx) {
		mListView = (ListView)findViewById(R.id.list_view);
		
		GlobalVars.AmfMember member = GlobalVars.getSharedInstance().mAmfMember;
		GlobalVars.AmfAlbum album = GlobalVars.getSharedInstance().mAmfAlbum;
		int count = 0;
		if(tabIdx == 0) {
			count = member.mItems.size();
		}
		else if(tabIdx == 1) {
			count = album.mActivePhoto.size();
		}
		
		mStrs = new String[count];
		for (int i = 0; i < mStrs.length ; ++i ) {
			if(tabIdx == 0 ) {
				mStrs[i] = member.mItems.get(i).mName + "\n" + member.mItems.get(i).mJobTitle; 
			}else if(tabIdx == 1) {
				mStrs[i] = album.mActivePhoto.get(i).mTitle + "\n" + album.mActivePhoto.get(i).mImgUrl;
			}
		}
		
		
		mListView.setAdapter(null);
		
		boolean isListItemFull = true;
		if(isListItemFull) {
			mData = new ItemData[member.mItems.size()];
			int idx;
			for (idx = 0; idx < member.mItems.size(); idx++) {
				GlobalVars.AmfMemberItem item = member.mItems.get(idx);
				mData[idx] = new ItemData(item.mName, item.mJobTitle, null);
				//String name, String jobTitle,String imageUrl
			}
			mContactAdapter = new ContactAdapter( this, mData );
			mListView.setAdapter(mContactAdapter);
			mListView.setOnItemClickListener(this);
		}
		else {
			mAdapter = new ArrayAdapter (this, R.layout.contact_list_item, mStrs);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GlobalVars vars = GlobalVars.getSharedInstance();
		ContactDetailActivity.sAmfMemberItem = vars.mAmfMember.mItems.get(position);
		Intent intent = new Intent(this, ContactDetailActivity.class);
		this.startActivity(intent);
	}

	
	public static LayoutInflater sInflater=null;
	public class ItemData {
		public String mImageUrl;
		public String mName;
		public String mJobTitle;
		public ItemData(String name, String jobTitle,String imageUrl) {
			mImageUrl = imageUrl;
			mName = name;
			mJobTitle = jobTitle;
		}
	}			
	public class ContactAdapter extends BaseAdapter {
	    private ItemData[] mData;
	    private Activity mActivity;    

	    public ContactAdapter(Activity a, ItemData[] d) {
	        mActivity = a;
	        mData=d;
	        sInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	        	vi = sInflater.inflate(R.layout.contact_list_item_full, null);
	        }
	        TextView text=(TextView)vi.findViewById(R.id.text);;
	        ImageView image=(ImageView)vi.findViewById(R.id.image);
	        
	        ItemData data = mData[position];
	        text.setText(data.mName +"\n"+ data.mJobTitle);
	        //mImageLoader.DisplayImage(mData[position].mImageUrl, image);
	        return vi;
	    }		
	}
	
    @Override
    public void onDestroy()
    {
    	if(mListView != null) {
    		mListView.setAdapter(null);
    	}
        super.onDestroy();
    }

}
