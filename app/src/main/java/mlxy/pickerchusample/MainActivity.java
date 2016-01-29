package mlxy.pickerchusample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import mlxy.pickerchu.PickerChu;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private CheckBox checkboxNeedCrop;
    private Button buttonCamera;
    private Button buttonGallery;

    private PickerChu pickerChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkboxNeedCrop = (CheckBox) findViewById(R.id.checkboxNeedCrop);
        this.buttonGallery = (Button) findViewById(R.id.buttonGallery);
        this.buttonCamera = (Button) findViewById(R.id.buttonCamera);
        this.imageView = (ImageView) findViewById(R.id.imageView);

        pickerChu = new PickerChu.Builder(this)
                                .saveIn(getExternalCacheDir())
                                .byAspectRatioOf(1, 1)
                                .inSizeOf(1024, 1024)
                                .onImageCropped(new PickerChu.OnImageCroppedListener() {
                                    @Override
                                    public void onImageCropped(Uri imageUri) {
                                        imageView.setImageURI(imageUri);
                                    }
                                })
                                .build();

        checkboxNeedCrop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pickerChu.setNeedToCrop(isChecked);
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerChu.takePhoto();
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerChu.pickPicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.pickerChu.handleActivityResult(requestCode, resultCode, data);
    }
}
