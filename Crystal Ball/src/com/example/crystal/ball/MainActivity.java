package com.example.crystal.ball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private CrystalBall mCrystalBall = new CrystalBall();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Declare our View variables and assign them the Views from the layout file
        final TextView answerLabel = (TextView) findViewById(R.id.textView1);
        Button getAnswerButton = (Button) findViewById(R.id.button1);
        
        getAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				String answer = mCrystalBall.getAnAnswer();
				
				// Update the label with our dynamic answer
				answerLabel.setText(answer);
				
				animateCrystalBall();
			}
		});
    }

    private void animateCrystalBall() {
    	ImageView crystalBallImage = (ImageView) findViewById(R.id.imageView1);
    	crystalBallImage.setImageResource(R.drawable.ball_animation);
    	AnimationDrawable ballAnimation = (AnimationDrawable) crystalBallImage.getDrawable();
    	if (ballAnimation.isRunning()){
    		ballAnimation.stop();
    	}
    	ballAnimation.start();
    }
    
    private void animateAnswer() {
    	
    	AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
    	fadeInAnimation.setDuration(1500);
    	fadeInAnimation.setFillAfter(true);
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
