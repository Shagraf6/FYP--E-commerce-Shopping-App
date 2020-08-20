package com.example.fyp.Interface;

import android.view.View;
import android.widget.AdapterView;

public interface OnitemSelectedListener {
  void onItemSelected (AdapterView<?> parent,
                                         View view,
                                         int position,
                                         long id);
}


