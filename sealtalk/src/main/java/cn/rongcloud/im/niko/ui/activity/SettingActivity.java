package cn.rongcloud.im.niko.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.io.File;

import androidx.core.content.FileProvider;
import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.VipActivity;
import cn.rongcloud.im.niko.db.DbManager;
import cn.rongcloud.im.niko.db.dao.UserDao;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.model.Status;
import cn.rongcloud.im.niko.model.sc.UserInfo;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.utils.log.SLog;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.dialog.ClearCacheDialog;
import cn.rongcloud.im.niko.widget.dialog.CommonDialog;
import cn.rongcloud.im.niko.widget.SettingItemView;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.siv_info)
    SettingItemView mSivInfo;
    @BindView(R.id.siv_notification)
    SettingItemView mSivNotification;
    @BindView(R.id.siv_hobby)
    SettingItemView mSivHobby;
    @BindView(R.id.siv_contact)
    SettingItemView mSivContact;
    @BindView(R.id.siv_modify_pwd)
    SettingItemView mSivModifyPwd;
    @BindView(R.id.siv_company)
    SettingItemView mSivCompany;
    @BindView(R.id.siv_clear)
    SettingItemView mSivClear;
    @BindView(R.id.siv_logout)
    SettingItemView mSivLogout;

    private CommonDialog mLogoutDialog;
    private CommonDialog mClearCacheDialog;
    private UserInfoViewModel mUserInfoViewModel;
    private File mFile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getProfileResult().observe(this, resource -> {
            if (resource.status == Status.SUCCESS) {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        ProfileUtils.sProfileInfo = resource.data;
                        mUserInfoViewModel.getProfileCache().saveUserCache(resource.data);
                        showToast("获取用户信息成功");
                    }
                });

            } else if (resource.status == Status.LOADING) {
//                    showLoadingDialog("");
            } else {
//                    dismissLoadingDialog(new Runnable() {
//                        @Override
//                        public void run() {
//                            showToast(resource.message);
//                        }
//                    });
            }
        });
        SLog.e("Userintfo",JSON.toJSONString(mUserInfoViewModel.getProfileCache().getUserCache()));
//        ProfileUtils.sProfileInfo = mUserInfoViewModel.getProfileCache().getUserCache();

        mUserInfoViewModel.getHasSetPasswordResult().observe(this,result->{
            ProfileUtils.hasSetPw = result.RsData;
            if (result.RsData){
                ToastUtils.showToast("设置过密码");
            }else {
                ToastUtils.showToast("未设置过密码");
            }
        });

        mUserInfoViewModel.getLogoutResult().observe(this,resource -> {
            if (resource.status == Status.SUCCESS) {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        showToast("退出成功");
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


        mUserInfoViewModel.getProfile(ProfileUtils.sProfileInfo.getId());
        mUserInfoViewModel.hasSetPassword();
    }



    @OnClick({R.id.siv_info, R.id.siv_notification, R.id.siv_hobby, R.id.siv_contact, R.id.siv_modify_pwd,
            R.id.siv_company, R.id.siv_clear, R.id.siv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_info:
                readyGo(SettingPersonInfoActivity.class);
                break;
            case R.id.siv_notification:
                readyGo(SettingNotificationActivity.class);
                break;
            case R.id.siv_hobby:
                DbManager.getInstance(mContext).openDb("niko");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDao userDao = DbManager.getInstance(mContext).getUserDao();
                        String s = "{\n" +
                                "    \"Firstname\": \"string\",\n" +
                                "    \"Lastname\": \"string\",\n" +
                                "    \"Email\": \"string\",\n" +
                                "    \"Phone\": \"string\",\n" +
                                "    \"Gender\": true,\n" +
                                "    \"DOB\": \"2020-07-11T07:33:02.833Z\",\n" +
                                "    \"Address\": \"string\",\n" +
                                "    \"Address2\": \"string\",\n" +
                                "    \"City\": \"string\",\n" +
                                "    \"State\": \"string\",\n" +
                                "    \"Country\": \"string\",\n" +
                                "    \"Origin\": \"string\",\n" +
                                "    \"Height\": 0,\n" +
                                "    \"Weight\": 0\n" +
                                "  }";
                        UserInfo userBean = JSON.parseObject(s, UserInfo.class);
                        userBean.setId("1");
                        userDao.insertUser(userBean);
                    }
                }).start();
                break;
            case R.id.siv_contact:
//                DbManager.getInstance(mContext).openDb("niko");
//
//                new Thread(() -> {
//                    UserInfo niko = DbManager.getInstance(mContext).getUserDao().getUserByIdSync("1");
//                    Log.e("niko",JSON.toJSONString(niko));
//
//                }).start();
                readyGo(VipActivity.class);
                break;
            case R.id.siv_modify_pwd:
                if (!ProfileUtils.hasSetPw) {
                    readyGo(SettingPwdActivity.class);
                } else {
                    readyGo(ModifyPwdActivity.class);
                }
                break;
            case R.id.siv_company:
                readyGo(ContactCompanyActivity.class);
                break;
            case R.id.siv_clear:
                showClearDialog();
                break;
            case R.id.siv_logout:
                logout();
                break;
        }
    }




//    private void cityTest() {
//        String json = FileUtils.getJson(this, "data.json");
//        List<AreaBean> areaBeans = JSONObject.parseArray(json, AreaBean.class);
//        Log.e("areaBeans","areaBeans.size()="+areaBeans.size());
//    }

    private void showClearDialog() {
        if(mClearCacheDialog==null) {
            mClearCacheDialog = new ClearCacheDialog.Builder()
                    .setTitleText(R.string.seal_set_account_dialog_clear_cache_title)
                    .setContentMessage(getString(R.string.seal_set_account_dialog_clear_cache_message))
                    .setButtonText(R.string.common_clear, R.string.common_cancel)
                    .build();
        }
        mClearCacheDialog.show(getSupportFragmentManager(), "clear_cache");
    }


    private void logout() {
        if(mLogoutDialog==null) {
            mLogoutDialog = new CommonDialog.Builder()
                    .setTitleText(R.string.dialog_logout_title)
                    .setContentMessage(getString(R.string.dialog_logout_content))
                    .setDialogButtonClickListener(new CommonDialog.OnDialogButtonClickListener() {
                        @Override
                        public void onPositiveClick(View v, Bundle bundle) {
//                            mLoginViewModel.login("", "13305938755", "qq123456");
                            mUserInfoViewModel.logout();
                        }

                        @Override
                        public void onNegativeClick(View v, Bundle bundle) {
                        }
                    })
                    .build();
        }
        mLogoutDialog.show(getSupportFragmentManager(), "logout");
    }

}
