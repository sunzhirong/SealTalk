package cn.rongcloud.im.niko.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.event.SelectAtEvent;
import cn.rongcloud.im.niko.event.SelectFriendEvent;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.ui.adapter.BaseItemView;
import cn.rongcloud.im.niko.utils.glideutils.GlideImageLoaderUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.widget.AsyncImageView;

public class ItemFriend extends BaseItemView {
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.rc_left)
    AsyncImageView mRcLeft;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private FriendInfo bean;

    public ItemFriend(Context context) {
        super(context);
    }

    public ItemFriend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_friend;
    }

    public void bindData(FriendInfo friendInfo) {
        this.bean = friendInfo;
        mTvName.setText(bean.getName());
        mTvName.setTextColor(ProfileUtils.getNameColor(bean.getNameColor()));
        GlideImageLoaderUtil.loadCircleImage(mContext,mRcLeft,bean.getUserIcon());
    }

    @OnClick(R.id.ll_container)
    public void onViewClicked() {
        mTvSelect.setSelected(!mTvSelect.isSelected());
        EventBus.getDefault().post(new SelectFriendEvent(bean));
    }
}
