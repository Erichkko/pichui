package com.pichui.news.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pi.core.uikit.recycleview.RecyclerViewClickListener;
import com.pi.core.uikit.recycleview.UniversalItemDecoration;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.test.TestGridAdpter;
import com.pichui.news.ui.adapter.test.TestGridModel;
import com.pichui.news.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestGridActivity extends BaseActivity {
    @BindView(R.id.rv)
    public RecyclerView rv;
    List<TestGridModel> mData = new ArrayList<>();
    private TestGridAdpter mAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_test_grid;
    }

    public void initData() {

    }
    public void initView() {
        rv.setLayoutManager(new GridLayoutManager(this,4));
        rv.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {

                ColorDecoration decoration = new ColorDecoration();
                //你的逻辑设置分割线
                decoration.bottom   = 2;//下分割
                decoration.right  = 2;// 右分割
//                decoration.left   = 2; //左分割
//                decoration.top     = 2;//上分割线
                decoration.decorationColor =  Color.parseColor("#F8F8F8");
//                分割线颜色
                return decoration;
            }
        });
        structModel();
        mAdapter = new TestGridAdpter(this,mData);
        rv.setAdapter(mAdapter);
        rv.addOnItemTouchListener(new RecyclerViewClickListener(this, rv, new RecyclerViewClickListener.OnItem2ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DebugLog.e("position = "+position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
    private void structModel(){
        TestGridModel psm1 = new TestGridModel();
        psm1.set_id(1);
        psm1.setTitle("报事报修");
        psm1.setIcon("");
        psm1.setIconId(R.drawable.icon_bsbx);
        mData.add(psm1);

        TestGridModel psm2 = new TestGridModel();
        psm2.set_id(2);
        psm2.setTitle("投诉建议");
        psm2.setIcon("");
        psm2.setIconId(R.drawable.icon_tsjy);
        mData.add(psm2);

        TestGridModel psm3 = new TestGridModel();
        psm3.set_id(3);
        psm3.setTitle("物业账单");
        psm3.setIcon("");
        psm3.setIconId(R.drawable.icon_wyjf);
        mData.add(psm3);

        TestGridModel psm4 = new TestGridModel();
        psm4.set_id(4);
        psm4.setTitle("访客通行");
        psm4.setIcon("");
        psm4.setIconId(R.drawable.icon_yjkm);
        mData.add(psm4);

        TestGridModel psm5 = new TestGridModel();
        psm5.set_id(5);
        psm5.setTitle("物业活动");
        psm5.setIcon("");
        psm5.setIconId(R.drawable.icon_sqhd);
        mData.add(psm5);

        TestGridModel psm6 = new TestGridModel();
        psm6.set_id(6);
        psm6.setTitle("便民信息");
        psm6.setIcon("");
        psm6.setIconId(R.drawable.icon_tsjy);
        mData.add(psm6);

        TestGridModel psm7 = new TestGridModel();
        psm7.set_id(7);
        psm7.setTitle("问卷调查");
        psm7.setIcon("");
        psm7.setIconId(R.drawable.icon_wjdc);
        mData.add(psm7);

        TestGridModel psm8 = new TestGridModel();
        psm8.set_id(8);
        psm8.setTitle("物业通知");
        psm8.setIcon("");
        psm8.setIconId(R.drawable.icon_tcjf);
        mData.add(psm8);

        TestGridModel psm9 = new TestGridModel();
        psm9.set_id(9);
        psm9.setTitle("跳蚤市场");
        psm9.setIcon("");
        psm9.setIconId(R.drawable.icon_tssc);
        mData.add(psm9);

        TestGridModel psm10 = new TestGridModel();
        psm10.set_id(1);
        psm10.setTitle("黄页店铺");
        psm10.setIcon("");
        psm10.setIconId(R.drawable.icon_hydp);
        mData.add(psm10);

    }
}
