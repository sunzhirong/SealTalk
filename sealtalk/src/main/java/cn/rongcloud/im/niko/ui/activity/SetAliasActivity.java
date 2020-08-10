package cn.rongcloud.im.niko.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.db.DbManager;
import cn.rongcloud.im.db.dao.GroupMemberDao;
import cn.rongcloud.im.db.dao.UserDao;
import cn.rongcloud.im.db.model.UserInfo;
import cn.rongcloud.im.im.IMManager;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.event.AliasChangeSuccessEvent;
import cn.rongcloud.im.niko.utils.ToastUtils;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.TitleBar;
import cn.rongcloud.im.utils.AsyncUtils;
import cn.rongcloud.im.utils.CharacterParser;
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
    private DbManager dbManager;

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
        dbManager = DbManager.getInstance(this);
    }


    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getSetAliasResult().observe(this, resource -> {
            if (resource != null && resource.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                updateAlias();
            } else {
                showToast("设置失败");
            }
        });
    }

    //修改备注
    private void updateAlias() {
        try {
            //数据库操作必须在子线程
            new AsyncUtils<String>(this) {
                @Override
                public String runOnIO() {
                    String displayName = "";
                    if (mEtNickname.getText() != null) {
                        displayName = mEtNickname.getText().toString().trim();
                        UserDao userDao = dbManager.getUserDao();
                        if (userDao != null) {
                            String aliasSpelling = CharacterParser.getInstance().getSpelling(displayName);
                            userDao.updateAlias(mTargetId, displayName, aliasSpelling);

                            UserInfo userInfo = userDao.getUserByIdSync(mTargetId);
                            if (userInfo != null) {
                                // 更新 IMKit 显示缓存
                                String alias = userInfo.getAlias();
                                if (TextUtils.isEmpty(alias)) {
                                    alias = userInfo.getName();
                                }
                                IMManager.getInstance().updateUserInfoCache(userInfo.getId(), alias, Uri.parse(userInfo.getPortraitUri()));
                                // 需要获取此用户所在自己的哪些群组， 然后遍历修改其群组的个人信息。
                                // 用于当有备注的好友在群组时， 显示备注名称
                                GroupMemberDao groupMemberDao = dbManager.getGroupMemberDao();
                                List<String> groupIds = groupMemberDao.getGroupIdListByUserId(mTargetId);
                                if (groupIds != null && groupIds.size() > 0) {
                                    for (String groupId : groupIds) {
                                        //如果有设置群昵称，则不设置好友别名
                                        if (TextUtils.isEmpty(groupMemberDao.getGroupMemberInfoDes(groupId, mTargetId).getGroupNickname())) {
                                            IMManager.getInstance().updateGroupMemberInfoCache(groupId, mTargetId, alias);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return displayName;
                }

                @Override
                public void runOnUI(String alias) {
                    if (alias != null) {
                        ToastUtils.showToast("设置成功");
                        EventBus.getDefault().post(new AliasChangeSuccessEvent(alias));
                        finish();
                    } else {
                        ToastUtils.showToast("设置失败");
                    }
                }
            }.excueTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEt() {
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setEnabled(true);
        mTvSubmit.setOnClickListener(v -> {
            mUserInfoViewModel.setAlias(Integer.parseInt(mTargetId), mEtNickname.getText().toString().trim());

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
            }
        });
    }


}
