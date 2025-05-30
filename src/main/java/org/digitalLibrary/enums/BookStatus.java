package org.digitalLibrary.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public  enum BookStatus {
    Available("AVAILABLE"),
    Checked_out("CHECKEDOUT"),
    Returned("RETURNED"),
    Expired("EXPIRED"),
    Unavailable("UNAVAILAABLE");

    private String Bookstatus;

   BookStatus(String Bookstatus){
        this.Bookstatus= Bookstatus;
    }
    @JsonValue
    public String getBookstatus(){
       return this.Bookstatus;
    }
    @JsonCreator
    public static BookStatus fromString(String bookstatus){
       for(BookStatus bookStatus : BookStatus.values()){
           if(bookStatus.getBookstatus().equalsIgnoreCase(bookstatus)){
               return bookStatus;
           }
       }
       throw new IllegalArgumentException("Unknown status " + bookstatus);

    }

    public boolean canTransitionTo(BookStatus newstatus){
       switch (this){
           case Available :
               return newstatus == Checked_out;
           case Checked_out:
               return newstatus == Returned;
           case Returned:
               return newstatus == Available;
           case Expired:
               return newstatus == Unavailable;
           case Unavailable:
               return false;
           default:
               return false;
       }
    }
}
