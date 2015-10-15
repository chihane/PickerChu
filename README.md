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

    The MIT License (MIT)
    
    Copyright (c) 2015 mlxy
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.tml
