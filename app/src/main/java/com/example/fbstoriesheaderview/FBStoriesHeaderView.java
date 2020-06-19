package com.example.fbstoriesheaderview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class FBStoriesHeaderView extends View {
    private int colorBackground;
    private int coloSelect;
    private int mSize;
    private Context mContext;
    private Paint mPaintBackGround;
    private Paint mPaintDefault;
    private int barHeight;
    private int widthView, heightView;
    private int paragraph;
    private int paddingParagraph;
    private int paddingTop, paddingBottom;
    private int tabSelect;
    private int endX, startX;

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

    public static int getWidthScreen(Context context) {
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int convertDpToPixel(float dp, Context context) {
        return (int) dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void initView(AttributeSet attrs) {
//        setSaveEnabled(true);
//        setBackgroundColor(Color.TRANSPARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.FBStoriesHeaderView, 0, 0);
        colorBackground = typedArray.getColor(R.styleable.FBStoriesHeaderView_colorBackGround, Color.parseColor("#80FFFFFF"));
        coloSelect = typedArray.getColor(R.styleable.FBStoriesHeaderView_colorSelect, Color.parseColor("#FFFFFF"));
        mSize = typedArray.getInt(R.styleable.FBStoriesHeaderView_size, 1);
        tabSelect = typedArray.getInt(R.styleable.FBStoriesHeaderView_tabSelect, 0);
        checkSize();
        checkTabSelect();

        barHeight = typedArray.getDimensionPixelSize(R.styleable.FBStoriesHeaderView_tabHeight, 5);
        paddingTop = convertDpToPixel(10, mContext);
        paddingBottom = convertDpToPixel(10, mContext);
        paddingParagraph = convertDpToPixel(4, mContext);
        setUpPaint();

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
        mPaintBackGround = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackGround.setColor(colorBackground);
        mPaintBackGround.setStyle(Paint.Style.FILL);

        mPaintDefault = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDefault.setColor(colorBackground);
        mPaintDefault.setStyle(Paint.Style.STROKE);
        mPaintDefault.setStrokeWidth(convertDpToPixel(5, mContext));
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

        if (widthMode == MeasureSpec.EXACTLY) {
            widthView = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthView = widthSize;
        } else {
            widthView = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heightView = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightView = Math.min(heightSize, barHeight);
        } else {
            heightView = heightSize;
        }

        if (mSize == 1)
            paragraph = (widthView - (mSize * convertDpToPixel(4, mContext))) / mSize;
        else paragraph = (widthView - ((mSize - 1) * convertDpToPixel(4, mContext))) / mSize;

        setMeasuredDimension(widthView, heightView + paddingBottom + paddingTop);
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
            canvas.drawLine(startX, paddingTop, endX + paragraph, paddingTop, mPaintDefault);
            startX = endX + paddingParagraph + paragraph;
            endX = startX;
        }
    }

    public void setTabSelect(int tabSelect) {
        this.tabSelect = tabSelect;
        invalidate();
    }
}
