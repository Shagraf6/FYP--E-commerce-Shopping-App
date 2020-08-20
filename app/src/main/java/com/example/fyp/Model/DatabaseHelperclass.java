package com.example.fyp.Model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DatabaseHelperclass {
    Context context;
    String id;
    int count=0;
    public DatabaseHelperclass(Context context,String id)
    {
        this.context=context;
        this.id=id;
        this.count=0;

    }
    public void DeleteCartDatabase()
    {
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+id, MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("DELETE FROM Cart"+id+" WHERE Id="+0+"");
         sqLiteDatabase.execSQL("DELETE FROM Cart"+id+" WHERE Id="+1+"");

    }

    public void createCartDatabase()
    {
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+id, MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Cart"+id+"(Id INT,value INT)");
        //sqLiteDatabase.execSQL("DELETE FROM Cart WHERE Id="+0+"");
       // sqLiteDatabase.execSQL("DELETE FROM Cart WHERE Id="+1+"");
        //sqLiteDatabase.execSQL("INSERT INTO Cart(Id,value) VALUES(0,0)");
        //sqLiteDatabase.execSQL("INSERT INTO Cart(Id,value) VALUES(1,0)");

    }
  public void setCartSize(int cartCounter)
    {
        int inc_counter=getcartData("0")+cartCounter;
        Log.i("setcartSize",inc_counter+"");
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+id, MODE_PRIVATE, null);
    if(iscrtempty()) {
        Log.i("set",inc_counter+"**************");
        sqLiteDatabase.execSQL("INSERT INTO Cart"+id+"(Id,value) VALUES(0,"+inc_counter+")");
    }else
        sqLiteDatabase.execSQL("UPDATE  Cart"+id+" SET value=" + inc_counter + " WHERE Id=0");
    }

    public void setCarttotalAmount(int new_amount)
    {
        int inc_counter=getcartData("1")+new_amount;
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+id, MODE_PRIVATE, null);
        if(iscrtempty()||getcartData("1")==0) {
            Log.i("set", inc_counter + "emmptycaarrrrt**************");
            sqLiteDatabase.execSQL("INSERT INTO Cart"+id+"(Id,value) VALUES(1," + new_amount + ")");
        }
        else
            Log.i("set", inc_counter + "emmptycaarrrrt**************nootttt");
            sqLiteDatabase.execSQL("UPDATE  Cart"+id+" SET value=" + inc_counter + " WHERE Id=1");
    }

    public int getcartData(String id)
    {
        int data=0;
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+this.id, MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Cart"+this.id+" WHERE Id="+id, null);
        int cartData = cursor.getColumnIndex("value");
        cursor.moveToFirst();
        boolean chk = cursor.isLast();
        if(chk==true)
        {
            data = cursor.getInt(cartData);
        cursor.close();
        return data;
        }
        if (cursor.getCount() <=0||cursor==null)
            chk = true;
        while (chk == false) {
            Log.i("helloo","world");
            data = cursor.getInt(cartData);
            cursor.moveToNext();
            chk = cursor.isLast();
            if (chk == true)
                break;
        }
//            data=cursor.getInt(cartData);
        Log.i("id=",id+" , value= "+data);
        cursor.close();
        return data;
    }
    public void createFavDatabase( )
    {
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Fav"+id, MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Fav"+this.id+"(favId VARCHAR)");
    }

    public void addToFavorite(String id)
    {
        SQLiteDatabase state = context.openOrCreateDatabase("Fav"+this.id, MODE_PRIVATE, null);
        state.execSQL("INSERT INTO Fav"+this.id+"(favId) VALUES('"+id+"')");
    }
    public void removeFromFavroties(String id)
    {
        SQLiteDatabase state = context.openOrCreateDatabase("Fav"+this.id, MODE_PRIVATE, null);
        state.execSQL("DELETE FROM Fav"+this.id+" WHERE favId='"+id+"'");
    }
    public boolean isempty()
    {
        boolean empty = false;
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Fav"+this.id, MODE_PRIVATE, null);
        Cursor cur = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM Fav"+this.id, null);
        cur.moveToFirst();
        int icount = cur.getInt(0);
        if(icount>0)
            empty=false;//leave
         else
      empty= true;
        Log.i("id=",id+" , value= "+empty);

        cur.close();
return empty;
//populate table
    }
    public boolean iscrtempty()
    {
        boolean empty = false;
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Cart"+this.id, MODE_PRIVATE, null);
        Cursor cur = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM Cart"+this.id, null);
        cur.moveToFirst();
        int icount = cur.getInt(0);
        if(icount>0) {
        this.count=icount;
            empty = false;//leave
        }
        else
            empty= true;
        Log.i("id=",id+" , value= "+empty);

        cur.close();
        return empty;
//populate table
    }

    public boolean isFav(String id)
    {
        String data=null;
         SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Fav"+this.id, MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Fav"+this.id+" WHERE favId= '"+id+"'", null);
        int cartData = cursor.getColumnIndex("favId");
        cursor.moveToFirst();
        boolean chk = cursor.isLast();
        if (cursor.getCount() == 0)
            chk = true;
        while (chk == false) {
            Log.i("helloo","world");
            chk = cursor.isLast();
            data = cursor.getString(cartData);
            cursor.moveToNext();
            if (chk == true)
                break;
        }
//            data=cursor.getInt(cartData);
        Log.i("id=",id+" , value= "+data);
        cursor.close();
    //    return data;
        if (data!=null)
        return true;
    return false;
    }

}
