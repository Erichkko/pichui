package com.pichui.news.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pi.core.uikit.recycleview.RecyclerViewClickListener;
import com.pi.core.uikit.recycleview.UniversalItemDecoration;
import com.pi.core.uikit.recycleview.galleryrecyclerview.GalleryRecyclerView;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.test.TestMainAdpter;
import com.pichui.news.ui.adapter.test.TestMainModel;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.uitil.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestMainActivity extends BaseActivity {
    @BindView(R.id.rv)
    public RecyclerView rv;
    List<TestMainModel> mData = new ArrayList<>();
    private TestMainAdpter mAdapter;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_test_grid;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public void initData() {

    }

    public void initView() {
        rv.setLayoutManager(new GridLayoutManager(this,1));
        rv.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {

                ColorDecoration decoration = new ColorDecoration();
                //你的逻辑设置分割线
//                decoration.bottom   = 2;//下分割
//                decoration.right  = 2;// 右分割
//                decoration.left   = 2; //左分割
                decoration.top     = 2;//上分割线
                decoration.decorationColor =  Color.parseColor("#F8F8F8");
//                分割线颜色
                return decoration;
            }
        });
        structModel();
        mAdapter = new TestMainAdpter(this,mData);
        rv.setAdapter(mAdapter);
        rv.addOnItemTouchListener(new RecyclerViewClickListener(this, rv, new RecyclerViewClickListener.OnItem2ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                DebugLog.e("position = "+position);
                TestMainModel testMainModel = mData.get(position);
                switch (testMainModel.getTitle()){
                    case "Recycleview":
                        intent.setClass(TestMainActivity.this, TestGridActivity.class);
                        startActivity(intent);
                        break;
                     case "GalleryRecycleview":
                        intent.setClass(TestMainActivity.this, GalleryActivity.class);
                        startActivity(intent);
                        break;
                     case "bottomDialog":
                        intent.setClass(TestMainActivity.this, BottomDialogActivity.class);
                        startActivity(intent);
                        break;

                        case "customeView":
                        intent.setClass(TestMainActivity.this, CustomeViewActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        UIUtils.showToast("暂未开放相关功能！");
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
    private void structModel(){
        TestMainModel tm1 = new TestMainModel();
        tm1.set_id(1);
        tm1.setTitle("Recycleview");
        TestMainModel tm2 = new TestMainModel();
        tm2.set_id(2);
        tm2.setTitle("Recycleview2");

        TestMainModel tm3 = new TestMainModel();
        tm3.set_id(3);
        tm3.setTitle("GalleryRecycleview");

        TestMainModel tm4 = new TestMainModel();
        tm4.set_id(4);
        tm4.setTitle("bottomDialog");

        TestMainModel tm5 = new TestMainModel();
        tm5.set_id(5);
        tm5.setTitle("customeView");

        mData.add(tm1);
        mData.add(tm2);
        mData.add(tm3);
        mData.add(tm4);
        mData.add(tm5);
    }
}
