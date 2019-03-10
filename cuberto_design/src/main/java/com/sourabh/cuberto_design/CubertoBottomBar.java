package com.sourabh.cuberto_design;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class CubertoBottomBar extends LinearLayout {
    private LayoutInflater layoutInflater;
    private int lastSelectedTab = -1;
    private List<Integer> targetWidths = new ArrayList<>();
    private OnBottomBarTabSelectedListener onBottomBarTabSelectedListener;

    public void setOnBottomBarTabSelectedListener(OnBottomBarTabSelectedListener onBottomBarTabSelectedListener) {
        this.onBottomBarTabSelectedListener = onBottomBarTabSelectedListener;
    }

    public CubertoBottomBar(Context context) {
        super(context);
        init(context);
    }

    public CubertoBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CubertoBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CubertoBottomBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        setOrientation(HORIZONTAL);
    }

    public void addItem(final CubertoBottomBarItem item) {
        FrameLayout bottomBarItem = (FrameLayout) layoutInflater.inflate(R.layout.cuberto_bottom_bar_item, this, false);
        final View subItem = bottomBarItem.findViewById(R.id.bottom_bar_sub_item);
        final TextView tabName = bottomBarItem.findViewById(R.id.tab_name);
        ImageView tabImage = bottomBarItem.findViewById(R.id.tab_image);
        tabName.setText(item.getName());
        tabImage.setImageResource(item.getImageRes());
        if(item.getPosition()==0){
            ((FrameLayout.LayoutParams)subItem.getLayoutParams()).gravity = Gravity.LEFT|Gravity.CENTER_VERTICAL;
        }else if(item.getPosition()==3){
            ((LayoutParams)bottomBarItem.getLayoutParams()).weight = 1;
            ((FrameLayout.LayoutParams)subItem.getLayoutParams()).gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
            subItem.setMinimumWidth(0);
        }
        addView(bottomBarItem);
        tabName.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                targetWidths.add(item.getPosition(), tabName.getMeasuredWidth());
                tabName.setVisibility(GONE);
                tabName.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        setSelectionBehaviour(bottomBarItem, item);
    }

    private void setSelectionBehaviour(final View tabItem, final CubertoBottomBarItem item) {
        tabItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastSelectedTab != item.getPosition()) {
                    if (lastSelectedTab != -1) {
                        View lastSelectedTabItem = getChildAt(lastSelectedTab);
                        TextView tabName = getChildAt(lastSelectedTab).findViewById(R.id.tab_name);
                        scaleView(lastSelectedTabItem.findViewById(R.id.bottom_bar_sub_item),tabName,0,targetWidths.get(lastSelectedTab),false);
                    }
                    if(onBottomBarTabSelectedListener!=null){
                        onBottomBarTabSelectedListener.onTabSelected(item.getPosition());
                    }
                    if(item.getPosition()==getChildCount()-1){
                        lastSelectedTab = -1;
                        return;
                    }
                    scaleView(tabItem.findViewById(R.id.bottom_bar_sub_item),tabItem.findViewById(R.id.tab_name), targetWidths.get(item.getPosition()), 0,true);
                    lastSelectedTab = item.getPosition();
                }
            }
        });
    }

    private void scaleView(final View parentContainer,final View imageView, final int targetWidth, final int initialWidth, final boolean bool) {
        if(bool) {
            parentContainer.setBackgroundResource(R.drawable.bottom_bar_background);
            imageView.getLayoutParams().width = 0;
            imageView.setVisibility(VISIBLE);
        }
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                imageView.getLayoutParams().width = interpolatedTime == 1
                        ? targetWidth : (int) ((targetWidth - initialWidth) * interpolatedTime) + initialWidth;
                imageView.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!bool){
                    imageView.setVisibility(GONE);
                    parentContainer.setBackground(null);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        a.setDuration(400);
        imageView.startAnimation(a);
    }
    public interface OnBottomBarTabSelectedListener{
        void onTabSelected(int tabPosition);
    }
}