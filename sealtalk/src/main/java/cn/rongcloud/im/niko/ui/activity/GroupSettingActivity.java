package cn.rongcloud.im.niko.ui.activity;

import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;
import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.event.AddFollowCompleteEvent;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.utils.log.SLog;
import io.rong.eventbus.EventBus;
import io.rong.imlib.model.Conversation;

public class GroupSettingActivity extends BaseActivity{
    private UserInfoViewModel mUserInfoViewModel;
    private Conversation.ConversationType conversationType;
    private String groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_setting;
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        conversationType = (Conversation.ConversationType) intent.getSerializableExtra(IntentExtra.SERIA_CONVERSATION_TYPE);
        groupId = intent.getStringExtra(IntentExtra.STR_TARGET_ID);
        if (groupId == null || conversationType == null) {
            return;
        }
        initViewModel();
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getGroupChatInfoResult().observe(this, result->{
            if(result.RsCode== NetConstant.REQUEST_SUCCESS_CODE) {

            }
        });
        mUserInfoViewModel.groupChatInfo(groupId);
    }
}
