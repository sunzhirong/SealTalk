package cn.rongcloud.im.niko.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.rongcloud.im.R;
import cn.rongcloud.im.db.DbManager;
import cn.rongcloud.im.im.IMManager;
import cn.rongcloud.im.niko.BaseFragment;
import cn.rongcloud.im.niko.common.LogTag;
import cn.rongcloud.im.niko.model.Status;
import cn.rongcloud.im.niko.utils.SPUtils;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.utils.log.SLog;
import cn.rongcloud.im.niko.viewmodel.LoginViewModel;
import cn.rongcloud.im.viewmodel.UserInfoViewModel;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class TwoFragment extends BaseFragment {
    @BindView(R.id.btn_get_token)
    Button mBtnGetToken;
    @BindView(R.id.btn_init_token)
    Button mBtnInitToken;
    @BindView(R.id.btn_create_db)
    Button mBtnCreateDb;
    @BindView(R.id.btn_insert)
    Button mBtnInsert;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    private LoginViewModel mLoginViewModel;
    //自己的测试用户1
    //{"userId":"niko1","token":"GEpsiFHSeu9WHjEyUGfZJ7rbXNyChVbiuqG1LeOB0KU=@u7r5.cn.rongnav.com;u7r5.cn.rongcfg.com"}
    //{"userId":"niko2","token":"ObnhXRXO+AVWHjEyUGfZJ2HTIIz+TTa+pXvEWg4+OqE=@u7r5.cn.rongnav.com;u7r5.cn.rongcfg.com"}
//    {"userId":"niko3","token":"4qzAmqgDGV1WHjEyUGfZJ59ivYEkLj0ZPFuR4TwKlck=@u7r5.cn.rongnav.com;u7r5.cn.rongcfg.com"}
    private String mToken ;
    private String mUserId;
    private UserInfoViewModel mUserInfoViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {
        mToken = SPUtils.getIMToken(getContext());
        UserInfoViewModel userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        userInfoViewModel.getUserInfo().observe(this,userInfoResource -> {
            Log.e("niko",JSON.toJSONString(userInfoResource));
        });
        userInfoViewModel.requestUserInfo(SPUtils.getIMIMUserId(getContext()));
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getGetImTokenResult().observe(this, resource -> {
            if (resource.status == Status.SUCCESS) {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        showToast("获取成功");
                        mToken = resource.data;
                        SPUtils.setIMToken(getContext(),resource.data);
//                        mUserId = resource.data.getUserId();
                    }
                });

            } else if (resource.status == Status.LOADING) {
                showLoadingDialog("");
            } else {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        showToast(resource.message);
                    }
                });
            }
        });


        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getUserInfo().observe(this,result->{
            Log.e("niko", JSONObject.toJSONString(result));
        });
    }

    @OnClick({R.id.btn_get_token, R.id.btn_init_token,R.id.btn_create_db, R.id.btn_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_token:
                mLoginViewModel.getImToken();
                break;
            case R.id.btn_init_token:
                connextIM();
                break;
            case R.id.btn_user:
                mUserInfoViewModel.requestUserInfo(IMManager.getInstance().getCurrentId());

                break;
        }
    }


    private void connextIM() {
        RongIM.connect(mToken, 10, new RongIMClient.ConnectCallback() {
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
//                if (callback != null) {
//                    callback.onSuccess(RongIMClient.getInstance().getCurrentUserId());
//                }
                SLog.e(LogTag.IM, "connect databaseOpenStatus ");

            }

            @Override
            public void onSuccess(String s) {
                // 连接 IM 成功后，初始化数据库
//                DbManager.getInstance(context).openDb(s);
                SLog.e(LogTag.IM, "connect success - code:" + s);
                ToastUtils.showToast("连接成功");
                DbManager.getInstance(getContext()).openDb(s);
            }

            public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                SLog.e(LogTag.IM, "connect error - code:" + errorCode.getValue());
//                if (errorCode == RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT) {
//                    getToken(new ResultCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//                            connectIM(loginResult.token, timeOut, callback);
//                        }
//34001 30007
//                        @Override
//                        public void onFail(int errorCode) {
//                            callback.onFail(errorCode);
//                        }
//                    });;
//                } else {
//                    if (callback != null) {
//                        callback.onFail(ErrorCode.IM_ERROR.getCode());
//                    } else {
//                        // do nothing
//                    }
//                }
            }
        });
    }


}
