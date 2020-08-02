package cn.rongcloud.im.niko.ui.item;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.event.FollowEvent;
import cn.rongcloud.im.niko.model.FollowRequestInfo;
import cn.rongcloud.im.niko.ui.adapter.BaseItemView;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.widget.AsyncImageView;

public class ItemFriendsRequest extends BaseItemView {

    @BindView(R.id.rc_left)
    AsyncImageView mRcLeft;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.msg)
    TextView mMsg;
    private int position;

    public ItemFriendsRequest(Context context) {
        super(context);
    }

    public ItemFriendsRequest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_friends_request;
    }

    public void bindData(FollowRequestInfo info, int position) {
        this.position = position;
        mName.setText(info.getName());
        mName.setTextColor(ProfileUtils.getNameColor(info.getNameColor()));

        if (TextUtils.isEmpty(info.getReqMsg())) {
            mMsg.setVisibility(GONE);
        } else {
            mMsg.setVisibility(VISIBLE);
            mMsg.setText("留言：" + info.getReqMsg());
        }
    }

    @OnClick(R.id.tv_follow)
    public void onViewClicked() {
        EventBus.getDefault().post(new FollowEvent(position));
    }
}
