import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public enum Habitat {

    OCEAN("Ocean"),
    REEF("Reef"),
    ISLAND("Island"),
    KELPFOREST("Kelp Forest"),
    GRAVE("Grave");

    /**
     * The Ocean, Reef, Island, and Kelp Forest are all playable habitats.
     * The Grave is the location all dead players reside. They cannot move
     * from the Grave and alive players cannot move to the Grave.
     */

    private String name;
    private List<Player> players;

    private Habitat(String name) {
        this.name = name;
        this.players = new ArrayList<Player>();
    }

    public static Habitat getHabitatForName(String name) {
        if (name == null) return null;
        switch (name) {
            case "Ocean":
                return Habitat.OCEAN;
            case "Reef":
                return Habitat.REEF;
            case "Island":
                return Habitat.ISLAND;
            case "Kelp Forest":
                return Habitat.KELPFOREST;
            case "Grave":
                return Habitat.GRAVE;
            default: return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> newPlayers) {
        this.players = newPlayers;
    }

}