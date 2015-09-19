#PickerChu

PickerChu is an image pick-and-crop library for Android pika.

##Integration

Import module `PickerChu` into your project and simply add the following line to the `dependencies` section of your `build.gradle` file:

    compile project(':PickerChu')

Or check this out: https://jitpack.io/#mlxy/PickerChu

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

GNU General Public License version 2 ([GPL v2][1])

[1]: https://raw.githubusercontent.com/mlxy/PickerChu/master/LICENSE