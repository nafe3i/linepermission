package ma.youcode.lineperm.model;

public class FichierProtege {

    private String name;
    private String proprietaire;

    private boolean ownerWrite;
    private boolean ownerReade;
    private boolean ownerDelete;

    private boolean otherWrite;
    private boolean otherReade;
    private boolean otherDelete;

    public FichierProtege(
            String name,
            String proprietaire,
            boolean ownerDelete,
            boolean ownerReade,
            boolean ownerWrite) {
        this.name = name;
        this.proprietaire = proprietaire;
        this.ownerDelete = ownerDelete;
        this.ownerReade = ownerReade;
        this.ownerWrite = ownerWrite;
    }

}
