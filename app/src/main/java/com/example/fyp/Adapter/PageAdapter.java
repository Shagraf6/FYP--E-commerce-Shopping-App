package com.example.fyp.Adapter;

import com.example.fyp.Tab1Fragment;
import com.example.fyp.Tab2Fragment;
import com.example.fyp.Tab3Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter   {
int noOftabs;
static public int check=0;
static public String Category,Itemname;
    public PageAdapter(@NonNull FragmentManager fm, int noOftabs) {
        super(fm);
        this.noOftabs = noOftabs;
    }

    public PageAdapter(@NonNull FragmentManager fm, int behavior, int noOftabs) {
        super(fm, behavior);
        this.noOftabs = noOftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
     //   if (this.check == 2) {
            switch (position) {
                case 0:
                    Tab1Fragment tab1 = new Tab1Fragment();
                    return tab1;
                case 1:
                    //   Tab1Fragment tab2 = new Tab1Fragment();
                    Tab2Fragment tab2 = new Tab2Fragment();
                    return tab2;
                case 2:
                    //Tab1Fragment tab3 = new Tab1Fragment();
                    Tab3Fragment tab3 = new Tab3Fragment();
                    return tab3;
                default:
                    return null;

            }
            //    }
       /* } else {
            Log.i("check is o", "********" + Category + "=" + Itemname);
            switch (position) {
                case 0:
                  //  Tab2Fragment tab1 = new Tab2Fragment("Women", Itemname);
                      View1Fragment tab1 = new View1Fragment("Women",Itemname);
                    return tab1;
                case 1:
                   // Tab2Fragment tab2 = new Tab2Fragment("Mens", Itemname);
                   View2Fragment tab2 = new View2Fragment("Mens",Itemname);
                    return tab2;
                case 2: {
                    Log.i("case2", "********" + Category + "=" + Itemname);
                    View3Fragment tab3 = new View3Fragment("Kids", Itemname);
                    return tab3;
                }
                default:
                    return null;

            }
            //            Toast.makeText(PageAdapter.class,"check is 0",Toast.LENGTH_SHORT).show();
        }*/
    }




    @Override
    public int getCount() {
        return noOftabs;
    }
}
