package cn.rongcloud.im.niko.task;

import android.content.Context;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.model.VIPCheckBean;
import cn.rongcloud.im.niko.model.VIPConfigBean;
import cn.rongcloud.im.niko.net.HttpClientManager;
import cn.rongcloud.im.niko.net.RetrofitUtil;
import cn.rongcloud.im.niko.net.service.ScUserService;

import java.util.List;

import androidx.lifecycle.LiveData;

public class VipTask {
    private ScUserService scUserService;
    private Context context;

    public VipTask(Context context) {
        this.context = context.getApplicationContext();
        scUserService = HttpClientManager.getInstance(context).getClient().createService(ScUserService.class);
    }

    public LiveData<Result<Boolean>> updateProfile(int type,String key,Object value){
        return scUserService.updateProfileInfo(RetrofitUtil.createJsonRequest(ProfileUtils.getUpdateInfo(type,key,value)));
    }

    public LiveData<Result<VIPCheckBean>> checkVip(){
        return scUserService.vipCheck();
    }

    public LiveData<Result<List<VIPConfigBean>>> vipInfo(){
        return scUserService.vipInfo();
    }

}
