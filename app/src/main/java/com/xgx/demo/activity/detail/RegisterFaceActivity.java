package com.xgx.demo.activity.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperButton;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xgx.demo.R;
import com.xgx.demo.activity.BaseVideoActivity;
import com.xgx.demo.utils.DialogCallback;
import com.xgx.demo.utils.Face;
import com.xgx.demo.utils.FaceSetUtil;
import com.xgx.demo.utils.FaceUtil;
import com.xgx.demo.utils.FinalUtil;
import com.xgx.demo.utils.JSONUtil;
import com.xgx.demo.utils.LzyResponse;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.utils.PictureUtil;
import com.xgx.demo.vo.LoginInformation;
import com.xgx.demo.vo.User;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Response;


/**
 * 离线视频流检测
 */
public class RegisterFaceActivity extends BaseVideoActivity {

    private final static String API_KEY = "lJsij4n8pYEj3bW-tSJqEhRgkdfHobC8";
    private final static String API_Secret = "i1H3kRBBzJ2Wo_1T-6RsbRmWgcHAREww";
    private final static String TAG = RegisterFaceActivity.class.getSimpleName();
    private SuperButton button_take_photos;
    private ImageView testImageView, imageView_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }


    @SuppressLint("ShowToast")
    @SuppressWarnings("deprecation")
    private void initUI() {
        testImageView = (ImageView) findViewById(R.id.testImageView);
        imageView_preview = (ImageView) findViewById(R.id.imageView_preview);
        button_take_photos = (SuperButton) findViewById(R.id.button_take_photos);
        button_take_photos.setClickable(true);
        button_take_photos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button_take_photos.setClickable(false);
                mCamera.takePicture(null, null, jpeg);
            }
        });
    }

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i(TAG, "onPictureTaken_data.length: " + data.length);
            String filename = "waitForRename.jpg";
            FileOutputStream fos;
            //获取拍到的图片Bitmap
            Bitmap bitmap_source = null;
            String pictureStoragePath = PictureUtil.getPictureStoragePath(getApplicationContext());
            File f = new File(pictureStoragePath, filename);
            try {
                fos = new FileOutputStream(f);
                if (data.length < 35000) {
                    YuvImage image = new YuvImage(nv21, ImageFormat.NV21, PREVIEW_WIDTH, PREVIEW_HEIGHT, null);   //将NV21 data保存成YuvImage
                    //图像压缩
                    image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 100, fos);
                    Log.i(TAG, "onPictureTaken_data.length<20000: " + data.length);
                    Log.i(TAG, "onPictureTaken_nv21.length: " + nv21.length);
                    bitmap_source = PictureUtil.compressFacePhoto(f.getAbsolutePath());
                    fos = new FileOutputStream(f);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    //旋转图片
                    // 根据旋转角度，生成旋转矩阵
                    Matrix matrix = new Matrix();
                    if (mCameraId == CameraInfo.CAMERA_FACING_FRONT) {
                        matrix.postRotate(270);
                    } else {
                        matrix.postRotate(90);
                    }
                    Bitmap mBitmap1 = Bitmap.createBitmap(bitmap_source, 0, 0, bitmap_source.getWidth(), bitmap_source.getHeight(), matrix, true);
                    testImageView.setImageBitmap(mBitmap1);
                    boolean result = mBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.close();
                    Log.i(TAG, "onPictureTaken_mBitmap1.compress: " + result);
                } else {
                    bitmap_source = PictureUtil.compressFacePhoto(data);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    //旋转图片
                    // 根据旋转角度，生成旋转矩阵
                    Matrix matrix = new Matrix();
                    if (mCameraId == CameraInfo.CAMERA_FACING_FRONT) {
                        matrix.postRotate(270);
                    } else {
                        matrix.postRotate(90);
                    }
                    Bitmap mBitmap1 = Bitmap.createBitmap(bitmap_source, 0, 0, bitmap_source.getWidth(), bitmap_source.getHeight(), matrix, true);
                    testImageView.setImageBitmap(mBitmap1);
                    mBitmap1.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                    bos.close();
                }
                //fos.write(data);
                fos.flush();
                fos.close();

                Log.i(TAG, "onPictureTaken: 图片保存成功");
                String username = null;
                File file = new File(pictureStoragePath, "waitForRename.jpg");
                if (file.exists()) {
                    RegisterFace(file, username);
                } else {
                    imageView_preview.setVisibility(View.GONE);
                    ToastUtils.showShort("错误代码-1，拍照文件保存失败，请检查磁盘空间！");
                }
                //mCamera.startPreview();//保存之后返回预览界面
            } catch (Exception e) {
                System.out.println("图片保存异常" + e.getMessage());
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:

                break;
            default:
                break;
        }

    }


    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case FinalUtil.DETECT_SUCCESS:
                    ToastUtils.showShort("认证成功");
                    finish();
                    break;
                case FinalUtil.DETECT_FAILED_IO_EXCEPTION:
                    ToastUtils.showShort("注册失败！\n请检查网络连接！！");

                    break;
                case FinalUtil.DETECT_FAILED_NO_FACE:
                    ToastUtils.showShort("注册失败！\n未检测到人脸，拍照时请保证光线充足且不要逆光！！");
                    break;
                case FinalUtil.DETECT_FAILED_LIMIT_EXCEEDED:
                    ToastUtils.showShort("注册失败！\n当前服务器压力过大，请稍后再试！！");
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 注册人脸
     *
     * @param imageFile
     * @param username
     */
    private void RegisterFace(final File imageFile, final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //验证头像人脸识别
                Response response = FaceUtil.detectFace(imageFile, API_KEY, API_Secret);
                if (null == response) {
                    Message message = myhandler.obtainMessage();
                    message.arg1 = FinalUtil.DETECT_FAILED_IO_EXCEPTION;
                    myhandler.sendMessage(message);
                    return;
                }
                Face face = null;
                int attempt = 0;
                //如果验证失败，重新验证，5次重连
                while (response.code() != 200 && attempt < 5) {
                    response = FaceUtil.detectFace(imageFile, API_KEY, API_Secret);
                    attempt++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Thread.sleep: ", e);
                        e.printStackTrace();
                    }
                }
                String JSON = null;
                try {
                    JSON = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONUtil jsonUtil = new JSONUtil();
                //人脸认证成功
                face = jsonUtil.parseDetectFaceJSON(JSON);
                if (face == null) {
                    Message message = myhandler.obtainMessage();
                    message.arg1 = FinalUtil.DETECT_FAILED_NO_FACE;
                    myhandler.sendMessage(message);
                } else {
                    File newFile = new File(PictureUtil.getPictureStoragePath(getApplicationContext()), face.getFace_token() + ".jpg");
                    if (!imageFile.renameTo(newFile)) {
                        imageView_preview.setVisibility(View.GONE);
                        return;
                    }
                    face.setImage_path(newFile.getAbsolutePath());
                    //更新当前登录人的信息
                    User user = LoginInformation.getInstance().getUser();
                    user.setFaceToken(face.getFace_token());
                    //上传图片到BMOB数据库
                    updateImageFile(newFile, user);
                    //将人脸放到宿舍的faceset中
                }
            }
        }).start();
    }


    private void updateImageFile(File imageFile, final User user) {

        PostRequest<LzyResponse<String>> request = OkGo.<LzyResponse<String>>post(MyUrl.baseUrl + MyUrl.uploadPath);
        request.params("file", imageFile);
        request.isMultipart(true);
        request.execute(new DialogCallback<LzyResponse<String>>(RegisterFaceActivity.this) {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<String>> response) {
                user.setFaceUrl(response.body().data);
                OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.updateStudent).params("user", JSON.toJSONString(user)).execute(new DialogCallback<LzyResponse<User>>(RegisterFaceActivity.this) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<User>> response) {
                        SPUtils.getInstance().put("user", JSON.toJSONString(response.body().data));
                        SPUtils.getInstance().put("isLogin", true);
                        LoginInformation.getInstance().setLogin(true);
                        LoginInformation.getInstance().setUser(response.body().data);
                        createFaceSet(user.getFaceToken(), user.getDeptid() + "", user.getDeptName());

                    }
                });

            }
        });

    }

    private void createFaceSet(final String faceToken, final String objectID, final String depName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建FaceSet集合，并将人脸加入集合
                int attempt = 0;
                Response response1 = FaceSetUtil.createFaceSet(API_KEY, API_Secret, depName, objectID, faceToken);
                while (response1 != null && response1.code() != 200 && attempt < 10) {
                    response1 = FaceSetUtil.createFaceSet(API_KEY, API_Secret, depName, objectID, faceToken);
                    attempt++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (response1 != null && response1.code() == 200) {
                    String JSON2 = null;
                    try {
                        JSON2 = response1.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (null != JSON2) {
                        if (JSON2.contains("\"face_added\": 1")) {
                            Message message = myhandler.obtainMessage();
                            message.arg1 = FinalUtil.DETECT_SUCCESS;
                            myhandler.sendMessage(message);
                        }
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        button_take_photos.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

