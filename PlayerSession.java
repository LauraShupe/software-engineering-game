public class PlayerSession {
    private String username;
    private int resources;

    public PlayerSession(String username, int startingResources) {
        this.username = username;
        this.resources = startingResources;
    }

    public String getUsername() { return username; }
    public int getResources() { return resources; }
    public void addResources(int amount) { resources += amount; }
    public boolean spendResources(int amount) {
        if (resources >= amount) {
            resources -= amount;
            return true;
        }
        return false;
    }
    
    public void setResources(int amount) {
        this.resources = amount;
    }

}
