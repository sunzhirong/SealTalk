package cn.rongcloud.im.niko.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import cn.rongcloud.im.niko.ui.adapter.ConversationListAdapterNikoEx;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class MyChatFragment extends ConversationListFragment {

    private int position = 0;
    private ConversationListAdapterNikoEx conversationListAdapterEx;

    public static MyChatFragment getInstance(int position) {
        MyChatFragment sf = new MyChatFragment();
        sf.position = position;
        return sf;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setTag(position);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setConversationlist();
    }

    private void setConversationlist() {
        Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        setUri(uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        setConversationlist();
    }

    //    @Override
//    public ConversationListAdapter onResolveAdapter(Context context) {
//
//        if (conversationListAdapterEx == null) {
//            conversationListAdapterEx = new ConversationListAdapterNikoEx(context);
//            conversationListAdapterEx.setGroupApplyMessageListener(count-> {
//                updateGroupNotifyUnReadCount(count);
//
//            });
//        }
//        return conversationListAdapterEx;
//    }

//    /**
//     * 更新群通知未读消息的数量
//     *
//     * @param num
//     */
//    public void updateGroupNotifyUnReadCount(int num) {
//        if (mainActivity != null) {
//            mainActivity.mainViewModel.setGroupNotifyUnReadNum(num);
//        }
//    }
//
//    private void initViewModel() {
//        groupNoticeInfoViewModel = ViewModelProviders.of(this).get(GroupNoticeInfoViewModel.class);
//        groupNoticeInfoViewModel.getGroupNoticeInfo().observe(this, new Observer<Resource<List<GroupNoticeInfo>>>() {
//            @Override
//            public void onChanged(Resource<List<GroupNoticeInfo>> listResource) {
//                if (listResource.status != Status.LOADING) {
//                    if (conversationListAdapterEx != null) {
//                        conversationListAdapterEx.updateNoticeInfoData(listResource.data);
//                    }
//                }
//            }
//        });
//    }
}