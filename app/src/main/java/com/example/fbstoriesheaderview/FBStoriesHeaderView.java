package com.example.fbstoriesheaderview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

public class FBStoriesHeaderView extends View {
    private int colorBackground;
    private int coloSelect;
    private int mSize;
    private Context mContext;
    private Paint mPaintDefault;
    private int barHeight;
    private int widthView, heightView;
    private float paragraph;
    private float paddingParagraph;
    private int padding;
    private int tabSelect;
    private float endX, startX;

    public FBStoriesHeaderView(Context context) {
        this(context, null);
    }

    public FBStoriesHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FBStoriesHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(attrs);
    }

    public static int convertDpToPixel(float dp, Context context) {
        return (int) dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void initView(AttributeSet attrs) {
        setSaveEnabled(true);
//        setBackgroundColor(Color.TRANSPARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.FBStoriesHeaderView, 0, 0);
        colorBackground = typedArray.getColor(R.styleable.FBStoriesHeaderView_colorBackGround, Color.parseColor("#80FFFFFF"));
        coloSelect = typedArray.getColor(R.styleable.FBStoriesHeaderView_colorSelect, Color.parseColor("#FFFFFF"));
        mSize = typedArray.getInt(R.styleable.FBStoriesHeaderView_size, 1);
        tabSelect = typedArray.getInt(R.styleable.FBStoriesHeaderView_tabSelect, 0);
        barHeight = typedArray.getDimensionPixelSize(R.styleable.FBStoriesHeaderView_tabHeight, 10);
        paddingParagraph = typedArray.getDimensionPixelSize(R.styleable.FBStoriesHeaderView_paddingRow, 8);
        checkSize();
        checkTabSelect();
        checkPaddingParagraph();

//        padding = convertDpToPixel(10, mContext);
//        paddingBottom = convertDpToPixel(10, mContext);
//        paddingParagraph = convertDpToPixel(4, mContext);
        setUpPaint();

    }

    private void checkPaddingParagraph() {
        if (paddingParagraph < 0) {
            paddingParagraph = 0;
        }
    }

    private void checkSize() {
        if (mSize <= 0) {
            mSize = 1;
        }
    }

    private void checkTabSelect() {
        if (tabSelect >= mSize) {
            tabSelect = mSize - 1;
        } else if (tabSelect < 0) {
            tabSelect = 0;
        } else {
            if (tabSelect != 0)
                tabSelect = tabSelect - 1;
        }
        if (mSize == 1) {
            tabSelect = 0;
        }
    }

    private void setUpPaint() {

        mPaintDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDefault.setColor(colorBackground);
        mPaintDefault.setStyle(Paint.Style.STROKE);
        mPaintDefault.setStrokeWidth(barHeight);
        mPaintDefault.setAlpha(255);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredHeight = barHeight + (2 * padding);

        if (widthMode == MeasureSpec.EXACTLY) {
            widthView = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthView = widthSize;
        } else {
            widthView = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heightView = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightView = Math.min(heightSize, barHeight);
        } else {
            heightView = desiredHeight;
        }

        padding = (heightView - barHeight);
        if (padding <= convertDpToPixel(20, mContext)) {
            padding = convertDpToPixel(20, mContext);
        }
        checkPaddingParagraph();
        if (mSize == 1)
            paragraph = (widthView - (mSize * paddingParagraph)) / mSize;
        else paragraph = (widthView - ((mSize - 1) * paddingParagraph)) / mSize;

        setMeasuredDimension(widthView, heightMode == MeasureSpec.EXACTLY ? heightView : heightView + padding - barHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = 0;
        endX = 0;
        for (int i = 0; i < mSize; i++) {
            if (i == tabSelect)
                mPaintDefault.setColor(coloSelect);
            else mPaintDefault.setColor(colorBackground);
            canvas.drawLine(startX, padding >> 1, endX + paragraph, padding >> 1, mPaintDefault);
            startX = endX + paddingParagraph + paragraph;
            endX = startX;
        }
    }

    public void setTabSelect(int tabSelect) {
        this.tabSelect = tabSelect;
        checkTabSelect();
        invalidate();
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
        invalidate();
    }

    public void setColoSelect(int coloSelect) {
        this.coloSelect = coloSelect;
        invalidate();
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
        checkSize();
        invalidate();
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        invalidate();
    }

    public void setPaddingParagraph(int paddingParagraph) {
        this.paddingParagraph = paddingParagraph;
        invalidate();
    }
}
