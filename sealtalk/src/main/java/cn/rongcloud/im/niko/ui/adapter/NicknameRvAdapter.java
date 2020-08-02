package cn.rongcloud.im.niko.ui.adapter;

import android.content.Context;
import android.view.View;

import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.model.VIPConfigBean;
import cn.rongcloud.im.niko.widget.SelectNicknameItemView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NicknameRvAdapter extends BaseRvAdapter<VIPConfigBean> {


    public NicknameRvAdapter(Context context, List<VIPConfigBean> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new SelectNicknameItemView(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((SelectNicknameItemView) holder.itemView).bindData(mDatas.get(position),position);
    }

}
