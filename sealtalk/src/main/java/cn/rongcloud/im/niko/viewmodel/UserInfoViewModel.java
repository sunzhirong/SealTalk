package cn.rongcloud.im.niko.viewmodel;

import android.app.Application;
import android.net.Uri;

import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.db.model.ProfileInfo;
import cn.rongcloud.im.niko.model.CommentAtReq;
import cn.rongcloud.im.niko.model.CommentBean;
import cn.rongcloud.im.niko.model.FollowBean;
import cn.rongcloud.im.niko.model.FollowRequestInfo;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.model.GroupDataReq;
import cn.rongcloud.im.niko.model.GroupInfoBean;
import cn.rongcloud.im.niko.model.Resource;
import cn.rongcloud.im.niko.model.Result;
import cn.rongcloud.im.niko.sp.ProfileCache;
import cn.rongcloud.im.niko.task.UserTask;
import cn.rongcloud.im.niko.utils.SingleSourceLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserInfoViewModel extends AndroidViewModel {

    private UserTask userTask;
    private ProfileCache profileCache;

    private SingleSourceLiveData<Resource<ProfileInfo>> profileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> updateProfileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> hasSetPasswordResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<Boolean>> logoutResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<String>> uploadResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<CommentBean>>> commentResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FollowBean>>> followResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FriendInfo>>> followerListResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FollowRequestInfo>>> getFollowerRequestListResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> addFollowingsResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> removeFollowingsResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Integer>> cmtAddResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Integer>> createGroupResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FriendInfo>>> getFriendListResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<GroupInfoBean>> getGroupInfoBeanResult =  new SingleSourceLiveData<>();




    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userTask = new UserTask(application);
        profileCache = new ProfileCache(application);
    }

    public ProfileCache getProfileCache(){
        return profileCache;
    }

    public SingleSourceLiveData<Resource<ProfileInfo>> getProfileResult() {
        return profileResult;
    }

    public void getProfile() {
        profileResult.setSource(userTask.getProfile());
    }


    public SingleSourceLiveData<Result<Boolean>> getUpdateProfile() {
        return updateProfileResult;
    }

    public void updateProfile(int type, String key, Object value) {
        updateProfileResult.setSource(userTask.updateProfile(type, key, value));
    }

    public SingleSourceLiveData<Result<Boolean>> getHasSetPasswordResult() {
        return hasSetPasswordResult;
    }

    public void hasSetPassword() {
        hasSetPasswordResult.setSource(userTask.hasSetPassword());
    }

    public SingleSourceLiveData<Resource<Boolean>> getLogoutResult() {
        return logoutResult;
    }

    public void logout() {
        logoutResult.setSource(userTask.logout());
    }



    public void uploadAvatar(Uri uri ){
        uploadResult.setSource(userTask.upload(uri));
    }

    public SingleSourceLiveData<Resource<String>> getUploadResult(){
        return uploadResult;
    }

    public void getCommentList(int skip,int take){
        commentResult.setSource(userTask.getCommentList(skip,take));
    }

    public SingleSourceLiveData<Result<List<CommentBean>>> getCommentListResult() {
        return commentResult;
    }

    public void getFollowList(int skip,int take){
        followResult.setSource(userTask.getFollowList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FollowBean>>> getFollowListResult() {
        return followResult;
    }


    public void getFollowerList(int skip,int take){
        followerListResult.setSource(userTask.getFollowerList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FriendInfo>>> getFollowerListResult() {
        return followerListResult;
    }

    public void getFollowerRequestList(int skip,int take){
        getFollowerRequestListResult.setSource(userTask.getFollowerRequestList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FollowRequestInfo>>> getFollowerRequestListResult() {
        return getFollowerRequestListResult;
    }

    public void addFollowings(int uid){
        addFollowingsResult.setSource(userTask.addFollowings(uid));
    }

    public SingleSourceLiveData<Result<Boolean>> getAddFollowingsResult() {
        return addFollowingsResult;
    }

    public void removeFollowings(int uid){
        removeFollowingsResult.setSource(userTask.removeFollowings(uid));
    }

    public SingleSourceLiveData<Result<Boolean>> getRemoveFollowingsResult() {
        return removeFollowingsResult;
    }

    public void cmtAdd(CommentAtReq data){
        cmtAddResult.setSource(userTask.cmtAdd(data));
    }

    public SingleSourceLiveData<Result<Integer>> getCmtAddResult() {
        return cmtAddResult;
    }


    public void createGroup(GroupDataReq data){
        createGroupResult.setSource(userTask.createGroup(data));
    }

    public SingleSourceLiveData<Result<Integer>> getCreateGroupResult() {
        return createGroupResult;
    }



    public void getFriendList(int skip,int take){
        getFriendListResult.setSource(userTask.getFriendList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FriendInfo>>> getFriendListResult() {
        return getFriendListResult;
    }


    public void groupChatInfo(String groupId){
        getGroupInfoBeanResult.setSource(userTask.groupChatInfo(groupId));
    }

    public SingleSourceLiveData<Result<GroupInfoBean>> getGroupChatInfoResult() {
        return getGroupInfoBeanResult;
    }





}
