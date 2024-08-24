package Home;

public class Property {
    private int propertyId;
    private Owner owner;
    private String location;
    private int numRooms;
    private double rent;

    public Property(int propertyId, Owner owner, String location, int numRooms, double rent) {
        this.propertyId = propertyId;
        this.owner = owner;
        this.location = location;
        this.numRooms = numRooms;
        this.rent = rent;
    }

    // Getters
    public int getPropertyId() {
        return propertyId;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getLocation() {
        return location;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public double getRent() {
        return rent;
    }

    // Setters
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    // Additional methods
    public void setName(String name) {
        // Assuming that "name" is a property of the owner
        owner.setName(name);
    }

    public String getName() {
        // Assuming that "name" is a property of the owner
        return owner.getName();
    }
}
