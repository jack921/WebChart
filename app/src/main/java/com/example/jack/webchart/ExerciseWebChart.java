package com.example.jack.webchart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/10/9.
 */

public class ExerciseWebChart extends View {

    //屏幕的宽度
    private int mScreemWidth;
    //屏幕的高度
    private int mScreemHight;
    //圆的线
    private Paint mCirclePaint;
    //圆区域的颜色
    private Paint mCirclePaintColor;
    //虚线
    private Paint mLineCircle;
    //圆点
    private Paint mCircleHoldPaint;
    //画字体
    private Paint mCenterCircle;
    //最外的圆的透明度
    private int mCircleAlpha1=0;
    //中间的圆的透明度
    private int mCircleAlpha2=0;
    //最内的圆的透明度
    private int mCircleAlpha3=0;
    //好友排名
    private int mFriendDranking=0;
    //达标天数
    private int mStandardDay=0;
    //平均步数
    private int mAverageCount=0;
    //好友排名的X轴坐标
    private float mFriendDrankingX=0;
    //好友排名的Y轴坐标
    private float mFriendDrankingY=0;
    //平均步数的X轴坐标
    private float mStandardDayX=0;
    //平均步数的Y轴坐标
    private float mStandardDayY=0;
    //达标天数的X轴坐标
    private float mAverageCountX=0;
    //达标天数的Y轴坐标
    private float mAverageCountY=0;
    //临时的View的半径
    private int tempCircleRadius=0;
    //View的半径
    private int circleRadius=0;
    //每个圆圈的间隔
    private float marginCircleSize=0;
    //圆的颜色
    private int circleColor=0;
    //朋友区域的颜色
    private int friendColor;
    //平均步数区域的颜色
    private int averageColor;
    //达标天数区域的颜色
    private int standardColor;
    //总步数
    private String allStep;
    //好友排名
    private String firendDrank;
    //达标天数
    private String standarDay;
    //平均步数
    private String averageCount;
    //波浪动画的数值
    private int waveData=-30;
    //中间文字翻转动画的数值
    private float centerData=0;
    //画波浪的看门狗
    private boolean waveWatchDag=false;
    //画虚线的看门狗
    private boolean lineWatchDag=false;
    //各点解释的看门狗
    private boolean expainWatchDag=false;
    //中心圆的内容的看门狗
    private boolean centerWatchDag=false;
    //解释的字符串
    private String averageCountTxt="平均步数";
    private String friendDrankTxt="好友排名";
    private String standarDayTxt="达标天数";
    private String theyCount="本周总步数";
    private String tip="步";

    public ExerciseWebChart(Context context) {
        this(context,null);
    }

    public ExerciseWebChart(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ExerciseWebChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs,R.styleable.ExerciseWebChart,defStyleAttr,0);
        int numCount=typedArray.getIndexCount();
        for(int i=0;i<numCount;i++){
            int attr=typedArray.getIndex(i);
            switch(attr){
                case R.styleable.ExerciseWebChart_frienDranking:
                    mFriendDranking=typedArray.getInt(attr,0);
                    break;
                case R.styleable.ExerciseWebChart_standardDay:
                    mStandardDay=typedArray.getInt(attr,0);
                    break;
                case R.styleable.ExerciseWebChart_averageCount:
                    mAverageCount=typedArray.getInt(attr,0);
                    break;
                case R.styleable.ExerciseWebChart_circleSize:
                    tempCircleRadius=typedArray.getInt(attr,0);
                    break;
                case R.styleable.ExerciseWebChart_circleColor:
                    circleColor=typedArray.getColor(attr,Color.parseColor("#BCBCBC"));
                    break;
                case R.styleable.ExerciseWebChart_friendColor:
                    friendColor=typedArray.getColor(attr,Color.parseColor("#32B7EB"));
                    break;
                case R.styleable.ExerciseWebChart_averageColor:
                    averageColor=typedArray.getColor(attr,Color.parseColor("#FBD700"));
                    break;
                case R.styleable.ExerciseWebChart_standardColor:
                    standardColor=typedArray.getColor(attr,Color.parseColor("#39E100"));
                    break;
                case R.styleable.ExerciseWebChart_allStep:
                    allStep=typedArray.getString(attr);
                    break;
                case R.styleable.ExerciseWebChart_friendDrankData:
                    firendDrank=typedArray.getString(attr);
                    break;
                case R.styleable.ExerciseWebChart_standarDayData:
                    standarDay=typedArray.getString(attr);
                    break;
                case R.styleable.ExerciseWebChart_averageCountData:
                    averageCount=typedArray.getString(attr);
                    break;
            }
        }
        typedArray.recycle();
        initValue();
    }

    public void initValue(){
        mCirclePaint=new Paint();
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setStrokeWidth(2);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mCirclePaintColor=new Paint();
        mCirclePaintColor.setAntiAlias(true);
        mCirclePaintColor.setStyle(Paint.Style.FILL_AND_STROKE);

        mLineCircle=new Paint();
        mLineCircle.setAntiAlias(true);
        mLineCircle.setStyle(Paint.Style.STROKE);

        mCircleHoldPaint=new Paint();
        mCircleHoldPaint.setAntiAlias(true);
        mCircleHoldPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCenterCircle=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterCircle.setAntiAlias(true);
        mCenterCircle.setStyle(Paint.Style.STROKE);
        mCenterCircle.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthModel=MeasureSpec.getMode(widthMeasureSpec);
        int heightModel=MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if(widthModel==MeasureSpec.EXACTLY){
            width=measureWidth;
        }else{
            width=getPaddingLeft()+getPaddingRight()+measureWidth;
        }
        if(heightModel==MeasureSpec.EXACTLY){
            height=measureHeight;
        }else{
            height=(getPaddingLeft()+getPaddingRight()+measureHeight)/2;
        }
        setMeasuredDimension(width,height);
        loadAnimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreemWidth=w;
        mScreemHight=h;
        //得出最大的圆的半径
        if(mScreemWidth>mScreemHight){
            circleRadius=Float.valueOf((w/3.4)+"").intValue();
        }else{
            circleRadius=Float.valueOf((h/3.4)+"").intValue();
        }
        if(tempCircleRadius!=0&&tempCircleRadius<=circleRadius){
            circleRadius=tempCircleRadius;
        }
        //得出每个圆的间隔
        marginCircleSize=circleRadius/6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2-(circleRadius/6));
        canvas.save();
        //画出三条圆圈
        drawCircle(canvas);
        //画出波浪图形
        drawWaves(canvas);
        //画虚线
        drawDottedLine(canvas);
        //画点
        drawCircleHold(canvas);
        //画解释的内容
        drawExpain(canvas);
        //画中心圆的内容
        centerCircleContent(canvas);
    }

    //画解释的内容
    public void drawExpain(Canvas canvas){
        if(!expainWatchDag){
            return ;
        }
        //
        int margin=circleRadius/5;
        //画平均步数和对应的数值
        Rect txtRect=new Rect();
        mCenterCircle.setColor(Color.BLACK);
        mCenterCircle.setTextSize(circleRadius/6);
        mCenterCircle.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText(averageCount,0,circleRadius+margin,mCenterCircle);
        mCenterCircle.setColor(friendColor);
        mCenterCircle.setTextSize(circleRadius/10);
        mCenterCircle.getTextBounds(averageCountTxt,0,averageCountTxt.length(),txtRect);
        canvas.drawText(averageCountTxt,0,circleRadius+margin+(txtRect.bottom-txtRect.top),mCenterCircle);
        //画好友排名和对应的数值
        mCenterCircle.setColor(Color.BLACK);
        mCenterCircle.setTextSize(circleRadius/6);
        canvas.drawText(firendDrank,-circleRadius,-(circleRadius-marginCircleSize),mCenterCircle);
        mCenterCircle.setColor(friendColor);
        mCenterCircle.setTextSize(circleRadius/10);
        mCenterCircle.getTextBounds(friendDrankTxt,0,friendDrankTxt.length(),txtRect);
        canvas.drawText(friendDrankTxt,-circleRadius,-(circleRadius-marginCircleSize)+(txtRect.bottom-txtRect.top),mCenterCircle);
        //画达标天数和对应的数值
        mCenterCircle.setColor(Color.BLACK);
        mCenterCircle.setTextSize(circleRadius/6);
        canvas.drawText(standarDay,circleRadius,-(circleRadius-marginCircleSize),mCenterCircle);
        mCenterCircle.setColor(friendColor);
        mCenterCircle.setTextSize(circleRadius/10);
        mCenterCircle.getTextBounds(friendDrankTxt,0,friendDrankTxt.length(),txtRect);
        canvas.drawText(standarDayTxt,circleRadius,-(circleRadius-marginCircleSize)+(txtRect.bottom-txtRect.top),mCenterCircle);

        centerWatchDag=true;
    }

    //画中心圆的内容
    public void centerCircleContent(Canvas canvas){
        if(!centerWatchDag){
            return ;
        }
        //画出颜色渐变的圆圈
        canvas.rotate(140);
        float centerSize=circleRadius-marginCircleSize*3-(circleRadius/20);
        mCenterCircle.setShader(new SweepGradient(0,0,new int[]{
                friendColor,friendColor,standardColor,averageColor},null));
        canvas.drawCircle(0,0,centerSize,mCenterCircle);
        canvas.rotate(-140);
        //画出运动的总步数
        mCenterCircle.setShader(null);
        mCenterCircle.setColor(friendColor);
        mCenterCircle.setTextSize(circleRadius/4);
        mCenterCircle.setTextAlign(Paint.Align.CENTER);
        Rect numRect=new Rect();
        mCenterCircle.getTextBounds(allStep,0,allStep.length(),numRect);
        Camera camera=new Camera();
        camera.rotateY(centerData);
        camera.applyToCanvas(canvas);
        canvas.drawText(allStep,0,(numRect.bottom-numRect.top)/2,mCenterCircle);
        //画出总运动步数右边的字
        Rect tipRect=new Rect();
        mCenterCircle.setTextSize(circleRadius/12);
        mCenterCircle.getTextBounds(tip,0,tip.length(),tipRect);
        canvas.drawText(tip,(numRect.right-numRect.left)/2+(tipRect.right-tipRect.left)/2+5
                ,(numRect.bottom-numRect.top)/2-3,mCenterCircle);
        //画出总运动步数下面的提示
        Rect theyRect=new Rect();
        mCenterCircle.getTextBounds(theyCount,0,theyCount.length(),theyRect);
        float marginBottom=circleRadius/12;
        mCenterCircle.setTextSize(circleRadius/11);
        canvas.drawText(theyCount,0,marginBottom+(numRect.bottom-numRect.top)/2
                +(theyRect.bottom-theyRect.top)/2,mCenterCircle);
    }

    //画圆点和虚线
    public void drawDottedLine(Canvas canvas){
        if(!lineWatchDag){
            return;
        }
        for(int i=0;i<3;i++){
            canvas.rotate(120);
            if(i==0){
                //画好友排名的虚线
                mLineCircle.setTextSize(18);
                mLineCircle.setColor(friendColor);
                drawDottedLine(canvas,judgeDotte(mFriendDranking));
            }else if(i==1){
                //画达标天数的虚线
                mLineCircle.setColor(standardColor);
                drawDottedLine(canvas,judgeDotte(mStandardDay));
            }else if(i==2){
                //画平均步数的虚线
                mLineCircle.setColor(averageColor);
                drawDottedLine(canvas,judgeDotte(mAverageCount));
            }
        }
        canvas.restore();
    }

    //判断虚线
    public List<Float> judgeDotte(int value){
        List<Float> temp=new ArrayList<>();
        if(value==1){
            temp.add(circleRadius-marginCircleSize*2);
            temp.add((float)circleRadius);
            temp.add(circleRadius-marginCircleSize*3);
        }else if(value==2){
            temp.add(circleRadius-marginCircleSize);
            temp.add((float)circleRadius);
            temp.add(circleRadius-marginCircleSize*3);
        }else if(value==3){
            temp.add(circleRadius-marginCircleSize*3);
            temp.add((float)circleRadius);
        }
        return temp;
    }

    //画虚线
    public void drawDottedLine(Canvas canvas,List<Float> data){
        if(data.size()==2){
            /*当数值是最大的是时候也就是3*/
            mLineCircle.setColor(Color.WHITE);
            Path path=new Path();
            path.moveTo(0,data.get(0));
            path.lineTo(0,data.get(1));
            canvas.drawPath(path,mLineCircle);
            return ;
        }else{
            /*当数值在1和2的时候*/
            //画出数值外的虚线
            Path pathOut=new Path();
            pathOut.moveTo(0,data.get(0));
            pathOut.lineTo(0,data.get(1));
            mLineCircle.setPathEffect(new DashPathEffect(new float[]{7,5,7,5},5));
            canvas.drawPath(pathOut,mLineCircle);
            //画出数值内的虚线
            Path pathIn=new Path();
            pathIn.moveTo(0,data.get(1));
            pathIn.lineTo(0,data.get(2));
            mLineCircle.setColor(Color.WHITE);
            canvas.drawPath(pathIn,mLineCircle);
        }
    }

    //画虚线上的圆点
    public void drawCircleHold(Canvas canvas){
        if(!lineWatchDag){
            return;
        }
        float[] yuan1=calculatePoint(circleRadius-marginCircleSize*2);
        float[] yuan2=calculatePoint(circleRadius-marginCircleSize);
        float[] yuan3=calculatePoint(circleRadius);
        //画好友排名的圆点
        drawCircleHoldImpl(-yuan1[0],-yuan1[1],-yuan2[0],-yuan2[1],
                -yuan3[0],-yuan3[1],mFriendDranking,canvas,friendColor);
        //画达标天数的圆点
        drawCircleHoldImpl(yuan1[0],-yuan1[1],yuan2[0],-yuan2[1],
                yuan3[0],-yuan3[1],mStandardDay,canvas,standardColor);
        //画平均步数的圆点
        drawCircleHoldImpl(0,circleRadius-marginCircleSize*2,0,circleRadius-marginCircleSize,
                0,circleRadius,mAverageCount,canvas,averageColor);
        expainWatchDag=true;
    }

    //画圆的具体的方法
    public void drawCircleHoldImpl(float mCirlce1X,float mCircle1Y,float mCirlce2X,float mCircle2Y,
               float mCirlce3X,float mCircle3Y,int action,Canvas canvas,int color){
        mCircleHoldPaint.setColor(color);
        if(action==1){
            //当数值为3时画所有圆圈
            canvas.drawCircle(mCirlce1X,mCircle1Y,8,mCircleHoldPaint);
            canvas.drawCircle(mCirlce2X,mCircle2Y,8,mCircleHoldPaint);
        }else if(action==2){
            //当数值为2时画中间的圆圈
            canvas.drawCircle(mCirlce2X,mCircle2Y,8,mCircleHoldPaint);
        }
        //画一定要画的圆圈和圆点
        canvas.drawCircle(mCirlce3X,mCircle3Y,8,mCircleHoldPaint);
        mCircleHoldPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCirlce1X,mCircle1Y,6,mCircleHoldPaint);
        canvas.drawCircle(mCirlce2X,mCircle2Y,6,mCircleHoldPaint);
        canvas.drawCircle(mCirlce3X,mCircle3Y,6,mCircleHoldPaint);
    }

    //画出波浪图形
    public void drawWaves(Canvas canvas){
        if(!waveWatchDag){
            return ;
        }
        canvas.rotate(waveData);
        float inCircleRadius=circleRadius-marginCircleSize*3;
        //最上面的点
        float topPointX=0;
        float topPointY=-inCircleRadius;
        //左下角的点
        float leftBottpmPointX=-(float)Math.sqrt(Math.pow(inCircleRadius,2)-Math.pow(inCircleRadius/2,2));
        float leftBottomPointY=inCircleRadius/2;
        //右小角的点
        float rightBottomPointX=-leftBottpmPointX;
        float rightBottomPointY=inCircleRadius/2;
        //好友排名半径
        float mFriendDrankingData=circleValue(mFriendDranking);
        //达标天数半径
        float mStandarDayData=circleValue(mStandardDay);
        //平均步数半径
        float mAverageCountData=circleValue(mAverageCount);

        /*画好友排名*/
        //得出左上角的圆的坐标
        float[] mFriendDrankingPoint=calculatePoint(mFriendDrankingData);
        //好友排名的X轴坐标
        mFriendDrankingX=-mFriendDrankingPoint[0];
        //好友排名的Y轴坐标
        mFriendDrankingY=-mFriendDrankingPoint[1];
        //画出还有排名的波浪线
        Path mFriendDrankingPath=new Path();
        mFriendDrankingPath.moveTo(leftBottpmPointX,leftBottomPointY);
        mFriendDrankingPath.lineTo(mFriendDrankingX-6,mFriendDrankingY-6);
        mFriendDrankingPath.lineTo(topPointX,topPointY);
        mFriendDrankingPath.lineTo(topPointX+10,topPointY+10);
        mCirclePaintColor.setPathEffect(new CornerPathEffect(20));
        mCirclePaintColor.setColor(friendColor);
        canvas.drawPath(mFriendDrankingPath,mCirclePaintColor);

        /*画达标天数*/
        //得出右上角的圆的坐标
        float[] mStandarDayPoint=calculatePoint(mStandarDayData);
        //达标天数的X轴坐标
        mStandardDayX=mStandarDayPoint[0];
        //达标天数的Y轴坐标
        mStandardDayY=-mStandarDayPoint[1];
        //画出还有达标天数的波浪线
        Path mStandarDayPath=new Path();
        mStandarDayPath.moveTo(topPointX,topPointY);
        mStandarDayPath.lineTo(mStandardDayX+6,mStandardDayY-6);
        mStandarDayPath.lineTo(rightBottomPointX,rightBottomPointY);
        mStandarDayPath.lineTo(rightBottomPointX-10,rightBottomPointY+10);
        mCirclePaintColor.setColor(standardColor);
        canvas.drawPath(mStandarDayPath,mCirclePaintColor);

        /*平均步数*/
        //平均步数的X轴坐标
        mAverageCountX=0;
        //平均步数的Y轴坐标
        mAverageCountY=mAverageCountData;
        //画出还有平均步数的波浪线
        Path mAverageCountPath=new Path();
        mAverageCountPath.moveTo(rightBottomPointX,rightBottomPointY);
        mAverageCountPath.lineTo(topPointX,mAverageCountData+8);
        mAverageCountPath.lineTo(leftBottpmPointX,leftBottomPointY);
        mAverageCountPath.lineTo(leftBottpmPointX+10,leftBottomPointY+10);
        mCirclePaintColor.setColor(averageColor);
        canvas.drawPath(mAverageCountPath,mCirclePaintColor);

        //最里面的圆
        mCirclePaintColor.setColor(Color.WHITE);
        canvas.drawCircle(0,0,circleRadius-marginCircleSize*3,mCirclePaintColor);
    }

    //画出三条圆圈
    public void drawCircle(Canvas canvas){
        //画出最大的圆
        mCirclePaint.setAlpha(mCircleAlpha1);
        canvas.drawCircle(0,0,circleRadius,mCirclePaint);
        //画出第二大的圆
        mCirclePaint.setAlpha(mCircleAlpha2);
        canvas.drawCircle(0,0,circleRadius-marginCircleSize,mCirclePaint);
        mCirclePaintColor.setColor(Color.parseColor("#F1FCFE"));
        mCirclePaintColor.setAlpha(mCircleAlpha2);
        canvas.drawCircle(0,0,circleRadius-marginCircleSize-2,mCirclePaintColor);
        //画出第三大的圆
        mCirclePaint.setAlpha(mCircleAlpha3);
        canvas.drawCircle(0,0,circleRadius-marginCircleSize*2,mCirclePaint);
        mCirclePaintColor.setColor(Color.parseColor("#E7F9FE"));
        mCirclePaintColor.setAlpha(mCircleAlpha3);
        canvas.drawCircle(0,0,circleRadius-marginCircleSize*2-2,mCirclePaintColor);
    }

    //算出弧线区域的半径
    public float circleValue(int mDataDranking){
        if(mDataDranking==1){
            return circleRadius-marginCircleSize*2;
        }else if(mDataDranking==2){
            return circleRadius-marginCircleSize;
        }else if(mDataDranking==3){
            return circleRadius;
        }else{
            return circleRadius-marginCircleSize*2;
        }
    }

    //算出右上角或左上角的坐标
    public  float[] calculatePoint(float radius){
        float[] result=new float[2];
        float pointY=radius/2;
        float pointX=(float)Math.sqrt(Math.pow(radius,2)-Math.pow(pointY,2));
        result[0]=pointX;
        result[1]=pointY;
        return result;
    }

    //启动动画的方法
    public void loadAnimator(){
        final ValueAnimator alphaAmimator3=ValueAnimator.ofInt(0,225);
        final ValueAnimator alphaAmimator2=ValueAnimator.ofInt(0,225);
        final ValueAnimator wavesAminator=ValueAnimator.ofInt(-30,0);
        final ValueAnimator centerAnimator=ValueAnimator.ofFloat(0,360);
        ValueAnimator alphaAmimator1=ValueAnimator.ofInt(0,225);

        centerAnimator.setDuration(1000);
        centerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerData=(float)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        wavesAminator.setDuration(1000);
        wavesAminator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveData=(int)animation.getAnimatedValue();
                waveWatchDag=true;
                if(waveData==0&&lineWatchDag==false){
                    lineWatchDag=true;
                    centerAnimator.start();
                }
                postInvalidate();
            }
        });
        alphaAmimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleAlpha3=(int)animation.getAnimatedValue();
                postInvalidate();
                if(mCircleAlpha3==225){
                    wavesAminator.start();
                }
            }
        });
        alphaAmimator3.setDuration(250);
        alphaAmimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleAlpha2=(int)animation.getAnimatedValue();
                postInvalidate();
                if(mCircleAlpha2==225){
                    alphaAmimator3.start();
                }
            }
        });
        alphaAmimator2.setDuration(250);
        alphaAmimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleAlpha1=(int)animation.getAnimatedValue();
                postInvalidate();
                if(mCircleAlpha1==225){
                    alphaAmimator2.start();
                }
            }
        });
        alphaAmimator1.setDuration(250);
        alphaAmimator1.start();
    }

}
