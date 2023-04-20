package com.example.wolo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyCanvas extends View {

    Paint paint;
    public int num_BBox;
    public float []lefts;
    public float []tops;
    public float []rights;
    public float []bottoms;
    public float []distances;

    public MyCanvas(Context context) {
        super(context);
        init();
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, 3, canvas.getWidth(), 3, paint);
        canvas.drawLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2, paint);


        //load image into bitmap
        Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        Rect srcRect;
        RectF destRect;

        //10m <1/3 show on the paint remove if run it real-time
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF(0, 30, (float) (canvas.getWidth()/3.5), (float)(canvas.getHeight()/2.2));
//        canvas.drawBitmap(car, srcRect, destRect, paint);
//
//        //10m 1/3~2/3
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF((float) (canvas.getWidth()/3.5) + 10, 30, (float) (canvas.getWidth()/3.5*2), (float)(canvas.getHeight()/2.2));
//        canvas.drawBitmap(car, srcRect, destRect, paint);
//
//        //10m 2/3~1
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF((float) (canvas.getWidth()/3.5 * 2), 30, (float) (canvas.getWidth()) - 200, (float)(canvas.getHeight()/2.2));
//        canvas.drawBitmap(car, srcRect, destRect, paint);
//
//        //20m <1/3
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF(0, (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()/3.5), canvas.getHeight()-10);
//        canvas.drawBitmap(car, srcRect, destRect, paint);
//
//        //20m 1/3~2/3
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF((float) (canvas.getWidth()/3.5) + 10, (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()/3.5*2), canvas.getHeight()-10);
//        canvas.drawBitmap(car, srcRect, destRect, paint);
//
//        //20m 2/3~1
//        srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
//        destRect = new RectF((float) (canvas.getWidth()/3.5 * 2), (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()) - 200, canvas.getHeight()-10);
//        canvas.drawBitmap(car, srcRect, destRect, paint);

        for(int i = 0; i < num_BBox; i++){
            //distance < 10
            if(distances[i] < 10){
                //toast msg to notify the driver
                Toast.makeText(getContext().getApplicationContext(), "Something Nearby",Toast.LENGTH_LONG).show();
                srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
                if(lefts[i] < 1/3){
                    destRect = new RectF(0, 30, (float) (canvas.getWidth()/3.5), (float)(canvas.getHeight()/2.2));
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
                else if(lefts[i] < 2/3){
                    destRect = new RectF((float) (canvas.getWidth()/3.5) + 10, 30, (float) (canvas.getWidth()/3.5*2), (float)(canvas.getHeight()/2.2));
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
                else{
                    destRect = new RectF((float) (canvas.getWidth()/3.5 * 2), 30, (float) (canvas.getWidth()) - 200, (float)(canvas.getHeight()/2.2));
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
            }

            //distance < 20
            else if(distances[i] < 20){
                srcRect = new Rect(0, 0, car.getWidth(), car.getHeight());
                if(lefts[i] < 1/3){
                    destRect = new RectF(0, (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()/3.5), canvas.getHeight()-10);
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
                else if(lefts[i] < 2/3){
                    destRect = new RectF((float) (canvas.getWidth()/3.5) + 10, (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()/3.5*2), canvas.getHeight()-10);
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
                else{
                    destRect = new RectF((float) (canvas.getWidth()/3.5 * 2), (float)(canvas.getHeight()/1.8), (float) (canvas.getWidth()) - 200, canvas.getHeight()-10);
                    canvas.drawBitmap(car, srcRect, destRect, paint);
                }
            }

            else{
                continue;
            }
            canvas.drawRoundRect(new RectF(lefts[i] * this.getWidth(), tops[i] * this.getHeight(), rights[i] * this.getWidth(), bottoms[i] * this.getHeight()), 20, 20, paint);
        }
    }
}