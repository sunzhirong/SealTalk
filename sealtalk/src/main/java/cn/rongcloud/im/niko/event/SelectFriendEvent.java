package cn.rongcloud.im.niko.event;

import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.model.FriendInfo;

public class SelectFriendEvent {
    public FriendInfo bean ;
    public SelectFriendEvent(FriendInfo bean) {
        this.bean = bean;
    }
}
