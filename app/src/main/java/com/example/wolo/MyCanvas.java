package com.example.wolo;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
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
        canvas.drawColor(Color.BLACK);

        for(int i = 0; i < num_BBox; i++){
            //distance < 10 -> red
            if(distances[i] < 10){
                paint.setColor(Color.RED);
            }

            //distance < 50 -> orange
            else if(distances[i] < 50){
                paint.setColor(Color.rgb(255, 165, 0));
            }

            else{
                paint.setColor(Color.GREEN);
            }
            canvas.drawRoundRect(new RectF(lefts[i] * this.getWidth(), tops[i] * this.getHeight(), rights[i] * this.getWidth(), bottoms[i] * this.getHeight()), 20, 20, paint);
        }
    }
}
