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

        //Chuyển đến trang review sản phẩm
        imgHomeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReview=new Intent(HomeActivity.this, ReviewActivity.class);
                startActivity(intentReview);
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
}
