package ma.youcode.lineperm.model;
public class User{

    private   String login;
    private  String passwordHash;

    public User(String login,String password) {
        this.login = login;
        this.passwordHash = password;
    }    
    public String getLogin(){
        return login;
    }
    public String getPasswordHash(){
        return passwordHash;
    }
}
way 