package cn.rongcloud.im.niko.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.ui.adapter.BaseItemView;

public class ItemMyLiked extends BaseItemView {
    public ItemMyLiked(Context context) {
        super(context);
    }

    public ItemMyLiked(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_my_liked;
    }
}
