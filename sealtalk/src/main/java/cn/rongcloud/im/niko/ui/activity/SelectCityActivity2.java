package cn.rongcloud.im.niko.ui.activity;

import android.os.Bundle;

import cn.rongcloud.im.niko.AreaBean;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.event.CitySelectEvent;
import cn.rongcloud.im.niko.ui.adapter.BaseRvAdapter;
import cn.rongcloud.im.niko.ui.adapter.SelectStatusRvAdpter;
import cn.rongcloud.im.niko.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.rong.eventbus.EventBus;

public class SelectCityActivity2 extends BaseActivity{
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            final List<AreaBean.ProvinceBean> list = (List<AreaBean.ProvinceBean>) bundle.getSerializable("bean");
            final String countryCode = bundle.getString("country_code");
            final String countryName = bundle.getString("country_name");
            SelectStatusRvAdpter selectStatusRvAdpter = new SelectStatusRvAdpter(mContext, list);
            mRvCity.setLayoutManager(new LinearLayoutManager(mContext));
            mRvCity.setAdapter(selectStatusRvAdpter);
            selectStatusRvAdpter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    AreaBean.ProvinceBean provinceBean = list.get(position);
                    if(provinceBean.getCity().size()!=0){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", (Serializable) provinceBean.getCity());
                        bundle.putString("country_code",countryCode);
                        bundle.putString("country_name",countryName);
                        bundle.putString("state_code",provinceBean.getProvince_code());
                        bundle.putString("state_name",provinceBean.getProvince_name());
                        readyGo(SelectCityActivity3.class,bundle);
                    }else {
                        ToastUtils.showToast("无下级，提交");
                        EventBus.getDefault().post(new CitySelectEvent(
                                countryName+list.get(position).getProvince_name(),
                                countryCode,
                                list.get(position).getProvince_code(),
                                ""
                        ));
                        finish();
                    }
                }
            });
        }
    }

    public void onEventMainThread(CitySelectEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
