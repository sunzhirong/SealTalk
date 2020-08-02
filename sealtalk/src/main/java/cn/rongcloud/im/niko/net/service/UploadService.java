package cn.rongcloud.im.niko.net.service;

import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.net.ScUrl;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface UploadService {
    @Multipart
    @POST(ScUrl.UPLOAD_AVATAR)
    LiveData<Result<String>> uploadAvatar(@Part List<MultipartBody.Part> partList);
//    LiveData<Result<String>> uploadAvatar(RequestBody params);
}
