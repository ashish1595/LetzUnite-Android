package com.letzunite.letzunite.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.letzunite.letzunite.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

/**
 * Created by Deven Singh on 07 Jun, 2018.
 */
public class ImageCaptureHandler {

    public interface ImageCaptureCallback {
        void onSuccess(String path);

        void onError(String error);
    }

    private static ImageCaptureCallback m_callBack;
    private static final int REQUEST_CAPTURE_PHOTO = 1727;
    private static final int REQUEST_PIC_FROM_GALLERY = 1427;
    private static final int REQUEST_CROP_PHOTO = 2789;
    private static final int REQUEST_PIC_FROM_GALLERY_KITKAT = 1791;
    final private int REQUEST_CODE_ASK_CAMERA_PERMISSIONS = 17;
    final private int REQUEST_CODE_ASK_EXTERNAL_STORAGE_PERMISSIONS = 27;
    private final int REQUEST_STORAGE_PERMISSION = 1492;

    private final AppCompatActivity activity;
    private final SharedPrefs sharedPrefs;
    private final PermissionHandler permissionHandler;
    private final AppUtils appUtils;

    @Inject
    public ImageCaptureHandler(AppCompatActivity activity, PermissionHandler permissionHandler,
                               SharedPrefs sharedPrefs, AppUtils appUtils) {
        this.activity = activity;
        this.permissionHandler = permissionHandler;
        this.sharedPrefs = sharedPrefs;
        this.appUtils = appUtils;
    }

    private String getAppDirectoryName() {
        return activity.getResources().getString(R.string.app_name);
    }

    private File createAppAlbumDirectory() throws Exception {
        File appAlbumDir = null;
        appAlbumDir = new File(Environment.getExternalStorageDirectory(), getAppDirectoryName());
        if (appAlbumDir != null) {
            if (!appAlbumDir.exists()) {
                appAlbumDir.mkdirs();
                File noMedia = new File(appAlbumDir, ".nomedia");
                if (noMedia != null) {
                    noMedia.createNewFile();
                }
            }
        }
        return appAlbumDir;
    }

    private File createImageFile() throws Exception {
        File imageFile = null;
        imageFile = new File(createAppAlbumDirectory(), "user_image.jpg");
        sharedPrefs.setCapturedImagePath(imageFile.getAbsolutePath());
        return imageFile;
    }

    private File createImageThumbnailFile() throws Exception {
        File imageFile = null;
        imageFile = new File(createAppAlbumDirectory(), "user_image_thumbnails.jpg");
        sharedPrefs.setCroppedImagePath(imageFile.getAbsolutePath());
        return imageFile;
    }

    private void setPic(String path) {
        // Get the dimensions of the View
        int targetW = (int) (appUtils.getDeviceWidth() * 0.8f);
        int targetH = (int) (appUtils.getDeviceHeight() * 0.8f);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        cropBitmap(resolveOrientationAnsSaveScaledBitmap(bitmap, path));
    }


    private File resolveOrientationAnsSaveScaledBitmap(Bitmap bitmaps, String path) {
        Bitmap bitmap = null;
        int rotateImage = getCameraPhotoOrientation(path);
        Matrix mat = new Matrix();
        mat.postRotate(rotateImage);
        bitmap = Bitmap.createBitmap(bitmaps, 0, 0, bitmaps.getWidth(),
                bitmaps.getHeight(), mat, false);
        File file = null;
        try {
            file = createImageFile();
        } catch (Exception e) {
            if (m_callBack != null) {
                m_callBack.onError("Storage Permission Denied");
                m_callBack = null;
            }
        }
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            if (m_callBack != null) {
                m_callBack.onError("Exception occur");
                m_callBack = null;
            }
        }
        return file;
    }

    private int getCameraPhotoOrientation(String path) {
        if (path == null) {
            return 0;
        }
        int rotate = 0;
        try {
            File imageFile = new File(path);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                default:
                    rotate = 0;
                    break;
            }

        } catch (Exception e) {
        }
        return rotate;
    }

    public void imageFunctionality(ImageCaptureCallback callback) {
        if (m_callBack != null) {
            m_callBack = null;
        }
        m_callBack = callback;
        imageFromDialog();
    }

    private void imageFromDialog() {
        Dialog mDialog = new Dialog(activity, R.style.DefaultDialogTheme);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(R.array.select_options,
                (dialog, which) -> {
                    switch (which) {
                        case 0: {
                            dialog.dismiss();
                            checkStoragePermissionBeforeCaptureImage();
                            break;
                        }
                        case 1: {
                            dialog.dismiss();
                            checkStoragePermission();
                            break;
                        }

                        default:
                            break;
                    }

                });
        builder.setPositiveButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (m_callBack != null) {
                m_callBack.onError("Cancelled");
                m_callBack = null;
            }
        });
        mDialog = builder.create();
        mDialog.show();

    }

    private void checkStoragePermission() {
        if (permissionHandler.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            dispatchPickImageIntent();
        } else {
            permissionHandler.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_CODE_ASK_EXTERNAL_STORAGE_PERMISSIONS, resultListener);
        }
    }

    private void checkStoragePermissionBeforeCaptureImage() {
        if (permissionHandler.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            checkCameraPermission();
        } else {
            permissionHandler.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    REQUEST_STORAGE_PERMISSION, resultListener);
        }
    }

    private void checkCameraPermission() {
        if (permissionHandler.isPermissionGranted(Manifest.permission.CAMERA)) {
            dispatchTakePictureIntent();
        } else {
            permissionHandler.requestPermission(Manifest.permission.CAMERA,
                    REQUEST_CODE_ASK_CAMERA_PERMISSIONS, resultListener);
        }
    }

    PermissionsResultListener resultListener = new PermissionsResultListener() {
        @Override
        public void onPermissionResult(int requestCode, boolean isPermissionGrated) {
            switch (requestCode) {
                case REQUEST_CODE_ASK_CAMERA_PERMISSIONS:
                    if (isPermissionGrated) {
                        dispatchTakePictureIntent();
                    } else {
                        if (m_callBack != null) {
                            m_callBack.onError("Camera Permission Denied");
                            m_callBack = null;
                        }
                    }
                    break;
                case REQUEST_CODE_ASK_EXTERNAL_STORAGE_PERMISSIONS:
                    if (isPermissionGrated) {
                        dispatchPickImageIntent();
                    } else {
                        if (m_callBack != null) {
                            m_callBack.onError("Storage Permission Denied");
                            m_callBack = null;
                        }
                    }
                    break;
                case REQUEST_STORAGE_PERMISSION:
                    if (isPermissionGrated) {
                        checkCameraPermission();
                    } else {
                        if (m_callBack != null) {
                            m_callBack.onError("Storage Permission Denied");
                            m_callBack = null;
                        }
                    }
                    break;
            }
        }

        @Override
        public void shouldShowPermissionRequestReason(int requestCode) {
            switch (requestCode) {
                case REQUEST_CODE_ASK_CAMERA_PERMISSIONS:
                    showMessageOKCancel("Wanna Grant Camera Permission", (dialog, which) -> permissionHandler.requestPermissionAfterShowReason(
                            Manifest.permission.CAMERA,
                            REQUEST_CODE_ASK_CAMERA_PERMISSIONS, resultListener));
                    break;
                case REQUEST_CODE_ASK_EXTERNAL_STORAGE_PERMISSIONS:
                    showMessageOKCancel("Wanna Grant Storage Permission", (dialog, which) -> permissionHandler.requestPermissionAfterShowReason(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            REQUEST_CODE_ASK_EXTERNAL_STORAGE_PERMISSIONS, resultListener));
                    break;
                case REQUEST_STORAGE_PERMISSION:
                    showMessageOKCancel("Wanna Grant Storage Permission", (dialog, which) -> permissionHandler.requestPermissionAfterShowReason(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            REQUEST_STORAGE_PERMISSION, resultListener));
                    break;
            }
        }
    };

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity, R.style.DefaultDialogTheme)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void cropBitmap(File file) {
        if (file == null) return;
        try {
            Crop.of(Uri.fromFile(file), Uri.fromFile(createImageThumbnailFile())).withMaxSize(512, 512).asSquare().start(activity);
        } catch (Exception e) {
            if (m_callBack != null) {
                m_callBack.onError("Exception occur");
                m_callBack = null;
            }
        }
    }

    private void dispatchPickImageIntent() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Select pic"), REQUEST_PIC_FROM_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            activity.startActivityForResult(intent, REQUEST_PIC_FROM_GALLERY_KITKAT);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                if (m_callBack != null) {
                    m_callBack.onError("Exception occur");
                    m_callBack = null;
                }
            }
            if (photoFile != null) {
                takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                activity.startActivityForResult(takePictureIntent, REQUEST_CAPTURE_PHOTO);
            }
        } else {
            if (m_callBack != null) {
                m_callBack.onError("Exception occur");
                m_callBack = null;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri originalUri = null;
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE_PHOTO:
                    setPic(sharedPrefs.getCapturedImagePath());
                    break;
                case REQUEST_PIC_FROM_GALLERY:
                    try {
                        setPic(getGalleryImagePath(data.getData()));
                    } catch (Exception e) {
                        if (m_callBack != null) {
                            m_callBack.onError("Exception occur");
                            m_callBack = null;
                        }
                    }
                    break;
                case REQUEST_PIC_FROM_GALLERY_KITKAT:
                    try {
                        originalUri = data.getData();
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        // Check for the freshest data.
                        activity.getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
                        setPic(getPath(activity, originalUri));
                    } catch (Exception e) {
                        if (m_callBack != null) {
                            m_callBack.onError("Exception occur");
                            m_callBack = null;
                        }
                    }
                    break;
                case Crop.REQUEST_CROP:
                    if (m_callBack != null) {
                        m_callBack.onSuccess(sharedPrefs.getCroppedImagePath());
                    }
                    m_callBack = null;
                    break;
            }
        } else {
            if (m_callBack != null) {
                m_callBack.onError("Cancelled");
                m_callBack = null;
            }
        }
    }

    private String getGalleryImagePath(Uri data) {
        if (Build.VERSION.SDK_INT < 19) {
            return getPath(data);
        } else {
            return data.getPath();
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    private String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
