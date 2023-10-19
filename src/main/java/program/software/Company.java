package program.software;

import java.util.LinkedList;
import java.util.List;

public class Company {
    public String companyName;
    public String companyId;
    List<Users> users = new LinkedList<>();
    public Company(String companyName, String companyId){
        this.companyId = companyId;
        this.companyName = companyName;
    }
    public void addUser(Users user){
        users.add(user);
    }
    public List<Users> getUsers(){
        return users;
    }
    public String getCompanyName(){
        return companyName;
    }
    public String getCompanyId(){
        return companyId;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }


}
