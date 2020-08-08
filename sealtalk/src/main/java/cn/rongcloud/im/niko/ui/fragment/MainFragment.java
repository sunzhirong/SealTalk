package cn.rongcloud.im.niko.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import androidx.lifecycle.LiveData;
import cn.rongcloud.im.db.DbManager;
import cn.rongcloud.im.niko.BaseFragment;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.db.model.ProfileInfo;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.model.sc.TokenBean;
import cn.rongcloud.im.niko.net.ScInterceptor;
import cn.rongcloud.im.niko.sp.UserConfigCache;
import cn.rongcloud.im.niko.utils.SPUtils;
import cn.rongcloud.im.niko.viewmodel.LoginViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {
    @BindView(R.id.btn_access_token)
    Button mBtnAccessToken;
    @BindView(R.id.btn_send_sms)
    Button mBtnSendSms;
    @BindView(R.id.btn_vertify_sms)
    Button mBtnVertifySms;
    @BindView(R.id.tv_access_token)
    TextView mTvAccessToken;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_send_sms)
    TextView mTvSendSms;
    @BindView(R.id.tv_vertify_sms)
    TextView mTvVertifySms;
    @BindView(R.id.btn_user_token)
    Button mBtnUserToken;
    @BindView(R.id.tv_user_token)
    TextView mTvUserToken;


    private LoginViewModel mLoginViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {

        initLoginInfo();
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getGetTokenResult().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean tokenBean) {
                if (tokenBean != null && !TextUtils.isEmpty(tokenBean.getAccess_token())) {
                    showToast("成功");
                    mTvAccessToken.setText("成功");
                    NetConstant.Authorization = "Bearer "+tokenBean.getAccess_token();
                } else {
                    showToast("失败");
                    mTvAccessToken.setText("失败");
                }
            }
        });


        mLoginViewModel.getGetSmsResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result!=null&&result.RsCode == 3) {
                    showToast("成功");
                    mTvSendSms.setText("成功");
                } else {
                    showToast("失败");
                    mTvSendSms.setText("失败");

                }
            }
        });

        mLoginViewModel.getVerifyResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.RsCode == 3) {
                    showToast("成功");
                    mTvVertifySms.setText("成功");

                } else {
                    showToast("失败");
                    mTvVertifySms.setText("失败");
                }
            }
        });

        mLoginViewModel.getGetUserTokenResult().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean tokenBean) {
                if (tokenBean != null && !TextUtils.isEmpty(tokenBean.getAccess_token())) {
                    showToast("成功");
                    mTvUserToken.setText("成功");
                    NetConstant.Authorization = "Bearer "+tokenBean.getAccess_token();
                    SPUtils.setUserToken(getContext(),tokenBean.getAccess_token());
                    SPUtils.setLogin(getContext(),true);
                    initLoginInfo();
                } else {
                    showToast("失败");
                    mTvUserToken.setText("失败");

                }
            }
        });
    }

    private void initLoginInfo() {
        if(SPUtils.getLogin(getContext())){
            mTvLogin.setText("已经登陆 Token为="+SPUtils.getUserToken(getContext()));
            NetConstant.Authorization = "Bearer "+SPUtils.getUserToken(getContext());
        }else {
            mTvLogin.setText("未登陆");
        }
    }

    @OnClick({R.id.btn_access_token, R.id.btn_send_sms, R.id.btn_vertify_sms, R.id.btn_user_token,R.id.btn_get,R.id.btn_insert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_access_token:
                mLoginViewModel.getToken();
                break;
            case R.id.btn_send_sms:
                mLoginViewModel.getSms();
                break;
            case R.id.btn_vertify_sms:
                mLoginViewModel.verifySms();
                break;
            case R.id.btn_user_token:
                NetConstant.Authorization = "Basic ampBcHBBcGlDbGllbnQ6Q2lyY2xlMjAyMEBXb3JsZA==";
                mLoginViewModel.getUserToken();
                break;
            case R.id.btn_get:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        List<ProfileInfo> userById = DbManager.getInstance(getContext()).getNikoUserDao().getAll();
                        Log.e("niko","获取成功"+ gson.toJson(userById));
                    }
                }).start();
                break;
            case R.id.btn_insert:
                String userString = "{\"Head\":{\"UID\":2196,\"Name\":\"Nikowq\",\"NameColor\":\"573C89\",\"UserIcon\":\"_aa_UserIcon.jpg\",\"Gender\":false},\"Bio\":\"Dydydyhhjvvnn\",\"Location\":\"中国宁夏石嘴山\",\"School\":\"Xnxbx767\",\"DOB\":\"2010-07-09T00:00:00Z\",\"Email\":null,\"City\":null,\"Followers\":8,\"Followings\":5,\"Likes\":0,\"Moments\":0}";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        ProfileInfo profileInfo = gson.fromJson(userString, ProfileInfo.class);
                        profileInfo.setId(2196);
                        DbManager.getInstance(getContext()).getNikoUserDao().insertUser(profileInfo);
                        Log.e("niko","保存成功");
                    }
                }).start();
                break;
        }
    }
}
