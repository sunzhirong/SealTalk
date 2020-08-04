package cn.rongcloud.im.im.provider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.rongcloud.im.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.ILinkClickListener;
import io.rong.imkit.widget.LinkTextViewMovementMethod;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

@ProviderTag( messageContent = TextMessage.class  )
public class MyTextMessageItemProvider extends TextMessageItemProvider{

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_text_message, (ViewGroup)null);
        MyTextMessageItemProvider.ViewHolder holder = new MyTextMessageItemProvider.ViewHolder();
        holder.message = (AutoLinkTextView)view.findViewById(R.id.text);
        holder.likeLeft = (ImageView)view.findViewById(R.id.iv_like_left);
        holder.likeRight = (ImageView)view.findViewById(R.id.iv_like_right);
        holder.rlLeft = (RelativeLayout) view.findViewById(R.id.rl_left);
        holder.llRight = (LinearLayout) view.findViewById(R.id.ll_right);
        view.setTag(holder);
        return view;
    }

    public Spannable getContentSummary(TextMessage data) {
        return null;
    }

    public Spannable getContentSummary(Context context, TextMessage data) {
        if (data == null) {
            return null;
        } else {
            String content = data.getContent();
            if (content != null) {
                if (content.length() > 100) {
                    content = content.substring(0, 100);
                }

                return new SpannableString(AndroidEmoji.ensure(content));
            } else {
                return null;
            }
        }
    }

    public void onItemClick(View view, int position, TextMessage content, UIMessage message) {
    }

    public void bindView(final View v, int position, TextMessage content, final UIMessage data) {
        MyTextMessageItemProvider.ViewHolder holder = (MyTextMessageItemProvider.ViewHolder)v.getTag();
        if (data.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.message.setBackgroundResource(R.drawable.rc_ic_bubble_right_new);
            holder.message.setTextColor(Color.parseColor("#0A0A0B"));
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(holder.message.getLayoutParams());
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            holder.like.setLayoutParams(layoutParams);
            holder.likeLeft.setVisibility(View.GONE);
            holder.rlLeft.setVisibility(View.GONE);
            holder.likeRight.setVisibility(View.VISIBLE);
            holder.llRight.setVisibility(View.VISIBLE);
        } else {
            holder.message.setBackgroundResource(R.drawable.rc_ic_bubble_left_new);
            holder.message.setTextColor(Color.parseColor("#ffffff"));
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(holder.message.getLayoutParams());
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            holder.like.setLayoutParams(layoutParams);
            holder.likeLeft.setVisibility(View.VISIBLE);
            holder.rlLeft.setVisibility(View.VISIBLE);
            holder.likeRight.setVisibility(View.GONE);
            holder.llRight.setVisibility(View.GONE);
        }

        final AutoLinkTextView textView = holder.message;
        if (data.getTextMessageContent() != null) {
            int len = data.getTextMessageContent().length();
            if (v.getHandler() != null && len > 500) {
                v.getHandler().postDelayed(new Runnable() {
                    public void run() {
                        textView.setText(data.getTextMessageContent());
                    }
                }, 50L);
            } else {
                textView.setText(data.getTextMessageContent());
            }
        }

        holder.message.setMovementMethod(new LinkTextViewMovementMethod(new ILinkClickListener() {
            public boolean onLinkClick(String link) {
                RongIM.ConversationBehaviorListener listener = RongContext.getInstance().getConversationBehaviorListener();
                RongIM.ConversationClickListener clickListener = RongContext.getInstance().getConversationClickListener();
                boolean result = false;
                if (listener != null) {
                    result = listener.onMessageLinkClick(v.getContext(), link);
                } else if (clickListener != null) {
                    result = clickListener.onMessageLinkClick(v.getContext(), link, data.getMessage());
                }

                if (listener == null && clickListener == null || !result) {
                    String str = link.toLowerCase();
                    if (str.startsWith("http") || str.startsWith("https")) {
                        Intent intent = new Intent("io.rong.imkit.intent.action.webview");
                        intent.setPackage(v.getContext().getPackageName());
                        intent.putExtra("url", link);
                        v.getContext().startActivity(intent);
                        result = true;
                    }
                }

                return result;
            }
        }));
        textView.stripUnderlines();
    }

    private static class ViewHolder {
        AutoLinkTextView message;
        ImageView likeLeft;
        ImageView likeRight;
        RelativeLayout rlLeft;
        LinearLayout llRight;
        boolean longClick;

        private ViewHolder() {
        }
    }

}