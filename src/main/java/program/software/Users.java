package program.software;

public class Users {
    String firstName;
    String lastName;
    String email;
    String companyId;
    String companyName;
    String userId;
    String password;
    Company company;
    public Users(String firstname, String lastname, String email, Company company){
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.company = company;
    }
}
