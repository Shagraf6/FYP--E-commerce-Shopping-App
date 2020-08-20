package com.example.fyp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    String userid;
    String Orderid;
    String Addres;
    String Name;
    String PhoneNo;
    String totalAmount;
    String Status;
    String TimeStamp;
    List<cartData> Productlist;
public Order()
{

}
public Order(String totalAmount,List<cartData> productlist)
{
    this.totalAmount=totalAmount;
this.Productlist=new ArrayList<>();
    this.Productlist=productlist;
}
    public Order(String userid, String orderid, String addres, String name, String phoneNo, String totalAmount, List<cartData> productlist) {
        this.userid = userid;
        Orderid = orderid;
        Addres = addres;
        Name = name;
        PhoneNo = phoneNo;
        this.totalAmount = totalAmount;
        Productlist = productlist;
    }

    public Order(String userid, String orderid, String addres, String name, String phoneNo, String totalAmount, String status, String timeStamp, List<cartData> productlist) {
        this.userid = userid;
        Orderid = orderid;
        Addres = addres;
        Name = name;
        PhoneNo = phoneNo;
        this.totalAmount = totalAmount;
        Status = status;
        TimeStamp = timeStamp;
        Productlist = productlist;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getAddres() {
        return Addres;
    }

    public void setAddres(String addres) {
        Addres = addres;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public List<cartData> getProductlist() {
        return Productlist;
    }

    public void setProductlist(List<cartData> productlist) {
        Productlist = productlist;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }



    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }
}
