package DataBase;

public class ContactBean {

	  //private variables
    int id;
    String password;
    String phone_number;
     
    // Empty constructor
    public ContactBean(){
         
    }
    // constructor
    public ContactBean(int id, String password, String phone_number){
        this.id = id;
        this.password = password;
        this.phone_number = phone_number;
    }
     
    // constructor
    public ContactBean(String password, String phone_number){
        this.password = password;
        this.phone_number = phone_number;
    }
    // getting ID
    public int getID(){
        return this.id;
    }
     
    // setting id
    public void setID(int id){
        this.id = id;
    }
     
    // getting name
    public String getPassword(){
        return this.password;
    }
     
    // setting name
    public void setPassword(String password){
        this.password = password;
    }
     
    // getting phone number
    public String getPhoneNumber(){
        return this.phone_number;
    }
     
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }
}
