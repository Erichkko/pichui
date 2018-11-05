package com.pichui.news.ui.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pichui.news.R;
import com.pichui.news.ui.adapter.base.BaseCellHolder;
import com.pichui.news.ui.adapter.base.BaseHeaderHolder;
import com.pichui.news.ui.adapter.base.BaseSectionedRecyclerViewAdapter;
import com.pichui.news.ui.adapter.test.TestEntity;
import com.pichui.news.uitil.ListUtils;

import java.util.ArrayList;
import java.util.List;


public class MineAdapter extends BaseSectionedRecyclerViewAdapter<MineHeaderHolder, RecyclerView.ViewHolder, RecyclerView.ViewHolder> {



    private static final int EMPTY_VIEW = 0;
    private static final int ITEM_TYPE_1 = 1;
    private static final int ITEM_TYPE_2 = 2;


    public List<MineMoelEntity> modelList;
    private Context mContext;
    private LayoutInflater mInflater;
    private onClickItemListener onClickItemListener;



    public MineAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<MineMoelEntity> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public void setOnClickItemListener(MineAdapter.onClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @Override
    protected int getSectionCount() {
        return ListUtils.isEmpty(modelList) ? 0 : modelList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = modelList.get(section).getModelList().size();
        return ListUtils.isEmpty(modelList.get(section).getModelList()) ? 0 : count;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected MineHeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new MineHeaderHolder(mInflater.inflate(R.layout.mine_title_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            case ITEM_TYPE_1:
                v = mInflater.inflate(R.layout.mine_base_cell_item, parent, false);
                return new MineBaseCellHolder(v);
            case ITEM_TYPE_2:
                v = mInflater.inflate(R.layout.mine_subtitle_cell_item, parent, false);
                return new MineSubTitleCellHolder(v);
            default:
                break;
        }

        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(MineHeaderHolder holder, int section) {

    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int section, final int position) {
        MineMoelEntity mineMoelEntity = modelList.get(section);
        MineModel mineModel = mineMoelEntity.getModelList().get(position);

        if (holder instanceof MineSubTitleCellHolder){
            ((MineSubTitleCellHolder) holder).tvTitle.setText(mineModel.getTitle());
            ((MineSubTitleCellHolder) holder).tvSubtitle.setText(mineModel.getSubTitle());
        }else if (holder instanceof MineBaseCellHolder){
            ((MineBaseCellHolder) holder).tvTitle.setText(mineModel.getTitle());
        }

        if (position == 0){
            ((MineCellHolder)holder).topLine.setVisibility(View.GONE);
        }else {
            ((MineCellHolder)holder).topLine.setVisibility(View.VISIBLE);
        }

        if ((section+1) == modelList.size() && (position+1) == modelList.get(section).getModelList().size()){
            ((MineCellHolder)holder).bottomLine.setVisibility(View.VISIBLE);
        }else {
            ((MineCellHolder)holder).bottomLine.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onItemClick(section,position);
                }
            }
        });

    }

    @Override
    protected int getSectionItemViewType(int section, int position) {
        MineMoelEntity mineMoelEntity = modelList.get(section);
        MineModel mineModel = mineMoelEntity.getModelList().get(position);
        String itemType = mineModel.getType();
        if ("1".equals(itemType)){
            return ITEM_TYPE_1;
        }else if ("2".equals(itemType)){
            return ITEM_TYPE_2;
        }
        return super.getSectionItemViewType(section, position);
    }
    public interface onClickItemListener{
        void onDownloadFile(int section ,int position);
        void onGotoDetail(int section,int position);
        void onItemClick(int section,int position);
    }
}
