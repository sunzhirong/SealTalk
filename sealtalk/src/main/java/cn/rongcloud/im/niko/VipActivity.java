package cn.rongcloud.im.niko;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.rongcloud.im.niko.db.model.ProfileInfo;
import cn.rongcloud.im.niko.model.VIPCheckBean;
import cn.rongcloud.im.niko.ui.activity.BaseActivity;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.utils.glideutils.GlideImageLoaderUtil;
import cn.rongcloud.im.niko.viewmodel.VipViewModel;
import cn.rongcloud.im.niko.widget.VipItemView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.widget.VipItemView;

public class VipActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    AppCompatImageView mIvAvatar;
    @BindView(R.id.tv_name)
    AppCompatTextView mTvName;
    @BindView(R.id.iv_is_vip)
    AppCompatImageView mIvIsVip;
    @BindView(R.id.tv_use)
    AppCompatTextView mTvUse;
    @BindView(R.id.vip_1)
    VipItemView mVip1;
    @BindView(R.id.vip_2)
    VipItemView mVip2;
    @BindView(R.id.vip_3)
    VipItemView mVip3;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.tv_copy)
    TextView mTvCopy;
    private VipViewModel mVipViewModel;
    private String mInviteCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initView() {
        ProfileInfo profileInfo = ProfileUtils.sProfileInfo;
        mTvName.setTextColor(Color.parseColor("#"+profileInfo.getHead().getNameColor()));
        mTvName.setText(profileInfo.getHead().getName());
        GlideImageLoaderUtil.loadCircleImage(mContext,mIvAvatar, ProfileUtils.sProfileInfo.getHead().getUserIcon());
        initViewModel();
    }

    private void initViewModel() {
        mVipViewModel = ViewModelProviders.of(this).get(VipViewModel.class);

        mVipViewModel.getVipCheckResult().observe(this,result->{
            if (result.RsCode == 3) {
                VIPCheckBean rsData = result.RsData;
                mInviteCode = rsData.getInviteCode();
                mTvCode.setText("邀请码："+rsData.getInviteCode());

                if(!rsData.isStep1Way1()||!rsData.isStep1Way2()||!rsData.isStep2()){
                    //不是vip
                    mIvIsVip.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_vip_non_member));
                    mTvUse.setVisibility(View.GONE);
                }else {
                    mIvIsVip.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_vip_member));
                    mTvUse.setVisibility(View.VISIBLE);
                    mVip1.setSelected(rsData.isStep1Way1());
                    mVip2.setSelected(rsData.isStep1Way2());
                    mVip3.setSelected(rsData.isStep2());
                }
            }
        });

        mVipViewModel.vipCheck();
    }



    @OnClick({R.id.tv_use, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use:
                readyGo(SelectNickNameColorActivity.class);
                break;
            case R.id.tv_copy:
                if(!TextUtils.isEmpty(mInviteCode)) {
                    copy(mInviteCode);
                }
                break;
        }
    }

    private void copy(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtils.showToast("复制成功");
        } catch (Exception e) {
        }
    }
}
