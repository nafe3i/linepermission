package ma.youcode.lineperm.model;
public class User{

    private final    String login;
    private  final  String passwordHash;

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
