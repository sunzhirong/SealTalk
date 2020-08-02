package cn.rongcloud.im.niko.net;

import cn.rongcloud.im.niko.common.ErrorCode;
import cn.rongcloud.im.niko.common.LogTag;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.common.ThreadManager;
import cn.rongcloud.im.niko.model.Resource;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.utils.log.SLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkOnlyResource<ResultType,RequestType> {
    private final ThreadManager threadManager;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkOnlyResource() {
        this.threadManager = ThreadManager.getInstance();
        if(threadManager.isInMainThread()) {
            init();
        }else {
            threadManager.runOnUIThread(() -> init());
        }

    }
    private void init(){
        result.setValue(Resource.loading(null));
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<RequestType> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            if (response != null) {
                if(response instanceof Result){
                    int code = ((Result)response).RsCode;
                    if(code != NetConstant.REQUEST_SUCCESS_CODE){
                        result.setValue(Resource.error(code, null,((Result) response).RsNote));
                        return;
                    } else {
                        // do nothing
                    }
                }

//                if(response instanceof List && ((List) response).get(0) instanceof Result){
//                    int code = ((Result)((List) response).get(0)).RsCode;
//                    if(code != NetConstant.REQUEST_SUCCESS_CODE){
//                        result.setValue(Resource.error(code, null));
//                        return;
//                    } else {
//                        // do nothing
//                    }
//                }
                threadManager.runOnWorkThread(() -> {
                    ResultType resultType = transformRequestType(response); //自定义的
                    if(resultType == null){
                        resultType = transformDefault(response); //默认
                    }
                    try {
                        saveCallResult(resultType);
                    } catch (Exception e) {
                        SLog.e(LogTag.DB, "saveCallResult failed:" + e.toString());
                    }
                    result.postValue(Resource.success(resultType));
                });
            } else {
                result.setValue(Resource.error(ErrorCode.API_ERR_OTHER.getCode(), null));
            }
        });
    }

    /**
     * 重写此方法完成请求类型和响应类型转换
     * 如果是请求结果是 Result<ResultType>  类型则不用重写
     * @param response
     * @return
     */
    @WorkerThread
    protected ResultType transformRequestType(RequestType response){
        return null;
    }

    @WorkerThread
    private ResultType transformDefault(RequestType response){
        if(response instanceof Result){
            Object result = ((Result) response).getRsData();
            if(result != null){
                try {
                    return  (ResultType)result;
                } catch (Exception e){
                    return null;
                }
            }else {
                return null;
            }
        }else{
            return null;
        }
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected void saveCallResult(@NonNull ResultType item){
    }

    @NonNull
    @MainThread
    protected abstract LiveData<RequestType> createCall();
}