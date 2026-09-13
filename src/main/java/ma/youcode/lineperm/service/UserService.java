package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UserService {

    private final Path saveFile = Path.of("src/main/resources/users.txt");
    private HashMap<String, User> users = new HashMap<>();

    public UserService() {
        load();
        // System.out.println(users);
    }

    public String signUp(String login, String password) {
        if (login == null || password == null) {
            return "Login et mot de passe ne peuvent pas etre vides.";
        }

        String nettLogin = login.trim();

        if (nettLogin.isEmpty() || password.isEmpty()) {
            return "Login et mot de passe ne peuvent pas etre vides.";
        }
        if (nettLogin.contains(":")) {
            return "Login ne peut pas contenir le caractere ':'.";
        }
        if (nettLogin.contains(" ")) {
            return "Login ne peut pas contenir le caractere ' '.";
        }
        if (users.containsKey(nettLogin)) {
            return "L'utilisateur est deja enregistre.";
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(nettLogin, passwordHash);
        System.out.println(user);
        users.put(nettLogin, user);
        save(user);

        return "Compte cree avec succes.";
    }

    public String login(String login, String password) {

        // boolean essay = false;
        String logs;

        if (!users.containsKey(login.trim())) {

            logs = "Login ou mot de passe incorrect.";
        } else {
            User user = users.get(login.trim());

            if (!BCrypt.checkpw(password, user.getPasswordHash())) {

                logs = "Login ou mot de passe incorrect.";
            } else {
                logs = "Connexion reussie.";
            }
        }

        return logs;
    }

    private void save(User user) {
        try {
            String line = user.getLogin() + ":" + user.getPasswordHash() + System.lineSeparator();
            Files.writeString(saveFile, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void load() {
        try {
            if (!Files.exists(saveFile)) {
                return;
            }
            List<String> lines = Files.readAllLines(saveFile);
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                User user = new User(parts[0], parts[1]);
                users.put(parts[0], user);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
        }
    }

    // public User getUser(String login) {
    //     return users.get(login);
    // }
}
