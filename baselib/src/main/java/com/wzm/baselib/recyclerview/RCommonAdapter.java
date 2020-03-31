package com.wzm.baselib.recyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

public abstract class RCommonAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    //item点击事件
    protected OnItemClickListener mOnItemClickListener;
    //item长按事件
    protected OnItemLongClickListener mOnItemLongClickListener;
    private boolean isReClick = true;
    private Timer mTimer;
    private int timerDelay = 400;

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final T holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {//item点击事件
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null && isReClick) {
                    isReClick = false;
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                    if (mTimer != null) {
                        mTimer.cancel();
                    }
                    mTimer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            isReClick = true;
                        }
                    };
                    mTimer.schedule(timerTask, timerDelay);
                }
            }
        });
        //item长按事件
        holder.itemView.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
            }
            return true;
        });
    }

    @Override
    public abstract int getItemCount();


    /**
     * item中子view的点击事件（回调）
     */
    public interface OnViewClickListener {
        /**
         * @param position item position
         * @param viewtype 点击的view的类型，调用时根据不同的view传入不同的值加以区分
         */
        void onViewClick(int position, int viewtype);
    }

    /**
     * item点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * item长按事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    /**
     * 设置item点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setDelay(int delay) {
        timerDelay = delay;
    }

}
