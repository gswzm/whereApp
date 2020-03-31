package com.wzm.baselib.bindadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseBindingAdapter
        <B extends ViewDataBinding, T> extends RecyclerView.Adapter<BaseBindViewHolder<B>> {
    /**
     * 适配器集合数据
     */
    protected List<T> data;
    private OnItemCommonClickListener commonClickListener;

    /**
     * 初始化集合
     */
    public BaseBindingAdapter() {
        this.data = new ArrayList<>();
    }

    public void setCommonClickListener(OnItemCommonClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
    }

    /**
     * 返回集合数据
     *
     * @return
     */
    public List<T> getList() {
        return data;
    }

    /**
     * 设置集合数据
     * 下拉刷新、第一次加载数据时调用此方法
     *
     * @param dataList
     */
    public void setList(List<T> dataList) {
        if (dataList == null) {
            return;
        }
        data.clear();
        data.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 设置集合数据
     * 上拉加载时调用此方法
     *
     * @param dataList
     */
    public void addList(List<T> dataList) {
        if (dataList == null) {
            return;
        }
        data.addAll(dataList);
        notifyDataSetChanged();
    }

    //泛型第二个参数代表viewHolder
    @NonNull
    @Override

    public BaseBindViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayout(viewType), parent, false);
        BaseBindViewHolder<B> viewHolder = new BaseBindViewHolder(binding.getRoot());
        viewHolder.setmDataBinding(binding);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseBindViewHolder<B> holder, int position) {
        onBindItem(holder.getmDataBinding(), position, data.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (commonClickListener != null) {
                commonClickListener.onItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 抽象方法 传布局文件id
     *
     * @param viewType
     * @return
     */
    protected abstract int getItemLayout(int viewType);

    /**
     * 返回 databinding对象
     *
     * @param binding  当前adapter 对应 binding 对象
     * @param position 当前item 位置
     * @param t        当前item对应实体
     */
    protected abstract void onBindItem(B binding, int position, T t);

    /*item点击事件**/
    public interface OnItemCommonClickListener {
        void onItemClickListener(int position);
    }

}
