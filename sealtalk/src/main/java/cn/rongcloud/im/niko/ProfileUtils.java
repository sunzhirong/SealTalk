package cn.rongcloud.im.niko;

import android.graphics.Color;

import com.alibaba.fastjson.JSON;
import cn.rongcloud.im.niko.db.model.ProfileInfo;

import java.util.HashMap;

public class ProfileUtils {
    public static ProfileInfo sProfileInfo;
    public static boolean hasSetPw;

    public static HashMap<String, Object> getUpdateInfo(int type, String key, Object value){
//        {
//            "Skip": 0,
//                "Take": 0,
//                "Data": {
//            "Head": {
//                "UID": 0,
//                        "Name": "string",
//                        "NameColor": "string",
//                        "UserIcon": "string",
//                        "Gender": true
//            },
//            "Bio": "string",
//                    "Location": "string",
//                    "School": "string",
//                    "DOB": "2020-07-15T01:47:10.243Z"
//        }
//        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Skip",0);
        map.put("Take",0);
        if(type==1) {
            map.put(key,value);
        }

        HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put("Bio",sProfileInfo.getBio());
        dataMap.put("Location",sProfileInfo.getLocation());
        dataMap.put("School",sProfileInfo.getSchool());
        dataMap.put("DOB",sProfileInfo.getDOB());

        if(type==2) {
            dataMap.put(key,value);
        }

        HashMap<String, Object> headMap = new HashMap<>();
        headMap.put("UID",sProfileInfo.getHead().getUID());
        headMap.put("Name",sProfileInfo.getHead().getName());
        headMap.put("NameColor",sProfileInfo.getHead().getNameColor());
        headMap.put("UserIcon",sProfileInfo.getHead().getUserIcon());
        headMap.put("Gender",sProfileInfo.getHead().isGender());

        if(type==3) {
            headMap.put(key,value);
        }

        dataMap.put("Head", JSON.toJSON(headMap));

        map.put("Data",JSON.toJSON(dataMap));
        return map;
    }

    public static int getNameColor(String color){
        try {
            return Color.parseColor("#"+color);

        }catch (Exception e){
            return Color.parseColor("#0A0A0B");
        }
    }

//    public static int getShowName(ProfileInfo info){
//        if(info.getHead().getName())
//    }
}
