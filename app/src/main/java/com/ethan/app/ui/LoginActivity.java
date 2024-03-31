package com.ethan.app.ui;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alicom.fusion.auth.AlicomFusionAuthCallBack;
import com.alicom.fusion.auth.AlicomFusionAuthUICallBack;
import com.alicom.fusion.auth.AlicomFusionBusiness;
import com.alicom.fusion.auth.AlicomFusionConstant;
import com.alicom.fusion.auth.HalfWayVerifyResult;
import com.alicom.fusion.auth.error.AlicomFusionEvent;
import com.alicom.fusion.auth.numberauth.FusionNumberAuthModel;
import com.alicom.fusion.auth.smsauth.AlicomFusionInputView;
import com.alicom.fusion.auth.smsauth.AlicomFusionVerifyCodeView;
import com.alicom.fusion.auth.token.AlicomFusionAuthToken;
import com.alicom.fusion.auth.upsms.AlicomFusionUpSMSView;
import com.ethan.app.MainActivity;
import com.ethan.app.R;
import com.ethan.app.util.Constant;
import com.ethan.app.util.GlobalInfoManager;
import com.ethan.app.util.TokenActionFactory;
import com.ethan.app.util.VerifyTokenResult;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.CustomInterface;
import com.nirvana.tools.core.ExecutorManager;

import java.util.concurrent.CountDownLatch;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private AlicomFusionBusiness mAlicomFusionBusiness;
    private AlicomFusionAuthCallBack mAlicomFusionAuthCallBack;
    private volatile int  sum=0;

    private volatile boolean verifySuccess=false;
    private Button loginTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verifySuccess=false;
        loginTest = findViewById(R.id.login_test);
        if (!TextUtils.isEmpty(GlobalInfoManager.getInstance().getUserInfo())) {
            loginTest.setText(GlobalInfoManager.getInstance().getUserInfo().substring(0,4)+"****"+GlobalInfoManager.getInstance().getUserInfo().substring(7));
        } else {
            loginTest.setText("登录/注册");
        }

        loginTest.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(GlobalInfoManager.getInstance().getUserInfo())) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                if (!TextUtils.isEmpty(GlobalInfoManager.getInstance().getToken())) {
                    if(verifySuccess){
                        mAlicomFusionBusiness.startSceneWithTemplateId(this, "100001",uiCallBack);
                    }else {
                        Toast.makeText(GlobalInfoManager.getInstance().getContext(), "初始化未完成,请稍候", Toast.LENGTH_SHORT).show();
                        mAlicomFusionBusiness.continueSceneWithTemplateId("100001", false);
                    }
                } else {
                    Toast.makeText(GlobalInfoManager.getInstance().getContext(), "正在获取token，请稍后", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TokenActionFactory.getToken(GlobalInfoManager.getInstance().getContext());
                        }
                    }).start();
                }
            }
        });
        if (TextUtils.isEmpty(GlobalInfoManager.getInstance().getUserInfo())){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    initAlicomFusionSdk();
                }
            }.start();
        }
    }

    private void initAlicomFusionSdk() {
        AlicomFusionBusiness.useSDKSupplyUMSDK(true,"ymeng");
        mAlicomFusionBusiness = new AlicomFusionBusiness();
        sum=0;
        AlicomFusionAuthToken token=new AlicomFusionAuthToken();
        token.setAuthToken(GlobalInfoManager.getInstance().getToken());
        mAlicomFusionBusiness.initWithToken(GlobalInfoManager.getInstance().getContext(), Constant.SCHEME_CODE,token);
        mAlicomFusionAuthCallBack = new AlicomFusionAuthCallBack() {
            @Override
            public AlicomFusionAuthToken onSDKTokenUpdate() {
                Log.d(TAG, "AlicomFusionAuthCallBack---onSDKTokenUpdate");
                AlicomFusionAuthToken token=new AlicomFusionAuthToken();
                CountDownLatch latch=new CountDownLatch(1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TokenActionFactory.getToken(GlobalInfoManager.getInstance().getContext());
                        latch.countDown();
                    }
                }).start();
                try {
                    latch.await();
                    token.setAuthToken(GlobalInfoManager.getInstance().getToken());
                } catch (InterruptedException e) {
                }
                return token;
            }

            @Override
            public void onSDKTokenAuthSuccess() {
                Log.d(TAG, "AlicomFusionAuthCallBack---onSDKTokenAuthSuccess");
                verifySuccess=true;
            }

            @Override
            public void onSDKTokenAuthFailure(AlicomFusionAuthToken token, AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onSDKTokenAuthFailure "+alicomFusionEvent.getErrorCode() +"  "+alicomFusionEvent.getErrorMsg());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        TokenActionFactory.getToken(GlobalInfoManager.getInstance().getContext());
                        AlicomFusionAuthToken authToken=new AlicomFusionAuthToken();
                        authToken.setAuthToken(GlobalInfoManager.getInstance().getToken());
                        mAlicomFusionBusiness.updateToken(authToken);
                    }
                }).start();
            }

            @Override
            public void onVerifySuccess(String token, String s1, AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onVerifySuccess  " +token);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VerifyTokenResult verifyTokenResult = TokenActionFactory.verifyToken(GlobalInfoManager.getInstance().getContext(), token);
                        updateBusiness(verifyTokenResult,s1);
                    }
                }).start();
            }

            @Override
            public void onHalfWayVerifySuccess(String nodeName, String maskToken, AlicomFusionEvent alicomFusionEvent, HalfWayVerifyResult halfWayVerifyResult) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onHalfWayVerifySuccess  "+maskToken);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VerifyTokenResult verifyTokenResult = TokenActionFactory.verifyToken(GlobalInfoManager.getInstance().getContext(), maskToken);
                        updateBusinessHalfWay(verifyTokenResult,halfWayVerifyResult,nodeName);
                    }
                }).start();
            }

            @Override
            public void onVerifyFailed(AlicomFusionEvent alicomFusionEvent, String s) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onVerifyFailed "+alicomFusionEvent.getErrorCode()+"   "+alicomFusionEvent.getErrorMsg());
                mAlicomFusionBusiness.continueSceneWithTemplateId("100001",false);
            }

            @Override
            public void onTemplateFinish(AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onTemplateFinish  "+alicomFusionEvent.getErrorCode()+"  "+alicomFusionEvent.getErrorMsg());
                sum=0;
                mAlicomFusionBusiness.stopSceneWithTemplateId("100001");
            }

            @Override
            public void onAuthEvent(AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onAuthEvent"+alicomFusionEvent.getErrorCode());
            }

            @Override
            public String onGetPhoneNumberForVerification(String s, AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onGetPhoneNumberForVerification");
                return GlobalInfoManager.getInstance().getUserInfo();
            }

            @Override
            public void onVerifyInterrupt(AlicomFusionEvent alicomFusionEvent) {
                Log.d(TAG, "AlicomFusionAuthCallBack---onVerifyInterrupt"+alicomFusionEvent.toString());
            }
        };
        mAlicomFusionBusiness.setAlicomFusionAuthCallBack(mAlicomFusionAuthCallBack);

    }

    private void updateBusinessHalfWay(VerifyTokenResult verifyTokenResult,HalfWayVerifyResult verifyResult,String nodeName){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(verifyTokenResult!=null&&verifyTokenResult.isSuccess()){
                    if ("PASS".equals(verifyTokenResult.getData().getVerifyResult())) {
                        Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验通过",Toast.LENGTH_SHORT).show();
                        verifyResult.verifyResult(true);
                        GlobalInfoManager.getInstance().setUserInfo(verifyTokenResult.getData().getPhoneNumber());
                    }else {
                        Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验未通过",Toast.LENGTH_SHORT).show();
                        if(nodeName.equals(AlicomFusionConstant.ALICOMFUSIONAUTH_SMSAUTHNODENAME)&&sum<3){
                            sum++;
                        }else {
                            verifyResult.verifyResult(false);
                            sum=0;
                        }
                    }
                }else {
                    Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验未通过",Toast.LENGTH_SHORT).show();
                    if(nodeName.equals(AlicomFusionConstant.ALICOMFUSIONAUTH_SMSAUTHNODENAME)&&sum<3){
                        sum++;
                    }else {
                        verifyResult.verifyResult(false);
                        sum=0;
                    }
                }
            }
        });

    }

    private void updateBusiness(VerifyTokenResult verifyTokenResult,String nodeNmae){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(verifyTokenResult!=null&&verifyTokenResult.isSuccess()){
                    if ("PASS".equals(verifyTokenResult.getData().getVerifyResult())) {
                        Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验通过",Toast.LENGTH_SHORT).show();
                        mAlicomFusionBusiness.continueSceneWithTemplateId("100001",true);
                        GlobalInfoManager.getInstance().setUserInfo(verifyTokenResult.getData().getPhoneNumber());
                        updateView();
                    }else {
                        Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验未通过",Toast.LENGTH_SHORT).show();
                        if(nodeNmae.equals(AlicomFusionConstant.ALICOMFUSIONAUTH_SMSAUTHNODENAME)&&sum<3){
                            sum++;
                        }else {
                            sum=0;
                            mAlicomFusionBusiness.continueSceneWithTemplateId("100001",false);
                        }
                    }
                }else {
                    Toast.makeText(GlobalInfoManager.getInstance().getContext(),"校验未通过",Toast.LENGTH_SHORT).show();
                    if(nodeNmae.equals(AlicomFusionConstant.ALICOMFUSIONAUTH_SMSAUTHNODENAME)&&sum<3){
                        sum++;
                    }else {
                        sum=0;
                        mAlicomFusionBusiness.continueSceneWithTemplateId("100001",false);
                    }
                }

            }
        });

    }

    private AlicomFusionAuthUICallBack uiCallBack =new AlicomFusionAuthUICallBack() {
        @Override
        public void onPhoneNumberVerifyUICustomView(String templateId,String nodeId, FusionNumberAuthModel fusionNumberAuthModel) {
            fusionNumberAuthModel.getBuilder()
                    .setPrivacyAlertBefore("请阅读")
                    .setPrivacyAlertEnd("等协议")
                    //单独设置授权页协议文本颜色
                    .setPrivacyOneColor(Color.RED)
                    .setPrivacyTwoColor(Color.BLUE)
                    .setPrivacyThreeColor(Color.BLUE)
                    .setPrivacyOperatorColor(Color.GREEN)
                    .setCheckBoxMarginTop(10)
                    .setPrivacyAlertOneColor(Color.RED)
                    .setPrivacyAlertTwoColor(Color.BLUE)
                    .setPrivacyAlertThreeColor(Color.GRAY)
                    .setPrivacyAlertOperatorColor(Color.GREEN)
                    /* .setProtocolShakePath("protocol_shake")
                     .setCheckBoxShakePath("protocol_shake")*/
                    //二次弹窗标题及确认按钮使用系统字体
                    /*.setPrivacyAlertTitleTypeface(Typeface.MONOSPACE)
                    .setPrivacyAlertBtnTypeface(Typeface.MONOSPACE)*/
                    /*//二次弹窗使用自定义字体
                    .setPrivacyAlertTitleTypeface(createTypeface(mContext,"globalFont.ttf"))
                    .setPrivacyAlertBtnTypeface(createTypeface(mContext,"testFont.ttf"))
                    .setPrivacyAlertContentTypeface(createTypeface(mContext,"testFont.ttf"))*/
                    //授权页使用系统字体
                    /* .setNavTypeface(Typeface.SANS_SERIF)
                     .setSloganTypeface(Typeface.SERIF)
                     .setLogBtnTypeface(Typeface.MONOSPACE)
                     .setSwitchTypeface(Typeface.MONOSPACE)
                     .setProtocolTypeface(Typeface.MONOSPACE)
                     .setNumberTypeface(Typeface.MONOSPACE)
                     .setPrivacyAlertContentTypeface(Typeface.MONOSPACE)*/
                    //授权页使用自定义字体
                    /*.setNavTypeface(createTypeface(mContext,"globalFont.ttf"))
                    .setSloganTypeface(createTypeface(mContext,"globalFont.ttf"))
                    .setLogBtnTypeface(createTypeface(mContext,"globalFont.ttf"))
                    .setSwitchTypeface(createTypeface(mContext,"testFont.ttf"))
                    .setProtocolTypeface(createTypeface(mContext,"testFont.ttf"))
                    .setNumberTypeface(createTypeface(mContext,"testFont.ttf"))*/
                    //授权页协议名称系统字体
                    /*.setProtocolNameTypeface(Typeface.SANS_SERIF)
                    //授权页协议名称自定义字体
                    //.setProtocolNameTypeface(createTypeface(mContext,"globalFont.ttf"))
                    //授权页协议名称是否添加下划线
                    .protocolNameUseUnderLine(true)
                    //二次弹窗协议名称系统字体
                    //.setPrivacyAlertProtocolNameTypeface(Typeface.SANS_SERIF)
                    //二次弹窗协议名称自定义字体
                    .setPrivacyAlertProtocolNameTypeface(createTypeface(getActivity(),"globalFont.ttf"))
                    //二次弹窗协议名称是否添加下划线
                    .privacyAlertProtocolNameUseUnderLine(true)*/
                    .setPrivacyAlertTitleContent("请注意")
                    .setPrivacyAlertBtnOffsetX(200)
                    .setPrivacyAlertBtnOffsetY(84)
                    .setPrivacyAlertBtnContent("同意")
                    .setStatusBarHidden(false)
                    .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LOW_PROFILE);

            fusionNumberAuthModel.addAuthRegistViewConfig("destory",new AuthRegisterViewConfig.Builder()
                    //两种加载方式都可以
                    .setView(initTestView(500))
                    //.setView(initNumberTextView())
                    //RootViewId有三个参数
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY 导航栏以下部分为body
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_TITLE_BAR 导航栏部分 设置导航栏部分记得setNavHidden和setNavReturnHidden显示后才可看到效果
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_NUMBER 手机号码部分
                    .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                    .setCustomInterface(new CustomInterface() {
                        @Override
                        public void onClick(Context context) {
                            mAlicomFusionBusiness.destory();
                            //必须在setProtocolShakePath之后才能使用
                            //mAlicomFusionBusiness.privacyAnimationStart();
                            //必须在setCheckBoxShakePath之后才能使用
                            //mAlicomFusionBusiness.checkBoxAnimationStart();
                        }
                    }).build());


            fusionNumberAuthModel.addPrivacyAuthRegistViewConfig("privacy_cancel",new AuthRegisterViewConfig.Builder()
                    //两种加载方式都可以
                    .setView(initCancelView(100))
                    //.setView(initNumberTextView())
                    //RootViewId有三个参数
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY 协议区域
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_TITLE_BAR 导航栏部分
                    //AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_NUMBER 二次弹窗为按钮区域
                    .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_NUMBER)
                    .setCustomInterface(new CustomInterface() {
                        @Override
                        public void onClick(Context context) {
                            fusionNumberAuthModel.quitPrivacyPage();
                        }
                    }).build());
        }

        @Override
        public void onSMSCodeVerifyUICustomView(String templateId,String s,boolean isAutoInput, AlicomFusionVerifyCodeView alicomFusionVerifyCodeView) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout rootRl = alicomFusionVerifyCodeView.getRootRl();
                    /*RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT, // 或者MATCH_PARENT等其他宽度值
                            dpToPx(500) // 将所需的dp高度转换为px
                    );
                    rootRl.setLayoutParams(layoutParams);*/

                    AlicomFusionInputView inputView = alicomFusionVerifyCodeView.getInputView();
                    RelativeLayout inputNumberRootRL = inputView.getInputNumberRootRL();
                    View inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.sms_title_content, null);
                    TextView otherLogin = inflate.findViewById(R.id.tv_test);
                    otherLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlicomFusionBusiness.destory();
                        }
                    });
                    RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                    inflate.setLayoutParams(rl);
                    inputNumberRootRL.addView(inflate);
                }
            });

        }

        @Override
        public void onSMSSendVerifyUICustomView(String templateId, String nodeId, AlicomFusionUpSMSView view, String receivePhoneNumber, String verifyCode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout rootRl = view.getRootRl();
                    View inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.up_title_content, null);
                    TextView otherLogin = inflate.findViewById(R.id.tv_test);
                    otherLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlicomFusionBusiness.destory();
                        }
                    });
                    RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rl.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                    inflate.setLayoutParams(rl);
                    rootRl.addView(inflate);
                }
            });

        }


    };

    // 这是一个将dp转换为px的方法
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    protected View initTestView(int marginTop) {
        TextView switchTV = new TextView(this);
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //一键登录按钮默认marginTop 270dp
        mLayoutParams.setMargins(0, dp2px(this, marginTop), 0, 0);
        mLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        switchTV.setText("其他登录");
        switchTV.setTextColor(Color.BLACK);
        switchTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0F);
        switchTV.setLayoutParams(mLayoutParams);
        return switchTV;
    }


    protected View initCancelView(int marginTop) {
        TextView switchTV = new TextView(this);
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(dp2px(this, 50), dp2px(this, marginTop), 0, 0);
        switchTV.setText("取消");
        switchTV.setTextColor(Color.BLACK);
        switchTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0F);
        switchTV.setLayoutParams(mLayoutParams);
        return switchTV;
    }

    public static int dp2px(Context context, float dipValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        } catch (Exception e) {
            return (int) dipValue;
        }
    }

    private void updateView(){
        ExecutorManager.getInstance().postMain(new Runnable() {
            @Override
            public void run() {
                loginTest.setText(GlobalInfoManager.getInstance().getUserInfo().substring(0,4)+"****"+GlobalInfoManager.getInstance().getUserInfo().substring(7));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mAlicomFusionBusiness!=null){
            mAlicomFusionBusiness.destory();
        }
    }
}