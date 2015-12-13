package com.faceswiping.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faceswiping.app.AppContext;
import com.faceswiping.app.R;
import com.faceswiping.app.api.remote.FaceSwipingApi;
import com.faceswiping.app.base.BaseActivity;
import com.faceswiping.app.bean.Result;
import com.faceswiping.app.util.DialogHelp;
import com.faceswiping.app.util.FileUtil;
import com.faceswiping.app.util.ImageUtils;
import com.faceswiping.app.util.StringUtils;
import com.faceswiping.app.util.UIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.InjectView;

public class AddNewFriendActivity extends BaseActivity {

    @InjectView(R.id.add_friend_faceAddFriendLayout)
    LinearLayout addFriendLayout;

    @InjectView(R.id.add_friend_faceAddFriendState)
    TextView addFriendState;

    @InjectView(R.id.add_friend_faceAddContactFriendLayout)
    LinearLayout addContactFriend;

    @InjectView(R.id.add_friend_faceAddWeiXinFriendLayout)
    LinearLayout addWeiXinFriend;

    @InjectView(R.id.add_friend_faceAddQQFriendLayout)
    LinearLayout addQQFriend;


    //相册
    public static final int ACTION_TYPE_ALBUM = 0;
    //相机
    public static final int ACTION_TYPE_PHOTO = 1;

    private UploadManager uploadManager;

    private String qiniuToken;

    private AsyncHttpResponseHandler updateHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            hideWaitDialog();

            String response = new String(responseBody);

            System.out.println(response);

            try {

//                Result result = JSON.parseObject(response, Result.class);

                Result result = new Gson().fromJson(response, Result.class);

                if (result.getErrorcode() == 0) {

                    UIHelper.showMarkNewFriendsActivity(AddNewFriendActivity.this);

                } else {

                    AppContext.showToast(result.getErrormsg());
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            hideWaitDialog();

            AppContext.showToast("上传成功～！");
        }
    };

    //头像保存路径
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/Inyouzi/Portrait/";

    private AsyncHttpResponseHandler qiniuHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {

                String response = new String(responseBody);

                System.out.println(response);

//                Result<String> result = JSON.parseObject(response, new TypeReference<Result<String>>() {
//                });

                Result<String> result = new Gson().fromJson(response, new TypeToken<Result<String>>() {
                }.getType());

                if (result.getErrorcode() == 0) {

                    qiniuToken = result.getData();

                    // 获取头像缩略图
                    if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
                        protraitBitmap = ImageUtils
                                .loadImgThumbnail(protraitPath, 200, 200);
                    } else {
                        hideWaitDialog();
                        AppContext.showToast("图像不存在，上传失败");
                        return;
                    }
                    if (protraitBitmap != null) {


                        uploadManager.put(protraitPath, UUID.randomUUID().toString(), qiniuToken, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {

                                //   System.out.println(info.isOK());


                                if (info.isOK()) {

                                    String[] keys = new String[1];

                                    keys[0] = key;

                                    FaceSwipingApi.updateUserProtrait(keys, updateHandler);


                                } else {

                                    hideWaitDialog();
                                    AppContext.showToast("认证失败～！");
                                }


                            }
                        }, null);


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(statusCode, headers, responseBody, e);

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              byte[] responseBody, Throwable error) {

            // System.out.println(statusCode);

            hideWaitDialog();
            AppContext.showToastShort("上传失败～！");

        }
    };

    //保存拍照图片的Uri
    private Uri origUri;
    private Uri cropUri;
    private File protraitFile;
    private Bitmap protraitBitmap;
    private String protraitPath;

    //该照片的绝对路径
    private String theLarge;

    private final static int CROP = 200;
    public static String USER_PROTRAIT = "host_user_protrait_";

    public static DisplayImageOptions optionsImage = new DisplayImageOptions
            .Builder()
            //.showImageOnLoading(R.drawable.ic_portrait)
            .showImageForEmptyUri(R.drawable.ic_portrait)
            .showImageOnFail(R.drawable.ic_portrait)
            .cacheInMemory(false)
            .cacheOnDisk(false)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_friend;
    }

    private ActionBar actionBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.actionbar_custom_view);
        View view = mActionBar.getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.actionbar_title);
        textView.setText("添加好友");

        return true;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {

        addFriendLayout.setOnClickListener(this);

        if (AppContext.getInstance().getLoginUser().getSecret() == 0) {
            addFriendState.setVisibility(View.VISIBLE);
        } else {
            addFriendState.setVisibility(View.GONE);
        }


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.add_friend_faceAddFriendLayout:

                if (AppContext.getInstance().getLoginUser().getSecret() == 0) {
                    UIHelper.showFaceIdentificationActivity(this);
                } else {

                    showSelectPicture();

                }

                break;
            default:

                break;

        }
    }


    private void showSelectPicture() {
        DialogHelp.getSelectDialog(this, "选择图片", getResources().getStringArray(R.array.choose_picture), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToSelectPicture(i);
            }
        }).show();
    }

    private void goToSelectPicture(int position) {
        switch (position) {
            case ACTION_TYPE_ALBUM:
                startImagePick();
                break;
            case ACTION_TYPE_PHOTO:
                startTakePhoto();
                break;
            default:
                break;
        }
    }

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        //19 android 4.4
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Inyouzi/Camera/";
            File savedir = new File(savePath);
            //不存在则创建
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "inyouzi_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        theLarge = savePath + fileName;// 该照片的绝对路径

        //拍照并输出
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    /**
     * 上传新照片
     */
    private void uploadNewPhoto() {

        showWaitDialog("正在上传...");

        FaceSwipingApi.getQiniuToken(qiniuHandler);

    }


    // 裁剪头像的绝对路径
    //传入的URI可能为相册标准uri也可能是拍照返回的非标准uri,
    //转为绝对路径之后获取扩展名，生成头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {

        //System.out.println(FILE_SAVEPATH);

        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            AppContext.showToast("无法保存上传的头像，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(this, uri);
        }

        //获取文件扩展名
        String ext = FileUtil.getFileFormat(thePath);
        //文件扩展名为空则jpg
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "inyouzi_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边，如果被裁减的图片比输出小，这个应该是手动
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边，这个应该是自动
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent imageReturnIntent) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadNewPhoto();//裁减后上传
                break;
        }
    }
}
