package com.example.admin.shopsmart.pojos;

import java.io.Serializable;

public class Food implements Serializable{

    private String food_ID;
    private String food_Type;
    private double food_Amount_Off;
    private double food_Reduced_Price;
    private double food_Normal_Price;
    private String food_Shop_ID;
    private String food_Weight;
    private String food_Brand_Name;
    private String food_Specification;
    private String image;
    /*
    *** Duration must be entered in days....
     */
    private String food_Special_Duration;

    public Food(double food_Amount_Off,String image, String food_Brand_Name, String food_ID, double food_Normal_Price, double food_Reduced_Price, String food_Shop_ID, String food_Special_Duration, String food_Specification, String food_Type, String food_Weight) {
        this.food_Amount_Off = food_Amount_Off;
        this.food_Brand_Name = food_Brand_Name;
        this.food_ID = food_ID;
        this.food_Normal_Price = food_Normal_Price;
        this.food_Reduced_Price = food_Reduced_Price;
        this.food_Shop_ID = food_Shop_ID;
        this.food_Special_Duration = food_Special_Duration;
        this.food_Specification = food_Specification;
        this.food_Type = food_Type;
        this.food_Weight = food_Weight;
        this.image = image;
    }

    public Food(String food_ID, String food_Brand_Name, String food_Shop_ID) {
        this.food_ID = food_ID;
        this.food_Brand_Name = food_Brand_Name;
        this.food_Shop_ID = food_Shop_ID;
    }

    public Food() {
    }

    public double getFood_Amount_Off() {
        return food_Amount_Off;
    }

    public void setFood_Amount_Off(double food_Amount_Off) {
        this.food_Amount_Off = food_Amount_Off;
    }

    public String getFood_Brand_Name() {
        return food_Brand_Name;
    }

    public void setFood_Brand_Name(String food_Brand_Name) {
        this.food_Brand_Name = food_Brand_Name;
    }

    public double getFood_Normal_Price() {
        return food_Normal_Price;
    }

    public void setFood_Normal_Price(double food_Normal_Price) {
        this.food_Normal_Price = food_Normal_Price;
    }

    public String getFood_ID() {
        return food_ID;
    }

    public void setFood_ID(String food_ID) {
        this.food_ID = food_ID;
    }

    public double getFood_Reduced_Price() {
        return food_Reduced_Price;
    }

    public void setFood_Reduced_Price(double food_Reduced_Price) {
        this.food_Reduced_Price = food_Reduced_Price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFood_Shop_ID() {
        return food_Shop_ID;
    }

    public void setFood_Shop_ID(String food_Shop_ID) {
        this.food_Shop_ID = food_Shop_ID;
    }

    public String getFood_Special_Duration() {
        return food_Special_Duration;
    }

    public void setFood_Special_Duration(String food_Special_Duration) {
        this.food_Special_Duration = food_Special_Duration;
    }

    public String getFood_Specification() {
        return food_Specification;
    }

    public void setFood_Specification(String food_Specification) {
        this.food_Specification = food_Specification;
    }

    public String getFood_Type() {
        return food_Type;
    }

    public void setFood_Type(String food_Type) {
        this.food_Type = food_Type;
    }

    public String getFood_Weight() {
        return food_Weight;
    }

    public void setFood_Weight(String food_Weight) {
        this.food_Weight = food_Weight;
    }

    @Override
    public String toString() {
        return "Food{" +
                "food_Amount_Off=" + food_Amount_Off +
                ", food_ID=" + food_ID +
                ", food_Type='" + food_Type + '\'' +
                ", food_Reduced_Price=" + food_Reduced_Price +
                ", food_Normal_Price=" + food_Normal_Price +
                ", food_Shop_ID=" + food_Shop_ID +
                ", food_Weight='" + food_Weight + '\'' +
                ", food_Brand_Name='" + food_Brand_Name + '\'' +
                ", food_Specification='" + food_Specification + '\'' +
                ", food_Special_Duration=" + food_Special_Duration +
                '}';
    }
}
