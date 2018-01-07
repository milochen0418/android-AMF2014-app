package com.mobicloud.amf2014;

import java.util.Locale;

import android.app.Activity;
import android.util.Log;
import com.google.gson.Gson;

public class AmfDataHelper {

	/*
	 * AMF APIs as the following
	 * album api 相簿列表 (用在 新聞專區 中的 相簿列表 GUI)
	 * http://www.amf.com.tw/api/en/news/album/index.php
	 * http://www.amf.com.tw/api/zh/news/album/index.php
	 * 
	 * news api 最新消息  (用在 新聞專區 中的 新聞內容 GUI)
	 * http://www.amf.com.tw/api/en/news/news_content/index.php
	 * http://www.amf.com.tw/api/zh/news/news_content/index.php
	 * 
	 * member api 與會者資料 (要確認一下，是否用在通訊錄的頁面上)
	 * http://www.amf.com.tw/api/en/member/index.php
	 * http://www.amf.com.tw/api/zh/member/index.php
	 * 
	 * speech api 講師介紹 (用在 講師介紹 的GUI) 
	 * http://www.amf.com.tw/api/en/speech/index.php
	 * http://www.amf.com.tw/api/zh/speech/index.php
	 * 
	 */
	
	
	static private String sAlbumApiUrlStr = "";
	static private String sNewsApiUrlStr = "";
	static private String sMemberApiUrlStr = "";
	static private String sSpeechApiUrlStr = "";
	
	static private String sLanguage ="";
	static public String getLanguage() {
		return sLanguage;
	}
	static public void setLanguage(String langStr) {
		sLanguage = langStr;
		if(sLanguage == "zh") {
			sAlbumApiUrlStr = "http://www.amf.com.tw/api/zh/news/album/index.php";
			sNewsApiUrlStr = "http://www.amf.com.tw/api/zh/news/news_content/index.php";
			sMemberApiUrlStr = "http://www.amf.com.tw/api/zh/member/index.php";
			sSpeechApiUrlStr = "http://www.amf.com.tw/api/zh/speech/index.php";			
		}
		else {
			sAlbumApiUrlStr = "http://www.amf.com.tw/api/en/news/album/index.php";
			sNewsApiUrlStr = "http://www.amf.com.tw/api/en/news/news_content/index.php";
			sMemberApiUrlStr = "http://www.amf.com.tw/api/en/member/index.php";
			sSpeechApiUrlStr = "http://www.amf.com.tw/api/en/speech/index.php";
		}
	}
	
	static public void setAllData() {
		
		if(sAlbumApiUrlStr == null || sAlbumApiUrlStr.length()==0) {
			setLanguage("en");
		}
		resetPersonalData();
		showPersonalData();
		
		resetNewsData();
		showNewsData();
		
		resetAlbumData();
		showAlbumData();
		
		resetMemberData();
		showMemberData();
		
		resetSpeechData();
		showSpeechData();
		
		resetScheduleData();
		
	}
	
	static public void resetScheduleData() {
			//String lang = mCurrentActivity.getResources().getConfiguration().locale.getLanguage();
			//Log.i("DEBUG_TAG","LOCALE.getLanguage() = " + lang);
			//}
		GlobalVars vars = GlobalVars.getSharedInstance();		
		if(sLanguage.equals("zh")) {		
			vars.mAmfSchedule = new GlobalVars.AmfSchedule(true);
			
		}
		else {
			vars.mAmfSchedule = new GlobalVars.AmfSchedule(false);
		}
	}
	

	static public void cleanPersonalData() {
		GlobalVars vars  = GlobalVars.getSharedInstance();		
		//if(GlobalVars.getSharedInstance().mAmfSpeech != null) {
		if(vars.mAmfPersonalInformation != null) {
			vars.mAmfPersonalInformation.mName = "";
			vars.mAmfPersonalInformation.mJobTitle = "";
			vars.mAmfPersonalInformation.mPhoneNumber = "";
			vars.mAmfPersonalInformation.mEmail = "";
		}
	}
	
	static public void setPersonalData() {
		GlobalVars vars = GlobalVars.getSharedInstance();
		vars.mAmfPersonalInformation = new GlobalVars.AmfPersonalInformation("", "", "", "");
		/*
		vars.mAmfPersonalInformation.mName = "Steve Jobs";
		vars.mAmfPersonalInformation.mJobTitle = "Apple Inc. CEO";
		vars.mAmfPersonalInformation.mEmail = "steve@apple.com";
		vars.mAmfPersonalInformation.mPhoneNumber = "+800-694-7466";
		*/
		vars.mAmfPersonalInformation.mName = "";
		vars.mAmfPersonalInformation.mJobTitle = "";
		vars.mAmfPersonalInformation.mEmail = "";
		vars.mAmfPersonalInformation.mPhoneNumber = "";
		
		
	}
	static public void resetPersonalData() {
		cleanPersonalData();
		setPersonalData();
	}
	static public void showPersonalData() {
		GlobalVars vars  = GlobalVars.getSharedInstance();
		if(vars.mAmfPersonalInformation != null) {
			Log.i("DEBUG_TAG", "AmfPersonalInformation.mName = "+vars.mAmfPersonalInformation.mName);
			Log.i("DEBUG_TAG", "AmfPersonalInformation.mJobTitle = "+vars.mAmfPersonalInformation.mJobTitle);
			Log.i("DEBUG_TAG", "AmfPersonalInformation.mJobEmail = "+vars.mAmfPersonalInformation.mEmail);
			Log.i("DEBUG_TAG", "AmfPersonalInformation.mPhoneNumber = "+vars.mAmfPersonalInformation.mPhoneNumber);
		}

	}
	
		
	

	static public void cleanSpeechData() {
		if(GlobalVars.getSharedInstance().mAmfSpeech != null) {
			if(GlobalVars.getSharedInstance().mAmfSpeech.mLectors != null) {
				for(GlobalVars.AmfSpeechLector lector : GlobalVars.getSharedInstance().mAmfSpeech.mLectors) {
					if(lector != null && lector.mSpeechList != null) {
						lector.mSpeechList.clear();
						lector.mSpeechList = null;
					}
				}
				GlobalVars.getSharedInstance().mAmfSpeech.mLectors.clear();
				GlobalVars.getSharedInstance().mAmfSpeech.mLectors = null;
			}
			GlobalVars.getSharedInstance().mAmfSpeech = null;
		}
		

		if(GlobalVars.getSharedInstance().mAmfSpeechGson != null) {
			if(GlobalVars.getSharedInstance().mAmfSpeechGson.lector != null) {
				for(GlobalVars.AmfSpeechLectorGson lector : GlobalVars.getSharedInstance().mAmfSpeechGson.lector) {
					if(lector != null && lector.speech_list != null) {
						lector.speech_list.clear();
						lector.speech_list = null;
					}
				}
				GlobalVars.getSharedInstance().mAmfSpeechGson.lector.clear();
				GlobalVars.getSharedInstance().mAmfSpeechGson.lector = null;
			}
			GlobalVars.getSharedInstance().mAmfSpeechGson = null;
		}
		
		/*
		if(GlobalVars.getSharedInstance().mAmfMemberGson != null) {
			if(GlobalVars.getSharedInstance().mAmfMemberGson.participants != null) {
				GlobalVars.getSharedInstance().mAmfMemberGson.participants.clear();
				GlobalVars.getSharedInstance().mAmfMemberGson.participants = null;
			}
			GlobalVars.getSharedInstance().mAmfMemberGson = null;
		}
		*/
	}
	
	static public void setSpeechData() {
		Gson gson = new Gson();
		int idx = 0;		
		if(GlobalVars.getSharedInstance().mAmfSpeechGson == null) {
			String newsJson = JsonProcessUtility.executeHttpGet(sSpeechApiUrlStr);
			String jsonStr = newsJson;
			GlobalVars.getSharedInstance().mAmfSpeechGson =  gson.fromJson(jsonStr, GlobalVars.AmfSpeechGson.class);
			idx = 0;
			
			for ( GlobalVars.AmfSpeechLectorGson lectorGson : GlobalVars.getSharedInstance().mAmfSpeechGson.lector ) {
				Log.i("DEBUG_TAG", "lector["+idx+"].id="+lectorGson.id);
				Log.i("DEBUG_TAG", "lector["+idx+"].date="+lectorGson.date);
				int sidx = 0;
				for (GlobalVars.AmfSpeechTeacherGson teacherGson : lectorGson.speech_list) {
					Log.i("DEBUG_TAG", "lector["+idx+"].speech_list["+sidx+"].name="+teacherGson.name);
					Log.i("DEBUG_TAG", "lector["+idx+"].speech_list["+sidx+"].job_title="+teacherGson.job_title);
					Log.i("DEBUG_TAG", "lector["+idx+"].speech_list["+sidx+"].content="+teacherGson.content);
					Log.i("DEBUG_TAG", "lector["+idx+"].speech_list["+sidx+"].img_url="+teacherGson.img_url);
					sidx++;
				}
				idx++;
			}		
		}
		
		if(GlobalVars.getSharedInstance().mAmfSpeech == null) {
			GlobalVars.getSharedInstance().mAmfSpeech = new GlobalVars.AmfSpeech();
		}

		
		idx = 0;
		for ( GlobalVars.AmfSpeechLectorGson speechLectorGson : GlobalVars.getSharedInstance().mAmfSpeechGson.lector ) {
			GlobalVars.AmfSpeechLector amfSpeechLector = new GlobalVars.AmfSpeechLector();
			//GlobalVars.AmfMemberItem amfMemberItem = new GlobalVars.AmfMemberItem();
			amfSpeechLector.mId = Integer.parseInt(JsonProcessUtility.decodeBase64ToUtf8(speechLectorGson.id));
			amfSpeechLector.mDate = JsonProcessUtility.decodeBase64ToUtf8(speechLectorGson.date);
			
			int sidx = 0;
			for (GlobalVars.AmfSpeechTeacherGson speechTeacherGson : speechLectorGson.speech_list) {
				GlobalVars.AmfSpeechTeacher amfSpeechTeacher = new GlobalVars.AmfSpeechTeacher();
				amfSpeechTeacher.mName = JsonProcessUtility.decodeBase64ToUtf8(speechTeacherGson.name);
				amfSpeechTeacher.mJobTitle = JsonProcessUtility.decodeBase64ToUtf8(speechTeacherGson.job_title);
				amfSpeechTeacher.mContent = JsonProcessUtility.decodeBase64ToUtf8(speechTeacherGson.content);
				amfSpeechTeacher.mImgUrl = JsonProcessUtility.decodeBase64ToUtf8(speechTeacherGson.img_url);
				
				amfSpeechLector.mSpeechList.add(amfSpeechTeacher);
				sidx++;
			}
			
			
			/*
			amfMemberItem.mName = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.name);
			amfMemberItem.mCompany = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.company);
			amfMemberItem.mJobTitle = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.job_title);
			amfMemberItem.mEmail = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.email);
			*/
			//GlobalVars.getSharedInstance().mAmfMember.mItems.add(amfMemberItem);
			GlobalVars.getSharedInstance().mAmfSpeech.mLectors.add(amfSpeechLector);
			
			idx++;
		}
		
	}
	static public void resetSpeechData() {
		cleanSpeechData();
		setSpeechData();
	}
	static public void showSpeechData() {
		int idx = 0;
		for ( GlobalVars.AmfSpeechLector amfSpeechLector : GlobalVars.getSharedInstance().mAmfSpeech.mLectors) {
			/*
			Log.i("DEBUG_TAG", "mAmfSpeech.mItems["+idx+"].mId="+ Integer.toString(amfMemberItem.mId ));
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mName="+ amfMemberItem.mName);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mComapny="+ amfMemberItem.mCompany);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mJobTitle="+ amfMemberItem.mJobTitle);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mEmail="+ amfMemberItem.mEmail);
			*/
			Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mId="+ Integer.toString(amfSpeechLector.mId ));
			Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mDate="+ amfSpeechLector.mDate);
			int sidx = 0;
			for (GlobalVars.AmfSpeechTeacher amfSpeechTeacher : amfSpeechLector.mSpeechList) {
				Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mSpeechList["+sidx+"].mName="+ amfSpeechTeacher.mName);
				Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mSpeechList["+sidx+"].mJobTitle="+ amfSpeechTeacher.mJobTitle);
				Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mSpeechList["+sidx+"].mContent="+ amfSpeechTeacher.mContent);
				Log.i("DEBUG_TAG", "mAmfSpeech.mLector["+idx+"].mSpeechList["+sidx+"].mImgUrl="+ amfSpeechTeacher.mImgUrl);
				sidx++;
			}
			idx++;
		}
	}
	
	

	static public void cleanMemberData() {
		if(GlobalVars.getSharedInstance().mAmfMember != null) {
			if(GlobalVars.getSharedInstance().mAmfMember.mItems != null) {
				GlobalVars.getSharedInstance().mAmfMember.mItems.clear();
				GlobalVars.getSharedInstance().mAmfMember.mItems = null;
			}
			GlobalVars.getSharedInstance().mAmfMember = null;
		}
		
		if(GlobalVars.getSharedInstance().mAmfMemberGson != null) {
			if(GlobalVars.getSharedInstance().mAmfMemberGson.participants != null) {
				GlobalVars.getSharedInstance().mAmfMemberGson.participants.clear();
				GlobalVars.getSharedInstance().mAmfMemberGson.participants = null;
			}
			GlobalVars.getSharedInstance().mAmfMemberGson = null;
		}
	}
	
	static public void setMemberData() {
		Gson gson = new Gson();
		int idx = 0;		
		if(GlobalVars.getSharedInstance().mAmfMemberGson == null) {
			String newsJson = JsonProcessUtility.executeHttpGet(sMemberApiUrlStr);
			String jsonStr = newsJson;
			GlobalVars.getSharedInstance().mAmfMemberGson =  gson.fromJson(jsonStr, GlobalVars.AmfMemberGson.class);
			idx = 0;
			for ( GlobalVars.AmfMemberItemGson albumItem : GlobalVars.getSharedInstance().mAmfMemberGson.participants ) {
				Log.i("DEBUG_TAG", "participants["+idx+"].id="+albumItem.id);
				Log.i("DEBUG_TAG", "participants["+idx+"].name="+albumItem.name);
				Log.i("DEBUG_TAG", "participants["+idx+"].company="+albumItem.company);
				Log.i("DEBUG_TAG", "participants["+idx+"].job_title="+albumItem.job_title);
				Log.i("DEBUG_TAG", "participants["+idx+"].email="+albumItem.email);
				idx++;
			}		
		}
		
		if(GlobalVars.getSharedInstance().mAmfMember == null) {
			GlobalVars.getSharedInstance().mAmfMember = new GlobalVars.AmfMember();
		}

		
		idx = 0;
		for ( GlobalVars.AmfMemberItemGson memberItemGson : GlobalVars.getSharedInstance().mAmfMemberGson.participants ) {
			GlobalVars.AmfMemberItem amfMemberItem = new GlobalVars.AmfMemberItem();
			amfMemberItem.mId = Integer.parseInt(JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.id));
			amfMemberItem.mName = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.name);
			amfMemberItem.mCompany = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.company);
			amfMemberItem.mJobTitle = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.job_title);
			amfMemberItem.mEmail = JsonProcessUtility.decodeBase64ToUtf8(memberItemGson.email);
			
			GlobalVars.getSharedInstance().mAmfMember.mItems.add(amfMemberItem);
			
			idx++;
		}
		
	}
	static public void resetMemberData() {
		cleanMemberData();
		setMemberData();
	}
	static public void showMemberData() {
		int idx = 0;
		for ( GlobalVars.AmfMemberItem amfMemberItem : GlobalVars.getSharedInstance().mAmfMember.mItems) {
			Log.i("DEBUG_TAG", "mAmfMember.mItems["+idx+"].mId="+ Integer.toString(amfMemberItem.mId ));
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mName="+ amfMemberItem.mName);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mComapny="+ amfMemberItem.mCompany);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mJobTitle="+ amfMemberItem.mJobTitle);
			Log.i("DEBUG_TAG", "mAmfMember.mName["+idx+"].mEmail="+ amfMemberItem.mEmail);
			idx++;
		}
	}
	
	
	
	
	
	static public void cleanAlbumData() {
		  
		if(GlobalVars.getSharedInstance().mAmfAlbum != null) {
			if(GlobalVars.getSharedInstance().mAmfAlbum.mActivePhoto != null) {
				GlobalVars.getSharedInstance().mAmfAlbum.mActivePhoto.clear();
				GlobalVars.getSharedInstance().mAmfAlbum.mActivePhoto = null;
			}
			GlobalVars.getSharedInstance().mAmfAlbum = null;
		}
		
		if(GlobalVars.getSharedInstance().mAmfAlbumGson != null) {
			if(GlobalVars.getSharedInstance().mAmfAlbumGson.activePhoto != null) {
				GlobalVars.getSharedInstance().mAmfAlbumGson.activePhoto.clear();
				GlobalVars.getSharedInstance().mAmfAlbumGson.activePhoto = null;
			}
			GlobalVars.getSharedInstance().mAmfAlbumGson = null;
		}
	}
	
	static public void setAlbumData() {
		Gson gson = new Gson();
		int idx = 0;		
		if(GlobalVars.getSharedInstance().mAmfAlbumGson == null) {
			String newsJson = JsonProcessUtility.executeHttpGet(sAlbumApiUrlStr);
			String jsonStr = newsJson;
			GlobalVars.getSharedInstance().mAmfAlbumGson =  gson.fromJson(jsonStr, GlobalVars.AmfAlbumGson.class);
			idx = 0;
			for ( GlobalVars.AmfAlbumItemGson albumItem : GlobalVars.getSharedInstance().mAmfAlbumGson.activePhoto ) {
				Log.i("DEBUG_TAG", "activePhoto["+idx+"].id="+albumItem.id);
				Log.i("DEBUG_TAG", "activePhoto["+idx+"].title="+albumItem.title);
				Log.i("DEBUG_TAG", "activePhoto["+idx+"].img_url="+albumItem.img_url);
				idx++;
			}		
		}
		
		if(GlobalVars.getSharedInstance().mAmfAlbum == null) {
			GlobalVars.getSharedInstance().mAmfAlbum = new GlobalVars.AmfAlbum();
		}

		
		idx = 0;
		for ( GlobalVars.AmfAlbumItemGson albumItemGson : GlobalVars.getSharedInstance().mAmfAlbumGson.activePhoto ) {
			GlobalVars.AmfAlbumItem amfAlbumItem = new GlobalVars.AmfAlbumItem();
			
			
			amfAlbumItem.mId = Integer.parseInt(JsonProcessUtility.decodeBase64ToUtf8(albumItemGson.id));
			amfAlbumItem.mTitle = JsonProcessUtility.decodeBase64ToUtf8(albumItemGson.title);
			amfAlbumItem.mImgUrl = JsonProcessUtility.decodeBase64ToUtf8(albumItemGson.img_url);
			
			
			//GlobalVars.getSharedInstance().mAmfNews.mNews.add(amfNew);
			GlobalVars.getSharedInstance().mAmfAlbum.mActivePhoto.add(amfAlbumItem);
			
			idx++;
		}
		
	}
	
	static public void resetAlbumData() {
		cleanAlbumData();
		setAlbumData();
	}
	
	static public void showAlbumData() {
		int idx = 0;
		for ( GlobalVars.AmfAlbumItem amfAlbumItem : GlobalVars.getSharedInstance().mAmfAlbum.mActivePhoto) {
			Log.i("DEBUG_TAG", "mActivePhoto["+idx+"].mId="+ Integer.toString(amfAlbumItem.mId ));
			Log.i("DEBUG_TAG", "mActivePhoto["+idx+"].mTitle="+amfAlbumItem.mTitle);
			Log.i("DEBUG_TAG", "mActivePhoto["+idx+"].mImgUrl="+amfAlbumItem.mImgUrl);
			idx++;
		}
	}
	
	static public void showNewsData() {
		int idx = 0;
		for ( GlobalVars.AmfNew amfNew : GlobalVars.getSharedInstance().mAmfNews.mNews) {
			Log.i("DEBUG_TAG", "mNews["+idx+"].mId="+ Integer.toString(amfNew.mId ));
			Log.i("DEBUG_TAG", "mNews["+idx+"].mTitle="+amfNew.mTitle);
			Log.i("DEBUG_TAG", "mNews["+idx+"].mContent="+amfNew.mContent);
			Log.i("DEBUG_TAG", "mNews["+idx+"].mDate="+amfNew.mDate);
			idx++;
		}
	}
	
	static public void cleanNewsData() {
		if(GlobalVars.getSharedInstance().mAmfNews != null) {
			if(GlobalVars.getSharedInstance().mAmfNews.mNews != null) {
				GlobalVars.getSharedInstance().mAmfNews.mNews.clear();
				GlobalVars.getSharedInstance().mAmfNews.mNews = null;
			}
			GlobalVars.getSharedInstance().mAmfNews = null;
		}
		if(GlobalVars.getSharedInstance().mAmfNewsGson != null) {
			if(GlobalVars.getSharedInstance().mAmfNewsGson.news != null) {
				GlobalVars.getSharedInstance().mAmfNewsGson.news.clear();
				GlobalVars.getSharedInstance().mAmfNewsGson.news = null;
			}
			GlobalVars.getSharedInstance().mAmfNewsGson = null;
		}
	}
	
	static public void resetNewsData() {
		cleanNewsData();
		setNewsData();
	}
	static private void restoreNewsDataFromLocalStorage() {
		
	}
	static private void saveNewsDataToLocalStorage() {
		
	}
	
	static public void setNewsData() {
		restoreNewsDataFromLocalStorage();
		Gson gson = new Gson();
		int idx = 0;		
		GlobalVars vars = GlobalVars.getSharedInstance();
		
		if(vars.mAmfNewsGson == null) {
			String newsJson = JsonProcessUtility.executeHttpGet(sNewsApiUrlStr);
			String jsonStr = newsJson;
			vars.mAmfNewsGson =  gson.fromJson(jsonStr, GlobalVars.AmfNewsGson.class);
			idx = 0;
			for ( GlobalVars.AmfNewGson theNew : vars.mAmfNewsGson.news ) {
				Log.i("DEBUG_TAG", "news["+idx+"].id="+theNew.id);
				Log.i("DEBUG_TAG", "news["+idx+"].title="+theNew.title);
				Log.i("DEBUG_TAG", "news["+idx+"].content="+theNew.content);
				Log.i("DEBUG_TAG", "news["+idx+"].date="+theNew.date);
				idx++;
			}		
		}
		
		if(vars.mAmfNews == null) {
			vars.mAmfNews = new GlobalVars.AmfNews();
		}
		
		idx = 0;
		for ( GlobalVars.AmfNewGson theNew : GlobalVars.getSharedInstance().mAmfNewsGson.news ) {
			GlobalVars.AmfNew amfNew = new GlobalVars.AmfNew();
			
			amfNew.mId = Integer.parseInt(JsonProcessUtility.decodeBase64ToUtf8(theNew.id));
			amfNew.mTitle = JsonProcessUtility.decodeBase64ToUtf8(theNew.title);
			amfNew.mContent = JsonProcessUtility.decodeBase64ToUtf8(theNew.content);
			amfNew.mDate = JsonProcessUtility.decodeBase64ToUtf8(theNew.date);
			
			vars.mAmfNews.mNews.add(amfNew);
			idx++;
		}
	}	
}