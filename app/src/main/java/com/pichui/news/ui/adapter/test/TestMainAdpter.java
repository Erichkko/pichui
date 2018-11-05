package com.pichui.news.ui.adapter.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pichui.news.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestMainAdpter extends RecyclerView.Adapter<TestMainAdpter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<TestMainModel> mData;
    private Context mContext;

    public TestMainAdpter(Context context, List<TestMainModel> data)
    {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_test_main,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestMainModel model = mData.get(position);
        holder.tv_title.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
