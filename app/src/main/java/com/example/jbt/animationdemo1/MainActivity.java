package com.example.jbt.animationdemo1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView;
    float degree = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);

        if(savedInstanceState!=null){
            imageView.setRotation(savedInstanceState.getFloat("rotation"));
        }

        findViewById(R.id.btnAni1).setOnClickListener(this);
        findViewById(R.id.btnAni2).setOnClickListener(this);
        findViewById(R.id.btnAni3).setOnClickListener(this);
        findViewById(R.id.btnAni4).setOnClickListener(this);
        findViewById(R.id.btnAni5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAni1:
                // Load the animation from the resource file
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.image_rotate_animation);
                imageView.startAnimation(animation);
                break;

            case R.id.btnAni2:
                degree = 300;
                if(imageView.getRotation() == degree){
                    degree = -300;

                }
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "rotation", degree);
                animator1.setDuration(1000);
                animator1.start();
                //degree *= 2;
                break;

            case R.id.btnAni3:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView, "translationX", -100);
                animator2.setDuration(2000);
                animator2.setInterpolator(new BounceInterpolator());

                ObjectAnimator animator3 = ObjectAnimator.ofFloat(textView, "translationY", 50);
                animator3.setDuration(3000);
                animator3.setInterpolator(new OvershootInterpolator(60));
                AnimatorSet set = new AnimatorSet();
                set.play(animator2).before(animator3);
                set.start();
                break;


            case R.id.btnAni4:
                //ObjectAnimator animator4 = ObjectAnimator.ofArgb(textView, "textColor", Color.RED); // API 21 and above!
                ObjectAnimator animator4 = ObjectAnimator.ofInt(textView, "textColor", Color.BLACK, Color.RED);
                animator4.setEvaluator(new ArgbEvaluator());
                animator4.setDuration(2000);
                animator4.setRepeatCount(5); //Animation.INFINITE
                // add a listener for the animator!
                animator4.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textView.setText("FINISHED!");
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        textView.setText("Repeat: ");
                    }
                });
                animator4.start();
                break;

            case R.id.btnAni5:
                float alpha = 1;
                if(imageView.getAlpha() == 1)
                    alpha = 0;
                ObjectAnimator animator5 = ObjectAnimator.ofFloat(imageView, "alpha", alpha);
                animator5.setDuration(3000);
                animator5.start();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putFloat("rotation", imageView.getRotation());
        super.onSaveInstanceState(outState);

    }
}
