package com.pichui.news.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pi.core.uikit.bottomdialog.BaseBottomDialog;
import com.pi.core.uikit.bottomdialog.BottomDialog;
import com.pi.core.uikit.bottomdialog.BottomDialog1;
import com.pi.core.uikit.bottomdialog.BottomTipDialog;
import com.pi.core.uikit.bottomdialog.SingleHorizontalBottomDialog;
import com.pi.core.uikit.bottomdialog.ViewPagerBottomDialog;
import com.pi.core.uikit.bottomdialog.WindowUtils;
import com.pi.core.uikit.bottomdialog.listener.OutsideClickListener;
import com.pi.core.uikit.bottomdialog.model.Item;
import com.pi.core.uikit.recycleview.RecyclerViewClickListener;
import com.pi.core.uikit.recycleview.UniversalItemDecoration;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.test.TestMainAdpter;
import com.pichui.news.ui.adapter.test.TestMainModel;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.uitil.JsonUtils;
import com.pichui.news.uitil.ShortcutBadgerUtil;
import com.pichui.news.uitil.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomDialogActivity extends BaseActivity {
    @BindView(R.id.rv)
    public RecyclerView rv;
    List<TestMainModel> mData = new ArrayList<>();
    private TestMainAdpter mAdapter;
    private BottomDialog bottomDialog;
    private BottomDialog1 bottomDialog1;
    private BottomDialog1 bottomDialog2;
    private BottomTipDialog bottomDialog4;
    private SingleHorizontalBottomDialog bottomDialog5;
    private SingleHorizontalBottomDialog bottomDialog6;
    private ViewPagerBottomDialog bottomDialog7;


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
                    case "bottom1":
                        showDilaog();
                        break;
                    case "bottom2":
                         showDilaog1();
                        break;
                    case "bottom3":
                        showDilaog2();
                        break;
                        case "bottomTip":
                        showDilaog4();
                        break;
                        case "bottomSingleHorizontal":
                        showDilaog5();
                            break;
                        case "bottomSingleHorizontal2":
                        showDilaog6();
                        break;
                        case "bottomViewpager":
                        showDilaog7();
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
        initShareDialog();
        initShareDialog1();
        initShareDialog2();
        initShareDialog4();
        initShareDialog5();
        initShareDialog6();
        initShareDialog7();
    }
    private void structModel(){
        TestMainModel tm1 = new TestMainModel();
        tm1.set_id(1);
        tm1.setTitle("bottom1");
        TestMainModel tm2 = new TestMainModel();
        tm2.set_id(2);
        tm2.setTitle("bottom2");

        TestMainModel tm3 = new TestMainModel();
        tm3.set_id(3);
        tm3.setTitle("bottom3");

        TestMainModel tm4 = new TestMainModel();
        tm4.set_id(4);
        tm4.setTitle("bottomTip");

        TestMainModel tm5 = new TestMainModel();
        tm5.set_id(5);
        tm5.setTitle("bottomSingleHorizontal");

        TestMainModel tm6 = new TestMainModel();
        tm6.set_id(6);
        tm6.setTitle("bottomSingleHorizontal2");

        TestMainModel tm7 = new TestMainModel();
        tm7.set_id(7);
        tm7.setTitle("bottomViewpager");

        mData.add(tm1);
        mData.add(tm2);
        mData.add(tm3);
        mData.add(tm4);
        mData.add(tm5);
        mData.add(tm6);
        mData.add(tm7);
    }


    private void initShareDialog4(){
        bottomDialog4 = new BottomTipDialog().init(this);

    }
    private void initShareDialog5(){
        final List<Item> items = JsonUtils.jsonToShareData(this,"share");

        bottomDialog5 =  new SingleHorizontalBottomDialog().init(this)
                .setDimAmount(0.9f)
                .titleVisible(false)
//                .setSpanCount(3)
                .items(items).setPadding(30);


    }

    private void initShareDialog6(){
        final List<Item> items = JsonUtils.jsonToShareData(this,"share");

        bottomDialog6 =  new SingleHorizontalBottomDialog().init(this)
                .setDimAmount(0.9f)
                .titleVisible(true)
                .cancelVisible(false).setBackgroundRes(R.drawable.shape_cc0000)
                .titleColor(R.color.text_grey)
//                .setSpanCount(3)
                .items(items);


    }

    private void initShareDialog7(){
        final List<Item> items = JsonUtils.jsonToShareData(this,"share");
//
        bottomDialog7 =  new ViewPagerBottomDialog().init(this)
                .setDimAmount(0.9f)
                .titleVisible(true)
                .items(items)
                .setPadding(30);


    }
    private void showDilaog4(){
        bottomDialog4.show("12233");
//        bottomDialog4.setTip("12233");
    }
    private void initShareDialog(){
        bottomDialog = new BottomDialog().init(this)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
//                        initView(v);
                    }
                })
                .setLayoutRes(R.layout.dialog_layout)
//                .setLayoutRes(R.layout.pi_grid_dialog_layout)
                .setDimAmount(0.9f)
                .setTag("BottomDialog");


    }
    private void showDilaog(){
        ShortcutBadgerUtil.clearBadgeCount(this);
        bottomDialog.show();
    }


    private void initShareDialog1(){
        final List<Item> items = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            items.add(new Item("列表项" + i));
        }
        bottomDialog1 =  new BottomDialog1().init(this)
                                            .setDimAmount(0.9f)
                                            .items(items);



    }
    private void showDilaog1(){
        bottomDialog1.show();
    }

    private void initShareDialog2(){
        final List<Item> items = JsonUtils.jsonToShareData(this,"share");

        bottomDialog2 =  new BottomDialog1().init(this)
                                            .items(items)
                                            .cancelVisible(true)
                                            .titleVisible(false)
                                            .cancelableOutside(true)
                                            .setSpanCount(4)
                                            .onOutsideClick(new OutsideClickListener() {
                                                @Override
                                                public void outsideClick(boolean isOutside, BaseBottomDialog dialog) {
                                                    Toast.makeText(BottomDialogActivity.this, "点击了外部 " + isOutside, Toast.LENGTH_SHORT).show();
                                                }
                                            })
//                                            .itemOrientation(LinearLayout.VERTICAL)
                                            .recyclerViewOrientation(BottomDialog1.ORIENTATION_GRID);
//                                            .dialogHeight(400);


    }
    private void showDilaog2(){
        bottomDialog2.show();
    }

    private void showDilaog5(){
        bottomDialog5.show();
    }
    private void showDilaog6(){
        bottomDialog6.show();
    }
    private void showDilaog7(){
        bottomDialog7.show();
    }
}
