package cn.rongcloud.im.niko.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.common.NetConstant;
import cn.rongcloud.im.niko.db.model.ProfileHeadInfo;
import cn.rongcloud.im.niko.model.FriendInfo;
import cn.rongcloud.im.niko.utils.glideutils.GlideImageLoaderUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.rong.imkit.widget.AsyncImageView;

public class MembersAdapter extends BaseAdapter implements SectionIndexer {

    public interface OnDeleteClickListener{
        void onDelete(int position);
    }

    private OnDeleteClickListener mOnDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }

    private List<MemberInfo> mList = new ArrayList<>();

    public void setData(List<MemberInfo> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MemberInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_list_item_contact_card, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.rc_user_name);
            viewHolder.portrait = (AsyncImageView) convertView.findViewById(R.id.rc_user_portrait);
            viewHolder.letter = (TextView) convertView.findViewById(R.id.letter);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FriendInfo userInfo = mList.get(position).userInfo;
        if (userInfo != null) {
            viewHolder.name.setText(userInfo.getName());
            viewHolder.name.setTextColor(ProfileUtils.getNameColor(userInfo.getNameColor()));
            GlideImageLoaderUtil.loadCircleImage(parent.getContext(),viewHolder.portrait, userInfo.getUserIcon());
//                if (!TextUtils.isEmpty(userInfo.getExtra())) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(userInfo.getExtra());
//                        if (jsonObject.has("displayName")) {
//                            viewHolder.name.setText(jsonObject.getString("displayName"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        viewHolder.name.setText(userInfo.getName());
//                    }
//                } else {
//                    viewHolder.name.setText(userInfo.getName());
//                }
//                viewHolder.portrait.setAvatar(userInfo.getPortraitUri());
        }

        viewHolder.delete.setOnClickListener(v -> {
            if(mOnDeleteClickListener!=null){
                mOnDeleteClickListener.onDelete(position);
            }
        });

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.letter.setVisibility(View.VISIBLE);
            viewHolder.letter.setText(mList.get(position).getLetter());
        } else {
            viewHolder.letter.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getLetter().charAt(0);
    }


    private static class ViewHolder {
        AsyncImageView portrait;
        TextView name;
        TextView letter;
        TextView delete;
    }

    public static class MemberInfo {
        public FriendInfo userInfo;
        String letter;

        public MemberInfo(FriendInfo userInfo) {
            this.userInfo = userInfo;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getLetter() {
            return letter;
        }
    }

    public static class PinyinComparator implements Comparator<MemberInfo> {


        public static PinyinComparator instance = null;

        public static PinyinComparator getInstance() {
            if (instance == null) {
                instance = new PinyinComparator();
            }
            return instance;
        }

        public int compare(MemberInfo o1, MemberInfo o2) {
            if (o1.getLetter().equals("@") || o2.getLetter().equals("#")) {
                return -1;
            } else if (o1.getLetter().equals("#") || o2.getLetter().equals("@")) {
                return 1;
            } else {
                return o1.getLetter().compareTo(o2.getLetter());
            }
        }

    }
}


