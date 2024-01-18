package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class GameView extends AppCompatImageView {

    private Paint paint;
    private int width;
    private int height;
    private byte[][] cells = new byte[4][4];

    public GameView(Context context) {
        super(context);
        initialize();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#aaaaaa"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        resetGame();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    private void resetGame() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j] = 0;
            }
        }
        dropNextNumber();
        dropNextNumber();
    }

    private void dropNextNumber() {
        while (true) {
            int randomX = (int) Math.floor(Math.random() * 4);
            int randomY = (int) Math.floor(Math.random() * 4);
            if (cells[randomX][randomY] == 0) {
                cells[randomX][randomY] = 2;
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = width / 4;
        int padding = 5;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                @SuppressLint("DrawAllocation") Rect rect = new Rect(
                        i * size + padding, j * size + padding,
                        (i + 1) * size - padding,
                        (j + 1) * size - padding);
                canvas.drawRect(rect, paint);
            }
        }
    }
}
