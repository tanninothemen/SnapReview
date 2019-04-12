package vn.com.snapreview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReviewActivity extends AppCompatActivity {
    //Khai báo biến toàn cục, mã yêu cầu camera
    ImageButton ibtnReviewCamera;
    ImageButton ibtnReviewFolder;
    ImageView imgReviewPicture;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //Ánh xạ
        AnhXa();

        //Tạo nút mở camera để chụp ảnh
        ibtnReviewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ReviewActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        });

        //Tạo nút mở folder để lấy ảnh
        ibtnReviewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentFolder=new Intent(Intent.ACTION_PICK);
//                intentFolder.setType("image/*");
//                startActivityForResult(intentFolder,REQUEST_CODE_FOLDER);
                ActivityCompat.requestPermissions(
                        ReviewActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER);
            }
        });
    }

    //Hàm ánh xạ
    private void AnhXa() {
        ibtnReviewCamera = (ImageButton) findViewById(R.id.imageButtonReviewCamera);
        ibtnReviewFolder = (ImageButton) findViewById(R.id.imageButtonReviewFolder);
        imgReviewPicture = (ImageView) findViewById(R.id.imageViewReviewPicture);
    }

    //Hàm xin quyền truy cập máy ảnh và thư mục của thiết bị
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
                }else {
                    Toast.makeText(this, "Bạn không cho phép mở camera!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentFolder = new Intent(Intent.ACTION_PICK);
                    intentFolder.setType("image/*");
                    startActivityForResult(intentFolder, REQUEST_CODE_FOLDER);
                }else {
                    Toast.makeText(this, "Bạn không cho phép mở thư mục ảnh!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //Hàm nhận kết quả hình ảnh trả về của camera và nhận hình ảnh tải lên từ folder
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmapReviewPicture = (Bitmap) data.getExtras().get("data");
            imgReviewPicture.setImageBitmap(bitmapReviewPicture);
        }

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uriReviewFolder = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uriReviewFolder);
                Bitmap bitmapReviewFolder = BitmapFactory.decodeStream(inputStream);
                imgReviewPicture.setImageBitmap(bitmapReviewFolder);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
