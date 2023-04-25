/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Model for Contact Objects. 
 * @author katil
 */
public class Contact {
    
  int contactID; 
  String contactName;
  String EMail;

  /** * Constructor for contact object. Creates the contact object using contactID, contactName, and Email.
     * @param contactID unique contactID
     * @param contactName contactName
     * @param EMail contact email address. */
  
    public Contact(int contactID, String contactName, String EMail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.EMail = EMail;
    }
  
    /**Contact constructor*/
  public Contact(){
  }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }
  
  
 
    
}
