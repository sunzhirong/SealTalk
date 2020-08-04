package cn.rongcloud.im.niko.event;

import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.model.MyLikeBean;

public class SelectMyLikeEvent {
    public MyLikeBean bean ;
    public SelectMyLikeEvent(MyLikeBean bean) {
        this.bean = bean;
    }
}
