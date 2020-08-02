package cn.rongcloud.im.niko.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.event.RefreshProfileEvent;
import cn.rongcloud.im.niko.model.Status;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.TitleBar;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import io.rong.eventbus.EventBus;

public class ModifyNicknameActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_nickname)
    AppCompatEditText mEtNickname;
    @BindView(R.id.tv_tips)
    AppCompatTextView mTvTips;
    @BindView(R.id.tv_error)
    AppCompatTextView mTvError;
    @BindView(R.id.tv_length)
    AppCompatTextView mTvLength;
    private TextView mTvSubmit;
    private int mType;

    private UserInfoViewModel mUserInfoViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initEt();
            initViewModel();
            mType = bundle.getInt("type", 0);
            switch (mType) {
                case SettingPersonInfoActivity.TYPE_NICKNAME:
                    String nickname = bundle.getString("nickname");
                    mEtNickname.setText(nickname);
                    mEtNickname.setHint(R.string.setting_your_nickname);
                    break;
                case SettingPersonInfoActivity.TYPE_SCHOOL:
                    String school = bundle.getString("school");
                    mEtNickname.setText(school);
                    mEtNickname.setHint(R.string.setting_your_school);
                    break;
                default:
                    String email = bundle.getString("email");
                    mEtNickname.setText(email);
                    mEtNickname.setHint(R.string.setting_your_email);
                    break;
            }
        }
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getUpdateProfile().observe(this, profileInfoResult -> {
            if (profileInfoResult.RsCode == 3) {
                switch (mType) {
                    case SettingPersonInfoActivity.TYPE_NICKNAME:
                        ProfileUtils.sProfileInfo.getHead().setName(mEtNickname.getText().toString().trim());
                        break;
                    case SettingPersonInfoActivity.TYPE_SCHOOL:
                        ProfileUtils.sProfileInfo.setSchool(mEtNickname.getText().toString().trim());
                        break;
                }
//                finish();
                mUserInfoViewModel.getProfile();

            }
        });

        mUserInfoViewModel.getProfileResult().observe(this,resource -> {
            if (resource.status == Status.SUCCESS) {
                ProfileUtils.sProfileInfo = resource.data;
                mUserInfoViewModel.getProfileCache().saveUserCache(resource.data);
                EventBus.getDefault().post(new RefreshProfileEvent());
                finish();
            }
        });
    }

    private void initEt() {
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(v -> {
            updateProfile();
        });
        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                mTvLength.setText(String.valueOf(10 - content.length()));
                mTvSubmit.setEnabled(!TextUtils.isEmpty(content));
                switch (mType) {
                    case SettingPersonInfoActivity.TYPE_NICKNAME:
                        mTvTips.setVisibility(mTvSubmit.isEnabled() ? View.GONE : View.VISIBLE);
                        break;
                    case SettingPersonInfoActivity.TYPE_SCHOOL:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void updateProfile() {
        switch (mType) {
            case SettingPersonInfoActivity.TYPE_NICKNAME:
                mUserInfoViewModel.updateProfile(3,"Name",mEtNickname.getText().toString().trim());
                break;
            case SettingPersonInfoActivity.TYPE_SCHOOL:
                mUserInfoViewModel.updateProfile(2,"School",mEtNickname.getText().toString().trim());
                break;
        }
    }

}
