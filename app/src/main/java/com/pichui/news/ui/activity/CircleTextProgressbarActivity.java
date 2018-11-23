package com.pichui.news.ui.activity;

import android.graphics.Color;
import android.view.View;

import com.pi.core.uikit.view.progress.CircleTextProgressbar;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CircleTextProgressbarActivity extends BaseActivity {
    /**
     * 默认类型。
     */
    @BindView(R.id.tv_default)
    public CircleTextProgressbar mTvDefault;
    /**
     * 五个字的。
     */
    @BindView(R.id.tv_five_text)
    public CircleTextProgressbar mTvFive;
    /**
     * 圆心点击变色。
     */
    @BindView(R.id.tv_red_circle_color)
    public CircleTextProgressbar mTvCicleColor;

    /**
     * 顺数进度条。
     */
    @BindView(R.id.tv_count)
    public CircleTextProgressbar mTvCount;
    /**
     * 宽进度条。
     */
    @BindView(R.id.tv_five_wide)
    public CircleTextProgressbar mTvWide;
    /**
     * 窄进度条。
     */
    @BindView(R.id.tv_five_narrow)
    public CircleTextProgressbar mTvNarrow;
    /**
     * 红色进度条。
     */
    @BindView(R.id.tv_red_progress)
    public CircleTextProgressbar mTvRedPro;
    /**
     * 红色边框。
     */
    @BindView(R.id.tv_red_frame)
    public CircleTextProgressbar mTvRedFrame;
    /**
     * 红色圆心。
     */
    @BindView(R.id.tv_red_circle)
    public CircleTextProgressbar mTvRedCircle;
    /**
     * 跳过。
     */
    @BindView(R.id.tv_red_skip)
    public CircleTextProgressbar mTvSkip;
    /**
     * 更新进度条文字。
     */
    @BindView(R.id.tv_red_progress_text1)
    public CircleTextProgressbar mTvProgressBar1;
    @BindView(R.id.tv_red_progress_text2)
    public CircleTextProgressbar mTvProgressBar2;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_circletextprogressbar;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        mTvCount.setProgressType(CircleTextProgressbar.ProgressType.COUNT);

        mTvWide.setProgressLineWidth(30);//写入宽度。
        mTvWide.setTimeMillis(3500);// 把倒计时时间改长一点。

        mTvNarrow.setProgressLineWidth(3);// 写入宽度。

        mTvRedPro.setProgressColor(Color.RED);

        mTvRedFrame.setOutLineColor(Color.RED);

        mTvRedCircle.setInCircleColor(Color.RED);

        mTvProgressBar1.setCountdownProgressListener(1, progressListener);
        mTvProgressBar1.setProgressType(CircleTextProgressbar.ProgressType.COUNT);

        mTvProgressBar2.setCountdownProgressListener(2, progressListener);

        mTvSkip.setOutLineColor(Color.TRANSPARENT);
        mTvSkip.setInCircleColor(Color.parseColor("#AAC6C6C6"));
        mTvSkip.setProgressColor(Color.DKGRAY);
        mTvSkip.setProgressLineWidth(3);
    }

    @Override
    public void initData() {

    }
    private CircleTextProgressbar.OnCountdownProgressListener progressListener = new CircleTextProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                mTvProgressBar1.setText(progress + "%");
            } else if (what == 2) {
                mTvProgressBar2.setText(progress + "%");
            }
            // 比如在首页，这里可以判断进度，进度到了100或者0的时候，你可以做跳过操作。
        }
    };

    @OnClick(R.id.btn_start)
    public void onClickView(View v){
        switch (v.getId()){
            case R.id.btn_start:
                mTvDefault.reStart();
                mTvFive.reStart();
                mTvCicleColor.reStart();
                mTvCount.reStart();
                mTvWide.reStart();
                mTvNarrow.reStart();
                mTvRedPro.reStart();
                mTvRedFrame.reStart();
                mTvRedCircle.reStart();
                mTvProgressBar1.reStart();
                mTvProgressBar2.reStart();
                mTvSkip.reStart();
                break;
        }
    }
}
