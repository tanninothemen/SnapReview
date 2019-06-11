package vn.com.snapreview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;

public class CreateReviewImage extends View {
    Paint mPaint;
    TextPaint mTextPaint;
    Bitmap mBitmap;
    CreateReviewActivity reviewActivity;

    public CreateReviewImage(Context context) {
        this(context, null);
    }

    public CreateReviewImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateReviewImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }



    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(30);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Tọa độ của text Review
        int paddingY=70;
        int paddingX=320;

        float xNameText=0;
        float yNameText=25;

        float xBrandText=xNameText+paddingX;
        float yBrandText=yNameText+paddingY;

        float xTimeText=xNameText-paddingX;
        float yTimeText=yNameText+paddingY;

        float xDesignText=xNameText+paddingX;
        float yDesignText=yNameText+paddingY;

        float xConfigurationText=xNameText-paddingX;
        float yConfigurationText=yNameText+paddingY;

        float xCameraText=xNameText+paddingX;
        float yCameraText=yNameText+paddingY;

        float xSoundText=xNameText-paddingX;
        float ySoundText=yNameText+paddingY;

        float xBatteryText=xNameText+paddingX;
        float yBatteryText=yNameText+paddingY;

        float left=(getWidth()-getImageReview().getWidth())/2.0f;
        float top=(getHeight()-getImageReview().getHeight())/2.0f;

        canvas.drawBitmap(getImageReview(),left,top,mPaint);


//        canvas.drawText(getNameReview(),0,getNameReview().length(),xNameText,yNameText,mTextPaint);
//        canvas.drawText("Thương hiệu: "+getBrandReview(),xBrandText,yBrandText,mTextPaint);
//        canvas.drawText("Thời gian sử dụng: "+getTimeReview(),xTimeText,yTimeText,mTextPaint);
//        canvas.drawText("Cấu hình: "+getConfigurationReview(),xConfigurationText,yConfigurationText,mTextPaint);
//        canvas.drawText("Thiết kế: "+getDesignReview(),xDesignText,yDesignText,mTextPaint);
//        canvas.drawText("Camera: "+getCameraReview(),xCameraText,yCameraText,mTextPaint);
//        canvas.drawText("Âm thanh: "+getSoundReview(),xSoundText,ySoundText,mTextPaint);
//        canvas.drawText("Thời lượng pin: "+getBatteryReview(),xBatteryText,yBatteryText,mTextPaint);
        DrawTextReview(canvas, getNameReview(),xNameText,yNameText);
        DrawTextReview(canvas,"Thương hiệu: "+ getBrandReview(),xBrandText,yBrandText);
        DrawTextReview(canvas, "Thời gian dùng: "+getTimeReview(),xTimeText,yTimeText);
        DrawTextReview(canvas, "Thiết kế: "+getDesignReview(),xDesignText,yDesignText);
        DrawTextReview(canvas, "Cấu hình: "+getConfigurationReview(),xConfigurationText,yConfigurationText);
        DrawTextReview(canvas, "Camera: "+getCameraReview(),xCameraText,yCameraText);
        DrawTextReview(canvas, "Âm thanh: "+getSoundReview(),xSoundText,ySoundText);
        DrawTextReview(canvas, "Thời lượng pin: "+getBatteryReview(),xBatteryText,yBatteryText);
    }


    private String getNameReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductName();
    }

    private Bitmap getImageReview(){
        Product productReview=reviewActivity.product;
        //chuyển kiểu byte[] -> ảnh bitmap
        byte[] hinhAnh=productReview.getProductImage();
        Bitmap bitmapImage=BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);

        return bitmapImage;
    }

    private String getBrandReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductBrand();
    }

    private String getTimeReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductUsedTime();
    }

    private String getConfigurationReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductConfiguration();
    }

    private String getDesignReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductDesign();
    }

    private String getCameraReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductCamera();
    }

    private String getSoundReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductSound();
    }

    private String getBatteryReview(){
        Product productReview=reviewActivity.product;
        return productReview.getProductBattery();
    }

    private void DrawText(Canvas canvas, String text){
        CharSequence str=text;
        SpannableString wordToSpan=new SpannableString(str);
        wordToSpan.setSpan(new BackgroundColorSpan(Color.parseColor("#000000")),0,wordToSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordToSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#e5fcc2")),0,wordToSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StaticLayout staticLayout=new StaticLayout(wordToSpan,mTextPaint,getWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,false);
        staticLayout.draw(canvas);
    }

    private void DrawTextReview(Canvas canvas, String text, float dx, float dy){
        canvas.translate(dx,dy);
        DrawText(canvas,text);
    }
}
