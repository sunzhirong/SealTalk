package cn.rongcloud.im.niko.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.rongcloud.im.niko.BaseFragment;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.ui.adapter.CommentRvAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class MyChatFragment extends ConversationListFragment {

    private int position = 0;

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
}