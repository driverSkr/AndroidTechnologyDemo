package com.ethan.app.vm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ethan.app.R
import com.ethan.sharer.Sharer

class ShareViewModel : ViewModel() {

    private val shareIconSave = ShareIcon(R.mipmap.icon_share_save, R.string.save)
    private val shareIconWechat = ShareIcon(R.mipmap.icon_share_wechat, R.string.share_wechat_text)
    private val shareIconDouyin = ShareIcon(R.mipmap.icon_share_tiktok, R.string.share_douyin_text)
    private val shareIconXiaohongshu = ShareIcon(R.mipmap.icon_share_xiaohongshu, R.string.share_xiaohongshu_text)
    private val shareIconMoments = ShareIcon(R.mipmap.icon_share_moments, R.string.share_moments_text)

    //已隐藏
    private val shareIconTiktok = ShareIcon(R.mipmap.icon_share_tiktok, R.string.share_tiktok_text)
    private val shareIconInstagram = ShareIcon(R.mipmap.icon_share_instagram, R.string.share_instagram_text)
    private val shareIconFacebook = ShareIcon(R.mipmap.icon_share_facebook, R.string.share_facebook_text)
    private val shareIconMore = ShareIcon(R.mipmap.icon_share_more, R.string.share_more_text)

    data class ShareIcon(@DrawableRes var icon: Int, @StringRes val text: Int)

    var shareIconList = listOf(
        shareIconSave,
        shareIconWechat,
        shareIconDouyin,
        shareIconXiaohongshu,
        shareIconMoments
    )

    data class ShareVideoState(
        val loadState: EncodeVideoState,
        val videoPath: String = "0",
                              ) {
        enum class EncodeVideoState {
            ENCODING, SUCCESS, ERROR
        }
    }

    var shareVideoPath = MutableLiveData<ShareVideoState>()

    fun doShare(activity: Activity, icon: ShareIcon, uri: Uri, launcher: ActivityResultLauncher<Intent>) {
        Log.d("ethan", "你选择的视频uri: $uri")
        //val pendingIntent = PendingIntent.getBroadcast(context, 0, Intent(context, ShareMediaReceiver::class.java), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        when (icon) {
            shareIconXiaohongshu -> {
                val intent = Sharer.share(
                    uri, "video/*",null, "shareText",
                )
                launcher.launch(intent)
            }
        }
    }


    /*fun doShare(fragment: BaseFragment, icon: ShareIcon, launcher: ActivityResultLauncher<Intent>, tag: String) {
        // val tagList = tag.split(",")
        val videoPath = shareVideoPath.value?.videoPath ?: return
        val context = fragment.activity ?: return
        val mediaUri = FileProvider.getUriForFile(context, AppUtils.getAppPackageName() + ".fileprovider", File(videoPath))
        val mediaType = Constants.TYPE_VIDEO
        val shareText = context.getString(R.string.share_text_content_text, context.getString(R.string.app_title_name), NetConfig.SHARE_URL + AppUtils.getAppPackageName()) + "\t#AiRizzApp"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, Intent(context, ShareMediaReceiver::class.java), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val bundle = Bundle()
        val templateName = IntentDataUtils.templateIntentData.shareDetail?.title?.translate?.en.getTemplateName()
        val isVip = if (IntentDataUtils.templateIntentData.shareDetail?.isVipTemp() == true) {
            "Y"
        } else {
            "N"
        }
        val name = "${IntentDataUtils.templateIntentData.shareDetail?.typeId}_${isVip}_$templateName"
        bundle.putString(Key.Source, IntentDataUtils.templateIntentData.shareSource)
        bundle.putString(Key.Template, name)
        shareAction = ShareAction(context)
        when (icon) {
            shareIconSave -> {
                viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main) {
                        fragment.showDialogNoBg()
                    }
                    GalleryUtils.add2Gallery(
                        context, "AIRIZZ",
                        "AIRIZZ_${SimpleDateFormat("yyyyMMddHHmm", Locale.US).format(Date())}_${UUID.randomUUID()}",
                        File(videoPath),
                                            )
                    withContext(Dispatchers.Main) {
                        fragment.dismissProgress()
                        fragment.activity?.let {
                            AppTipsHelp()
                                .initNormalTips(it, context.getString(R.string.saved_to_the_gallery), R.drawable.svg_check, 0.3F)
                                .show()
                        }
                    }
                    bundle.putString(Key.Item_Name, "save_video")
                }
            }

            shareIconWechat -> {
                /*shareLink(context, NetConfig.URL_PRIVACY_POLICY, "隐私协议", "这是深圳市软牛集团的隐私协议", Platform.WECHAT, object: UmengShare.OnShareListener{
                    override fun onStart(platform: Platform) {
                        Log.d("ethan", "分享开始")
                    }
                    override fun onSucceed(platform: Platform) {
                        Log.d("ethan", "分享成功")
                    }
                    override fun onError(platform: Platform, t: Throwable?) {
                        Log.d("ethan", "分享错误")
                    }
                    override fun onCancel(platform: Platform) {
                        Log.d("ethan", "分享取消")
                    }
                })*/
            }

            shareIconTiktok -> {
                context.grantUriPermission(Constants.PKG_TIKTOK, mediaUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.grantUriPermission(Constants.PKG_TIKTOK2, mediaUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                Sharer.shareToTikTokByMediaType(context, mediaUri.toString(), context.getString(R.string.app_not_install_text), mediaType, listOf()) {}
                bundle.putString(Key.Item_Name, "Tiktok")
            }

            shareIconInstagram -> {
                viewModelScope.launch(Dispatchers.Default) {
                    bundle.putString(Key.Item_Name, "Instagram")
                    val goShare = DialogHelper.dialogCopyAndShare(context, shareText)
                    if (goShare) {
                        withContext(Dispatchers.Main) {
                            ClipboardUtils.copyText(shareText)
                            val intent = Sharer.share(
                                context,
                                mediaUri, mediaType, Constants.PKG_INSTAGRAM, context.getString(R.string.app_not_install_text),
                                pendingIntent, shareText,
                                                     )
                            if (intent != null) launcher.launch(intent)
                        }
                    }
                }

            }

            shareIconFacebook -> {
                context.grantUriPermission(Constants.PKG_FACEBOOK, mediaUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                Sharer.shareFaceBookByMediaType(context, mediaUri, Constants.PKG_FACEBOOK, context.getString(R.string.app_not_install_text), mediaType, listOf(shareText)) {
                    Log.i(TAG, "doShare: Sharer.shareFaceBookByMediaType: $it")
                }
                bundle.putString(Key.Item_Name, "Facebook")
            }

            shareIconMore -> {
                val intent = Sharer.share(mediaUri, mediaType, pendingIntent, shareText)
                if (intent != null) launcher.launch(intent)
                bundle.putString(Key.Item_Name, "More")
            }

            else -> {}
        }
        fragment.activity?.let {
            Event.event(it, Name.Portrait_Shared, bundle)
        }
    }*/

}