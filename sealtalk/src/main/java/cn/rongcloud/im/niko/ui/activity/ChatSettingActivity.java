package cn.rongcloud.im.niko.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.db.DbManager;
import cn.rongcloud.im.db.dao.FriendDao;
import cn.rongcloud.im.db.model.BlackListEntity;
import cn.rongcloud.im.im.IMManager;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.SettingItemView;
import cn.rongcloud.im.niko.widget.dialog.CommonDialog;
import cn.rongcloud.im.utils.AsyncUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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
    private UserInfoViewModel mUserInfoViewModel;

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
        initViewModel();
        //置顶聊天
        mSivSetTop.setSwitchCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, targetId, isChecked, false, new
                        RongIMClient.ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean success) {
                                showToast(isChecked ? "置顶成功" : "取消置顶成功");
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode ErrorCode) {
                                showToast(isChecked ? "置顶失败" : "取消置顶失败");
                                buttonView.setChecked(!isChecked);
                            }
                        });
            }
        });
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getAddBlackesult().observe(this, resource -> {
            if (resource != null && resource.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                ToastUtils.showToast("设置成功");
                //从通讯录删除
                AsyncUtils.createExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        FriendDao friendDao = DbManager.getInstance(ChatSettingActivity.this).getFriendDao();
                        if (friendDao != null) {
                            BlackListEntity blackListEntity = new BlackListEntity();
                            blackListEntity.setId(targetId);
                            friendDao.addToBlackList(blackListEntity);
                        }
                    }
                });
                //清除会话及消息
                IMManager.getInstance().clearConversationAndMessage(targetId, Conversation.ConversationType.PRIVATE);
            } else {
                ToastUtils.showToast("设置失败");
            }
        });
    }


    @OnClick({R.id.siv_alias, R.id.siv_black, R.id.siv_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_alias:
                Bundle bundle = new Bundle();
                bundle.putString(IntentExtra.STR_TARGET_ID, targetId);
                readyGo(SetAliasActivity.class, bundle);
                break;
            case R.id.siv_black:
                addBlack();
                break;
            case R.id.siv_report:
                break;
        }
    }

    private void addBlack() {
        if (mAddBlackDialog == null) {
            mAddBlackDialog = new CommonDialog.Builder()
                    .setTitleText(R.string.dialog_add_black_title)
                    .setContentMessage(getString(R.string.dialog_add_black_content))
                    .setButtonText(R.string.dialog_add_black, R.string.common_cancel)
                    .setPositiveColor(R.color.color_red_text)
                    .setDialogButtonClickListener(new CommonDialog.OnDialogButtonClickListener() {
                        @Override
                        public void onPositiveClick(View v, Bundle bundle) {
                            mUserInfoViewModel.addBlack(Integer.parseInt(targetId));
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
