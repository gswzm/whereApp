package com.wzm.baselib.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.wzm.baselib.R;
import com.wzm.baselib.net.ActivityLifeCycleEvent;
import com.wzm.baselib.view.CustomProgressDialog;
import com.wzm.baselib.widget.NavigationBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;

/**
 * @Description:fragment 基类
 * @Author: yaochangliang
 * @CreateDate: 2019-07-24 09:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-12-04 09:47
 * @UpdateRemark: 更新说明 增加databind 基类
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    //退出activity 取消请求
    protected final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject = PublishSubject.create();

    protected String TAG = "default_fragment_tag";
    protected Context context;
    protected long lastClickTime;

    NavigationBar navigationBar;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected T mBinding;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getContentView(), container, false);
        //如果mBinding为空，说明子类的fragment没有在类名后面加泛型继承"，例如：
        //"public class MineFragment extends BaseFragment<FragmentMineBinding>"
        View view = null;
        if (mBinding == null) {
            //那就用普通的infalte方法加载布局
            view = inflater.inflate(getContentView(), container, false);
        } else {
            //用到了databind
            view = mBinding.getRoot();
        }
        unbinder = ButterKnife.bind(this, view);
        TAG = this.getClass().getSimpleName();
        context = getActivity();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;


        navigationBar = view.findViewById(R.id.navigationBar);
        //fragment不需要返回键，
        if (navigationBar != null) {
            navigationBar.setLeftImageViewDrawable(0, null);
        }
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        lifeCycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

    protected abstract int getContentView();

    protected abstract void initData();

    protected Dialog progressDialog; // 公用的等待对话框对象


    /**
     * 吐司提示
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showDialog(String msg, boolean cancelable) {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = CustomProgressDialog.show(context, "", msg, cancelable);
    }


    protected void showDialog(boolean cancelable) {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = CustomProgressDialog.show(context, "", "数据加载中，请稍后...", cancelable);
    }

    protected void showDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = CustomProgressDialog.show(context, "", "数据加载中，请稍后...", true);
    }

    protected void cancelDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    /**
     * 防止控件被重复点击
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
