package vn.com.snapreview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    ImageButton imgHomeCamera, imgHomeGallery;
    int REQUEST_CODE_CAMERA = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Ánh xạ
        imgHomeCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        imgHomeGallery = (ImageButton) findViewById(R.id.imageButtonGallery);

        //Xin quyền và bật tính năng camera của smartphone.
        imgHomeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        });

        //Gán nút mở cửa sổ thư viện ảnh sản phẩm review.
        imgHomeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "gallery", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Hàm xin cấp quyền đồng ý mở camera của smartphone.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(this, "Bạn không cho phép mở camera!", Toast.LENGTH_SHORT).show();
        }
    }
}
