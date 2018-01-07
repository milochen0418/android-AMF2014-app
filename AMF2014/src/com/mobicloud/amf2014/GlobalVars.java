package com.mobicloud.amf2014;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;



public class GlobalVars {
	
	static private GlobalVars sSharedInstance = null;
	static public GlobalVars getSharedInstance()  {
		if(sSharedInstance == null) {
			sSharedInstance = new GlobalVars();
			sSharedInstance.mAmfSchedule = new AmfSchedule();
		}
		return sSharedInstance;
	}
	private GlobalVars() {
		mNewsJsonString = "";
		
	}
	
	
	
	public String mNewsJsonString;  
	public AmfNews mAmfNews = null;
	public AmfNewsGson mAmfNewsGson = null;
	
	public AmfAlbum mAmfAlbum = null;
	public AmfAlbumGson mAmfAlbumGson = null;
	
	public AmfMember mAmfMember = null;
	public AmfMemberGson mAmfMemberGson = null;

	public AmfSpeech mAmfSpeech = null;
	public AmfSpeechGson mAmfSpeechGson = null;
	
	public AmfSpeechTeacher mCurrentTeacher = null;
	
	public AmfSchedule mAmfSchedule = null;
	public AmfScheduleDayClass mSelectedDayClass = null;
	
	public AmfPersonalInformation mAmfPersonalInformation = null;
	
	
	static public class AmfNewsGson
	{
		ArrayList<AmfNewGson> news; 
	}
	

	static public class AmfNewGson
	{	
		String id;
		String title;
		String content;
		String date;
	}
	
	
	static public class AmfNew {
		int mId;
		String mTitle;
		String mContent;
		String mDate;
		public AmfNew() {
			mId = 0;
			mTitle = "";
			mContent ="";
			mDate = "";
		}
	}
	
	static public class AmfNews {
		ArrayList<AmfNew> mNews;
		public AmfNews() {
			mNews = new ArrayList<AmfNew>();
		}
	}	
	
	static public class AmfAlbumGson {
		ArrayList<AmfAlbumItemGson> activePhoto;
	}
	
	static public class AmfAlbum {
		ArrayList<AmfAlbumItem> mActivePhoto;
		public AmfAlbum() {
			mActivePhoto = new ArrayList<AmfAlbumItem>();
		}
	}
	
	static public class AmfAlbumItemGson {
		String id;
		String title;
		String img_url;
	}
	
	static public class AmfAlbumItem {
		int mId;
		String mTitle;
		String mImgUrl;
	}
	
	static public class AmfMemberGson {
		ArrayList<AmfMemberItemGson> participants;
	}
	static public class AmfMemberItemGson {
		String id;
		String name;
		String company;
		String job_title;
		String email;
	}
	
	
	static public class AmfMember {
		ArrayList<AmfMemberItem> mItems;
		public AmfMember() {
			mItems = new ArrayList<AmfMemberItem>();
		}
	}
	static public class AmfMemberItem {
		int mId;
		String mName;
		String mCompany;
		String mJobTitle;
		String mEmail;
	}
	
	
	static public class AmfSpeech {
		ArrayList<AmfSpeechLector> mLectors;
		public AmfSpeech() {
			mLectors = new ArrayList<AmfSpeechLector>();
		}
	}
	static public class AmfSpeechLector {
		int mId;
		String mDate;
		ArrayList<AmfSpeechTeacher> mSpeechList;
		public AmfSpeechLector() {
			mSpeechList = new ArrayList<AmfSpeechTeacher>();
		}
	}
	static public class AmfSpeechTeacher {
		int mId;
		String mName;
		String mJobTitle;
		String mContent;
		String mImgUrl;
		@Override
		public String toString()
		{
		    return new StringBuffer()
		    .append("[Name]-")
		    .append(mName)
		    .append("JobTitle:")
		    .append(mJobTitle)
		    .append(" mImgUrl: ")
		    .append(mImgUrl)
		    .append(" mContent: ")
		    .append(mContent).toString();
		}
	}
	
	static public class AmfSpeechGson {
		ArrayList<AmfSpeechLectorGson> lector;
	}
	
	static public class AmfSpeechLectorGson {
		String id;
		String date;
		ArrayList<AmfSpeechTeacherGson> speech_list;
	}
	
	static public class AmfSpeechTeacherGson {
		String name;
		String job_title;
		String content;
		String img_url;
	}
	
	
	//AmfSchedule is not class for JSON currently
	static public class AmfSchedule {
		AmfScheduleDay1 mDay1;
		AmfScheduleDay2 mDay2;
		public AmfSchedule(boolean isChinese) {
			mDay1 = new AmfScheduleDay1(isChinese);
			mDay2 = new AmfScheduleDay2(isChinese);
		}
		public AmfSchedule() { 
			//if(getResources().getConfiguration().locale.getCountry().equals("CN"))			
			mDay1 = new AmfScheduleDay1();
			mDay2 = new AmfScheduleDay2();
		}
		
	}
	
	static public class AmfScheduleDay1 {
		AmfScheduleDayClass mClass1; //來賓報到
		AmfScheduleDayClass mClass2; //開幕致詞 
		AmfScheduleDayClass mClass3; //專題演講1:下一步創新故事力
		AmfScheduleDayClass mClass4; //CEO 對談。會展趨勢新紀元 
		AmfScheduleDayClass mClass5; //中場休息
		AmfScheduleDayClass mClass6; //專題演講2:東京國際馬拉松
		AmfScheduleDayClass mClass7; //分組座1:  創新城市行銷
		public AmfScheduleDay1(boolean isChinese) {
			//return AmfScheduleDay1();
			this.setAmfScheduleDay1(isChinese);
		}
		public void setAmfScheduleDay1(boolean isChinese) {
			if(isChinese) {
				setAmfScheduleDay1AsChinese();
			}
			else {
				setAmfScheduleDay1AsEnglish();
			}
		}
		
		public void setAmfScheduleDay1AsEnglish() {
			mClass1 = new AmfScheduleDayClass("13:00-13:30","","");
			mClass2 = new AmfScheduleDayClass("13:30-13:50","Opening Remarks","1.Representative from Bureau of Foreign Trade, MOEA\n2. Representative from TAITRA");
			mClass3 = new AmfScheduleDayClass("13:50-14:30","Keynote Speech 1:\nNext Step - The Power of Creativity","Mr. John Zeigler, Chairman nd CEO, DDB Group Asia Pacific");
			mClass4 = new AmfScheduleDayClass("14:30-15:40","CEO Discussion:\nNew Trends of the MICE Industry","Moderator:"+ 
"\nMr. Paul C.F. Wang, Chief Secretary, Bureau of Foreign Trade, MOEA\nPanelists:\n1.Mr. David DuBois, President, IAEE\n2.Mr. Paul Woodward, Managing Director, UFI\n3.Mr. Martin Winter, Board of Directors for Asia Pacific geographical region, ICCA"); 			
			mClass5 = new AmfScheduleDayClass("15:40-16:00","Break","");
			mClass6 = new AmfScheduleDayClass("16:00-17:00","Keynote Speech 2:\nSuccess of Tokyo Marathon","Mr. Tadaaki Hayano, CSO & Tokyo Marathon Race Director, Tokyo Marathon Foundation");
			mClass7 = new AmfScheduleDayClass("17:00-18:15","Panel Session 1:\nDestination Marketing Innovation","Moderator："+ 
"\nMr. Jason Yeh, Chairman, Taiwan Convention & Exhibition Association (TCEA)\nPanelists:"+
"\n1.Mr. Kazunori Saeki, Former Chief, Kumamoto Prefectural Government, Tokyo Branch Office"+
"\n2.Mr. Lieven Bertels, Director, Sydney Festival"+ 
"\n3.Mr. Tadaaki Hayano, CSO & Tokyo Marathon Race Director, Tokyo Marathon Foundation"+
"\n4.Ms. Chen Shu-Hui, Director, Tourism Department of Taitung County Government."+
"\n5.Mr. Michael Tu, Chairman, Uniplan Taiwan Corporation"
);
		}
		
		public void setAmfScheduleDay1AsChinese() {
			mClass1 = new AmfScheduleDayClass("13:00-13:30","","");
			mClass2 = new AmfScheduleDayClass("13:30-13:50","開幕致詞","經濟部國際貿易局代表\n中華民國對外貿易發展協會代表");
			mClass3 = new AmfScheduleDayClass("13:50-14:30","專題演講1:\n下一步，創新故事力!","Mr. John Zeigler/董事長兼執行長/ DDB Group Asia Pacific");
			mClass4 = new AmfScheduleDayClass("14:30-15:40","CEO對談：\n會展趨勢新紀元","主持人："+ 
"\n王振福/主任秘書/經濟部國際貿易局"+ 
"\n與談人："+
"\nMr. David DuBois/主席/國際展覽與活動協會"+ 
"\nMr. Paul Woodward/總經理/國際展覽業協會"+ 
"\nMr. Martin Winter/亞太區董事/國際會議協會");
			
			mClass5 = new AmfScheduleDayClass("15:40-16:00","中場休息","");
			mClass6 = new AmfScheduleDayClass("16:00-17:00","專題演講2:\n東京國際馬拉松","早野忠昭/事業局長/東京國際馬拉松聯盟");
			mClass7 = new AmfScheduleDayClass("17:00-18:15","分組座談1：\n創新城市行銷","主持人："+ 
"\n葉泰民/理事長/中華國際會議展覽協會"+ 
"\n與談人："+
"\n1.佐伯和典/前東京事務所所長/日本熊本縣"+
"\n2.Mr. Lieven Bertels/活動總監/雪梨藝術節"+ 
"\n3.早野忠昭/事業局長/東京國際馬拉松聯盟"+
"\n4.陳淑慧/處長/台東縣政府觀光旅遊處"+
"\n5.涂建國/董事長/安益國際展覽集團" );
			
		}
		
		
		
		public AmfScheduleDay1() {
			setAmfScheduleDay1(false); 
	
//			mClass1 = new AmfScheduleDayClass("13:00-13:30","","");
//			mClass2 = new AmfScheduleDayClass("13:30-13:50","開幕致詞","經濟部國際貿易局代表\n中華民國對外貿易發展協會代表");
//			mClass3 = new AmfScheduleDayClass("13:50-14:30","專題演講1:\n下一步，創新故事力!","Mr. John Zeigler/董事長兼執行長/ DDB Group Asia Pacific");
//			mClass4 = new AmfScheduleDayClass("14:30-15:40","CEO對談：\n會展趨勢新紀元","主持人："+ 
//"\n徐大衛/副局長/經濟部國際貿易局"+ 
//"\n與談人："+
//"\nMr. David DuBois/主席/國際展覽與活動協會"+ 
//"\nMr. Paul Woodward/總經理/國際展覽業協會"+ 
//"\nMr. Martin Winter/亞太區董事/國際會議協會");
//			
//			mClass5 = new AmfScheduleDayClass("15:40-16:00","中場休息","");
//			mClass6 = new AmfScheduleDayClass("16:00-17:00","專題演講2:\n東京國際馬拉松","早野忠昭/事業局長/東京國際馬拉松聯盟");
//			mClass7 = new AmfScheduleDayClass("13:00-13:30","分組座談1：\n創新城市行銷","主持人："+ 
//"\n葉泰民/理事長/中華國際會議展覽協會"+ 
//"\n與談人："+
//"\n1.台東縣政府代表"+
//"\n2.早野忠昭/事業局長/東京國際馬拉松聯盟"+ 
//"\n3.佐伯和典/前東京事務所所長/日本熊本縣"+ 
//"\n4.Mr. Lieven Bertels/活動總監/雪梨藝術節");
			
			
		}
	}
	
	static public class AmfScheduleDay2 {
		AmfScheduleDayClass mClass1; //專題演講3:雪梨藝術節籌辦經驗分享
		AmfScheduleDayClass mClass2; //專題演講4：2014 UFI ICT Award得獎分享 
		AmfScheduleDayClass mClass3; //中場休息
		AmfScheduleDayClass mClass4; //專題演講5：2013 SITE Crystal Award得獎分享 
		AmfScheduleDayClass mClass5; //專題演講6：2013 ICCA Best Marketing Award得獎分享
		AmfScheduleDayClass mClass6; //午餐
		AmfScheduleDayClass mClass7; //分組座談2：兩岸會展合作之商機探討
		AmfScheduleDayClass mClass8; //中場休息
		AmfScheduleDayClass mClass9; //分組座談3：體驗行銷-兩岸成功案例分享
		
		public AmfScheduleDay2(boolean isChinese) {
			setAmfScheduleDay2(isChinese);
		}
		
		public void setAmfScheduleDay2(boolean isChinese) {
			if(isChinese) {
				setAmfScheduleDay2AsChinese();
			}
			else {
				setAmfScheduleDay2AsEnglish();
			}
		}
		
		public void setAmfScheduleDay2AsChinese() {
			mClass1 = new AmfScheduleDayClass("09:00-09:40","專題演講3:\n雪梨藝術節籌辦經驗分享","Mr. Lieven Bertels/活動總監/雪梨藝術節");
			mClass2 = new AmfScheduleDayClass("09:40-10:20","專題演講4:\n2014 UFI ICT Award 得獎分享","Mr. Santiago Quiroga/國際業務處處長/西班牙馬德里會展局(IFEMA)");
			mClass3 = new AmfScheduleDayClass("10:20-10:40","中場休息","");
			mClass4 = new AmfScheduleDayClass("10:40-11:20","專題演講5:\n2013 SITE Crystal Award得獎分享","Mr. Gebert Janssen/總裁/ the Party- & Eventarchitect");
			mClass5 = new AmfScheduleDayClass("11:20-12:00","專題演講6：\n2013 ICCA Best Marketing Award得獎分享","Mrs. Anna Górska/執行長/波蘭格但斯克會展局");
			mClass6 = new AmfScheduleDayClass("12:05-14:00","午餐","");
			mClass7 = new AmfScheduleDayClass("14:00-15:40","分組座談2：\n兩岸會展合作之商機探討","主持人："+ 
"\n葉明水/副秘書長/中華民國對外貿易發展協會"+
"\n與談人："+ 
"\n1.袁再青/會長/中國會展經濟研究會"+ 
"\n2.過聚榮/處長/中國商務部國際貿易經濟合作   研究院"+ 
"\n3.徐强/副總裁/中國國際展覽中心集團公司"+ 
"\n4.朱裕倫/名譽主席/香港展覽會議業協會"+ 
"\n5.林茂廷/榮譽理事長/中華民國展覽暨會議商業同業公會" +
"\n6.康益智/處長/外貿協會展覽業務處" +
"");
			
			mClass8 = new AmfScheduleDayClass("15:40-16:00","中場休息","");
			mClass9 = new AmfScheduleDayClass("16:00-17:40","分組座談3：\n體驗行銷-兩岸成功案例分享","主持人："+ 
"\n張志浩/董事總經理/奧美行銷互動公司"+ 
"\n與談人："+
"\n1. 楊亓瑋/副總經理/老舍茶館"+ 
"\n2. 莊崧冽/創始人/雕刻時光"+ 
"\n3. 汪麗琴/執行長/好樣書店"+ 
"\n4. 陳來助/執行長/微熱山丘");			
		}
		
		
		
		public void setAmfScheduleDay2AsEnglish(){
			mClass1 = new AmfScheduleDayClass("09:00-09:40","Keynote Speech 3:\nSydney Festival","Mr. Lieven Bertels, Festival Director, Sydney Festival");
			mClass2 = new AmfScheduleDayClass("09:40-10:20","Keynote Speech 4:\nCase Sharing – 2014 UFI ICT Award","Mr. Santiago Quiroga, International Management Director, IFEMA");
			mClass3 = new AmfScheduleDayClass("10:20-10:40","Break","");
			mClass4 = new AmfScheduleDayClass("10:40-11:20","Keynote Speech 5:\nCase Sharing – 2013 SITE Crystal Award","Mr. Gebert Janssen, Managing Director, the Party- & Eventarchitect");
			mClass5 = new AmfScheduleDayClass("11:20-12:00","Keynote Speech 6:\nCase Sharing – 2013 ICCA Best Marketing Award","Mrs. Anna Gorska, CEO, Gdansk Convention Bureau");
			mClass6 = new AmfScheduleDayClass("12:05-14:00","Launch","");
			mClass7 = new AmfScheduleDayClass("14:00-15:40","Panel Discussion 2:\nCross Strait Forum on Business Cooperation Opportunities – Exhibition","Moderator:"+ 
"\nMr. Walter M. S. Yeh, Executive Vice President, TAITRA"+
"\nPanelists:"+ 
"\n1.Mr. Tsai Ching Yuan, Chairman, China Convention & Exhibition Society"+ 
"\n2.Mr. Jurong Guo, Director General, Chinese Academy of International Trade and Economic Cooperation PRC"+ 
"\n3.Mr. Qiang Xu, CFO, China International Exhibition Center Group Corporation"+ 
"\n4.Mr. Stanley Chu, Founder and Chairman, Adsale Exhibition Services Ltd."+ 
"\n5.Mr. Tiger Lin, Honorary President, Taiwan Exhibition & Convention Association (TECA)" +
"\n6.Mr. Yih-Jyh Kang, Executive Director, Exhibition Department, TAITRA" +
"");
			mClass8 = new AmfScheduleDayClass("15:40-16:00","Break","");
			mClass9 = new AmfScheduleDayClass("16:00-17:40","Panel Discussion 3:\nCross Strait Case Sharing – \nSuccessful Experience Marketing","Moderator:"+ 
"\nMr. Eric Chang, Managing Director, OgilvyOne Worldwide"+ 
"\nPanelists:"+
"\n1. Mr. Qiwei Yang, Deputy General Manager, Laoshe Teahouse"+ 
"\n2. Mr. Jimmy Zhuang, Founder, Sculpting in time"+ 
"\n3. Mr. Lai-Juh Chen, CEO, SunnyHills"+ 
"\n4. Grace Wong, CEO, VVG Bookstore");
			
		}
		
		
		
		
		public AmfScheduleDay2() {
			setAmfScheduleDay2AsChinese();
		
		}
	}
	
	
	static public class AmfScheduleDayClass {
		public String mTime;
		public String mClassName;
		public String mSpeakerIntroduction;
		public String mScore;
		public String mSuggestion;
		
		public AmfScheduleDayClass(String timeStr, String classNameStr, String speakerIntroductionStr) {
			mTime = timeStr;
			mClassName = classNameStr;
			mSpeakerIntroduction = speakerIntroductionStr;
			mScore = "0";
			mSuggestion = "";
		}
	}
	
	static public class AmfPersonalInformation {
		public String mName;
		public String mJobTitle;
		public String mPhoneNumber;
		public String mEmail;
		public AmfPersonalInformation(String name, String jobTitle, String phoneNumber, String email ) {
			mName = name;
			mJobTitle = jobTitle;
			mPhoneNumber = phoneNumber;
			mEmail = email;
		}
	}

}

