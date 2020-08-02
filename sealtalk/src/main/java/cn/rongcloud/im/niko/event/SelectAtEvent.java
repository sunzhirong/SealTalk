package cn.rongcloud.im.niko.event;

import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.model.FollowBean;
import cn.rongcloud.im.niko.model.FriendInfo;

public class SelectAtEvent {
    public FollowBean bean ;
    public SelectAtEvent(FollowBean bean) {
        this.bean = bean;
    }
}
