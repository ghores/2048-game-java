package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class GameView extends AppCompatImageView {

    private Paint paint;
    private Paint textPaint;
    private int width;
    private int height;
    private int[][] cells = new int[4][4];

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

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);

        resetGame();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    public void resetGame() {
        G.score = 0;
        G.updateScore();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j] = 0;
            }
        }
        dropNextNumber();
        dropNextNumber();
    }

    private void dropNextNumber() {
        boolean gameFinished = true;
        int freeCells = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cells[i][j] == 0) {
                    freeCells++;
                    if (freeCells > 1) {
                        gameFinished = false;
                        break;
                    }
                }
            }
        }
        if (gameFinished) {
            Toast.makeText(getContext(), "You lose!!!", Toast.LENGTH_SHORT).show();
        } else {
            if (!gameFinished) {
                while (true) {
                    int randomX = (int) Math.floor(Math.random() * 4);
                    int randomY = (int) Math.floor(Math.random() * 4);
                    if (cells[randomX][randomY] == 0) {
                        cells[randomX][randomY] = 2;
                        break;
                    }
                }
                invalidate();
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
                paint.setColor(Color.parseColor(getNumberColor(cells[i][j])));
                @SuppressLint("DrawAllocation") Rect rect = new Rect(
                        i * size + padding, j * size + padding,
                        (i + 1) * size - padding,
                        (j + 1) * size - padding);
                canvas.drawRect(rect, paint);
                if (cells[i][j] != 0) {
                    canvas.drawText(String.valueOf(cells[i][j]), (i + 0.5f) * size, (j + 0.60f) * size, textPaint);
                }
            }
        }
    }

    public void power(int i, int j) {
        cells[i][j] *= 2;
        G.score += cells[i][j];
        G.updateScore();
    }

    /*
     *      2   4
     *  2          2
     *      2
     *              8
     */

    /*
     *  2   4
     *  4
     *  2
     *  8
     */

    private void goToLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j > 0) {
                    int k = j;
                    while (k > 0 && cells[i][k - 1] == 0) {
                        cells[i][k - 1] = cells[i][k];
                        cells[i][k] = 0;
                        k--;
                    }
                    if (k > 0) {
                        if (cells[i][k - 1] == cells[i][k]) {
                            power(i, k - 1);
                            cells[i][k] = 0;
                        }
                    }
                }
            }
        }
        invalidate();
    }

    /*
     *      2  4
     *  2         2
     *      2
     *  8      2  8
     */

    /*
     *         2  4
     *            4
     *            2
     *      8  2  8
     */

    private void goToRight() {
        for (int i = 3; i >= 0; i--) {
            for (int j = 3; j >= 0; j--) {
                if (j < 3) {
                    int k = j;
                    while (k < 3 && cells[i][k + 1] == 0) {
                        cells[i][k + 1] = cells[i][k];
                        cells[i][k] = 0;
                        k++;
                    }
                    if (k < 3) {
                        if (cells[i][k + 1] == cells[i][k]) {
                            power(i, k + 1);
                            cells[i][k] = 0;
                        }
                    }
                }
            }
        }
        invalidate();
    }

    /*
     *      2  4
     *  2         2
     *      2
     *  8      2  8
     */

    /*
     *   2  4  4  2
     *   8     2  8
     *
     *
     */

    private void goToUp() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (i > 0) {
                    int k = i;
                    while (k > 0 && cells[k - 1][j] == 0) {
                        cells[k - 1][j] = cells[k][j];
                        cells[k][j] = 0;
                        k--;
                    }
                    if (k > 0) {
                        if (cells[k - 1][j] == cells[k][j]) {
                            power(k - 1, j);
                            cells[k][j] = 0;
                        }
                    }
                }
            }
        }
        invalidate();
    }


    /*
     *      2  4
     *  2         2
     *      2
     *  8      2  8
     */

    /*
     *
     *
     *   2     4  2
     *   8  4  2  8
     */
    private void goToDown() {
        for (int j = 3; j >= 0; j--) {
            for (int i = 3; i >= 0; i--) {
                if (i < 3) {
                    int k = i;
                    while (k < 3 && cells[k + 1][j] == 0) {
                        cells[k + 1][j] = cells[k][j];
                        cells[k][j] = 0;
                        k++;
                    }
                    if (k < 3) {
                        if (cells[k + 1][j] == cells[k][j]) {
                            power(k + 1, j);
                            cells[k][j] = 0;
                        }
                    }
                }
            }
        }
        invalidate();
    }

    private float lastDownX;
    private float lastDownY;

    private boolean movePending;

    public static final int MODE_LEFT = 1;
    public static final int MODE_RIGHT = 2;
    public static final int MODE_UP = 3;
    public static final int MODE_DOWN = 4;

    private String getNumberColor(int number) {
        switch (number) {
            case 0:
                return "#ccc0b3";
            case 2:
                return "#eee4da";
            case 4:
                return "#ede0c8";
            case 8:
                return "#f2b179";
            case 16:
                return "#f59563";
            case 32:
                return "#f67c5f";
            case 64:
                return "#f65e3b";
        }
        return "#ccc0b3";
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastDownX = event.getRawX();
                lastDownY = event.getRawY();
                movePending = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!movePending) {
                    return true;
                }

                int mode = 0;
                float diffX = lastDownX - event.getRawX();
                float diffY = lastDownY - event.getRawY();

                if (Math.abs(diffX) > 10 && Math.abs(diffX) > 2 * Math.abs(diffY)) {
                    if (diffX > 0) {
                        mode = MODE_LEFT;
                    } else {
                        mode = MODE_RIGHT;
                    }
                    movePending = false;
                }
                if (Math.abs(diffY) > 10 && Math.abs(diffY) > 2 * Math.abs(diffX)) {
                    if (diffY > 0) {
                        mode = MODE_UP;
                    } else {
                        mode = MODE_DOWN;
                    }
                    movePending = false;
                }

                switch (mode) {
                    case MODE_LEFT:
                        goToUp();
                        dropNextNumber();
                        break;

                    case MODE_RIGHT:
                        goToDown();
                        dropNextNumber();
                        break;

                    case MODE_UP:
                        goToLeft();
                        dropNextNumber();
                        break;

                    case MODE_DOWN:
                        goToRight();
                        dropNextNumber();
                        break;
                }
                break;
        }
        return true;
    }
}
