package mlxy.pickerchu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

import mlxy.utils.F;

/** Pika pika. */
public class PickerChu {
    private Activity activity;
    private Config config;
    private File tempPhoto;
    private Uri resultImage;

    private PickerChu() {}
    private PickerChu(Activity activity) {
        this.activity = activity;
        this.config = new Config(activity);
    }

    /** Take a photo. */
    public void takePhoto() {
        tempPhoto = F.createTempFile(activity, System.currentTimeMillis() + "");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhoto));
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_TAKE_PHOTO);
    }

    /** Pick a picture from gallery. */
    public void pickPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_PICK_PICTURE);
    }

    /** Handles <code>onActivityResult()</code> event. Must be called. */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case Constants.REQUEST_CODE_TAKE_PHOTO:
                if (tempPhoto != null) {
                    if (config.needToCrop) {
                        crop(Uri.fromFile(tempPhoto));
                    } else {
                        photoAsResult(tempPhoto);
                    }
                }

                break;

            case Constants.REQUEST_CODE_PICK_PICTURE:
                if (data != null && data.getData() != null) {
                    if (config.needToCrop) {
                        crop(data.getData());
                    } else {
                        pictureAsResult(data.getData());
                    }
                }

                break;

            case Constants.REQUEST_CODE_CROP:
                deliverResult();
                break;
        }
    }

    // Sorry dad we're so ugly and would better go die.
    private void photoAsResult(File photo) {
        try {
            createResultImageFile();
            F.copy(photo, new File(resultImage.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        deliverResult();
    }

    // Agreed.
    private void pictureAsResult(Uri uri) {
        resultImage = uri;
        deliverResult();
    }

    /** Crop image. */
    private void crop(Uri imageUri) {
        try {
            createResultImageFile();

            Intent intent = prepareCropIntent(imageUri);
            activity.startActivityForResult(intent, Constants.REQUEST_CODE_CROP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Create file to store cropped image. */
    private void createResultImageFile() throws IOException {
        File resultImageFile = new File(config.saveIn, System.currentTimeMillis()+".png");
        F.delete(resultImageFile);
        F.create(resultImageFile);
        resultImage = Uri.fromFile(resultImageFile);
    }

    /** Setup a intent for cropping. */
    private Intent prepareCropIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, resultImage);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", config.aspectX);
        intent.putExtra("aspectY", config.aspectY);
        intent.putExtra("outputX", config.outputX);
        intent.putExtra("outputY", config.outputY);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);

        return intent;
    }

    /** Delete temp photo and callback. */
    private void deliverResult() {
        F.delete(tempPhoto);
        callListener();
    }

    /** Callback. */
    private void callListener() {
        if (config.onImageCroppedListener != null && config.saveIn != null) {
            config.onImageCroppedListener.onImageCropped(resultImage);
        }
    }

    /** PickerChu's builder. */
    public static class Builder {
        private Config config;

        public Builder(Activity activity) {
            config = new Config(activity);
        }

        /** <p>If needs to crop images. </p> */
        public Builder needToCrop(boolean needToCrop) {
            config.needToCrop = needToCrop;
            return this;
        }

        /** Save images cropped in <code>dir</code>.
         *
         * <p>Use <code>SDCard/Android/data/&lt;package_name&gt;/files/Pictures/</code> <br>
         * or <code>/data/data/&lt;package_name&gt;/files/</code> <br>
         * for <code>dir</code> by default depending on SDCard status. </p>*/
        public Builder saveIn(File dir) {
            config.saveIn = dir;
            return this;
        }

        /** <p>Crop images by ratio <code>ratioX</code>:<code>ratioY</code>. </p>
         *
         * <p>1:1 by default. </p> */
        public Builder byAspectRatioOf(int ratioX, int ratioY) {
            config.aspectX = ratioX;
            config.aspectY = ratioY;
            return this;
        }

        /** <p>Scale image cropped to <code>width</code> and <code>height</code>. </p>
         *
         * <p>(512, 512) by default. </p>*/
        public Builder inSizeOf(int width, int height) {
            config.outputX = width;
            config.outputY = height;
            return this;
        }

        /** Notify <code>onImageCroppedListener</code> when image cropped. */
        public Builder onImageCropped(OnImageCroppedListener onImageCroppedListener) {
            config.onImageCroppedListener = onImageCroppedListener;
            return this;
        }

        /** I choose you! <b>PickerChu</b>! */
        public PickerChu build() {
            PickerChu pickerChu = new PickerChu(config.activity);
            pickerChu.config = config;
            return pickerChu;
        }
    }

    /** PickerChu's configurations. */
    static class Config {
        Activity activity;
        boolean needToCrop;
        File saveIn;
        int aspectX;
        int aspectY;
        int outputX;
        int outputY;
        OnImageCroppedListener onImageCroppedListener;

        public Config(Activity activity) {
            initByDefault(activity);
        }

        void initByDefault(Activity activity) {
            this.activity = activity;
            needToCrop = true;
            saveIn = F.getFileDir(activity);
            aspectX = 1;
            aspectY = 1;
            outputX = 512;
            outputY = 512;
            onImageCroppedListener = null;
        }
    }

    public interface OnImageCroppedListener {
        void onImageCropped(Uri imageUri);
    }

    public void reset() {
        config = new Config(activity);
    }

    /*=======================Setters & Getters=======================*/
    public void setNeedToCrop(boolean needToCrop) {
        config.needToCrop = needToCrop;
    }
    public boolean needToCrop() {
        return config.needToCrop;
    }
    public void setSaveTo(File dir) {
        config.saveIn = dir;
    }
    public File getSaveTo() {
        return config.saveIn;
    }
    public void setAspectRatio(int ratioX, int ratioY) {
        config.aspectX = ratioX;
        config.aspectY = ratioY;
    }
    public int getAspectRatioX() {
        return config.aspectX;
    }
    public int getAspectRatioY() {
        return config.aspectY;
    }
    public void setSize(int width, int height) {
        config.outputX = width;
        config.outputY = height;
    }
    public int getOutputWidth() {
        return config.outputX;
    }
    public int getOutputHeight() {
        return config.outputY;
    }
    public void setOnImageCroppedListener(OnImageCroppedListener onImageCroppedListener) {
        config.onImageCroppedListener = onImageCroppedListener;
    }
    public OnImageCroppedListener getOnImageCroppedListener() {
        return config.onImageCroppedListener;
    }
    /*=======================Setters & Getters=======================*/
}
