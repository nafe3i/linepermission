package ma.youcode.lineperm.ui;

import java.util.Scanner;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp {

    private UserService userService = new UserService();
    private FileService fileService = new FileService();
    private String currentUser = null;
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("=====================================");
        System.out.println("  LinPerm - gestion de fichiers & droits");
        System.out.println("=====================================");
        System.out.println("Non connecte. Commandes : signup | login | help | exit");
        // String[] commands = {"signup", "login", "logout", "exit"};
        // for (int i = 0; i < commands.length; i++) {
        //     System.out.println(c);
        // }
        if (currentUser != null) {
            System.out.println("logout");
        }
        System.out.println();

        while (true) {
            if (currentUser == null) {
                System.out.print("linperm> ");
            } else {
                System.out.print(currentUser + "@linperm> ");
            }

            String command = scanner.nextLine().trim();
            String[] commandClain = command.split("\\s+");
            // System.out.println(commandClain[0] + "   ===  " + commandClain[1]);

            switch (commandClain[0]) {
                case "signup":
                    handleSignup();
                    break;

                case "login":
                    handleLogin();
                    break;

                case "logout":
                    handleLogout();
                    break;

                case "exit":
                    System.out.println("Au revoir.");
                    return;

                case "help":
                    handleHelp();
                    break;
                // the cases of thesecond part.
                case "ls":
                    if (commandClain.length >= 2 && commandClain[1].equals("-l")) {
                        showFiles();
                    }
                    break;
                case "touch":
                    if (commandClain.length >= 2) {
                        createFile(commandClain[1], currentUser);
                    }
                    break;
                case "cat":
                    showFileContent(commandClain[1], currentUser);
                    break;
                case "nano":

                case "":
                    break;

                default:
                    System.out.println("Commande inconnue. Write help!");
            }
        }
        // String userCommand = scanner.nextLine().trim();
        // String commandUser = scanner.nextLine().trim();

        // handleCommand(commandUser, currentUser);
    }

    private void handleSignup() {
        if (currentUser != null) {
            System.out.println("Deja connecte. Deconnectez-vous d'abord.");
            return;
        }
        System.out.print("Login : ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        String result = userService.signUp(login, password);
        System.out.println(result);
    }

    private void handleLogin() {
        if (currentUser != null) {
            System.out.println("Deja connecte. Deconnectez-vous d'abord.");
            return;
        }
        System.out.print("Login : ");
        String login = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        String result = userService.login(login, password);

        if (result.equals("Connexion reussie.")) {
            currentUser = login.trim();
            System.out.println("Bienvenue " + currentUser + " !");
        } else {
            System.out.println(result);
        }
    }

    private void handleLogout() {
        if (currentUser == null) {
            System.out.println("Vous n'etes pas connecte.");
            return;
        }
        currentUser = null;
        System.out.println("Deconnecte.");
    }

    private void handleHelp() {
        System.out.println("Commandes disponibles :");
        System.out.println("signup - Creer un compte utilisateur");
        System.out.println("login - Se connecter avec un compte existant");
        System.out.println("logout - Se deconnecter");
        System.out.println("exit - Quitter l'application");
    }

    //second part .
    private void showFiles() {
        if (currentUser == null) {
            System.out.println("Vous n'etes pas connecte.");
            return;
        }
        fileService.shoWFile();
        // System.out.println(".(salam el alam)");

    }

    private void createFile(String nameFile, String propFile) {
        if (currentUser == null) {
            System.out.println("Vous n'etes pas connecte.");
            return;
        }
        // System.out.println("tu peux creer les fichier" + nameFile + "prop est " + propFile);
        if (nameFile.trim() != null) {

            String msg = fileService.create(nameFile, propFile);
            System.out.println(msg);
        }
    }

    private void showFileContent(String fileName) {
        if (currentUser == null) {
            System.out.println("Vous n'etes pas connecte.");
            return;
        }
        fileService.showContentFile(fileName, currentUser);
        // System.out.println("tu peux consulter le fichier" + command + "prop est " + propfile);
    }

    private void createcontent(String fileName) {
        if (currentUser == null) {
            System.out.println("Vous n'etes pas connecte.");
            return;
        }
        fileService.writeContent(fileName, fileName, currentUser);
    }

    // private boolean checkAuth() {
    //     if (currentUser == null) {
    //         System.out.println("Vous n'etes pas connecte.");
    //     }
    // }
// private void handleCommand(String command, String userLog) {
//     if (userLog == null) {
//         return;
//     }
//     switch (command) {
//         case "ls-l":
//             System.out.println("lister les fichiers");
//             break;
//         case "cat":
//             System.out.println("afficher le contenu d'un fichier");
//             break;
//         case "touch":
//             System.out.println("creer un fichier");
//             break;
//         default:
//            System.out.println("command non trouver");;
//     }
// }
}
