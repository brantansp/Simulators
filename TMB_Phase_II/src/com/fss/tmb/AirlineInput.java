package com.fss.tmb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
/*
 * AirlineInput.java
 *
 * Created on March 26, 2011, 6:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author jaffirdheenj  
 */
public class AirlineInput{
    private mPAY midlet;
    private String airTitle;
    private String title;
    public static String titleForInput;
    private String titleForList;
    private int adultsCount;
    private int childrenCount;
    private int infantCount;
    private int totalCount;
    private String titleArray[];
    private String requestArray[];
    private int countArray[];
    private String countStringArray[];
    private String numberString[] ={"First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Ninth","Tenth"};
    private Intent intent;
   // private Context context;
    
    /** Creates a new instance of AirlineInput */
    
    public AirlineInput() {        
        this.midlet = StaticStore.midlet;        
        adultsCount                 =  Integer.parseInt(midlet.airTotalAdults.trim());
        childrenCount               =  Integer.parseInt(midlet.airTotalChildren.trim());
        infantCount                 =  Integer.parseInt(midlet.airTotalInfants.trim());
        totalCount                  =  Integer.parseInt(midlet.airTotalTickets.trim());
        titleArray = new String[totalCount];
        requestArray = new String[totalCount];
        countArray = new int[totalCount];
        countStringArray = new String[totalCount];
        titleForList = "Booking Person Title";
        StaticStore.airlineTitle = titleForList;
    }
    
    public  AirlineInput(String airTitle){   
         int privateIndex;
         if(StaticStore.indexAir == 0) {
            privateIndex = 0;
        } else{
            privateIndex = StaticStore.indexAir / 6;
        }
         this.midlet = StaticStore.midlet;        
        adultsCount                 =  Integer.parseInt(midlet.airTotalAdults.trim());
        childrenCount               =  Integer.parseInt(midlet.airTotalChildren.trim());
        infantCount                 =  Integer.parseInt(midlet.airTotalInfants.trim());
        totalCount                  =  Integer.parseInt(midlet.airTotalTickets.trim());
        titleArray = new String[totalCount];
        requestArray = new String[totalCount];
        countArray = new int[totalCount];
        countStringArray = new String[totalCount];
        this.airTitle = airTitle;
        if(StaticStore.indexAir == 1000){
            StaticStore.airValues[1] = airTitle;
            StaticStore.airValues[4] = midlet.airTotalTickets.trim();
            title = "Booking Persons";
            titleForInput = title+" " + "Details";           
        }else{            
        	StaticStore.LogPrinter('i',">>>"+StaticStore.indexAir +">>>"+6*Integer.parseInt(midlet.airTotalTickets));
           // StaticStore.passValues[privateIndex + 2] = airTitle;
             StaticStore.passValues[StaticStore.indexAir + 2] = airTitle;
            if((airTitle.equals("Mrs"))||(airTitle.equals("Miss"))){
            //StaticStore.passValues[privateIndex + 5] = "Female";    
                StaticStore.passValues[StaticStore.indexAir + 5] = "Female";    
            }else{
            //StaticStore.passValues[privateIndex + 5] = "Male";
                StaticStore.passValues[StaticStore.indexAir + 5] = "Male";
             }
            
            title = setTitleForInput();
            if(titleArray[privateIndex].equals("Adult")){
             //StaticStore.passValues[privateIndex] =  "A";
                StaticStore.passValues[StaticStore.indexAir] =  "A";
            }else if(titleArray[privateIndex].equals("Child")){
             //StaticStore.passValues[privateIndex] =  "C";  
                StaticStore.passValues[StaticStore.indexAir] =  "C";  
            }else if(titleArray[privateIndex].equals("Infant")){
              //StaticStore.passValues[privateIndex] =  "I";
                StaticStore.passValues[StaticStore.indexAir] =  "I";
            }            
            
            titleForInput = title+" " + "Details";            
        }        
        StaticStore.airlineTitle = titleForInput;
    }
    
    public  AirlineInput(boolean temp){
    	 this.midlet = StaticStore.midlet;      
        adultsCount                 =  Integer.parseInt(midlet.airTotalAdults.trim());
        childrenCount               =  Integer.parseInt(midlet.airTotalChildren.trim());
        infantCount                 =  Integer.parseInt(midlet.airTotalInfants.trim());
        totalCount                  =  Integer.parseInt(midlet.airTotalTickets.trim());       
        if(StaticStore.indexAir  < (6 * totalCount)){            
            titleArray = new String[totalCount];
            requestArray = new String[totalCount];
            countArray = new int[totalCount];
            countStringArray = new String[totalCount];
            //this.midlet = midlet;           
            String temp2 = setTitleForInput();
            titleForList = setTitleForList();
            StaticStore.airlineTitle = titleForList;
        }else{
          //  midlet.sendAirlineSMS();
        }
    }
    
   
    private String setTitleForInput(){
        int privateIndex;
        String returnTitle;
        if(StaticStore.indexAir == 0) {
            privateIndex = 0;
        }else{
            privateIndex = StaticStore.indexAir / 6;
        }
        for(int i = 0;i < titleArray.length;i++){
            if(i < adultsCount)  {
                titleArray[i] = "Adult";
                requestArray[i] = "A";
            }else if(i < adultsCount + childrenCount){
                titleArray[i] = "Child";
                requestArray[i] = "C";
            }else{
                titleArray[i] = "Infant";
                requestArray[i] = "I";
            }
        }
        int j = 0;
        int k = 0;
        int l = 0;
        for(int i = 0;i < titleArray.length;i++){
            if(titleArray[i].equals("Adult"))   {
                j += 1;
                countArray[i] = j;
            }else  if(titleArray[i].equals("Child"))   {
                k += 1;
                countArray[i] = k;
            }else{
                l += 1;
                countArray[i] = l;
            }
        }
        for(int i = 0;i < countStringArray.length;i++){
            countStringArray[i] = numberString[countArray[i]-1];
        }
        returnTitle = countStringArray[privateIndex] + " " +titleArray[privateIndex];
        return  returnTitle;
    }
    private String setTitleForList(){
        int privateIndex;
        String returnTitle;
        if(StaticStore.indexAir == 0) {
            privateIndex = 0;
        } else{
            privateIndex = StaticStore.indexAir / 6;
        }
        //returnTitle = "SELECT"+ " " +countStringArray[privateIndex]+ " "+titleArray[privateIndex]+ " "+"TITLE";
        returnTitle = countStringArray[privateIndex]+ " "+titleArray[privateIndex]+ " "+"Title";
        return returnTitle;
    }
}
