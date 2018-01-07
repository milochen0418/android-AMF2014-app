package com.mobicloud.amf2014;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;



import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.encode.EncodeActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.encode.*;
import com.google.zxing.common.BitMatrix;

public class PersonalActivity extends Activity implements OnClickListener {
/*
 * 首頁中按下"個人資料"button 後進入此頁，此頁的上方bar指名這兒叫「名片製作 Name Card」頁面, 
 * 這個頁面主要作的事情是，將個人資料輸入
 * 1. 姓名、 2.職位、 3.手機號碼 與 4.email, 最後面則是 一個QRCode 的ImageView, 下方有個button 叫"重新產生QR碼"
 * 按下去之後 ，會自動產生 QR Code 來
 * 
 */
	
	
	private static final String TAG = "DEBUG_TAG";
	private static final String PACKAGE_NAME = "com.mobicloud.amf2014";//ZXingTestActivity.class.getPackage().getName();

	public static GlobalVars.AmfPersonalInformation sAmfPersonalInfomration = null;
	public GlobalVars.AmfPersonalInformation mAmfPersonalInformation;
	
	public TextView mPersonalNameTextView;
	public TextView mPersonalJobTitleTextView;
	public TextView mPersonalEmailTextView;
	public TextView mPersonalPhoneNumberTextView;
	
	public EditText mPersonalNameEditText;
	public EditText mPersonalJobTitleEditText;
	public EditText mPersonalEmailEditText;
	public EditText mPersonalPhoneNumberEditText;
	
	public TextView mPersonalEditTextView;
	
	
	public boolean mIsOnPersonalEdit = false;
	
	private boolean mIsOnCreate = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		findViewById(R.id.back_btn).setOnClickListener(this);
		findViewById(R.id.personal_edit).setOnClickListener(this);


		findViewById(R.id.regenerate_qrcode_btn).setOnClickListener(this);
		
		mPersonalEditTextView = (TextView) findViewById(R.id.personal_edit);
		
		mPersonalNameTextView = (TextView) findViewById(R.id.personal_name_text_view);
		mPersonalJobTitleTextView = (TextView) findViewById(R.id.personal_jobtitle_text_view);
		mPersonalEmailTextView = (TextView) findViewById(R.id.personal_email_text_view);
		mPersonalPhoneNumberTextView = (TextView) findViewById(R.id.personal_phonenumber_text_view);
		
		mPersonalNameEditText = (EditText) findViewById(R.id.personal_name_edit_text);
		mPersonalJobTitleEditText = (EditText) findViewById(R.id.personal_jobtitle_edit_text);
		mPersonalEmailEditText = (EditText)  findViewById(R.id.personal_email_edit_text);
		mPersonalPhoneNumberEditText = (EditText) findViewById(R.id.personal_phonenumber_edit_text);
		
		mIsOnCreate = true;
		
		refreshGuiByPersonalInformation();		
	}
	

	@Override
	public void onClick(View v) {
		int vid = v.getId(); 
		switch(vid) {
		case R.id.back_btn:
			if(mIsOnPersonalEdit) {
				mIsOnPersonalEdit = false;
				refreshGuiByPersonalInformation();
			}
			else {
				super.onBackPressed();
			}
			break;
			/*
		case R.id.encode_contact:
			encodeContact();
			break;
		case R.id.scan_qrcode:
			scanQRCode();
			break;
		case R.id.encode_contact_2:
			encodeContact2();
			break;
		case R.id.scan_qrcode_2:
			scanQRCode2();
			break;
			*/
		case R.id.regenerate_qrcode_btn:
			encodeContact2();
			break;
		case R.id.personal_edit:
			personalEdit();
			break;
		}
	}
	
	
	public void testReadWriteTextInSandbox(String content, String fileName) {
		getDir(fileName, Context.MODE_PRIVATE);	 
		this.writeContentToFile(content, fileName);
		String readContent = this.readContentFromFile(fileName);
		
		Log.i("DEBUG_TAG", "[Sandbox R/W TEST] writeContent \""+content+"\" and read data as \""+readContent+"\"");
	}
	
	public String readContentFromFile(String fileName) {
		int readed;
		String content="";
		byte[] buff = new byte[256];
		FileInputStream reader = null;
		
		try {
			reader = openFileInput(fileName);
			while((readed = reader.read(buff))!=-1){
				content+=new String(buff).trim();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;
	}
	public void writeContentToFile(String content, String fileName) {
		FileOutputStream writer = null;
		try {
			writer = openFileOutput(fileName, Context.MODE_PRIVATE);
			writer.write(content.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
	}	
	public void personalEdit() {
		if(mIsOnPersonalEdit) {
			//user click edit complete
			mIsOnPersonalEdit = false;
			personalEditedStatusSaveToPersonalInformation();
			refreshGuiByPersonalInformation();
		}
		else {
			//user click edit start button
			mIsOnPersonalEdit = true;
			refreshGuiByPersonalInformation();
		}
	}
	

	public void personalEditedStatusSaveToPersonalInformation() {

		mAmfPersonalInformation.mName = mPersonalNameEditText.getEditableText().toString();
		mAmfPersonalInformation.mJobTitle = mPersonalJobTitleEditText.getEditableText().toString();
		mAmfPersonalInformation.mPhoneNumber = mPersonalPhoneNumberEditText.getEditableText().toString();
		mAmfPersonalInformation.mEmail = mPersonalEmailEditText.getEditableText().toString();
		
		GlobalVars vars = GlobalVars.getSharedInstance();
		PersonalActivity.sAmfPersonalInfomration = mAmfPersonalInformation;
		vars.mAmfPersonalInformation = PersonalActivity.sAmfPersonalInfomration;
		
		this.saveAmfInformation();
		this.restoreAmfInformationToGlobalVarsIfFileExist();
	}
	
	public void saveAmfInformation() {
		GlobalVars vars = GlobalVars.getSharedInstance();
		this.testPersonalInformationWriteAndRead(vars.mAmfPersonalInformation, "personal.json");
	}
	
	public void restoreAmfInformationToGlobalVarsIfFileExist() {
		GlobalVars vars = GlobalVars.getSharedInstance();
		String readJsonString = this.readContentFromFile("personal.json");
		if(readJsonString != null && (!readJsonString.equals(""))) {
			Gson gsonRead = new Gson();
			GlobalVars.AmfPersonalInformation info = gsonRead.fromJson(readJsonString, GlobalVars.AmfPersonalInformation.class);
			if(info != null) { 
				vars.mAmfPersonalInformation = info;
			}					
		}
	}
	
	public void testPersonalInformationWriteAndRead(GlobalVars.AmfPersonalInformation amfPersonalInformation, String fileName) { 
		Gson gson = new Gson();
		String jsonString = gson.toJson(amfPersonalInformation);
		this.testReadWriteTextInSandbox(jsonString, fileName);
	}
	
	
	public void refreshGuiByPersonalInformation() {
		
		GlobalVars vars = GlobalVars.getSharedInstance();
		restoreAmfInformationToGlobalVarsIfFileExist();
		PersonalActivity.sAmfPersonalInfomration = vars.mAmfPersonalInformation;
		
		mAmfPersonalInformation = PersonalActivity.sAmfPersonalInfomration;		
		if(mAmfPersonalInformation != null) {
			mPersonalNameTextView.setText(mAmfPersonalInformation.mName );
			mPersonalJobTitleTextView.setText(mAmfPersonalInformation.mJobTitle);
			mPersonalEmailTextView.setText(mAmfPersonalInformation.mEmail);
			mPersonalPhoneNumberTextView.setText(mAmfPersonalInformation.mPhoneNumber);
			
			mPersonalNameEditText.setText(mAmfPersonalInformation.mName);
			mPersonalJobTitleEditText.setText(mAmfPersonalInformation.mJobTitle);
			mPersonalEmailEditText.setText(mAmfPersonalInformation.mEmail);
			mPersonalPhoneNumberEditText.setText(mAmfPersonalInformation.mPhoneNumber);
		}
		
		if(mIsOnPersonalEdit) {
			mPersonalNameTextView.setVisibility(View.GONE);
			mPersonalJobTitleTextView.setVisibility(View.GONE);
			mPersonalEmailTextView.setVisibility(View.GONE);
			mPersonalPhoneNumberTextView.setVisibility(View.GONE);
			
			mPersonalNameEditText.setVisibility(View.VISIBLE);
			mPersonalJobTitleEditText.setVisibility(View.VISIBLE);
			mPersonalEmailEditText.setVisibility(View.VISIBLE);
			mPersonalPhoneNumberEditText.setVisibility(View.VISIBLE);
			
			//mPersonalEditTextView.setText("完成");
			mPersonalEditTextView.setText(this.getResources().getString(R.string.personal_edit_ok_text));
			
		}
		else {
			
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			View view = this.getCurrentFocus();
		    if (view == null) { 
		        //return;
		    }
		    else {
		    	imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		    }
		    
			mPersonalNameTextView.setVisibility(View.VISIBLE);
			mPersonalJobTitleTextView.setVisibility(View.VISIBLE);
			mPersonalEmailTextView.setVisibility(View.VISIBLE);
			mPersonalPhoneNumberTextView.setVisibility(View.VISIBLE);
			
			mPersonalNameEditText.setVisibility(View.GONE);
			mPersonalJobTitleEditText.setVisibility(View.GONE);
			mPersonalEmailEditText.setVisibility(View.GONE);
			mPersonalPhoneNumberEditText.setVisibility(View.GONE);
			
			//mPersonalEditTextView.setText("編輯");
			mPersonalEditTextView.setText(getResources().getString(R.string.personal_edit_text));
			
			if(mIsOnCreate) {
				mIsOnCreate = false;
			}
			else {
				encodeContact2();
			}
		}
	}
	
	
	public void encodeContact2() {
		/*
	      Bundle bundle = new Bundle();
	      bundle.putString(ContactsContract.Intents.Insert.NAME, "Jenny");
	      bundle.putString(ContactsContract.Intents.Insert.PHONE, "8675309");
	      bundle.putString(ContactsContract.Intents.Insert.EMAIL, "jenny@the80s.com");
	      bundle.putString(ContactsContract.Intents.Insert.POSTAL, "123 Fake St. San Francisco, CA 94102");
	      encodeBarcode("CONTACT_TYPE", bundle);
		 */
		/*
		try {
			generateQRCodeToImageView();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.i("DEBUG_TAG", "");
		}
		*/
		
		//bitcoinEncodeContactWithText();
		bitcoinEncodeContactWithAddressBook();
		if(true) return;
		
		Intent intent = new Intent(this, com.google.zxing.client.android.encode.EncodeActivity.class);
		intent.setAction("com.google.zxing.client.android.ENCODE") ;
		intent.putExtra(Intents.Encode.TYPE, "CONTACT_TYPE");
		intent.putExtra("postal","123 Fake St. San Francisco, CA 94102");
		intent.putExtra("email","jenny@the80s.com");
		intent.putExtra("phone","8675309");
		intent.putExtra("name", "Jenny");
		startActivity(intent);
	}

	 
	public void generateQRCodeToImageView() throws WriterException {
		//referne test case by searching QRCodeWriter function in ZXing source code
		int bigEnough = 256;
		QRCodeWriter writer = new QRCodeWriter();

		BitMatrix matrix = writer.encode("http://www.google.com", BarcodeFormat.QR_CODE, bigEnough, bigEnough, null);
		
		int x,y;
		int x_max = matrix.getWidth();
		int y_max = matrix.getHeight();
		
		Log.i("DEBUG_TAG", "Show google url QRCode Matrix");
		for (y = 0; y < y_max; y++) {
			String line = "";
			for (x = 0; x < x_max; x++) {
				boolean v = matrix.get(x, y);
				if(v) {
					line = line + "1";
				}
				else {
					line = line + "0";
				}
			}
			Log.i("DEBUG_TAG", line);
		}
		
		
		
	
	}
	public void bitcoinEncodeContactWithAddressBook() {
		
	    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    Point displaySize = new Point();
	    display.getSize(displaySize);
	    int width = displaySize.x;
	    int height = displaySize.y;
		
	    int smallerDimension = width < height ? width : height;
	    smallerDimension = smallerDimension * 7 / 8;
	  
		Intent intent = new Intent(Intents.Encode.ACTION);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		
		intent.putExtra(Intents.Encode.TYPE, Contents.Type.CONTACT);
		Bundle bundle = new Bundle();
	      //bundle.putString(ContactsContract.Intents.Insert.NAME, "Jenny");
		
		if(mAmfPersonalInformation.mName.equals("")) {
			bundle.putString(ContactsContract.Intents.Insert.NAME, "N/A");	
		}
		else {
			bundle.putString(ContactsContract.Intents.Insert.NAME, mAmfPersonalInformation.mName);	
		}
		
		
	      bundle.putString(ContactsContract.Intents.Insert.PHONE, mAmfPersonalInformation.mPhoneNumber);
	      bundle.putString(ContactsContract.Intents.Insert.EMAIL, mAmfPersonalInformation.mEmail);
	      //bundle.putString(ContactsContract.Intents.Insert.JOB_TITLE, mAmfPersonalInformation.mJobTitle);
	      bundle.putString(ContactsContract.Intents.Insert.COMPANY, mAmfPersonalInformation.mJobTitle);
	      //bundle.putString(ContactsContract.Intents.Insert., mAmfPersonalInformation.mJobTitle);
	      intent.putExtra(Intents.Encode.DATA, bundle);
	      

		
		intent.putExtra(Intents.Encode.FORMAT,BarcodeFormat.QR_CODE.toString());
		QRCodeEncoder qrCodeEncoder = null;
		try {
			qrCodeEncoder = new QRCodeEncoder(this, intent, 0 , true);
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			if (bitmap == null) {
	          Log.w(TAG, "Could not encode barcode");
	          //showErrorMessage(R.string.msg_encode_contents_failed);
	          Log.i("DEBUG_TAG", "Msg Encode Contents Failed");
	          qrCodeEncoder = null;
	          return;
	        }
			
		      ImageView view = (ImageView) findViewById(R.id.qrcode_image_view);
		      view.setImageBitmap(bitmap);
			

		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(qrCodeEncoder != null ) {
			
		}
		//qrCodeEncoder.requestBarcode(handler, dimension);
	}
	
	
	
	public void bitcoinEncodeContactWithText() {
		
	    //int width = displaySize.x;
	    //int height = displaySize.y;
		//int width = 800;
		//int height = 800;
	    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    Point displaySize = new Point();
	    display.getSize(displaySize);
	    int width = displaySize.x;
	    int height = displaySize.y;
		
	    int smallerDimension = width < height ? width : height;
	    smallerDimension = smallerDimension * 7 / 8;
	    
	    
	    
		
		//change data to VCard by reference this address 
		//http://stackoverflow.com/questions/19219775/how-to-create-and-show-qr-code-in-android-app
	    //String data ="http://www.google.com";
	    String data = "Name : "+"NameMilo"+"\n Company : "+"CompanyPieApple";
	    

	    

		Intent intent = new Intent(Intents.Encode.ACTION);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
		intent.putExtra(Intents.Encode.DATA, data);
		intent.putExtra(Intents.Encode.FORMAT,BarcodeFormat.QR_CODE.toString());
		QRCodeEncoder qrCodeEncoder = null;
		try {
			qrCodeEncoder = new QRCodeEncoder(this, intent, 0 , true);
			Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			if (bitmap == null) {
	          Log.w(TAG, "Could not encode barcode");
	          //showErrorMessage(R.string.msg_encode_contents_failed);
	          Log.i("DEBUG_TAG", "Msg Encode Contents Failed");
	          qrCodeEncoder = null;
	          return;
	        }
			
		      ImageView view = (ImageView) findViewById(R.id.qrcode_image_view);
		      view.setImageBitmap(bitmap);
			

		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(qrCodeEncoder != null ) {
			
		}
		//qrCodeEncoder.requestBarcode(handler, dimension);
	}
	
	

	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
			/*
			case R.id.encode_succeeded:
				Bitmap image = (Bitmap) message.obj;
				ImageView view = (ImageView) findViewById(R.id.qr_code);
				view.setImageBitmap(image);
				// TextView contents = (TextView)
				// findViewById(R.id.contents_text_view);
				// contents.setText(qrCodeEncoder.getDisplayContents());
				break;
			case R.id.encode_failed:
				Toast.makeText(ReceiveMoney.this, "QR generation failed", Toast.LENGTH_LONG).show();
				qrCodeEncoder = null;
				break;
				*/
			default:
				break;
			}
		}
	};	
	
	
	
	public void scanQRCode2()  {
		try {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}
		catch(Exception e) {
			Log.i("DEBUG_TAG", "Scanner Not Found");
			
		}
	}
	
	
	
	
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
                
            }
        }
    }	
	
	public void encodeContact() {
	      Bundle bundle = new Bundle();
	      bundle.putString(ContactsContract.Intents.Insert.NAME, "Jenny");
	      bundle.putString(ContactsContract.Intents.Insert.PHONE, "8675309");
	      bundle.putString(ContactsContract.Intents.Insert.EMAIL, "jenny@the80s.com");
	      bundle.putString(ContactsContract.Intents.Insert.POSTAL, "123 Fake St. San Francisco, CA 94102");
	      encodeBarcode("CONTACT_TYPE", bundle);		
	}
	
	public void scanQRCode()  {
		//IntentIntegrator integrator = new IntentIntegrator(ZXingTestActivity.this);
		//IntentIntegrator integrator = new IntentIntegrator(ZXingTestActivity.this);
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
	}
	
	
	  private void showDialog(int title, CharSequence message) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle(title);
		    builder.setMessage(message);
		    //builder.setPositiveButton(R.string.ok_button, null);
		    builder.setPositiveButton("OK", null);
		    builder.show();
		  }

		  private void encodeBarcode(CharSequence type, CharSequence data) {
		    IntentIntegrator integrator = new IntentIntegrator(this);
		    integrator.shareText(data, type);
		  }

		  private void encodeBarcode(CharSequence type, Bundle data) {
		    IntentIntegrator integrator = new IntentIntegrator(this);
		    integrator.addExtra("ENCODE_DATA", data);
		    integrator.shareText(data.toString(), type); // data.toString() isn't used
		  }

		  private static CharSequence getFlattenedParams() {
		    Camera camera = Camera.open();
		    if (camera == null) {
		      return null;
		    }
		    try {
		      Camera.Parameters parameters = camera.getParameters();
		      if (parameters == null) {
		        return null;
		      }
		      return parameters.flatten();
		    } finally {
		      camera.release();
		    }
		  }

		  private static void writeStats(String resultString) {
		    File cameraParamsFile = new File(Environment.getExternalStorageDirectory().getPath() + "/CameraParameters.txt");
		    Writer out = null;
		    try {
		      out = new OutputStreamWriter(new FileOutputStream(cameraParamsFile), Charset.forName("UTF-8"));
		      out.write(resultString);
		    } catch (IOException e) {
		      Log.e(TAG, "Cannot write parameters file ", e);
		    } finally {
		      if (out != null) {
		        try {
		          out.close();
		        } catch (IOException e) {
		          Log.w(TAG, e);
		        }
		      }
		    }
		  }
}
