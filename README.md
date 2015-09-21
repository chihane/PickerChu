#PickerChu

PickerChu is an image pick-and-crop library for Android pika.

##Integration

Simply add the following line to the `dependencies` section of your `build.gradle` file:

    compile 'mlxy.pickerchu:PickerChu:1.0'

##For Eclipse users

Just copy and paste all files from `/PickerChu/src/main/java/mlxy/pickerchu` to anywhere in your `src` folder, that'll work too.

Although I highly recommend you to migrate your Android projects to Android Studio.

##Usage

1. Build a `PickerChu` in your code:

    ```java
    pickerChu = new PickerChu.Builder(this)
                            .saveIn(getExternalCacheDir())
                            .byAspectRatioOf(1, 1)
                            .inSizeOf(1024, 1024)
                            .onImageCropped(new PickerChu.OnImageCroppedListener() {
                                @Override
                                public void onImageCropped(Uri imageUri) {
                                    // imageView.setImageURI(imageUri);
                                }
                            })
                            .build();
    ```

1. Handle `onActivityResult()` event:

    ```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.pickerChu.handleActivityResult(requestCode, resultCode, data);
    }
    ```

1. Choose a way to get a picture:

    ```java
    // pickerChu.takePhoto();
    pickerChu.pickPicture();
    ```

##Author

**mlxy**

- <http://www.cnblogs.com/chihane/>
- <chihane@yeah.net>

##License

GNU General Public License version 2 ([GPL-2.0][1])

[1]: http://www.gnu.org/licenses/gpl-2.0.html