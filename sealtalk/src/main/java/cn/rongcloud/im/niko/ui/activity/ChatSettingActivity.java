package cn.rongcloud.im.niko.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.niko.widget.SettingItemView;
import cn.rongcloud.im.niko.widget.dialog.CommonDialog;

public class ChatSettingActivity extends BaseActivity {
    @BindView(R.id.siv_img)
    SettingItemView mSivImg;
    @BindView(R.id.siv_alias)
    SettingItemView mSivAlias;
    @BindView(R.id.siv_set_top)
    SettingItemView mSivSetTop;
    @BindView(R.id.siv_black)
    SettingItemView mSivBlack;
    @BindView(R.id.siv_report)
    SettingItemView mSivReport;
    private String targetId;
    private CommonDialog mAddBlackDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_setting;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        targetId = intent.getStringExtra(IntentExtra.STR_TARGET_ID);
    }


    @OnClick({R.id.siv_alias, R.id.siv_black, R.id.siv_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_alias:
                Bundle bundle = new Bundle();
                bundle.putString(IntentExtra.STR_TARGET_ID,targetId);
                readyGo(SetAliasActivity.class,bundle);
                break;
            case R.id.siv_black:
                addBlack();
                break;
            case R.id.siv_report:
                break;
        }
    }

    private void addBlack() {
        if(mAddBlackDialog ==null) {
            mAddBlackDialog = new CommonDialog.Builder()
                    .setTitleText(R.string.dialog_add_black_title)
                    .setContentMessage(getString(R.string.dialog_add_black_content))
                    .setButtonText(R.string.dialog_add_black,R.string.common_cancel)
                    .setPositiveColor(R.color.color_red_text)
                    .setDialogButtonClickListener(new CommonDialog.OnDialogButtonClickListener() {
                        @Override
                        public void onPositiveClick(View v, Bundle bundle) {
                        }

                        @Override
                        public void onNegativeClick(View v, Bundle bundle) {
                        }
                    })
                    .build();
        }
        mAddBlackDialog.show(getSupportFragmentManager(), "addBlack");
    }
}
