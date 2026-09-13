package ma.youcode.lineperm.access;

import ma.youcode.lineperm.model.FichierProtege;

public class ControleAcces {

    public static boolean estAutorise(String login, FichierProtege fichier, char droit) {
        boolean isProprietaire = login.equals(fichier.getOwner());

        if (isProprietaire) {
            switch (droit) {
                case 'r':
                    return fichier.isOwnerReade();
                case 'w':
                    return fichier.isOwnerWrite();
                case 'd':
                    return fichier.isOwnerDelete();
                default:
                    return false;
            }
        } else {
            switch (droit) {
                case 'r':
                    return fichier.isOtherReade();
                case 'w':
                    return fichier.isOtherWrite();
                case 'd':
                    return fichier.isOtherDelete();
                default:
                    return false;
            }
        }
    }
}