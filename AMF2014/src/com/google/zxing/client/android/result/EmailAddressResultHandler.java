/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android.result;

import com.mobicloud.amf2014.R;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Handles email addresses.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class EmailAddressResultHandler extends ResultHandler {
  private static final int[] buttons = {
      R.string.button_email,
      R.string.button_add_contact
  };

  public EmailAddressResultHandler(Activity activity, ParsedResult result) {
    super(activity, result);
  }

  @Override
  public int getButtonCount() {
    return buttons.length;
  }

  @Override
  public int getButtonText(int index) {
    return buttons[index];
  }

  @Override
  public void handleButtonPress(int index) {
    EmailAddressParsedResult emailResult = (EmailAddressParsedResult) getResult();
    
    switch (index) {
      case 0:
    	//change by milochen
    	  /*
        sendEmail(emailResult.getTos(),
                  emailResult.getCCs(),
                  emailResult.getBCCs(),
                  emailResult.getSubject(),
                  emailResult.getBody());
                  */
    	  Log.i("DEBUG_TAG","Don't support send Email");
    	  //Toast.makeText(this, "MILO: Don't support send Email", Toast.LENGTH_SHORT).show();
    	  Toast.makeText(this.getActivity().getApplicationContext(),"MILO: Don't support send Email", Toast.LENGTH_SHORT).show();
    	  //The download library is old code , so it cannot running the code, please refer https://github.com/zxing/zxing/blob/master/core/src/main/java/com/google/zxing/client/result/EmailAddressParsedResult.java
        break;
      case 1:
    	  //change by milochen
    	  /*
        addEmailOnlyContact(emailResult.getTos(), null);
        */
    	//The download library is old code , so it cannot running the code, please refer the code https://github.com/zxing/zxing/blob/master/core/src/main/java/com/google/zxing/client/result/EmailAddressParsedResult.java
    	  Log.i("DEBUG_TAG","Don't support send Email");
    	  Toast.makeText(this.getActivity().getApplicationContext(),"MILO: Don't support send Email", Toast.LENGTH_SHORT).show();    	  
        break;
    }
  }

  @Override
  public int getDisplayTitle() {
    return R.string.result_email_address;
  }
}
