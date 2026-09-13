package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.model.FichierProtege;

public class FileService {

    private final Path dataFolder = Path.of("src/main/resources/files");
    private final Path metadataFile = Path.of("src/main/resources/files.txt");

    private final HashMap<String, FichierProtege> dataFiles = new HashMap<>();

    public FileService() {
        load();
    }

    public String create(String filename, String propri) {
        if (filename == null || filename.trim().isEmpty()) {
            return "Le nom de fichier ne peut pas etre vide.";
        }
        if (filename.contains("/") || filename.contains("\\")) {
            return "Le nom de fichier ne doit pas contenir de chemin.";
        }
        if (dataFiles.containsKey(filename)) {
            return "Le fichier est deja cree.";
        }

        try {
            if (!Files.exists(dataFolder)) {
                Files.createDirectories(dataFolder);
            }

            Path fullPath = dataFolder.resolve(filename);
            Files.createFile(fullPath);

            FichierProtege fichierProtege = new FichierProtege(filename, propri);
            dataFiles.put(filename, fichierProtege);
            save(fichierProtege);

            return "Fichier cree : " + fullPath;

        } catch (IOException e) {
            return "Erreur lors de la creation : " + e.getMessage();
        }
    }

    public void shoWFile() {
        char otherWrite;
        char otherReade;
        char otherDelete;
        for (FichierProtege val : dataFiles.values()) {
            // String key = entry.getKey();
            // FichierProtege val = entry.getValue();
            otherReade = val.isOtherReade() ? 'r' : '-';
            otherWrite = val.isOtherWrite() ? 'w' : '-';
            otherDelete = val.isOtherDelete() ? 'd' : '-';

            // if (val.isOtherWrite()) {
            //     otherWrite = 'w';
            // } else {
            //     otherWrite = '-';
            // }
            // if (val.isOtherReade()) {
            //     otherReade = 'r';
            // } else {
            //     otherReade = '-';
            // }
            // if (val.isOtherDelete()) {
            //     otherDelete = 'd';
            // } else {
            //     otherDelete = '-';
            // }
            System.out.println("rwd|" + otherReade + otherWrite + otherDelete + "   -   " + val.getOwner() + "  -  " + val.getName());
        }

    }

    public String showContentFile(String fileName, String currenteUser, char command) {
        Path fullPath = dataFolder.resolve(fileName);

        if (!dataFiles.containsKey(fileName)) {

            return "Le fichier n'existe pas.";
            // return;
        }
        // FichierProtege fichierProtege = dataFiles.get(fileName);
        if (canManipale(fileName, currenteUser, command)) {
            try {
                String contenu = Files.readString(fullPath);
                return contenu;
            } catch (IOException e) {
                return "Erreur de lecture du fichier : " + e.getMessage();
            }
            // } else if (!fichierProtege.getOwner().equals(currenteUser) && fichierProtege.isOtherReade() == true) {

            //     try {
            //         String contenu = Files.readString(fullPath);
            //         System.out.println(contenu);
            //     } catch (IOException e) {
            //         System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            //     }
        } else {
            return "Vous n'avez pas la permission de lire ce fichier.";
        }
    }

    public String writeContent(String fileName, String content, String currentUser) {

        FichierProtege fichier = dataFiles.get(fileName);
        if (fichier == null) {
            return "Le fichier n'existe pas.";
            // return msg;
        }
        if (!ControleAcces.estAutorise(currentUser, fichier, 'w')) {
            return "Vous n'avez pas la permission d'ecrire dans ce fichier.";
        }
        // if (fichier.getOwner().equals(currentUser)) {
        //     try {
        //         Path fullPath = dataFolder.resolve(fileName);
        //         Files.writeString(fullPath, content + System.lineSeparator(),
        //                 StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        //         msg = "Contenu écrit avec succès.";
        //     } catch (IOException e) {
        //         System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        //     }
        // } else if (!fichier.getOwner().equals(currentUser) && fichier.isOtherWrite()) {
        try {
            Path fullPath = dataFolder.resolve(fileName);
            Files.writeString(fullPath, content + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return "Contenu ecrit avec succes.";
        } catch (IOException e) {
            return "Erreur lors de l'ecriture : " + e.getMessage();
        }
        // } else {
        //     System.out.println("Vous n'avez pas la permission d'ecrire dans ce fichier.");
        // }
    }

    private void load() {
        try {
            if (!Files.exists(metadataFile)) {
                return;
            }

            List<String> lines = Files.readAllLines(metadataFile);
            for (String line : lines) {
                String[] nameFile = line.split(":", 2);
                String[] dataFile = nameFile[1].split(",");
                FichierProtege fichierProtege = new FichierProtege(nameFile[0], dataFile[0]);
                dataFiles.put(nameFile[0], fichierProtege);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement : " + e.getMessage());
        }
    }
    // FichierProtege fichierProteg

    private void save(FichierProtege fichierProtege) {
        try {
            String line = fichierProtege.getName() + ":" + fichierProtege.getOwner() + ","
                    + fichierProtege.isOwnerReade() + "," + fichierProtege.isOwnerWrite() + ","
                    + fichierProtege.isOwnerDelete() + "," + fichierProtege.isOtherReade() + ","
                    + fichierProtege.isOtherWrite() + "," + fichierProtege.isOtherDelete()
                    + System.lineSeparator();
            Files.writeString(metadataFile, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    public boolean canManipale(String fileName, String currentUser, char command) {
        FichierProtege fichier = dataFiles.get(fileName);
        if (fichier == null) {
            return false;
        }
        return ControleAcces.estAutorise(currentUser, fichier, command);
    }

    public String chmod(String fileName, char droit, boolean accorder, String currentUser) {
        FichierProtege fichier = dataFiles.get(fileName);
        if (fichier == null) {
            return "Le fichier n'existe pas.";
        }

        if (!fichier.getOwner().equals(currentUser)) {
            return "Permission denied.";
        }

        switch (droit) {
            case 'r':
                fichier.setOtherReade(accorder);
                break;
            case 'w':
                fichier.setOtherWrite(accorder);
                break;
            case 'd':
                fichier.setOtherDelete(accorder);
                break;
            default:
                return "Droit invalide.";
        }

        save(fichier);

        String action = accorder ? "accorde" : "retire";
        return "Droit " + droit + " " + action + " pour les autres sur " + fileName + ".";
    }
}
