package ma.youcode.lineperm.model;

public class FichierProtege {

    private String name;
    private String proprietaire;

    private final boolean ownerWrite = true;
    private final boolean ownerReade = true;
    private final boolean ownerDelete = true;

    private boolean otherWrite = false;
    private boolean otherReade = false;
    private boolean otherDelete = false;

    public FichierProtege(String name, String proprietaire) {
        this.name = name;
        this.proprietaire = proprietaire;
        this.otherWrite = false;
        this.otherReade = false;
        this.otherDelete = false;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return proprietaire;
    }

    public boolean isOwnerWrite() {
        return ownerWrite;
    }

    public boolean isOwnerReade() {
        return ownerReade;
    }

    public boolean isOwnerDelete() {
        return ownerDelete;
    }

    public boolean isOtherWrite() {
        return otherWrite;
    }

    public boolean isOtherReade() {
        return otherReade;
    }

    public boolean isOtherDelete() {
        return otherDelete;
    }

    public void setOtherWrite(boolean value) {
        this.otherWrite = value;
    }

    public void setOtherReade(boolean value) {
        this.otherReade = value;
    }

    public void setOtherDelete(boolean value) {
        this.otherDelete = value;
    }
}
