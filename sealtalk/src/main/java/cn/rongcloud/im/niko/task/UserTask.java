package cn.rongcloud.im.niko.task;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import androidx.lifecycle.MediatorLiveData;
import cn.rongcloud.im.db.DbManager;
import cn.rongcloud.im.db.dao.NikoUserDao;
import cn.rongcloud.im.db.dao.UserDao;
import cn.rongcloud.im.db.model.UserInfo;
import cn.rongcloud.im.im.IMManager;
import cn.rongcloud.im.model.UserCacheInfo;
import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.db.model.ProfileInfo;
import cn.rongcloud.im.niko.model.CommentAtReq;
import cn.rongcloud.im.niko.model.CommentBean;
import cn.rongcloud.im.niko.model.FollowBean;
import cn.rongcloud.im.niko.model.FollowRequestInfo;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.model.GroupInfoBean;
import cn.rongcloud.im.niko.model.GroupDataReq;
import cn.rongcloud.im.niko.model.MyLikeBean;
import cn.rongcloud.im.niko.model.Resource;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.model.sc.TokenBean;
import cn.rongcloud.im.niko.net.HttpClientManager;
import cn.rongcloud.im.niko.net.NetworkBoundResource;
import cn.rongcloud.im.niko.net.NetworkOnlyResource;
import cn.rongcloud.im.niko.net.RetrofitUtil;
import cn.rongcloud.im.niko.net.ScInterceptor;
import cn.rongcloud.im.niko.net.service.ScIMService;
import cn.rongcloud.im.niko.net.service.ScUserService;
import cn.rongcloud.im.niko.net.service.TokenService;
import cn.rongcloud.im.niko.net.service.UploadService;
import cn.rongcloud.im.niko.net.token.TokenHttpClientManager;
import cn.rongcloud.im.niko.net.upload.UploadHttpClientManager;
import cn.rongcloud.im.niko.utils.log.SLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import cn.rongcloud.im.utils.RongGenerate;
import cn.rongcloud.im.utils.SearchUtils;
import io.rong.message.utils.BitmapUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserTask {
//    private FileManager fileManager;
    private ScUserService scUserService;
    private TokenService tokenService;
    private ScIMService scIMService;
    private UploadService uploadService;
    private Context context;
    private DbManager dbManager;

    public UserTask(Context context) {
        this.context = context.getApplicationContext();
        scUserService = HttpClientManager.getInstance(context).getClient().createService(ScUserService.class);
        tokenService = TokenHttpClientManager.getInstance(context).getClient().createService(TokenService.class);
        scIMService = HttpClientManager.getInstance(context).getClient().createService(ScIMService.class);
        uploadService = UploadHttpClientManager.getInstance(context).getClient().createService(UploadService.class);

        dbManager = DbManager.getInstance(context.getApplicationContext());
    }





    /**
     * 用户登录
     *
     * @param region   国家区号
     * @param phone    手机号码
     * @param password 密码
     */
    public LiveData<Resource<String>> login(String region, String phone, String password) {
//        MediatorLiveData<Resource<String>> result = new MediatorLiveData<>();
//        result.setValue(Resource.loading(null));

        LiveData<Resource<String>> login = new NetworkOnlyResource<String, Result<String>>() {
            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
                RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
                return scUserService.login(body);
            }
        }.asLiveData();
        return login;

//        result.addSource(login, loginResultResource -> {
//            if (loginResultResource.status == Status.SUCCESS) {
//                result.removeSource(login);
//
//                LoginResult loginResult = loginResultResource.data;
//                if (loginResult != null) {
//                    imManager.connectIM(loginResult.token, true, new ResultCallback<String>() {
//                        @Override
//                        public void onSuccess(String s) {
//                            result.postValue(Resource.success(s));
//                            // 存储当前登录成功的用户信息
//                            UserCacheInfo info = new UserCacheInfo(s, loginResult.token, phone, password, region, countryCache.getCountryInfoByRegion(region));
//                            userCache.saveUserCache(info);
//                        }
//
//                        @Override
//                        public void onFail(int errorCode) {
//                            result.postValue(Resource.error(errorCode, null));
//                        }
//                    });
//                } else {
//                    result.setValue(Resource.error(ErrorCode.API_ERR_OTHER.getCode(), null));
//                }
//            } else if (loginResultResource.status == Status.ERROR) {
//                result.setValue(Resource.error(loginResultResource.code, null));
//            } else {
//                // do nothing
//            }
//        });
//        return result;
    }


    public LiveData<TokenBean> getAccessToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "client_credentials");
        paramsMap.put("scope", "jjApiScope");
        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }

    public LiveData<Result> getSms(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getSms(body);
    }

    public LiveData<Result> smsVerify(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        paramsMap.put("VCode", "9999");
        RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.verifyCode(body);
    }

    public LiveData<TokenBean> getUserToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "password");
        paramsMap.put("scope", "jjApiScope");
        paramsMap.put("UserName", "13305938755");
        paramsMap.put("Password", ScInterceptor.getDV()+"9999");
        paramsMap.put("VCode", "9999");

        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }


    public LiveData<Resource<ProfileInfo>> getProfile(int uuid){
        return new NetworkBoundResource<ProfileInfo, Result<ProfileInfo>>() {
            @Override
            protected void saveCallResult(@NonNull Result<ProfileInfo> item) {
                if (item.getRsData() == null) return;
                ProfileInfo userInfo = item.getRsData();

                NikoUserDao userDao = dbManager.getNikoUserDao();
                if (userDao != null) {
                    userInfo.setId(userInfo.getHead().getUID());
                    userDao.insertUser(userInfo);
                    Gson gson = new Gson();
                    Log.e("niko","保存成功"+ gson.toJson(userInfo));

                    LiveData<ProfileInfo> userById = userDao.getUserById(userInfo.getHead().getUID());
                    Log.e("niko","保存成功"+ gson.toJson(userById.getValue()));
                }

//                // 更新 IMKit 显示缓存
//                String alias = "";
//                if (userDao != null) {
//                    alias = userDao.getUserByIdSync(userInfo.getId()).getAlias();
//                }
//                //有备注名的时，使用备注名
//                String name = TextUtils.isEmpty(alias) ? userInfo.getName() : alias;
//                IMManager.getInstance().updateUserInfoCache(userInfo.getId(), name, Uri.parse(userInfo.getPortraitUri()));
            }

            @NonNull
            @Override
            protected LiveData<ProfileInfo> loadFromDb() {
//                return new MediatorLiveData<>();

                NikoUserDao userDao = dbManager.getNikoUserDao();
                if (userDao != null) {
                    LiveData<ProfileInfo> user = userDao.getUserById(uuid);
                    Log.e("niko","获取成功"+JSON.toJSONString(user.getValue()));

                    return user;
                } else {
                    return new MediatorLiveData<>();
                }
            }

            @NonNull
            @Override
            protected LiveData<Result<ProfileInfo>> createCall() {
                return scUserService.getProfileInfo();
            }
        }.asLiveData();
//        LiveData<Resource<ProfileInfo>> profile = new NetworkOnlyResource<ProfileInfo, Result<ProfileInfo>>() {
//            @NonNull
//            @Override
//            protected LiveData<Result<ProfileInfo>> createCall() {
//                return scUserService.getProfileInfo();
//            }
//        }.asLiveData();
//        return profile;
    }



    public LiveData<Result<Boolean>> updateProfile(int type,String key,Object value){
        return scUserService.updateProfileInfo(RetrofitUtil.createJsonRequest(ProfileUtils.getUpdateInfo(type,key,value)));
    }

    public LiveData<Result<Boolean>> hasSetPassword(){
        return scUserService.hasSetPassword();
    }

    public LiveData<Resource<Boolean>> logout(){

        return new NetworkOnlyResource<Boolean, Result<Boolean>>() {

            @NonNull
            @Override
            protected LiveData<Result<Boolean>> createCall() {
                return scUserService.logout();
            }
        }.asLiveData();
    }


    public LiveData<Resource<String>> getImToken(){

        return new NetworkOnlyResource<String, Result<String>>() {

            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {
                return scIMService.getIMToken();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> changePw(String oldPw,String newPw){

        return new NetworkOnlyResource<Boolean, Result<Boolean>>() {

            @NonNull
            @Override
            protected LiveData<Result<Boolean>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("OldPassword", oldPw);
                paramsMap.put("NewPassword", newPw);
                RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
                return tokenService.changePwByOldPw(requestBody);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> setPw(String newPw){

        return new NetworkOnlyResource<Boolean, Result<Boolean>>() {

            @NonNull
            @Override
            protected LiveData<Result<Boolean>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("PhoneNumber", "13305938755");
                paramsMap.put("PhoneCountry", "86");
                paramsMap.put("VCode", "9999");
                paramsMap.put("Password", newPw);
                RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
                return tokenService.changePwByCode(requestBody);
            }
        }.asLiveData();
    }

    /**
     * 获取本地文件真实 uri
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public LiveData<Resource<String>> upload(Uri uri){
        return new NetworkOnlyResource<String, Result<String>>() {

            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {

                File uploadFile = new File(uri.getPath());
                if (!uploadFile.exists()) {
                    uploadFile = new File(getRealPathFromUri(uri));
                }

                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
                builder.addFormDataPart("uploadFile", uploadFile.getPath(), imageBody);//imgfile 后台接收图片流的参数名

                List<MultipartBody.Part> parts = builder.build().parts();
                SLog.e("niko",JSON.toJSONString(imageBody)+"--"+JSON.toJSONString(uploadFile));

                String imageBase64 = getImageBase64(uploadFile.getAbsolutePath());
                SLog.e("niko","base64 = "+ imageBase64);


                return uploadService.uploadAvatar(parts);
            }
        }.asLiveData();
    }

    public static String getImageBase64(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        int options = 100;
        while (bos.toByteArray().length / 1024 > 200){
            bos.reset();
            options -= 5;
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,bos);
        }
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public LiveData<Result<List<CommentBean>>> getCommentList(int skip,int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getCommentList(requestBody);
    }

    public LiveData<Result<List<FollowBean>>> getFollowList(int skip, int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getFollowList(requestBody);
    }

    public LiveData<Result<List<FriendInfo>>> getFollowerList(int skip, int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getFollowerList(requestBody);
    }



    public LiveData<Result<List<FollowRequestInfo>>> getFollowerRequestList(int skip, int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getFollowerRequestList(requestBody);
    }


    public LiveData<Result<Boolean>> addFollowings(int uid){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", uid);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.addFollowings(requestBody);
    }

    public LiveData<Result<Boolean>> removeFollowings(int uid){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", uid);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.removeFollowings(requestBody);
    }


    public LiveData<Result<Integer>> cmtAdd(CommentAtReq data){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", data);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.cmtAdd(requestBody);
    }


    public LiveData<Result<Integer>> createGroup(GroupDataReq data){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", data);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scIMService.createGroup(requestBody);
    }

    public LiveData<Result<List<FriendInfo>>> getFriendList(int skip, int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getFriendList(requestBody);
    }


    public LiveData<Result<GroupInfoBean>> groupChatInfo(String groupId){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", groupId);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scIMService.groupChatInfo(requestBody);
    }

    public LiveData<Result<Boolean>> setFriendAlias(int uuid,String alias){
        HashMap<String, Object> paramsMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("UID",uuid);
        dataMap.put("Alias",alias);
        paramsMap.put("Data", dataMap);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scIMService.setFriendAlias(requestBody);
    }

    public LiveData<Result<Boolean>> addBlack(int uuid){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Data", uuid);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scIMService.addBlack(requestBody);
    }

    public LiveData<Result<List<MyLikeBean>>> myLiekList(int skip, int take){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("Skip", skip);
        paramsMap.put("Take", take);
        paramsMap.put("Data", 0);
        RequestBody requestBody = RetrofitUtil.createJsonRequest(paramsMap);
        return scUserService.getMyLiekList(requestBody);
    }





}
