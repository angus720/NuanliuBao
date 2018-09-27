package nuanliu.com.modao.ui.dialog;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.utils.PermissionsChecker;
import nuanliu.com.modao.widget.photopicker.PhotoPicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2016/10/11.
 */

public class ImageStudioDialog extends DialogFragment {

    private static final int    TAKE_PHOTO    = 100;
    private static final int    SYS_PHOTO     = 101;
    private static final String EXTRA_IMGNAME = "extra_imgName";
    private static final String EXTRA_PICNUM = "extra_picnum";
    private static final String EXTRA_ISCROP  = "extra_isCrop";
    private static final int    CROP_PHOTO    = 2;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 100;
    private File   file;
    private String imgName;

    private boolean isCrop;

    private int  picnum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.transparentFrameNoMarginWindowStyle);
        imgName = getArguments().getString(EXTRA_IMGNAME, SystemClock.uptimeMillis() + ".jpg");
        isCrop = getArguments().getBoolean(EXTRA_ISCROP, false);
        picnum = getArguments().getInt(EXTRA_PICNUM);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        file = new File(getActivity().getExternalCacheDir(), imgName);
        imageUri = Uri.fromFile(file);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_image_studio, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setWindowAnimations(R.style.my_menu_animstylepopup);
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
    }

    @OnClick({R.id.photo, R.id.gallery, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo:
                if (!PermissionsChecker.lacksPermission(getActivity(), Manifest.permission.CAMERA)) {
                    camera();
                } else {
                    Toast.makeText(App.getContext(), "您未授权此应用拍照权限", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.gallery:

                openPic();
                dismiss();

                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private Uri imageUri;


    /**
     * 调用系统拍照
     */
    private void camera() {
        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            getImageByCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), "nuanliu.com.modao.provider_picture", file));
        }
        startActivityForResult(getImageByCamera,TAKE_PHOTO);
    }




    private void openPic() {

//        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
//        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(pickIntent,SYS_PHOTO);

        PhotoPicker.builder()
                .setPhotoCount(picnum)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(true)
                .start(getActivity(),PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO://拍照
                if (resultCode == RESULT_OK) {
                    startCrop(imageUri);
                }
                break;
            case SYS_PHOTO://相册
                if (resultCode == PhotoPicker.REQUEST_CODE) {
                    if (data != null && data.getData() != null) {
                        startCrop(data.getData());
                    }
                }
                break;
            case PhotoPicker.REQUEST_CODE://相册
                if (resultCode == PhotoPicker.REQUEST_CODE) {
                    if (data != null && data.getData() != null) {
                        startCrop(data.getData());
                    }
                }
                break;
            case CROP_PHOTO://裁剪
                if (resultCode == RESULT_OK) {
                    if (callback != null) {
                        dismiss();
                        List<String> list=new ArrayList<>();
                        list.add(imageUri.getPath());
                        callback.callback(list);
                    }
                }
                break;
        }
        if (resultCode != RESULT_OK) {
            dismiss();
        }
    }

    private void startCrop(Uri uri) {
        if (!isCrop && callback != null) {
            dismiss();
            List<String> list=new ArrayList<>();
            list.add(uri.getPath());
            callback.callback(list);
            return;
        }
        //每次启动裁剪前，删除之前的图片
//        File file1 = new File(imageUri.getPath());
//        if (file1.exists() && file1.isFile()) {
//            try {
//                file1.delete();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            intent.setDataAndType(uri, "image/*");
        } else {
            intent.setDataAndType(FileProvider.getUriForFile(getActivity(), "com.nuanliu.maintain.provider", this.file), "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        // return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
        // return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
    }

    public void show(FragmentManager manager, String tag, Callback callback) {
        super.show(manager, tag);
        this.callback = callback;
    }

    private Callback callback;

    /**
     * @param name 图片的名字 如image.jpg
     */
    public static ImageStudioDialog newInstance(String name) {
        ImageStudioDialog testDialog = new ImageStudioDialog();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_IMGNAME, name);
        testDialog.setArguments(bundle);
        return testDialog;
    }

    /**
     * @param name   图片的名字 如image.jpg
     * @param isCrop 是否调用裁剪  true为裁剪，false不裁剪
     * @return
     */
    public static ImageStudioDialog newInstance(String name, boolean isCrop,int picnum) {
        ImageStudioDialog testDialog = new ImageStudioDialog();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_IMGNAME, name);
        bundle.putBoolean(EXTRA_ISCROP, isCrop);
        bundle.putInt(EXTRA_PICNUM,picnum);
        testDialog.setArguments(bundle);
        return testDialog;
    }

    public interface Callback {
        void callback(List<String> list);
    }

}
