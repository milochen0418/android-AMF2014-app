package com.mobicloud.amf2014;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailActivity extends Activity implements OnClickListener{

	static public GlobalVars.AmfMemberItem sAmfMemberItem;
	public GlobalVars.AmfMemberItem mAmfMemberItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		findViewById(R.id.back_btn).setOnClickListener(this);
		
		if(sAmfMemberItem != null) {
			mAmfMemberItem = sAmfMemberItem;
		}
		
		TextView contactNameTextView = (TextView) findViewById(R.id.contact_name_text_view);
		TextView contactJobTitleTextView = (TextView) findViewById(R.id.contact_jobtitle_text_view);
		TextView contactCompanyTextView = (TextView) findViewById(R.id.contact_company_text_view);
		TextView contactEmailTextView = (TextView) findViewById(R.id.contact_email_text_view);
		
		contactNameTextView.setText(mAmfMemberItem.mName);
		contactJobTitleTextView.setText(mAmfMemberItem.mJobTitle);
		contactCompanyTextView.setText(mAmfMemberItem.mCompany);
		contactEmailTextView.setText(mAmfMemberItem.mEmail);
		
		findViewById(R.id.send_email_btn).setOnClickListener(this);
		findViewById(R.id.add_to_contact_btn).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		switch(vid) {
		case R.id.back_btn:
			super.onBackPressed();
			break;
		case R.id.send_email_btn:
			Toast.makeText(this.getBaseContext(), "發送信件", Toast.LENGTH_SHORT).show();
			composeEmail(mAmfMemberItem.mEmail);
			break;
		case R.id.add_to_contact_btn:
			Toast.makeText(this.getBaseContext(), "加到我的聯絡人", Toast.LENGTH_SHORT).show();
			//addToContact();
			//addToContactVersion2();
			addToContactBy(mAmfMemberItem.mName, mAmfMemberItem.mEmail, mAmfMemberItem.mCompany, mAmfMemberItem.mJobTitle);
			
			break;
		}
	}
	
	public void composeEmail (String emailAddr) {
		  Intent intent = new Intent(Intent.ACTION_SENDTO);
		  Uri uri = Uri.parse("mailto:"+emailAddr);
		  intent.setData(uri);
		  intent.putExtra("subject", "來自於  AMF 2014 的成員. From AMF member");
		  intent.putExtra("body", "我透過AMF2014通訊錄App與您聯絡. I send email to you through AMF contactApp ");
		  startActivity(intent);		
	}
	
	

	public void addToContactBy(String nameStr, String emailStr, String companyStr, String jobTitleStr) {

		String DisplayName = nameStr;
		String MobileNumber = null;
		String HomeNumber = null;
		//String WorkNumber = workPhonenumberStr;
		String WorkNumber = null;
		String emailID = emailStr;
		String company = companyStr;

		String jobTitle = jobTitleStr;
	
		 ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();
	
		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());
	
		 //------------------------------------------------------ Names
		 if (DisplayName != null) {
		     ops.add(ContentProviderOperation.newInsert(
		     ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
		         .withValue(
		     ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
		     DisplayName).build());
		 }
	
		 //------------------------------------------------------ Mobile Number                     
		 if (MobileNumber != null) {
		     ops.add(ContentProviderOperation.
		     newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
		         .build());
		 }
	
		 //------------------------------------------------------ Home Numbers
		 if (HomeNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
		         .build());
		 }
	
		 //------------------------------------------------------ Work Numbers
		 if (WorkNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
		         .build());
		 }
	
		 //------------------------------------------------------ Email
		 if (emailID != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
		         .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
		         .build());
		 }
	
		 //------------------------------------------------------ Organization
		 if (!company.equals("") && !jobTitle.equals("")) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .build());
		 }
	
		 // Asking the Contact provider to create a new contact                 
		 try {
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		 } catch (Exception e) {
		     e.printStackTrace();
		     Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		 } 	
	}
	
	
	public void addToContactVersion2() {
		String DisplayName = "aaaXYZ";
		 String MobileNumber = "123456";
		 String HomeNumber = "1111";
		 String WorkNumber = "2222";
		 String emailID = "email@nomail.com";
		 String company = "bad";
		 String jobTitle = "abcd";
	
		 ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();
	
		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());
	
		 //------------------------------------------------------ Names
		 if (DisplayName != null) {
		     ops.add(ContentProviderOperation.newInsert(
		     ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
		         .withValue(
		     ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
		     DisplayName).build());
		 }
	
		 //------------------------------------------------------ Mobile Number                     
		 if (MobileNumber != null) {
		     ops.add(ContentProviderOperation.
		     newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
		         .build());
		 }
	
		 //------------------------------------------------------ Home Numbers
		 if (HomeNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
		         .build());
		 }
	
		 //------------------------------------------------------ Work Numbers
		 if (WorkNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
		         .build());
		 }
	
		 //------------------------------------------------------ Email
		 if (emailID != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
		         .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
		         .build());
		 }
	
		 //------------------------------------------------------ Organization
		 if (!company.equals("") && !jobTitle.equals("")) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .build());
		 }
	
		 // Asking the Contact provider to create a new contact                 
		 try {
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		 } catch (Exception e) {
		     e.printStackTrace();
		     Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		 } 	
	}


	
	public void addToContact() {
		String szFullname = "aszFullname";
		String szLastname = "aszLastname";
		String szFirstname = "aszFirstname";
		
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();			
		int rawContactInsertIndex = ops.size();
		  
		ops.add(ContentProviderOperation.newInsert(
			ContactsContract.RawContacts.CONTENT_URI)
			.withValue(RawContacts.ACCOUNT_TYPE, null)
			.withValue(RawContacts.ACCOUNT_NAME, null)
			.build()
		);
	      
	
	 //INSERT NAME
	    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
	        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
	        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, szFullname) // Name of the person
	        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, szLastname) // Name of the person
	        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, szFirstname) // Name of the person
	        
	        
	        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, mAmfMemberItem.mEmail)
	        .build());	
	    try {
		    ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	        if (res!=null && res[0]!=null) {
	        	Uri newContactUri = res[0].uri;	
	        	//02-20 22:21:09 URI added contact:content://com.android.contacts/raw_contacts/612
	        	Log.d("DEBUG_TAG", "URI added contact:"+ newContactUri);
	        }
	        else {
	        	Log.e("DEBUG_TAG", "Contact not added.");	    
	        }
	    }
	    catch(Exception e) {
	    	
	    }		
	}
}
