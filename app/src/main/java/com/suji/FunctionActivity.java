package com.suji;

import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.util.AdError;
import com.sigmob.windad.WindAdError;
import com.sigmob.windad.WindAdOptions;
import com.sigmob.windad.WindAds;
import com.sigmob.windad.rewardVideo.WindRewardAdRequest;
import com.sigmob.windad.rewardVideo.WindRewardInfo;
import com.sigmob.windad.rewardVideo.WindRewardVideoAd;
import com.sigmob.windad.rewardVideo.WindRewardVideoAdListener;
import com.suji.adv.ADVAdapter;
import com.suji.adv.ADVEntity;
import com.suji.adv.RecordOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener {
    private WindAds ads;
    private final String sigmobAppKey = "1c58c7e015cae6e5";
    private final String sigmobAppID = "43920";

    private final String tencentAppID = "1207064427";
//    private final String tencentADVID = "5150952326114184";
    private final String tencentADVID = "5131884215344153";

//    private  final String tencentAppID="1207064433";
//    private final String tencentADVID="6120444540470862";

    private WindRewardVideoAd windRewardVideoAd;
    private RewardVideoAD rewardVideoAD;
    private final Context mContext = this;

    private List<ADVEntity> advEntityList;
    private RecordOperation operation;
    private ListView lv;
    private ADVAdapter advAdapter;
    private   String getCurrentFormattedTime() {
        // 获取当前时间
        Date now = new Date();

        // 创建 SimpleDateFormat 对象并定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd-HH:mm:ss");

        // 格式化当前时间并返回
        return sdf.format(now);
    }
    private void showPrivacyPolicyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("隐私政策");
        builder.setMessage(R.string.policy);
        builder.setPositiveButton("关闭", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        lv=findViewById(R.id.lst_ecpm);
        findViewById(R.id.btn_adv).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_policy).setOnClickListener(this);
        findViewById(R.id.btn_adv_t).setOnClickListener(this);
        Log.e("Function", "onCreate: " + SDKStatus.getIntegrationSDKVersion());

        operation=new RecordOperation(mContext);
        InitADVEntityList();
        InitSigmob();
        InitGDT();
    }
    private void InitADVEntityList(){
        advEntityList=operation.query();
        advAdapter=new ADVAdapter(mContext,advEntityList);
        lv.setAdapter(advAdapter);
    }

    public void InitSigmob() {
        WindAds.requestPermission(this);
        ads = WindAds.sharedAds();
        ads.startWithOptions(this, new WindAdOptions(sigmobAppID, sigmobAppKey));
        WindRewardAdRequest request = new WindRewardAdRequest("f9f3af4f9cb", null, null);
        windRewardVideoAd = new WindRewardVideoAd(request);
        windRewardVideoAd.setWindRewardVideoAdListener(new WindRewardVideoAdListener() {

            //仅sigmob渠道有回调，聚合其他平台无次回调
            @Override
            public void onRewardAdPreLoadSuccess(String placementId) {

            }

            //仅sigmob渠道有回调，聚合其他平台无次回调
            @Override
            public void onRewardAdPreLoadFail(String placementId) {

            }

            @Override
            public void onRewardAdLoadSuccess(String placementId) {

            }

            @Override
            public void onRewardAdPlayStart(String placementId) {

            }

            @Override
            public void onRewardAdPlayEnd(String placementId) {

            }

            @Override
            public void onRewardAdClicked(String placementId) {

            }

            //获得奖励回调
            @Override
            public void onRewardAdRewarded(WindRewardInfo windRewardInfo, String placementId) {
                if (windRewardInfo.isReward()) {
                    Toast.makeText(mContext, "Sigmob:激励视频广告完整播放，给予奖励", Toast.LENGTH_SHORT).show();
                }
                windRewardVideoAd.loadAd();
            }


            @Override
            public void onRewardAdClosed(String placementId) {

            }

            /**
             * 加载广告错误回调
             * WindAdError 激励视频错误内容
             * placementId 广告位
             */
            @Override
            public void onRewardAdLoadError(WindAdError windAdError, String placementId) {
                Log.e("load error", "" + windAdError);
                Toast.makeText(mContext, "Sigmob:激励视频广告加载错误", Toast.LENGTH_SHORT).show();
            }

            /**
             * 播放错误回调
             * WindAdError 激励视频错误内容
             * placementId 广告位
             */
            @Override
            public void onRewardAdPlayError(WindAdError windAdError, String placementId) {
                Toast.makeText(mContext, "Sigmmob:激励视频广告播放错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void InitGDT() {
        MultiProcessFlag.setMultiProcess(true);
        GDTAdSdk.initWithoutStart(mContext, tencentAppID); // 该接口不会采集用户信息
        // 调用initWithoutStart后请尽快调用start，否则可能影响广告填充，造成收入下降
        GDTAdSdk.start(new GDTAdSdk.OnStartListener() {
            @Override
            public void onStartSuccess() {
                Log.d("GDTAdSdk", "onStartSuccess: ");
                RewardVideoADListener rewardVideoADListener = new RewardVideoADListener() {
                    @Override
                    public void onADLoad() {
                        Toast.makeText(mContext, "优量汇:Tencent advertisement load success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVideoCached() {

                    }

                    @Override
                    public void onADShow() {

                    }

                    @Override
                    public void onADExpose() {

                    }

                    @Override
                    public void onReward(Map<String, Object> map) {
                        Toast.makeText(mContext, "优量汇:Tencent Advertisement get reward", Toast.LENGTH_SHORT).show();
                        ADVEntity entity=new ADVEntity();
                        Log.d("Tencent", "reward: "+ rewardVideoAD.getECPM()+" level:"+rewardVideoAD.getECPMLevel());
                        entity.setCpm((float) ((float) rewardVideoAD.getECPM()/1000.0));
                        entity.setPlatform("Tencent");
                        entity.setTime(getCurrentFormattedTime());
                        advEntityList.add(0,entity);
                        advAdapter.updateData(advEntityList);
                    }

                    @Override
                    public void onADClick() {

                    }

                    @Override
                    public void onVideoComplete() {
                        rewardVideoAD.loadAD();
                    }

                    @Override
                    public void onADClose() {
                        rewardVideoAD.loadAD();
                    }

                    @Override
                    public void onError(AdError adError) {
                        Log.e("Tencent ADV", "onError: " + adError.getErrorMsg() + adError.getErrorCode());
                        Toast.makeText(mContext, "优量汇:优量汇广告加载错误，请重试", Toast.LENGTH_SHORT).show();
                    }
                };
                rewardVideoAD = new RewardVideoAD(mContext, tencentADVID, rewardVideoADListener); // 有声播放
                rewardVideoAD.loadAD();
            }

            @Override
            public void onStartFailed(Exception e) {
                Log.e("优量汇:gdt onStartFailed:", e.toString());
            }
        });
        if (windRewardVideoAd != null) {
            windRewardVideoAd.loadAd();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_adv:
                try {
                    /**
                     * 收到onRewardAdLoadSuccess回调代表广告已ready
                     */
                    if (windRewardVideoAd != null && windRewardVideoAd.isReady()) {
                        HashMap showOption = new HashMap();
                        //广告播放
                        windRewardVideoAd.show(showOption);
                    } else {
                        Log.e("not ready", "adv not ready");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_policy:
                showPrivacyPolicyDialog();
                break;
            case R.id.btn_adv_t:
                if (rewardVideoAD.isValid()) {
                    rewardVideoAD.showAD();
                } else {
                    Toast.makeText(this, "优量汇:Tencent advertisement is not already,please try again", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}