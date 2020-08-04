package cn.rongcloud.im.niko.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.rongcloud.im.niko.model.MyLikeBean;
import cn.rongcloud.im.niko.ui.item.ItemMyLiked;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyLikedRvAdapter extends BaseRvAdapter<MyLikeBean> {
    public MyLikedRvAdapter(Context context, List<MyLikeBean> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemMyLiked(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemMyLiked) holder.itemView).bindData(mDatas.get(position));
    }
}
