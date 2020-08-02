package cn.rongcloud.im.niko.net.service;

import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.model.GroupInfoBean;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.net.ScUrl;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ScIMService {
    @POST(ScUrl.GET_IM_TOKEN)
    @Headers(NetConstant.JSON)
    LiveData<Result<String>> getIMToken();



    @POST(ScUrl.CREATE_GROUP)
    @Headers(NetConstant.JSON)
    LiveData<Result<Integer>> createGroup(@Body RequestBody body);


    @POST(ScUrl.GROUP_CHAT_INFO)
    @Headers(NetConstant.JSON)
    LiveData<Result<GroupInfoBean>> groupChatInfo(@Body RequestBody body);


    @POST(ScUrl.SET_FRIEND_ALIAS)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> setFriendAlias(@Body RequestBody body);



}
