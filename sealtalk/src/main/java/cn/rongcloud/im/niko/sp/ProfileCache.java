package cn.rongcloud.im.niko.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.rongcloud.im.SealApp;
import cn.rongcloud.im.niko.db.model.ProfileInfo;
import cn.rongcloud.im.niko.model.sc.UserInfo;
import cn.rongcloud.im.niko.utils.SPUtils;

import com.google.gson.Gson;

public class ProfileCache {
    private static final String SP_NAME = "Profile_cache";
    private static final String SP_CACHE_USER = "user_profile";
    private final SharedPreferences sp;

    public ProfileCache(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 缓存去登录之后 User 的信息。
     * @param userCacehInfo
     */
    public void saveUserCache(ProfileInfo userCacehInfo) {
        Gson gson = new Gson();
        String userJson = gson.toJson(userCacehInfo);
        sp.edit().putString(SP_CACHE_USER, userJson).commit();
    }



    /**
     * 获取用户缓存信息
     * @return
     */
    public ProfileInfo getUserCache() {
        try {
            String userJson = sp.getString(SP_CACHE_USER, "");
            if (TextUtils.isEmpty(userJson)) {
                return  null;
            }

            Gson gson = new Gson();
            ProfileInfo profileInfo = gson.fromJson(userJson, ProfileInfo.class);
            return profileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 退出登录所要清理的缓存
     */
    public void logoutClear() {
        sp.edit().putString(SP_CACHE_USER, "").commit();
        SPUtils.setIMToken(SealApp.getApplication(),"");
        SPUtils.setLogin(SealApp.getApplication(),false);
    }
}
