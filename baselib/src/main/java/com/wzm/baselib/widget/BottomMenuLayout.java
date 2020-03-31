package com.wzm.baselib.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wzm.baselib.R;
import com.wzm.baselib.model.BottomMenuModel;
import com.wzm.baselib.utils.ColorHelper;

import java.util.List;

/**
 * @Description:主界面底部的菜单栏控件
 * @Author: yaochangliang
 * @CreateDate: 2019-12-03 09:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-12-03 09:17
 * @UpdateRemark: 更新说明
 */

public class BottomMenuLayout extends LinearLayout {
    Context context;
    LayoutInflater inflater;
    //底部菜单的数据源
    List<BottomMenuModel> menuModels;
    //选中时候的文字颜色
    int selectedTextColor;
    //没有选中时候文字的颜色
    int unSelectedTextColor;

    int defaultSelectPosition = 0;

    LinearLayout ll_parent_container;

    //是否开启点击动画，默认关闭
    private boolean isOpenAnim=false;

    public BottomMenuLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BottomMenuLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BottomMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //默认值
        selectedTextColor = ContextCompat.getColor(context, R.color.appThemeColor);
        unSelectedTextColor = ContextCompat.getColor(context, R.color.app_main_gray_text);
    }

    private void initView() {


        View view = inflater.inflate(R.layout.layout_bottom_menu, this, false);
        ll_parent_container = view.findViewById(R.id.ll_container);
        if (menuModels != null) {
            for (int i = 0; i < menuModels.size(); i++) {
                BottomMenuModel bottomMenuModel = menuModels.get(i);
                View childView = inflater.inflate(R.layout.item_layout_bottom_menu, null);
                ImageView iv_menu = childView.findViewById(R.id.iv_menu);
                TextView tv_name = childView.findViewById(R.id.tv_name);
                LinearLayout ll_child_container = childView.findViewById(R.id.ll_child_container);
                ll_child_container.setTag(i);
                tv_name.setText(bottomMenuModel.getText());

                if (defaultSelectPosition == i) {
                    //判断用的哪种构造方法
                    tv_name.setTextColor(bottomMenuModel.getSelectedTextColor() == 0 ? selectedTextColor : bottomMenuModel.getSelectedTextColor());
                    if (bottomMenuModel.getChunSeImage() == 0) {
                        iv_menu.setImageResource(bottomMenuModel.getSelectedImage());
                    } else {
                        ColorHelper.drawColor(context, iv_menu, bottomMenuModel.getChunSeImage(), selectedTextColor);
                    }

                } else {
                    //判断用的哪种构造方法
                    tv_name.setTextColor(bottomMenuModel.getUnSelectedTextColor() == 0 ? unSelectedTextColor : bottomMenuModel.getUnSelectedTextColor());
                    if (bottomMenuModel.getChunSeImage() == 0) {
                        iv_menu.setImageResource(bottomMenuModel.getUnSelectedImage());
                    } else {
                        ColorHelper.drawColor(context, iv_menu, bottomMenuModel.getChunSeImage(), unSelectedTextColor);
                    }

                }

                LayoutParams params = new LayoutParams(0, dip2px(context, 52), 1);
                ll_child_container.setLayoutParams(params);
                ll_parent_container.addView(childView);

                ll_child_container.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int clickPosition = (Integer) v.getTag();
                        if (listener != null) {
                            listener.onClick(bottomMenuModel, clickPosition);
                        }
                        refreshOtherView(clickPosition);

                        if (fragments != null && !fragments.isEmpty()) {
                            showFragment(fragments.get(clickPosition));
                        }

                        //判断如果有动画，就展示动画
                        if(isOpenAnim()){
                            ObjectAnimator.ofFloat(iv_menu, "scaleX",1f,1.3f,1f)
                                    .setDuration(350)
                                    .start();

                            ObjectAnimator.ofFloat(iv_menu, "scaleY",1f,1.3f,1f)
                                    .setDuration(350)
                                    .start();
                        }

                    }
                });


            }
        }
        addView(view);
        if (fragments != null && !fragments.isEmpty()) {
            showFragment(fragments.get(defaultSelectPosition));
        }
    }

    List<Fragment> fragments;
    AppCompatActivity activity;
    int containerId;
    private Fragment currentFragment;

    /**
     * setFragmentData调用的时候必须在setBottomData方法之前，当然不是必须调setFragmentData
     *
     * @param containerId
     * @param fragments
     * @param activity
     */
    public void setFragmentData(int containerId, List<Fragment> fragments, AppCompatActivity activity) {
        this.containerId = containerId;
        this.fragments = fragments;
        this.activity = activity;

    }

    //底部菜单的数据源
    public void setBottomData(List<BottomMenuModel> menuModels, int selectedTextColor, int unSelectedTextColor) {
        this.menuModels = menuModels;
        this.selectedTextColor = selectedTextColor;
        this.unSelectedTextColor = unSelectedTextColor;
        initView();
    }

    //底部菜单的数据源
    public void setBottomData(List<BottomMenuModel> menuModels) {
        this.menuModels = menuModels;
        initView();
    }


    public int getDefaultSelectPosition() {
        return defaultSelectPosition;
    }

    public void setDefaultSelectPosition(int defaultSelectPosition) {
        this.defaultSelectPosition = defaultSelectPosition;
    }

    /**
     * 06.     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 07.
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    OnBottomMenuClicklistener listener;

    public void setOnBottomMenuClicklistener(OnBottomMenuClicklistener l) {
        listener = l;
    }

    public interface OnBottomMenuClicklistener {
        void onClick(BottomMenuModel menuModel, int position);
    }

    private void refreshOtherView(int clickPosition) {
        for (int i = 0; i < ll_parent_container.getChildCount(); i++) {
            BottomMenuModel bottomMenuModel = menuModels.get(i);
            View childView = ll_parent_container.getChildAt(i);
            ImageView iv_menu = childView.findViewById(R.id.iv_menu);
            TextView tv_name = childView.findViewById(R.id.tv_name);
            if (clickPosition == i) {
                //判断用的哪种构造方法
                tv_name.setTextColor(bottomMenuModel.getSelectedTextColor() == 0 ? selectedTextColor : bottomMenuModel.getSelectedTextColor());
                if (bottomMenuModel.getChunSeImage() == 0) {
                    iv_menu.setImageResource(bottomMenuModel.getSelectedImage());
                } else {
                    ColorHelper.drawColor(context, iv_menu, bottomMenuModel.getChunSeImage(), selectedTextColor);
                }

            } else {
                tv_name.setTextColor(bottomMenuModel.getUnSelectedTextColor() == 0 ? unSelectedTextColor : bottomMenuModel.getUnSelectedTextColor());
                //判断用的哪种构造方法
                if (bottomMenuModel.getChunSeImage() == 0) {
                    iv_menu.setImageResource(bottomMenuModel.getUnSelectedImage());
                } else {
                    ColorHelper.drawColor(context, iv_menu, bottomMenuModel.getChunSeImage(), unSelectedTextColor);
                }

            }

        }
    }


    private void showFragment(Fragment fragment) {
        if (activity != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(containerId, fragment);
            }
            if (currentFragment != null && currentFragment != fragment) {
                transaction.hide(currentFragment);
            }
            transaction.commit();
            currentFragment = fragment;
        }

    }

    public boolean isOpenAnim() {
        return isOpenAnim;
    }

    public void setOpenAnim(boolean openAnim) {
        isOpenAnim = openAnim;
    }
}
