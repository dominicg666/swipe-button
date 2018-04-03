package ui.dom.com.Button;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by Design-4 on 3/26/2018.
 */

public class Swipe extends RelativeLayout {
    private RelativeLayout background;
    TypedArray typedArray;
    private Boolean BUTTONSTATE;

    //layout
    private RelativeLayout Tez_strock_layout;
    private ImageView swipeButtonInner;
    private Drawable buttonDrawable;
    private LayoutParams layoutParamsStrock;
    private LinearLayout layer;
    private TextView Textvalue;
    //attrs
    private float Tez_strock_size;
    private int Tez_button_width;
    private int Tez_button_height;
    private float buttonLeftPadding;
    private float buttonTopPadding;
    private float buttonRightPadding;
    private float buttonBottomPadding;

    private float TezPaddingLeft;
    private float TezPaddingRight;
    private float TezPaddingTop;
    private float TezPaddingBottom;


    //lisner
    private float startX;
    private float startY;
    private int CLICK_ACTION_THRESHOLD = 200;
    private float initialY;

    private Paint paint;
    private ArrayList<RippleView> rippleViewList=new ArrayList<RippleView>();
    private ArrayList<RippleView> rippleViewList2=new ArrayList<RippleView>();
    AnimatorSet animatorSet;
    AnimatorSet animatorSet2;
    RippleView rippleView;

    private String topText;
    private String bottomText;
    private OnStateChangeListener onStateChangeListener;
    private OnActiveListener onActiveListener;
    private int active;



    public Swipe(Context context) {
        super(context);
        init(context, null, -1, -1);
    }
    public Swipe(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public Swipe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        BUTTONSTATE=false;
        background = new RelativeLayout(context);
        LayoutParams layoutParamsView = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsView.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        addView(background, layoutParamsView);

        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.Tez,
                    defStyleAttr, defStyleRes);

            TezPaddingLeft= typedArray.getDimension(R.styleable.Tez_tez_padding, 50);
            TezPaddingRight= typedArray.getDimension(R.styleable.Tez_tez_padding, 50);
            TezPaddingTop= typedArray.getDimension(R.styleable.Tez_tez_padding, 50);
            TezPaddingBottom= typedArray.getDimension(R.styleable.Tez_tez_padding, 50);
            background.setPadding((int) TezPaddingLeft,(int) TezPaddingTop,(int) TezPaddingRight,(int) TezPaddingBottom);


            Tez_button_width=(int) typedArray.getDimension(R.styleable.Tez_tez_button_width, 180);
            Tez_button_height=(int) typedArray.getDimension(R.styleable.Tez_tez_button_height, 180);

            Tez_strock_size= typedArray.getDimension(R.styleable.Tez_tez_strock_size, 36);
            Tez_strock_layout=new RelativeLayout(context);
            Tez_strock_layout.setGravity(Gravity.CENTER);
            layoutParamsStrock = new LayoutParams(
                    Tez_button_width+(int)Tez_strock_size*2,
                    Tez_button_height+(int)Tez_strock_size*2);

            layoutParamsStrock.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            background.addView(Tez_strock_layout, layoutParamsStrock);
            Tez_strock_layout.setBackground(ContextCompat.getDrawable(context, R.drawable.strock_shape));
            Tez_strock_layout.setPadding((int) Tez_strock_size,(int) Tez_strock_size,(int) Tez_strock_size,(int) Tez_strock_size);

            Drawable strockSolid = Tez_strock_layout.getBackground();
            if (strockSolid instanceof GradientDrawable) {
                ((GradientDrawable)strockSolid).setColor(typedArray.getColor(R.styleable.Tez_tez_strock_color,
                        ContextCompat.getColor(context,R.color.colorPrimaryDark)));
            }


            final ImageView swipeButton = new ImageView(context);
            this.swipeButtonInner = swipeButton;

            LayoutParams layoutParamsButton = new LayoutParams( Tez_button_width,  Tez_button_height);


            layoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            layoutParamsButton.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParamsButton.setMargins((int)Tez_strock_size,(int)Tez_strock_size,(int)Tez_strock_size,(int)Tez_strock_size);

            //swipeButtonInner.setImageDrawable(disabledDrawable);
            swipeButtonInner.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape));
            background.addView(swipeButtonInner, layoutParamsButton);

            //text Top
            final TextView textvalue = new TextView(context);
            this.Textvalue = textvalue;
            Textvalue.setGravity(Gravity.CENTER);

            LayoutParams layoutParamsText = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsText.addRule(RelativeLayout.ALIGN_TOP);
            Tez_strock_layout.addView(Textvalue, layoutParamsText);
            //Textvalue.setText(typedArray.getText(R.styleable.Tez_tez_top_value));
            Textvalue.setTextColor(typedArray.getColor(R.styleable.Tez_tez_text_color,
                    Color.WHITE));
            topText=typedArray.getText(R.styleable.Tez_tez_top_value).toString();
            bottomText=typedArray.getText(R.styleable.Tez_tez_bottom_value).toString();


            Drawable buttonSolid = swipeButtonInner.getBackground();
            if (strockSolid instanceof GradientDrawable) {
                ((GradientDrawable)buttonSolid).setColor(typedArray.getColor(R.styleable.Tez_tez_background_color,
                        ContextCompat.getColor(context,R.color.colorWhite)));
            }
            buttonDrawable = typedArray.getDrawable(R.styleable.Tez_tez_button_image);
            if(buttonDrawable==null)
                swipeButtonInner.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.tez));
            else
                swipeButtonInner.setImageDrawable(buttonDrawable);
            buttonTopPadding = typedArray.getDimension(R.styleable.Tez_tez_button_paddingTop, 0);
            buttonBottomPadding = typedArray.getDimension(R.styleable.Tez_tez_button_paddingBottom, 0);
            buttonLeftPadding= typedArray.getDimension(R.styleable.Tez_tez_button_paddingLeft, 0);
            buttonRightPadding = typedArray.getDimension(R.styleable.Tez_tez_button_paddingRight, 0);

            swipeButtonInner.setPadding((int) buttonLeftPadding,(int) buttonTopPadding,(int) buttonRightPadding,(int) buttonBottomPadding);
            wave();
            waveTwo();
            typedArray.recycle();
            // swipeButtonInner.setOnClickListener(getOnClickListener());
            Tez_strock_layout.setOnTouchListener(getButtonTouchListener());
            active=0;
            if(onStateChangeListener!=null){
                onStateChangeListener.onStateChange(active);
            }
            if (onActiveListener != null) {
                onActiveListener.onActive();
            }


        }


    }
    private OnTouchListener getButtonTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //TouchUtils.isTouchOutsideInitialPosition(event, swipeButtonInner,BUTTONSTATE)
                        startX = event.getX();
                        startY = event.getY();
                        for(RippleView rippleView:rippleViewList){
                            //if((swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<getHeight()-Tez_strock_size-TezPaddingBottom))
                            rippleView.setVisibility(GONE);

                        }
                        animatorSet.cancel();
                        for(RippleView rippleView:rippleViewList2){
                            //if((swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<getHeight()-Tez_strock_size-TezPaddingBottom))
                            rippleView.setVisibility(GONE);

                        }
                        animatorSet2.cancel();
                        return  true;
                    case MotionEvent.ACTION_CANCEL:
                        getOnClickListener();

                        return  true;
                    case MotionEvent.ACTION_MOVE:

//                        if (initialY == 0) {
//                            initialY = swipeButtonInner.getY();
//                        }

//                        if (event.getY() > swipeButtonInner.getHeight() / 2 &&
//                                event.getY() + swipeButtonInner.getHeight() / 2 < getHeight()-Tez_strock_size-TezPaddingTop) {
//                            swipeButtonInner.setY((event.getY() - swipeButtonInner.getHeight() / 2)-Tez_strock_size-TezPaddingTop);
//                        }

                        if(event.getY()>getHeight()/2){
                            active=2;
                            if(onStateChangeListener!=null){
                                onStateChangeListener.onStateChange(active);
                            }
                            if (onActiveListener != null) {
                                onActiveListener.onActive();
                            }
                            Textvalue.setText(bottomText);
                        }else if(event.getY()<getHeight()/2 ) {
                            active=1;
                            if(onStateChangeListener!=null){
                                onStateChangeListener.onStateChange(active);
                            }
                            if (onActiveListener != null) {
                                onActiveListener.onActive();
                            }
                            Textvalue.setText(topText);
                        }

                        if (event.getY() > swipeButtonInner.getHeight() / 2 &&
                                event.getY() + swipeButtonInner.getHeight() / 2 < getHeight()-Tez_strock_size-TezPaddingBottom &&
                                Tez_strock_layout.getHeight()>swipeButtonInner.getHeight()+Tez_strock_size*2) {
                            swipeButtonInner.setY((event.getY() - swipeButtonInner.getHeight() / 2)-Tez_strock_size+TezPaddingBottom);
                        }

                        if (event.getY() + swipeButtonInner.getHeight() / 2 > getHeight()-TezPaddingBottom-Tez_strock_size &&
                                swipeButtonInner.getY() + swipeButtonInner.getHeight() / 2 < getHeight()-TezPaddingBottom-Tez_strock_size &&
                                Tez_strock_layout.getHeight()>swipeButtonInner.getHeight()+Tez_strock_size*2) {
                            swipeButtonInner.setY((getHeight() - swipeButtonInner.getHeight())-TezPaddingBottom-Tez_strock_size);
                        }

                        if (event.getY() < swipeButtonInner.getHeight() &&
                                Tez_strock_layout.getHeight()>swipeButtonInner.getHeight()+Tez_strock_size*2) {
                            swipeButtonInner.setY(TezPaddingTop+Tez_strock_size);
                        }
                        // Toast.makeText(v.getContext(),event.getY()+"",Toast.LENGTH_SHORT).show();
                        return  true;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();



                        if (isAClick(startX, endX, startY, endY)) {
                            getOnClickListener();// WE HAVE A CLICK!!
                        }

                        if((getWidth()/2<swipeButtonInner.getY()) && (swipeButtonInner.getY()-swipeButtonInner.getHeight()/2>(getHeight()+swipeButtonInner.getHeight())/2)){

                            for(RippleView rippleView:rippleViewList){
                                //if((swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<getHeight()-Tez_strock_size-TezPaddingBottom))
                                rippleView.setVisibility(VISIBLE);

                            }
                            animatorSet.start();


                        }else if((getWidth()/2>swipeButtonInner.getY()) && (swipeButtonInner.getHeight()-swipeButtonInner.getY()>swipeButtonInner.getHeight()/2)){
                            for(RippleView rippleView:rippleViewList2){
                                //if((swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<getHeight()-Tez_strock_size-TezPaddingBottom))
                                rippleView.setVisibility(VISIBLE);

                            }
                            animatorSet2.start();
                        }

                        // getOnClickListener();
//                        if ((swipeButtonInner.getY()<swipeButtonInner.getHeight() &&  swipeButtonInner.getHeight()-swipeButtonInner.getY()<swipeButtonInner.getHeight()-Tez_strock_size)
//                                ||  (swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<Tez_strock_layout.getHeight()-Tez_strock_size)) {
//                            getOnClickListener();
//                        }


                        return  true;
                }
                return false;
            }
        };
    }

    private void wave(){
        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE); //round
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);

        LayoutParams rippleParams=new LayoutParams((int)(2*(50+6)),(int)(2*(50+6)));
        rippleParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        rippleParams.addRule(CENTER_HORIZONTAL, TRUE);
        //rippleParams.setMarginEnd((int) ((Tez_button_width/2)-(TezPaddingTop*1.2)));
        rippleParams.setMargins(0,0,0,(int) (TezPaddingBottom+Tez_strock_size*2));

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        for(int i=0;i<2;i++){
            rippleView=new RippleView(swipeButtonInner.getContext());
            addView(rippleView,rippleParams);
            rippleViewList.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, 4); //rippleScale 6
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * 2000/2);
            scaleXAnimator.setDuration(2000); //rippleDurationTime 3000
            animatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, 4);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * 2000/2);
            scaleYAnimator.setDuration(2000);
            animatorList.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * 2000/2);
            alphaAnimator.setDuration(2000);
            animatorList.add(alphaAnimator);
        }
        animatorSet.playTogether(animatorList);

    }
    private void waveTwo(){
        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE); //round
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);

        LayoutParams rippleParams=new LayoutParams((int)(2*(50+6)),(int)(2*(50+6)));
        rippleParams.addRule(ALIGN_PARENT_TOP, TRUE);
        rippleParams.addRule(CENTER_HORIZONTAL, TRUE);
        //rippleParams.setMarginEnd((int) ((Tez_button_width/2)-(TezPaddingTop*1.2)));
        rippleParams.setMargins(0,(int) (TezPaddingBottom+Tez_strock_size*2),0,0);

        animatorSet2 = new AnimatorSet();
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList=new ArrayList<Animator>();
        for(int i=0;i<2;i++){
            rippleView=new RippleView(swipeButtonInner.getContext());
            addView(rippleView,rippleParams);
            rippleViewList2.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, 4); //rippleScale 6
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * 2000/2);
            scaleXAnimator.setDuration(2000); //rippleDurationTime 3000
            animatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, 4);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * 2000/2);
            scaleYAnimator.setDuration(2000);
            animatorList.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * 2000/2);
            alphaAnimator.setDuration(2000);
            animatorList.add(alphaAnimator);
        }
        animatorSet2.playTogether(animatorList);

    }
    private class RippleView extends View{

        public RippleView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius=(Math.min(getWidth(),getHeight()))/2;
            canvas.drawCircle(radius,radius,radius-12f,paint);
        }
    }
    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
    }
    private void getOnClickListener(){
        if(!BUTTONSTATE){
            expandButton();
            BUTTONSTATE=true;
        }else {
            if ((swipeButtonInner.getY()<swipeButtonInner.getHeight() &&  swipeButtonInner.getHeight()-swipeButtonInner.getY()<swipeButtonInner.getHeight()-Tez_strock_size-TezPaddingBottom)
                    ||  (swipeButtonInner.getY()>swipeButtonInner.getHeight() && swipeButtonInner.getHeight()+swipeButtonInner.getY()<getHeight()-Tez_strock_size-TezPaddingBottom)) {
                //float collapse=((Tez_strock_layout.getHeight()/2)-(swipeButtonInner.getHeight()-Tez_strock_size/4));
                float collapse=((background.getHeight()/2)-swipeButtonInner.getHeight()/2);
                collapseButton(); //collapse startt

                moveButtonBack(collapse);
                active=0;
                if(onStateChangeListener!=null){
                    onStateChangeListener.onStateChange(active);
                }
                if (onActiveListener != null) {
                    onActiveListener.onActive();
                }
                //Toast.makeText(getContext(),"sf"+Tez_strock_size+""+collapse,Toast.LENGTH_SHORT).show();
                BUTTONSTATE = false;
            }
        }
    }
    private void expandButton() {
        ValueAnimator anim = ValueAnimator.ofInt(Tez_strock_layout.getMeasuredHeightAndState(),
                getHeight());
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = Tez_strock_layout.getLayoutParams();
                layoutParams.height = val;
                Tez_strock_layout.setLayoutParams(layoutParams);
            }
        });
        anim.start();

    }
    private void collapseButton() {
        ValueAnimator anim = ValueAnimator.ofInt(Tez_strock_layout.getMeasuredHeightAndState(),
                (int)(Tez_button_height+(Tez_strock_size*2)));
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = Tez_strock_layout.getLayoutParams();
                layoutParams.height = val;
                Tez_strock_layout.setLayoutParams(layoutParams);

            }
        });
        anim.start();
    }
    private void moveButtonBack(float collapse) {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(collapse, collapse);
        positionAnimator.setDuration(200);
        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) positionAnimator.getAnimatedValue();
                swipeButtonInner.setY(x);
            }
        });

        positionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //active = 1;
                active=0;
                if(onStateChangeListener!=null){
                    onStateChangeListener.onStateChange(active);
                }
                if (onActiveListener != null) {
                    onActiveListener.onActive();
                }
            }
        });
        //positionAnimator.setDuration(100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(positionAnimator);
        animatorSet.start();
    }





}