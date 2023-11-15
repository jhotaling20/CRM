package program.software;

import Databases.TestDatabaseConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Company {
    public String companyName;
    public String companyId;
    public boolean isInitialized = false;
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
    public Company getCompanyFromDatabase(String companyId) throws Exception {
        String sql = "SELECT * FROM companies WHERE company_id = ?";
        try (Connection connection = new TestDatabaseConnection().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, companyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String foundCompanyId = rs.getString("company_id");
                    String companyName = rs.getString("company_name");
                    return new Company(companyName, foundCompanyId);
                } else {
                    return null;  // No company found with the provided ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
