package com.example.fyp.Behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.fyp.R;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;
import edmt.dev.advancednestedscrollview.MyViewGroupUtils;

public class CustomBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {
    public CustomBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull View dependency) {
        return dependency.getId()== R.id.toolbar_container;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);

        int fabHalHeight=child.findViewById(R.id.fab).getHeight()/2;
        setTopMargin(child.findViewById(R.id.card_view),fabHalHeight);
        int rvMaxHeight=child.getHeight()-fabHalHeight-child.findViewById(R.id.card_title).getHeight()
                -child.findViewById(R.id.card_sub_title).getHeight();
   //   ///  MaxHeightRecyclerView rv=child.findViewById(R.id.card_recycler_view);
     //   rv.setMaxHeight(rvMaxHeight);

        View cardContainer=child.findViewById(R.id.card_container);
        int toolbarContainerHeight=parent.getDependencies(child).get(0).getHeight();
        setPaddingTop(cardContainer,rvMaxHeight-toolbarContainerHeight);
        ViewCompat.offsetTopAndBottom(child,toolbarContainerHeight);
       // setPaddingBottom(rv,toolbarContainerHeight);
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull MotionEvent ev) {
        return ev.getActionMasked()==MotionEvent.ACTION_DOWN &&
                isTouchInChildBounds(parent,child,ev)
                && !isTouchInChildBounds(parent,child.findViewById(R.id.card_view),ev)
                && !isTouchInChildBounds(parent,child.findViewById(R.id.fab),ev);
    }

    private boolean isTouchInChildBounds(ViewGroup parent, View child, MotionEvent ev) {
        return MyViewGroupUtils.isPointInChildBounds(parent,child,(int)ev.getX(),(int)ev.getY());
    }

    private void setPaddingBottom(View view, int bottom) {
        if (view.getPaddingBottom() !=bottom){
            view.setPadding(view.getPaddingLeft(),view.getPaddingTop(),view.getPaddingRight(),bottom);
        }
    }

    private void setPaddingTop(View view, int top) {
        if(view.getPaddingTop()!=top){
            view.setPadding(view.getPaddingLeft(),top,view.getPaddingRight(),view.getPaddingBottom());
        }
    }

    private void setTopMargin(View view,int top){

        ViewGroup.MarginLayoutParams lp=(ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if(lp.topMargin != top)
        {
         lp.topMargin=top;
         view.setLayoutParams(lp);
        }
    }
}
