import java.util.Random;

/**
 * Created by Ashley Walker on 2/17/2018.
 */

public enum Role {

    ORCA("Orca", 1, Habitat.OCEAN),
    SHARK("Shark", 2, Habitat.REEF),
    POLARBEAR("Polar Bear", 3, Habitat.ISLAND),
    EEL("Eel", 4, Habitat.OCEAN),
    OCTOPUS("Octopus", 5, Habitat.KELPFOREST),
    SEASTAR("Sea Star", 5, Habitat.REEF),
    SEAHORSE("Sea Horse", 5, Habitat.OCEAN),
    SEALION("Sea Lion", 5, Habitat.ISLAND),
    SEAURCHIN("Sea Urchin", 5, Habitat.KELPFOREST),
    FUGU("Fugu", 5, Habitat.KELPFOREST),
    CRAB("Crab", 5, Habitat.REEF),
    REMORA("Remora", 5, Habitat.KELPFOREST),
    TURTLE("Turtle", 5, Habitat.ISLAND),
	BIGFISH("Big Fish", 6, null);

    private String name;
    private int rank;
    private Habitat home;

    private Role(String name, int rank, Habitat home) {
        this.name = name;
        this.rank = rank;
        this.home = home;
    }

    public String getName() {
        return this.name;
    }

    public int getRank() {
        return this.rank;
    }

    public Habitat getHome() { return this.home; }

    public static Role getRoleForName(String name) {
        if (name != null) {
            switch (name) {
                case "Orca":
                    return Role.ORCA;
                case "Shark":
                    return Role.SHARK;
                case "Polar Bear":
                    return Role.POLARBEAR;
                case "Eel":
                    return Role.EEL;
                case "Octopus":
                    return Role.OCTOPUS;
                case "Sea Star":
                    return Role.SEASTAR;
                case "Sea Horse":
                    return Role.SEAHORSE;
                case "Sea Lion":
                    return Role.SEALION;
                case "Sea Urchin":
                    return Role.SEAURCHIN;
                case "Fugu":
                    return Role.FUGU;
                case "Crab":
                    return Role.CRAB;
                case "Remora":
                    return Role.REMORA;
                case "Turtle":
                    return Role.TURTLE;
                case "Big Fish":
                	return Role.BIGFISH;
                default: return null;
            }
        } else return null;
    }

    public static Role getRandomRole() {
        Random rand = new Random();
        int i = rand.nextInt(13);

        switch (i) {
            case 0:
                return Role.ORCA;
            case 1:
                return Role.SHARK;
            case 2:
                return Role.POLARBEAR;
            case 3:
                return Role.EEL;
            case 4:
                return Role.OCTOPUS;
            case 5:
                return Role.SEASTAR;
            case 6:
                return Role.SEAHORSE;
            case 7:
                return Role.SEALION;
            case 8:
                return Role.SEAURCHIN;
            case 9:
                return Role.FUGU;
            case 10:
                return Role.CRAB;
            case 11:
                return Role.REMORA;
            case 12:
                return Role.TURTLE;
            case 13:
            	return Role.BIGFISH;
            default: return null;
        }
    }

    /**
     * Returns a boolean determining if the role's win condition
     * has been met. Needs to access a global list of players.
     */
    public boolean winCondition() {
        switch (this.name) {
            case "Orca":
                return orcaWC();
            case "Shark":
                return sharkWC();
            case "Polar Bear":
                return polarbearWC();
            case "Eel":
                return eelWC();
            case "Octopus":
                return octopusWC();
            case "Sea Star":
                return seastarWC();
            case "Sea Horse":
                return seahorseWC();
            case "Sea Lion":
                return sealionWC();
            case "Sea Urchin":
                return seaurchinWC();
            case "Fugu":
                return fuguWC();
            case "Crab":
                return crabWC();
            case "Remora":
                return remoraWC();
            case "Turtle":
                return turtleWC();
            default: return false;
        }
    }

    /**
     * @return true if the orca player is alive
     */
    private boolean orcaWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the shark player is alive
     */
    private boolean sharkWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the polar bear player is alive
     */
    private boolean polarbearWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the orca player is dead
     */
    private boolean eelWC() {
        // TODO
        return false;
    }

    /**
     * @return true is the octpus player is alive
     */
    private boolean octopusWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the sea star player is alive
     */
    private boolean seastarWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the sea horse player is alive
     */
    private boolean seahorseWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the sea lion player is alive
     *          AND has visited the Ocean habitat before round 4
     */
    private boolean sealionWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the sea urchin player is alive
     */
    private boolean seaurchinWC() {
        // TODO
        return false;
    }

    /**
     * @return true if 9 or more players are dead by the end (of four rounds)
     */
    private boolean fuguWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the shark is alive
     */
    private boolean crabWC() {
        // TODO
        return false;
    }

    /**
     * @return true if the orca is alive
     */
    private boolean remoraWC() {
        // TODO
        return false;
    }

    /**
     * Returns true if the turtle's guess has won
     */
    private boolean turtleWC() {
        // TODO
        return false;
    }

}