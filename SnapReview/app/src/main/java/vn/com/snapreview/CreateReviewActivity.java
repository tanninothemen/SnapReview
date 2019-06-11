package vn.com.snapreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.net.URISyntaxException;

public class CreateReviewActivity extends AppCompatActivity {
    public static Product product;
    public Bundle bundleInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        Intent intent=getIntent();
        if (intent==null){
            Toast.makeText(this, "Loi khong truyen thoong tin", Toast.LENGTH_SHORT).show();
        }
        bundleInformation=intent.getBundleExtra("productInformation");

        if(bundleInformation!=null){
            product= (Product) bundleInformation.getSerializable("product");
        }
    }
}
