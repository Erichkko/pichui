package com.pichui.news.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pi.core.uikit.recycleview.RecyclerViewClickListener;
import com.pi.core.uikit.recycleview.UniversalItemDecoration;
import com.pichui.news.R;
import com.pichui.news.ui.activity.MainActivity;
import com.pichui.news.ui.activity.Test1Activity;
import com.pichui.news.ui.adapter.base.SectionedSpanSizeLookup;
import com.pichui.news.ui.adapter.mine.MineAdapter;
import com.pichui.news.ui.adapter.mine.MineModel;
import com.pichui.news.ui.adapter.mine.MineMoelEntity;

import com.pichui.news.ui.base.BaseFragment;
import com.pichui.news.ui.service.DownloadIntentService;
import com.pichui.news.uitil.DebugLog;
import com.pichui.news.uitil.JsonUtils;
import com.pichui.news.uitil.PublicFunction;
import com.pichui.news.uitil.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseFragment {

    private static final int DOWNLOADAPK_ID = 10;
//    @BindView( R.id.tv_download)
//    public TextView tv_download;
//
    @BindView(R.id.rv)
    public RecyclerView rv;
    private MineAdapter mAdapter;
    List<MineMoelEntity> mineMoelEntities;
    @Override
    protected int provideContentViewId() {
        return  R.layout.fragment_mine;
    }



    @Override
    public void initData() {
        DebugLog.e("initData");
    }

    @Override
    public void initView(View rootView) {
//        tv_download.setText("哈哈哈");
        mAdapter = new MineAdapter(mActivity);
        GridLayoutManager manager = new GridLayoutManager(mActivity,1);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));

        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
        mineMoelEntities = stuctData();
        mAdapter.setData(mineMoelEntities);
        mAdapter.setOnClickItemListener(new MineAdapter.onClickItemListener() {
            @Override
            public void onDownloadFile(int section,int position) {

            }

            @Override
            public void onGotoDetail(int section,int position) {

            }

            @Override
            public void onItemClick(int section,int position) {
                MineModel model = mineMoelEntities.get(section).getModelList().get(position);
                DebugLog.e("model title== "+model.getTitle());
                DebugLog.e("model type== "+model.getType());
            }
        });

    }
    private List<MineMoelEntity> stuctData(){
        List<MineMoelEntity> list = new ArrayList<>();
        MineMoelEntity entity1 = new MineMoelEntity();
        ArrayList<MineModel> models1 = new ArrayList<>();
        MineModel model1 = new MineModel();
        model1.setTitle("消息通知");
        model1.setType("1");
        models1.add(model1);
        entity1.setModelList(models1);

        MineMoelEntity entity2 = new MineMoelEntity();
        ArrayList<MineModel> models2 = new ArrayList<>();
        MineModel model2 = new MineModel();
        model2.setTitle("头条商城");
        model2.setSubTitle("邀请好友获得200元现金红包");
        model2.setType("2");
        models2.add(model2);
        entity2.setModelList(models2);

        MineModel model3 = new MineModel();
        model3.setTitle("京东特供");
        model3.setSubTitle("新人领188元红包");
        model3.setType("2");
        models2.add(model3);
        entity2.setModelList(models2);


        MineMoelEntity entity3 = new MineMoelEntity();
        ArrayList<MineModel> models3 = new ArrayList<>();

        MineModel model4 = new MineModel();
        model4.setTitle("我要爆料");

        model4.setType("1");
        models3.add(model4);


        MineModel model5 = new MineModel();
        model5.setTitle("用户反馈");

        model5.setType("1");
        models3.add(model5);


        MineModel model6 = new MineModel();
        model6.setTitle("系统设置");

        model6.setType("1");
        models3.add(model6);

        entity3.setModelList(models3);

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);

        return list;
    }
//    @OnClick({R.id.tv_download,R.id.tv_1})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_download:
//                if (PublicFunction.isServiceRunning(mActivity,DownloadIntentService.class.getName())){
//                    UIUtils.showToast("正在下载...");
//                }
//                String downloadUrl = "/qqmi/aphone_p2p/TencentVideo_V6.0.0.14297_848.apk";
//                Intent intent = new Intent(mActivity, DownloadIntentService.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("download_url", downloadUrl);
//                bundle.putInt("download_id", DOWNLOADAPK_ID);
//                bundle.putString("download_file", downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1));
//                intent.putExtras(bundle);
//                mActivity.startService(intent);
//                break;
//            case R.id.tv_1:
//                Intent intent1 = new Intent(mActivity, Test1Activity.class);
//                mActivity.startActivity(intent1);
//                break;
//        }
//    }

}
