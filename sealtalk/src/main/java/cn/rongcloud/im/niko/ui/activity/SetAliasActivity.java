package cn.rongcloud.im.niko.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.event.RefreshProfileEvent;
import cn.rongcloud.im.niko.model.Status;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.TitleBar;
import cn.rongcloud.im.niko.widget.dialog.CommonDialog;
import io.rong.eventbus.EventBus;

public class SetAliasActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_nickname)
    AppCompatEditText mEtNickname;
    @BindView(R.id.tv_length)
    AppCompatTextView mTvLength;
    private TextView mTvSubmit;
    private UserInfoViewModel mUserInfoViewModel;
    private String mTargetId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_alias;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initEt();
            initViewModel();
            mTargetId = bundle.getString(IntentExtra.STR_TARGET_ID);
        }
    }


    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);


        mUserInfoViewModel.getSetAliasResult().observe(this,resource -> {
            if (resource.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                ToastUtils.showToast("设置成功");
                finish();
            }
        });
    }

    private void initEt() {
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(v -> {
            mUserInfoViewModel.groupChatInfo(Integer.parseInt(mTargetId),mEtNickname.getText().toString().trim());
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
            }
        });
    }


}
