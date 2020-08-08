package cn.rongcloud.im.niko.ui.activity;

import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.lifecycle.ViewModelProviders;
import cn.rongcloud.im.R;
import cn.rongcloud.im.niko.ChatFragment;
import cn.rongcloud.im.niko.ProfileUtils;
import cn.rongcloud.im.niko.event.CitySelectEvent;
import cn.rongcloud.im.niko.event.ItemCommentEvent;
import cn.rongcloud.im.niko.event.ShowMoreEvent;
import cn.rongcloud.im.niko.ui.fragment.MainFragment;
import cn.rongcloud.im.niko.ui.fragment.TwoFragment;
import cn.rongcloud.im.niko.ui.item.ItemComment;
import cn.rongcloud.im.niko.viewmodel.UserInfoViewModel;
import cn.rongcloud.im.niko.widget.ChatTipsPop;
import cn.rongcloud.im.niko.widget.MainBottomTabGroupView;
import cn.rongcloud.im.niko.widget.MainBottomTabItem;
import cn.rongcloud.im.niko.widget.TabGroupView;
import cn.rongcloud.im.niko.widget.TabItem;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import io.rong.eventbus.EventBus;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tg_bottom_tabs)
    MainBottomTabGroupView mTgBottomTabs;
    @BindView(R.id.fl_order_layout)
    FrameLayout mFlOrderLayout;
    private MainFragment mMainFragment;
    private TwoFragment mTwoFragment;
    private Fragment mSelectFragment;
    private ChatFragment mChatFragment;

    private boolean showPop;
    private ChatTipsPop mPop;

    protected List<View> touchViews = new ArrayList<>();

//    protected List<View> getExcludeTouchHideInputViews(){
//        if(mChatFragment!=null){
//            if(mChatFragment.getCommentFragment()!=null){
//                if(mChatFragment.getCommentFragment().getLlInput()!=null&&mChatFragment.getCommentFragment().getLlInput().getVisibility()==View.VISIBLE){
//                    if(touchViews.size()==0){
//                        touchViews.add(mChatFragment.getCommentFragment().getLlInput());
//                    }
//                    return touchViews;
//                }
//            }
//        }
//        return null;
//    }
//    protected void hideInput() {
//        if(mChatFragment!=null){
//            if(mChatFragment.getCommentFragment()!=null){
//                if(mChatFragment.getCommentFragment().getLlInput()!=null&&mChatFragment.getCommentFragment().getLlInput().getVisibility()==View.VISIBLE){
//                    mChatFragment.getCommentFragment().getLlInput().setVisibility(View.GONE);
//                }
//            }
//        }
//
//    }
    /**
     * tab 项枚举
     */
    public enum Tab {
        /**
         * 我的
         */
        MY(0),
        /**
         * 三俩
         */
        TWO_THREE(1),
        /**
         * 联系
         */
        CHAT(2),
        /**
         * 个人
         */
        ME(3);

        private int value;

        Tab(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Tab getType(int value) {
            for (Tab type : Tab.values()) {
                if (value == type.getValue()) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * tabs 的图片资源
     */
    private int[] tabImageRes = new int[]{
            R.drawable.seal_ic_my,
            R.drawable.seal_ic_two,
            R.drawable.seal_ic_chat,
            R.drawable.seal_ic_me
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        UserInfoViewModel userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        ProfileUtils.sProfileInfo = userInfoViewModel.getProfileCache().getUserCache();

        EventBus.getDefault().register(this);
        if (mFlOrderLayout.getForeground() != null) {
            mFlOrderLayout.getForeground().setAlpha(0);
        }
        initTabs();
    }

    /**
     * 初始化 Tabs
     */
    private void initTabs() {
        // 初始化 tab
        List<TabItem> items = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.tab_names);

        for (Tab tab : Tab.values()) {
            TabItem tabItem = new TabItem();
            tabItem.id = tab.getValue();
            tabItem.text = stringArray[tab.getValue()];
            tabItem.drawable = tabImageRes[tab.getValue()];
            items.add(tabItem);
        }

        mTgBottomTabs.initView(items, new TabGroupView.OnTabSelectedListener() {
            @Override
            public void onSelected(View view, TabItem item) {
                // 当点击 tab 的后， 也要切换到正确的 fragment 页面
//                int currentItem = vpFragmentContainer.getCurrentItem();
//                if (currentItem != item.id) {
//                     切换布局
//                    vpFragmentContainer.setCurrentItem(item.id);
//                     如果是我的页面， 则隐藏红点
//                    if (item.id == Tab.ME.getValue()) {
//                        ((MainBottomTabItem) tabGroupView.getView(Tab.ME.getValue())).setRedVisibility(View.GONE);
//                    }
//                }
                if(item.id == Tab.TWO_THREE.getValue()){
                    changeFragment(mTwoFragment);
                }else if(item.id == Tab.MY.getValue()){
                    changeFragment(mMainFragment);
                }else if(item.id == Tab.CHAT.getValue()){
                    changeFragment(mChatFragment);
                }
            }
        });

        mTgBottomTabs.setOnTabDoubleClickListener(new MainBottomTabGroupView.OnTabDoubleClickListener() {
            @Override
            public void onDoubleClick(TabItem item, View view) {
                // 双击定位到某一个未读消息位置
//                if (item.id == Tab.CHAT.getValue()) {
//                    MainConversationListFragment fragment = (MainConversationListFragment) fragments.get(Tab.CHAT.getValue());
//                    fragment.focusUnreadItem();
//                }
                if(item.id == Tab.ME.getValue()){
                    readyGo(SettingActivity.class);
                }else if(item.id == Tab.MY.getValue()){
//                    readyGo(ChatFragment.class);
                }else {
//                    readyGo(ConversationListActivity.class);
                }
            }
        });



//        // 未读数拖拽
//        ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setTabUnReadNumDragListener(new DragPointView.OnDragListencer() {
//
//            @Override
//            public void onDragOut() {
//                ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setNumVisibility(View.GONE);
//                showToast(getString(R.string.seal_main_toast_unread_clear_success));
//                clearUnreadStatus();
//            }
//        });
//        ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setNumVisibility(View.VISIBLE);


//        addFragment(new MainConversationListFragment());
        mMainFragment = new MainFragment();
        addFragment(mMainFragment);
        mTwoFragment = new TwoFragment();
        addFragment(mTwoFragment);
        mSelectFragment = mMainFragment;
        mChatFragment = new ChatFragment();
        addFragment(mChatFragment);
        changeFragment(mMainFragment);
    }

    private void addFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_container, fragment);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    private void changeFragment(Fragment lastFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mSelectFragment);
        fragmentTransaction.show(lastFragment);
        fragmentTransaction.commit();
        mSelectFragment = lastFragment;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ShowMoreEvent event) {
        if(event.show) {
            mFlOrderLayout.getForeground().setAlpha(153);
        }else {
            mFlOrderLayout.getForeground().setAlpha(0);
        }
    }

    private void showTipsPop() {
        mPop = new ChatTipsPop(this);
        View tabsView = mTgBottomTabs.getView(Tab.CHAT.getValue()).findViewById(R.id.iv_tab_img);
        mPop.showUp(tabsView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!showPop){
            showTipsPop();
            showPop = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isDestroyed()){return;}
                    if(mPop!=null&&mPop.isShowing()){
                        mPop.dismiss();
                        if(mTgBottomTabs!=null) {
                            ((MainBottomTabItem) mTgBottomTabs.getView(Tab.CHAT.getValue())).setNum("11");
                            ((MainBottomTabItem) mTgBottomTabs.getView(Tab.CHAT.getValue())).setNumVisibility(View.VISIBLE);
                        }
                    }
                }
            }, 3000);
        }
    }
    protected void onKeyBoardChange(boolean isPopup){
        mTgBottomTabs.setVisibility(isPopup?View.GONE:View.VISIBLE);
    }

}
