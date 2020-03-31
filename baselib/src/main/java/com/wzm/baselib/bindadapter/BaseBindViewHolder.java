package com.wzm.baselib.bindadapter;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseBindViewHolder<B> extends RecyclerView.ViewHolder {
    public B getmDataBinding() {
        return mDataBinding;
    }

    public void setmDataBinding(B mDataBinding) {
        this.mDataBinding = mDataBinding;
    }

    public BaseBindViewHolder(View itemView) {
        super(itemView);
    }

   private B mDataBinding;

}
