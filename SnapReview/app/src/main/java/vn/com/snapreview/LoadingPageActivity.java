package vn.com.snapreview;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingPageActivity extends AppCompatActivity {
    //Khai báo biến toàn cục
    ProgressBar pgbLoadingApp;
    TextView txtLoadingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        //Ánh xạ
        pgbLoadingApp=(ProgressBar) findViewById(R.id.progressBarLoadingApp);
        txtLoadingTime=(TextView) findViewById(R.id.textViewTimeLoading);

        //Gọi hàm LoadingProgress()
        LoadingProgress();

    }

    //Hàm tải trang loading của ứng dụng
    private void LoadingProgress(){
        CountDownTimer loadingAppTime=new CountDownTimer(6000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current=pgbLoadingApp.getProgress();
                pgbLoadingApp.setProgress(current+2);
                txtLoadingTime.setText(current+"%");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(LoadingPageActivity.this, HomeActivity.class));
                finish();
            }
        }.start();
    }
}
