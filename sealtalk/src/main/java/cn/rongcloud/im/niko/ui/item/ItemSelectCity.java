package cn.rongcloud.im.niko.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.rongcloud.im.niko.AreaBean;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.ui.adapter.BaseItemView;

import butterknife.BindView;

public class ItemSelectCity extends BaseItemView {


    @BindView(R.id.tv_city)
    TextView mTvCity;

    public ItemSelectCity(Context context) {
        super(context);
    }

    public ItemSelectCity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_select_city;
    }


    public void bindData(AreaBean areaBean) {
        mTvCity.setText(areaBean.getState_name());
    }

    public void bindData(AreaBean.ProvinceBean areaBean) {
        mTvCity.setText(areaBean.getProvince_name());
    }

    public void bindData(AreaBean.ProvinceBean.CityBean areaBean) {
        mTvCity.setText(areaBean.getCity_name());
    }
}
