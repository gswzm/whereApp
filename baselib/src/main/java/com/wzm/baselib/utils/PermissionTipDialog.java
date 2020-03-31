package com.wzm.baselib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wzm.baselib.R;


/**
 * 文件描述：申请权限的dialog
 * Created by WangXW on 2019/3/15.
 */

public class PermissionTipDialog extends Dialog {


    private Activity mActivity;
    private TextView contentText;
    private ClickListener mListener;
    private String yesText = "去打开";
    private String noText = "拒绝";
    private boolean isOnlySure = false;

    public void setOnlySure(boolean onlySure) {
        isOnlySure = onlySure;
    }

    public void setYesText(String yesText) {
        this.yesText = yesText;
    }

    public void setNoText(String noText) {
        this.noText = noText;
    }

    public PermissionTipDialog(@NonNull Context context, Activity mActivity) {
        super(context, R.style.dc_dialog);
        this.mActivity = mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_my_tips_item_dialog_base);
        initView();
    }

    private void initView() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();

        if (window != null) {
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
            window.setAttributes(p);
        }
        TextView yes = findViewById(R.id.tv_yes);
        TextView no = findViewById(R.id.tv_no);
        View line = findViewById(R.id.line);
        contentText = findViewById(R.id.text);
        yes.setText(yesText);
        no.setText(noText);

        if (isOnlySure) {
            no.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                if (mListener != null) {
                    mListener.sureListener();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                if (mListener != null) {
                    mListener.cancelListener();
                }
            }
        });
    }

    public void showDialog(String content) {
        show();
        contentText.setText("\u3000\u3000" + content);

    }

    public void setListener(ClickListener mListener) {
        this.mListener = mListener;
    }

    public interface ClickListener {

        void cancelListener();

        void sureListener();
    }


}
