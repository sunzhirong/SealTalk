package cn.rongcloud.im.niko.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.rongcloud.im.R;
import cn.rongcloud.im.common.IntentExtra;
import cn.rongcloud.im.model.AddMemberResult;
import cn.rongcloud.im.model.GroupResult;
import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.event.SelectFriendEvent;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.model.GroupDataReq;
import cn.rongcloud.im.niko.ui.adapter.FollowRvAdapter;
import cn.rongcloud.im.niko.ui.adapter.FriendRvAdapter;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.rongcloud.im.utils.ToastUtils;
import cn.rongcloud.im.utils.log.SLog;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class SelectGoupMemberActivity extends BaseActivity {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_group)
    RecyclerView mRvGroup;
    private UserInfoViewModel mUserInfoViewModel;
    private FriendRvAdapter mFriendRvAdapter;
    private List<FriendInfo> mRsData;
    private List<FriendInfo> mList;
    private String createGroupName = "Niko 测试群聊";
    private boolean isReturnResult;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_group_member;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        isReturnResult = intent.getBooleanExtra(IntentExtra.BOOLEAN_CREATE_GROUP_RETURN_RESULT, false);

        EventBus.getDefault().register(this);
        mList = new ArrayList<>();

        mRvGroup.setLayoutManager(new LinearLayoutManager(mContext));
        mFriendRvAdapter = new FriendRvAdapter(mContext, new ArrayList<>());
        mRvGroup.setAdapter(mFriendRvAdapter);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                if(TextUtils.isEmpty(content)){
                    mFriendRvAdapter.setDatas(mRsData);
                }else {
                    List<FriendInfo> infos = new ArrayList<>();
                    for(FriendInfo info :mRsData){
                        if(info.getName().contains(content)){
                            infos.add(info);
                        }
                    }
                    mFriendRvAdapter.setDatas(infos);
                }
            }
        });
        initViewModel();
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getCreateGroupResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                processCreateResult(String.valueOf(result.RsData));
//                finish();
            }
        });

        mUserInfoViewModel.getFriendListResult().observe(this,result->{
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                mRsData = result.RsData;
                mFriendRvAdapter.setDatas(mRsData);
            }
        });
        mUserInfoViewModel.getFriendList(NetConstant.SKIP,NetConstant.TAKE);

    }

    /**
     * 处理创建结果
     *
     * @param
     */
//    private void processCreateResult(GroupResult groupResult) {
    private void processCreateResult(String groupId) {
//        String groupId = "";
//        if (groupResult != null) {
//            groupId = groupResult.id;

//            // 判断是否有人需要同意加入群，显示提示信息
//            if (groupResult.userStatus != null && groupResult.userStatus.size() > 0) {
//                String tips = getString(R.string.seal_create_success);
//                //1 为已加入, 2 为等待管理员同意, 3 为等待被邀请者同意
//                for (AddMemberResult result : groupResult.userStatus) {
//                    if (result.status == 3) {
//                        tips = getString(R.string.seal_add_need_member_agree);
//                        break;
//                    }
//                }
//                ToastUtils.showToast(tips);
//            }
//        }

        // 返回结果时候设置结果并结束
        if (isReturnResult) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(IntentExtra.GROUP_ID, groupId);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            //不返回结果时，创建成功后跳转到群组聊天中
            toGroupChat(groupId);
        }
    }

    /**
     * 跳转到群组聊天
     */
    private void toGroupChat(String groupId) {
        RongIM.getInstance().startConversation(this, Conversation.ConversationType.GROUP, groupId, createGroupName);
        finish();
    }


    @OnClick({R.id.tv_cancel, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_complete:
                if(mList.size()!=0) {
                    createGroup();
                }
                break;
        }
    }

    private void createGroup() {
        GroupDataReq groupDataReq = new GroupDataReq();
        groupDataReq.setChatGrpID(0);
        groupDataReq.setTitle("Niko 测试群聊");
        List<Integer> integers = new ArrayList<>();
        //加入自己
        for(FriendInfo info : mList){
            integers.add(info.getUID());
        }

        groupDataReq.setUIDs(integers);
        integers.add(0, ProfileUtils.sProfileInfo.getHead().getUID());
        mUserInfoViewModel.createGroup(groupDataReq);
    }


    public void onEventMainThread(SelectFriendEvent event) {
        if(!mList.contains(event.bean)) {
            mList.add(event.bean);
            mTvComplete.setText("创建" + mList.size());
        }else {
            mList.remove(event.bean);
            if(mList.size()==0){
                mTvComplete.setText("创建");
            }else {
                mTvComplete.setText("创建" + mList.size());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
