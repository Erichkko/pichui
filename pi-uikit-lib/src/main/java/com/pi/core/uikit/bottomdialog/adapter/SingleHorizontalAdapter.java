package com.pi.core.uikit.bottomdialog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.core.uikit.bottomdialog.BaseBottomDialog;
import com.pi.core.uikit.bottomdialog.SingleHorizontalBottomDialog;
import com.pi.core.uikit.bottomdialog.WindowUtils;
import com.pi.core.uikit.bottomdialog.listener.OnItemClickListener;
import com.pi.core.uikit.bottomdialog.model.Item;
import com.pi.uikit.R;

import java.util.List;

/**
 * Created by CC on 2018/3/8.
 */

public class SingleHorizontalAdapter extends RecyclerView.Adapter<SingleHorizontalAdapter.SmartViewHolder> {

    private List<Item> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private int paddingleft = 0;

    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public SingleHorizontalAdapter(Context context, List<Item> list) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setPaddingleft(int paddingleft) {
        this.paddingleft = paddingleft;
    }

    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmartViewHolder(mInflater.inflate(R.layout.item_single_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(SmartViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Item item = mList.get(position);
        holder.tvName.setText(item.getName());


        int windowWidth = WindowUtils.getWindowWidth(mContext);
        RecyclerView.LayoutParams linearParams = (RecyclerView.LayoutParams)holder. container
                .getLayoutParams();
            linearParams.width = (windowWidth - dp2px(mContext, 10+ paddingleft))*2 / (2* SingleHorizontalBottomDialog.spanCount + 1);
            holder. container.setLayoutParams(linearParams);

        // 无图模式
        if ((int)item.getIcon() == 0) {
            holder.ivIcon.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.tvName.getLayoutParams();
            params.height = dp2px(mContext, 50);
            params.setMargins(60, 0, 0, 0);
            holder.tvName.setTextSize(20);
            holder.tvName.setLayoutParams(params);
        } else {
            holder.ivIcon.setVisibility(View.VISIBLE);
            holder.ivIcon.setImageResource((int)item.getIcon());
            // 有图模式
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.ivIcon.getLayoutParams();
            holder.tvName.setGravity(Gravity.CENTER);
            params.gravity = Gravity.CENTER;
            holder.tvName.setTextSize(14);
            params.setMargins(20, 0, 20, 20);
            holder.ivIcon.setLayoutParams(params);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(position, item);
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemLongClickListener != null)
                    mOnItemLongClickListener.onItemLongClick(position, item);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class SmartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        LinearLayout container;
        ImageView ivIcon;

        SmartViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            container = (LinearLayout) itemView.findViewById(R.id.container);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }

    public  int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
