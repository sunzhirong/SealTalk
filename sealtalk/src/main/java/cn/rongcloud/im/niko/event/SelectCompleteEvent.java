package cn.rongcloud.im.niko.event;

import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.model.FollowBean;
import cn.rongcloud.im.niko.model.FriendInfo;

import java.util.List;

public class SelectCompleteEvent {
    public List<FollowBean> list ;
    public SelectCompleteEvent(List<FollowBean> list) {
        this.list = list;
    }
}
