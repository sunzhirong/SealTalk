package cn.rongcloud.im.niko.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.rongcloud.im.niko.model.FollowRequestInfo;
import cn.rongcloud.im.niko.ui.item.ItemFriendsRequest;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FriendsRequestRvAdapter extends BaseRvAdapter<FollowRequestInfo> {
    public FriendsRequestRvAdapter(Context context, List<FollowRequestInfo> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemFriendsRequest(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemFriendsRequest) holder.itemView).bindData(mDatas.get(position),position);
    }
}
