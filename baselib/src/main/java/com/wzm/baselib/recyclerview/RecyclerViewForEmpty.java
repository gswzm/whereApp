package com.wzm.baselib.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangzm on 2019/2/21
 */
public class RecyclerViewForEmpty extends RecyclerView {

    private View mEmptyView;

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();

    public RecyclerViewForEmpty(Context context) {
        super(context);
    }

    public RecyclerViewForEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewForEmpty(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter adapter = getAdapter();
            if (adapter != null && mEmptyView != null) {
                if (adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(VISIBLE);
                    RecyclerViewForEmpty.this.setVisibility(GONE);
                } else {
                    mEmptyView.setVisibility(GONE);
                    RecyclerViewForEmpty.this.setVisibility(VISIBLE);
                }
            }
        }
    }


    public void setEmptyView(View emptyView) {
        //把空数据显示布局加入recyclerview父控件
        mEmptyView = emptyView;
        mDataObserver.onChanged();

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
        }
        mDataObserver.onChanged();
    }
}
