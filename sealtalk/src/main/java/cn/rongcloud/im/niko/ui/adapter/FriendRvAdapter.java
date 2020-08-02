package cn.rongcloud.im.niko.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.ui.item.ItemFollow;
import cn.rongcloud.im.niko.ui.item.ItemFriend;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FriendRvAdapter extends BaseRvAdapter<FriendInfo> {

    public FriendRvAdapter(Context context, List<FriendInfo> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemFriend(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemFriend) holder.itemView).bindData(mDatas.get(position));
    }
}
