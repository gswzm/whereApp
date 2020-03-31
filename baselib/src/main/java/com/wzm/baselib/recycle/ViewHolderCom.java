package com.wzm.baselib.recycle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 类名：com.wzm.utilslib.wedgit
 * 时间：2018/1/23 9:58
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author wangzm
 */

public class ViewHolderCom extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> mViews;
    private View mConvertView;
    private OnItemCommonClickListener commonClickListener;

    public ViewHolderCom(Context context, View itemView) {
        super(itemView);
        mConvertView = itemView;
        mConvertView.setOnClickListener(this);
        mViews = new SparseArray<>();
    }

    public ViewHolderCom setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolderCom setTypeface(int viewId, Typeface tf) {
        if (getView(viewId) instanceof TextView) {
            TextView tv = getView(viewId);
            tv.setTypeface(tf);
        }
        return this;
    }


    public ViewHolderCom setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public ViewHolderCom textColorId(int id, int colorId) {
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
        return this;
    }

    public ViewHolderCom setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public static ViewHolderCom get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolderCom(context, itemView);
    }

    public static ViewHolderCom createViewHolder(Context context, View itemView) {
        return new ViewHolderCom(context, itemView);
    }

    public static ViewHolderCom createViewHolder(Context context,
                                                 ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new ViewHolderCom(context, itemView);
    }

    /**
     * 通过viewid获取组件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View view) {
        if (commonClickListener != null) {
            commonClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    public interface OnItemCommonClickListener {
        void onItemClickListener(int position);
    }

    public void setCommonClickListener(OnItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
