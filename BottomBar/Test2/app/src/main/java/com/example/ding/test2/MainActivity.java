package com.example.ding.test2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends FragmentActivity implements OnClickListener{

	private fg1 fg1;
	private fg2 fg2;
	private fg3 fg3;

	private FrameLayout flayout;

	private RelativeLayout meet_layout;
	private RelativeLayout contacts_layout;
	private RelativeLayout settings_layout;

	private ImageView meet_image;
	private ImageView contacts_image;
	private ImageView settings_image;
	private TextView meet_text;
	private TextView settings_text;
	private TextView contacts_text;

	private int whirt = 0xFFFFFFFF;
	private int gray = 0xFF7597B3;
	private int blue =0xFF0AB2FB;

	FragmentManager fManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fManager = getSupportFragmentManager();
		initViews();
		meet_image.setImageResource(R.drawable.ic_tabbar_contacts_pressed);
		meet_text.setTextColor(blue);
		meet_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
		onClick(meet_layout);


	}

	
	public void initViews()
	{
		meet_image = (ImageView) findViewById(R.id.meet_image);
		contacts_image = (ImageView) findViewById(R.id.contacts_image);
		settings_image = (ImageView) findViewById(R.id.setting_image);
		meet_text = (TextView) findViewById(R.id.meet_text);
		contacts_text = (TextView) findViewById(R.id.contacts_text);
		settings_text = (TextView) findViewById(R.id.setting_text);
		meet_layout = (RelativeLayout) findViewById(R.id.meet_layout);
		contacts_layout = (RelativeLayout) findViewById(R.id.contacts_layout);
		settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		meet_layout.setOnClickListener(this);
		contacts_layout.setOnClickListener(this);
		settings_layout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.meet_layout:
			setChioceItem(0);
			break;
	    case R.id.contacts_layout:
	    	setChioceItem(1);
	    	break;
	    case R.id.setting_layout:
	    	setChioceItem(2);
	    	break;
	    default:
			break;
		}
		
	}
	
	
	public void setChioceItem(int index)
	{

		FragmentTransaction transaction = fManager.beginTransaction();  
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			meet_image.setImageResource(R.drawable.ic_tabbar_contacts_pressed);
			meet_text.setTextColor(blue);
			meet_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
            if (fg1 == null) {  

                fg1 = new fg1();
                transaction.add(R.id.content, fg1);
            } else {  

                transaction.show(fg1);
            }  
            break;  

		case 1:
			contacts_image.setImageResource(R.drawable.ic_tabbar_contacts_pressed);
			contacts_text.setTextColor(blue);
			contacts_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
            if (fg2 == null) {  

                fg2 = new fg2();
                transaction.add(R.id.content, fg2);  
            } else {  

                transaction.show(fg2);
            }  
            break;      
		
		 case 2:
			settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);  
			settings_text.setTextColor(blue);
			settings_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
            if (fg3 == null) {  

                fg3 = new fg3();
                transaction.add(R.id.content, fg3);  
            } else {  

                transaction.show(fg3);
            }  
            break;                 
		}
		transaction.commit();
	}
	
	private void hideFragments(FragmentTransaction transaction) {
        if (fg1 != null) {  
            transaction.hide(fg1);  
        }  
        if (fg2 != null) {  
            transaction.hide(fg2);  
        }  
        if (fg3 != null) {  
            transaction.hide(fg3);  
        }  
    }  
		
	
	public void clearChioce()
	{
		meet_image.setImageResource(R.drawable.ic_tabbar_contacts_normal);
		meet_layout.setBackgroundColor(whirt);
		meet_text.setTextColor(gray);
		contacts_image.setImageResource(R.drawable.ic_tabbar_contacts_normal);
		contacts_layout.setBackgroundColor(whirt);
		contacts_text.setTextColor(gray);
		settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		settings_layout.setBackgroundColor(whirt);
		settings_text.setTextColor(gray);
	}

}
