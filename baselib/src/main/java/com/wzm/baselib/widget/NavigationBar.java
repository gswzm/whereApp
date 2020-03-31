package com.wzm.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wzm.baselib.R;


public class NavigationBar extends LinearLayout {

    public FTextView tv_title;
    public LinearLayout ll_back;
    public AlphaImageView iv_left;
    public AlphaTextView tv_right;

    public LinearLayout ll_iv_right;
    public AlphaImageView iv_right;

    //导航栏标题
    private String title;


    //按钮文字属性
    private String rightTvText;
    private int rightIvResource;

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.NavigationBar_title);
            rightIvResource = typedArray.getResourceId(R.styleable.NavigationBar_right_iv_drawable,0);
            rightTvText = typedArray.getString(R.styleable.NavigationBar_right_tv_text);
            typedArray.recycle();
        }
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        View view = LayoutInflater.from(context).inflate( R.layout.navigation_bar, this,false);
        tv_title = view.findViewById(R.id.tv_title);
        ll_back =view.findViewById(R.id.ll_back);
        iv_left=view.findViewById(R.id.iv_left);
        tv_right = view.findViewById(R.id.tv_right);

        ll_iv_right =view.findViewById(R.id.ll_iv_right);
        iv_right=view.findViewById(R.id.iv_right);


        if (!TextUtils.isEmpty(rightTvText)) {
            tv_right.setText(rightTvText);
            tv_right.setVisibility(VISIBLE);
        }
        if (rightIvResource!=0) {
            iv_right.setImageResource(rightIvResource);
            ll_iv_right.setVisibility(VISIBLE);
        }

        //设置文字属性
        tv_title.setText(title);
        addView(view);

    }

    /**
     * 左边的图案以及点击事件的设置
     * @param leftImageViewClickListener
     */
    public void setOnLeftImageViewClickListener(OnClickListener leftImageViewClickListener) {
        ll_back.setOnClickListener(leftImageViewClickListener);
    }
    public void setLeftImageViewDrawable(int resourceId){
        iv_left.setImageResource(resourceId);
    }
    public void setLeftImageViewDrawable(int resourceId, OnClickListener leftImageViewClickListener){
        iv_left.setImageResource(resourceId);
        ll_back.setOnClickListener(leftImageViewClickListener);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
    }



    /**
     * 右边的一系列的按钮团点击事件
     * @param rightTvClickListener
     */

    public void setOnRightTvClickListener(OnClickListener rightTvClickListener) {
        tv_right.setOnClickListener(rightTvClickListener);
    }

    public void setRightTvText(String text){
        tv_right.setText(text);
        tv_right.setVisibility(VISIBLE);
    }

    public void setRightTvText(String text, OnClickListener rightTvClickListener){
        tv_right.setText(text);
        tv_right.setOnClickListener(rightTvClickListener);
        tv_right.setVisibility(VISIBLE);
    }

    //标题栏右边的imageview
    public void setOnRightIvClickListener(OnClickListener rightIvClickListener) {
        iv_right.setOnClickListener(rightIvClickListener);
    }

    public void setRightIvResource(int resourceId){
        iv_right.setImageResource(resourceId);
        ll_iv_right.setVisibility(VISIBLE);
    }

    public void setRightIvResource(int resourceId, OnClickListener rightIvClickListener){
        iv_right.setImageResource(resourceId);
        ll_iv_right.setOnClickListener(rightIvClickListener);
        ll_iv_right.setVisibility(VISIBLE);
    }

    public AlphaTextView getRightTv(){
        return tv_right;
    }

    public LinearLayout getRightIvLayout(){
        return ll_iv_right;
    }


}
