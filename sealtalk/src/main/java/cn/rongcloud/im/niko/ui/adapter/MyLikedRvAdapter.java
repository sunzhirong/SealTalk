package cn.rongcloud.im.niko.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.rongcloud.im.niko.ui.item.ItemMyLiked;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyLikedRvAdapter extends BaseRvAdapter<String> {
    public MyLikedRvAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemMyLiked(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {

    }
}
