package com.bu.meet.client;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bu.meet.R;


public class ButtomBar extends Activity implements
        android.view.View.OnClickListener {

    private ViewPager mViewPager;// 用来放置界面切换
    private PagerAdapter mPagerAdapter;// 初始化View适配器
    private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-03

    //Three tabs
    private RelativeLayout meet_layout;
    private RelativeLayout contacts_layout;
    private RelativeLayout settings_layout;

    //Three buttons
    private ImageView meet_image;
    private ImageView contacts_image;
    private ImageView settings_image;
    private TextView meet_text;
    private TextView settings_text;
    private TextView contacts_text;

    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int blue =0xFF0AB2FB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_buttom_bar);
        initView();
        initViewPage();

        meet_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
        meet_text.setTextColor(blue);
        meet_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);

        initEvent();
    }

    private void initEvent() {

        meet_layout.setOnClickListener(this);
        contacts_layout.setOnClickListener(this);
        settings_layout.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            /**
             *ViewPage左右滑动时
             */
            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg();
                        meet_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
                        meet_text.setTextColor(blue);
                        break;
                    case 1:
                        resetImg();
                        contacts_image.setImageResource(R.drawable.ic_tabbar_contacts_pressed);
                        break;
                    case 2:
                        resetImg();
                        settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 初始化设置
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
        //Initialize three buttons
        meet_image = (ImageView) findViewById(R.id.meet_image);
        contacts_image = (ImageView) findViewById(R.id.contacts_image);
        settings_image = (ImageView) findViewById(R.id.setting_image);
        //Initialize three relativeLayout
        meet_layout = (RelativeLayout) findViewById(R.id.meet_layout);
        contacts_layout = (RelativeLayout) findViewById(R.id.contacts_layout);
        settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);

        meet_text = (TextView) findViewById(R.id.meet_text);
        contacts_text = (TextView) findViewById(R.id.contacts_text);
        settings_text = (TextView) findViewById(R.id.setting_text);

//        meet_layout.setOnClickListener(this);
//        contacts_layout.setOnClickListener(this);
//        settings_layout.setOnClickListener(this);

    }

    /**
     * 初始化ViewPage
     */
    private void initViewPage() {

        // 初妈化四个布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View tab01 = mLayoutInflater.inflate(R.layout.activity_welcome, null);
        View tab02 = mLayoutInflater.inflate(R.layout.activity_friends, null);
        View tab03 = mLayoutInflater.inflate(R.layout.activity_profilenew, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);

        // 适配器初始化并设置
        mPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViews.get(position));

            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return mViews.size();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    /**
     * 判断哪个要显示，及设置按钮图片
     */
    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.meet_layout:
                resetImg();
                mViewPager.setCurrentItem(0);
                meet_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
                meet_text.setTextColor(blue);
                meet_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);

                break;
            case R.id.contacts_layout:
                resetImg();
                mViewPager.setCurrentItem(1);

                contacts_image.setImageResource(R.drawable.ic_tabbar_contacts_pressed);
                contacts_text.setTextColor(blue);
                contacts_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);


                break;
            case R.id.setting_layout:
                resetImg();
                mViewPager.setCurrentItem(2);

                settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);
                settings_text.setTextColor(blue);
                settings_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);

                break;
            default:
                break;
        }
    }

    /**
     * 把所有图片变暗
     */
    private void resetImg() {
        meet_image.setImageResource(R.drawable.ic_tabbar_found_normal);
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
