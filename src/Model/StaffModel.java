package Model;

public class StaffModel implements CSVParsable<StaffModel> {
    public String UserName;
    public String Password;
    public String Role;

    public StaffModel()
    {

    }

    public StaffModel(String userName, String password, String role)
    {
        this.UserName = userName;
        this.Password = password;
        this.Role = role;
    }

    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String userName)
    {
        this.UserName = userName;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {
        this.Password = password;
    }

    public String getRole()
    {
        return Role;
    }

    public void setRole(String role)
    {
        this.Role = role;
    }

    @Override
    public String toString() {
        return "{" +
                "UserName=" + UserName + ',' +
                "Password=" + Password + ',' +
                "Role=" + Role + ',' +
                '}';
    }

    @Override
    public StaffModel parseFromCSV(String[] values) {
        return new StaffModel(
                values[0],
                values[1],
                values[2]);
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{
                UserName,
                Password,
                Role
        };
    }
}
