package cn.rongcloud.im.niko.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.model.Status;
import cn.rongcloud.im.niko.ui.activity.BaseActivity;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.viewmodel.LoginViewModel;
import cn.rongcloud.im.niko.widget.TitleBar;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

public class SettingPwdActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_confirm)
    EditText mEtConfirm;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    private TextView mTvSubmit;
    private boolean pwdPass;
    private boolean pwdConfirm;
    private LoginViewModel mLoginViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_pwd;
    }

    @Override
    protected void initView() {
        initViewModel();
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = mEtPwd.getText().toString();
                String confirm = mEtConfirm.getText().toString();
                if(pwd.equals(confirm)){
                    mLoginViewModel.setPw(mEtConfirm.getText().toString());
                }else {
                    mTvTips.setVisibility(View.VISIBLE);
                }
            }
        });
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pwdPass = !TextUtils.isEmpty(s.toString().trim());
                mTvSubmit.setEnabled(pwdPass&&pwdConfirm);
            }
        });
        mEtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pwdConfirm = !TextUtils.isEmpty(s.toString().trim());
                mTvSubmit.setEnabled(pwdPass&&pwdConfirm);
            }
        });

    }

    private void initViewModel() {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getSetPwResult().observe(this,resource -> {
            if (resource.status == Status.SUCCESS) {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        ProfileUtils.hasSetPw  = true;
                        finish();
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
    }
}
