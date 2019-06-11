package vn.com.snapreview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReviewActivity extends AppCompatActivity {
    //Khai báo biến toàn cục, mã yêu cầu camera
    ImageButton ibtnReviewCamera, ibtnReviewFolder;
    ImageView imgReviewPicture;
    EditText edtProductName;
    RadioGroup rdgDesign, rdgConfiguration, rdgCamera, rdgSound, rdgBattery;
    Button btnReviewThuongHieu, btnReviewThoiGianSuDung, btnConfirmReviewImage, btnResetReview, btnQuit;
    RadioButton rdDesignXS, rdDesignTot, rdDesignBinhThuong, rdDesignTe,
            rdConfigurationXS, rdConfigurationTot, rdConfigurationBinhThuong, rdConfigurationTe,
            rdCameraXS, rdCameraTot, rdCameraBinhThuong, rdCameraTe,
            rdSoundXS, rdSoundTot, rdSoundBinhThuong, rdSoundTe,
            rdBatteryXS, rdBatteryTot, rdBatteryBinhThuong, rdBatteryTe;
    Product product;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;
    byte[] productImage;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //Ánh xạ
        AnhXa();


        //Tạo đối tượng sản phẩm
        product = new Product(0, productImage, edtProductName.toString(), btnReviewThuongHieu.getText().toString().trim(),
                btnReviewThoiGianSuDung.getText().toString().trim(), resultDesign(), resultConfiguration(), resultCamera(), resultSound(), resultBattery());


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
                ActivityCompat.requestPermissions(
                        ReviewActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER);
            }
        });

        //Tạo phần chọn menu thương hiệu
        btnReviewThuongHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuThuongHieu();
            }
        });

        //Tạo phần chọn menu thời gian sử dụng
        btnReviewThoiGianSuDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuThoiGianSuDung();
            }
        });

        //Tạo nút xác thực ảnh để tạo
        btnConfirmReviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateReviewImageNotify();
            }
        });

        //Tạo nút reset những lựa chọn của người review
        btnResetReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductName.setText("");
                btnReviewThuongHieu.setText("Chọn thương hiệu");
                btnReviewThoiGianSuDung.setText("Chọn thời gian");
                rdgDesign.clearCheck();
                rdgConfiguration.clearCheck();
                rdgCamera.clearCheck();
                rdgSound.clearCheck();
                rdgBattery.clearCheck();
                Toast.makeText(ReviewActivity.this, "Tạo lại thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        //Tạo nút Thoát ra ngoài trang chủ
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    //Hàm ánh xạ
    private void AnhXa() {
        edtProductName          = (EditText) findViewById(R.id.textViewProductName);
        ibtnReviewCamera        = (ImageButton) findViewById(R.id.imageButtonReviewCamera);
        ibtnReviewFolder        = (ImageButton) findViewById(R.id.imageButtonReviewFolder);
        imgReviewPicture        = (ImageView) findViewById(R.id.imageViewReviewPicture);
        btnReviewThuongHieu     = (Button) findViewById(R.id.buttonReviewThuongHieu);
        btnReviewThoiGianSuDung = (Button) findViewById(R.id.buttonReviewThoiGianDung);
        btnConfirmReviewImage   = (Button) findViewById(R.id.buttonXacNhan);
        btnResetReview          = (Button) findViewById(R.id.buttonHuy);
        btnQuit                 = (Button) findViewById(R.id.buttonThoat);
        rdgDesign               = (RadioGroup) findViewById(R.id.radioGroupDesign);
        rdgConfiguration        = (RadioGroup) findViewById(R.id.radioGroupConfiguration);
        rdgCamera               = (RadioGroup) findViewById(R.id.radioGroupCamera);
        rdgSound                = (RadioGroup) findViewById(R.id.radioGroupSound);
        rdgBattery              = (RadioGroup) findViewById(R.id.radioGroupBattery);
        //ánh xạ những radio button review
        rdDesignXS                      =(RadioButton) findViewById(R.id.radioButtonTKXuatSac);
        rdDesignTot                     =(RadioButton) findViewById(R.id.radioButtonTKTot);
        rdDesignBinhThuong              =(RadioButton) findViewById(R.id.radioButtonTKBinhThuong);
        rdDesignTe                      =(RadioButton) findViewById(R.id.radioButtonTKTe);
        rdConfigurationXS               =(RadioButton) findViewById(R.id.radioButtonCHXuatSac);
        rdConfigurationTot              =(RadioButton) findViewById(R.id.radioButtonCHBinhThuong);
        rdConfigurationBinhThuong       =(RadioButton) findViewById(R.id.radioButtonCHBinhThuong);
        rdConfigurationTe               =(RadioButton) findViewById(R.id.radioButtonCHTe);
        rdCameraXS                      =(RadioButton) findViewById(R.id.radioButtonCamXuatSac);
        rdCameraTot                     =(RadioButton) findViewById(R.id.radioButtonCamTot);
        rdCameraBinhThuong              =(RadioButton) findViewById(R.id.radioButtonCamBinhThuong);
        rdCameraTe                      =(RadioButton) findViewById(R.id.radioButtonCamTe);
        rdSoundXS                       =(RadioButton) findViewById(R.id.radioButtonSoundXuatSac);
        rdSoundTot                      =(RadioButton) findViewById(R.id.radioButtonSoundTot);
        rdSoundBinhThuong               =(RadioButton) findViewById(R.id.radioButtonSoundBinhThuong);
        rdSoundTe                       =(RadioButton) findViewById(R.id.radioButtonSoundTe);
        rdBatteryXS                     =(RadioButton) findViewById(R.id.radioButtonBatteryXuatSac);
        rdBatteryTot                    =(RadioButton) findViewById(R.id.radioButtonBatteryTot);
        rdBatteryBinhThuong             =(RadioButton) findViewById(R.id.radioButtonBatteryBinhThuong);
        rdBatteryTe                     =(RadioButton) findViewById(R.id.radioButtonBatteryTe);
    }

    //-------------------PHẦN REVIEW-------------------------------
    //Hàm xin quyền truy cập máy ảnh và thư mục của thiết bị
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "Bạn không cho phép mở camera!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intentFolder = new Intent(Intent.ACTION_PICK);
                    intentFolder.setType("image/*");
                    startActivityForResult(intentFolder, REQUEST_CODE_FOLDER);
                } else {
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

    //Hàm hiển thị menu thương hiệu
    private void ShowMenuThuongHieu() {
        PopupMenu popupMenuThuongHieu = new PopupMenu(this, btnReviewThuongHieu);
        popupMenuThuongHieu.getMenuInflater().inflate(R.menu.menu_review_thuonghieu, popupMenuThuongHieu.getMenu());
        popupMenuThuongHieu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuReviewThuongHieuApple:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuSamsung:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuHuawei:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuLenovo:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuLG:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuNokia:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuOppo:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuSony:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuVivo:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                    case R.id.menuReviewThuongHieuXiaomi:
                        btnReviewThuongHieu.setText(item.getTitle().toString());
                        break;
                }
                return false;
            }
        });
        popupMenuThuongHieu.show();
    }

    //Hàm hiển thị menu thời gian sử dụng
    private void ShowMenuThoiGianSuDung() {

        PopupMenu popupMenuThoiGianSuDung = new PopupMenu(this, btnReviewThoiGianSuDung);
        popupMenuThoiGianSuDung.getMenuInflater().inflate(R.menu.menu_review_usedtime, popupMenuThoiGianSuDung.getMenu());
        popupMenuThoiGianSuDung.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuTime1Month:
                        btnReviewThoiGianSuDung.setText(item.getTitle().toString());
                        break;
                    case R.id.menuTime1To3Month:
                        btnReviewThoiGianSuDung.setText(item.getTitle().toString());
                        break;
                    case R.id.menuTime3To6Month:
                        btnReviewThoiGianSuDung.setText(item.getTitle().toString());
                        break;
                    case R.id.menuTime6MonthTo1Year:
                        btnReviewThoiGianSuDung.setText(item.getTitle().toString());
                        break;
                    case R.id.menuTimeOver1Year:
                        btnReviewThoiGianSuDung.setText(item.getTitle().toString());
                        break;

                }
                return false;
            }
        });
        popupMenuThoiGianSuDung.show();
    }

    //------------------PHẦN XÁC THỰC-------------------------------

    private void CreateReviewImageNotify() {
        AlertDialog.Builder dialogCreate = new AlertDialog.Builder(this);
        dialogCreate.setTitle("Tạo ảnh review");
        dialogCreate.setIcon(R.drawable.image_dialog_create);
        dialogCreate.setMessage("Bạn có muốn tạo ảnh Review về sản phẩm này không?");
        dialogCreate.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //chuyển data imageview -> []byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgReviewPicture.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                productImage = byteArray.toByteArray();

                product.setProductName(edtProductName.getText().toString().trim());
                product.setProductBrand(btnReviewThuongHieu.getText().toString().trim());
                product.setProductUsedTime(btnReviewThoiGianSuDung.getText().toString().trim());
                product.setProductImage(productImage);
                product.setProductDesign(resultDesign());
                product.setProductBattery(resultBattery());
                product.setProductCamera(resultCamera());
                product.setProductConfiguration(resultConfiguration());
                product.setProductSound(resultSound());

                Intent intentSendReview = new Intent(ReviewActivity.this, CreateReviewActivity.class);
                Bundle bundleInformation = new Bundle();
                bundleInformation.putSerializable("product", product);

                intentSendReview.putExtra("productInformation", bundleInformation);

                startActivity(intentSendReview);

            }
        });
        dialogCreate.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogCreate.show();
    }

    //Hàm nhận kết quả của những radiogroup Design
    private String resultDesign() {
        String result="";
        if (rdDesignXS.isChecked())
            result=rdDesignXS.getText().toString();
        if (rdDesignTot.isChecked())
            result=rdDesignTot.getText().toString();
        if (rdDesignBinhThuong.isChecked())
            result=rdDesignBinhThuong.getText().toString();
        if (rdDesignTe.isChecked())
            result=rdDesignTe.getText().toString();
        return result;
    }

    //Hàm nhận kết quả của những radiogroup Configuration
    private String resultConfiguration() {
        String result="";
        if (rdConfigurationXS.isChecked())
            result=rdConfigurationXS.getText().toString();
        if (rdConfigurationTot.isChecked())
            result=rdConfigurationTot.getText().toString();
        if (rdConfigurationBinhThuong.isChecked())
            result=rdConfigurationBinhThuong.getText().toString();
        if (rdConfigurationTe.isChecked())
            result=rdConfigurationTe.getText().toString();
        return result;
    }

    //Hàm nhận kết quả của những radiogroup Camera
    private String resultCamera() {
        String result="";
        if (rdCameraXS.isChecked())
            result=rdCameraXS.getText().toString();
        if (rdCameraTot.isChecked())
            result=rdCameraTot.getText().toString();
        if (rdCameraBinhThuong.isChecked())
            result=rdCameraBinhThuong.getText().toString();
        if (rdCameraTe.isChecked())
            result=rdCameraTe.getText().toString();
        return result;
    }

    //Hàm nhận kết quả của những radiogroup Sound
    private String resultSound() {
        String result="";
        if (rdSoundXS.isChecked())
            result=rdSoundXS.getText().toString();
        if (rdSoundTot.isChecked())
            result=rdSoundTot.getText().toString();
        if (rdSoundBinhThuong.isChecked())
            result=rdSoundBinhThuong.getText().toString();
        if (rdSoundTe.isChecked())
            result=rdSoundTe.getText().toString();
        return result;
    }

    //Hàm nhận kết quả của những radiogroup Battery
    private String resultBattery() {
        String result="";
        if (rdBatteryXS.isChecked())
            result=rdBatteryXS.getText().toString();
        if (rdBatteryTot.isChecked())
            result=rdBatteryTot.getText().toString();
        if (rdBatteryBinhThuong.isChecked())
            result=rdBatteryBinhThuong.getText().toString();
        if (rdBatteryTe.isChecked())
            result=rdBatteryTe.getText().toString();
        return result;
    }

}
