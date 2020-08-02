package cn.rongcloud.im.niko.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import cn.rongcloud.im.R;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.fragment.SubConversationListFragment;
import io.rong.imlib.model.Conversation;

public class SubConversationListActivtiy extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subconversationlist);
        FragmentManager fragmentManage = getSupportFragmentManager();
        SubConversationListFragment fragement = (SubConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("subconversationlist")
                .appendQueryParameter("type", Conversation.ConversationType.PRIVATE.getName())
                .build();
        fragement.setUri(uri);
    }
}
