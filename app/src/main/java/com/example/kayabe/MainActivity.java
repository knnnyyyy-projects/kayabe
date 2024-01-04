package com.example.kayabe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to views
        CardView cardView = findViewById(R.id.card_view);
        ImageView csfpLogo = findViewById(R.id.csfpLogo);

        // Initially hide the CardView
        cardView.setVisibility(View.INVISIBLE);

        // Animate csfpLogo from center to top as CardView loads
        ObjectAnimator translateY = ObjectAnimator.ofFloat(csfpLogo, "translationY", 0, -500);
        translateY.setDuration(1000); // Adjust duration as needed

        // Use a Handler to delay the appearance of the CardView and start csfpLogo animation
        new Handler().postDelayed(() -> {
            // Show the CardView with animation after 1 second
            cardView.setVisibility(View.VISIBLE);
            int startSize = dpToPixels(this,260);
            int endSize = dpToPixels(this, 210);
            animateLogoSizeChange(csfpLogo, startSize, endSize); // Change logo size gradually
            translateY.start();
            animateCardView(cardView);
        }, 1000); // 1000 milliseconds = 1 second delay
    }

    private void animateCardView(CardView cardView) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(cardView, "scaleY", 0f, 1f);
        scaleX.setDuration(500); // Set the duration of the animation (in milliseconds)
        scaleY.setDuration(500);

        scaleX.start();
        scaleY.start();
    }

    private void animateLogoSizeChange(ImageView imageView, int startSize, int endSize) {
        ValueAnimator anim = ValueAnimator.ofInt(startSize, endSize);
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            imageView.getLayoutParams().width = val;
            imageView.getLayoutParams().height = val;
            imageView.requestLayout();
        });
        anim.setDuration(1000); // Adjust duration as needed
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    private int dpToPixels(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}