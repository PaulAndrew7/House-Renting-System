package Home;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException(String message) {
        super(message);
    }
}

class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String message) {
        super(message);
    }
}
public class Owner{
  List<Property> properties;
  String name;

  public Owner() {
      this.properties = new ArrayList<>();
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void addProperty() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter property ID: ");
      int propertyId = scanner.nextInt();
      scanner.nextLine();
      System.out.print("Enter owner name: ");
      name = scanner.nextLine();
      System.out.print("Enter number of rooms: ");
      int numRooms = scanner.nextInt();
      scanner.nextLine();
      System.out.print("Enter location: ");
      String location = scanner.nextLine();
      System.out.print("Enter the rent: ");
      double rent = scanner.nextDouble();
      properties.add(new Property(propertyId, this, location, numRooms, rent));

      // Save details to Owner.txt
      saveToFile("Owner.txt", propertyId, name, location, numRooms, rent);
  }

  private void saveToFile(String fileName, int propertyId, String ownerName, String location, int numRooms, double rent) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
          writer.write(String.format("%d,%s,%s,%d,%.2f%n", propertyId, ownerName, location, numRooms, rent));
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  public void updateProperty(int propertyId) {
      List<Property> property = getPropertyDetails();
      Property propertyToUpdate=null;
      for(Property prop : property){
        if(prop.getPropertyId() == propertyId){
          propertyToUpdate=prop;
        }
      }
      if (propertyToUpdate != null) {
          Scanner scanner = new Scanner(System.in);

          System.out.println("Choose the detail to update:");
          System.out.println("1. Name");
          System.out.println("2. Location");
          System.out.println("3. Number of Rooms");
          System.out.println("4. Rent");
          System.out.print("Enter your choice (1-4): ");

          int choice = scanner.nextInt();
          scanner.nextLine(); 

          switch (choice) {
              case 1:
                  System.out.print("Enter the new name: ");
                  String newName = scanner.nextLine();
                  propertyToUpdate.setName(newName);
                  break;
              case 2:
                  System.out.print("Enter the new location: ");
                  String newLocation = scanner.nextLine();
                  propertyToUpdate.setLocation(newLocation);
                  break;
              case 3:
                  System.out.print("Enter the new number of rooms: ");
                  int newNumRooms = scanner.nextInt();
                  propertyToUpdate.setNumRooms(newNumRooms);
                  break;
              case 4:
                  System.out.print("Enter the new rent: ");
                  double newRent = scanner.nextDouble();
                  propertyToUpdate.setRent(newRent);
                  break;
              default:
                  try{
                    throw new InvalidChoiceException("Invalid choice"); 
                  }
              catch(InvalidChoiceException e){
                    System.out.println(e);
              }
          }
          for(Property prop : property){
            if(prop.getPropertyId() == propertyId){
              prop=propertyToUpdate;
            }
          }
          updateFile("Owner.txt", property);
      } else {
        try{
          throw new PropertyNotFoundException("Property with ID " + propertyId + " not found.");
        }
        catch(PropertyNotFoundException e){
          System.out.println(e);
          return;
        }
      }
  }
  public void deleteProperty(int propertyId) {
    List<Property> properties = getPropertyDetails();
    for(Property prop : properties){
      if(prop.getPropertyId()==propertyId){
        properties.remove(prop);
        updateFile("Owner.txt", properties);
        System.out.println("Property with ID " + propertyId + " deleted successfully.");
        return;
      }
    }
    try{
      throw new PropertyNotFoundException("Property with ID " + propertyId + " not found.");
    }
    catch(PropertyNotFoundException e){
      System.out.println(e);
      return;
    }
  }
  private List<Property> getPropertyDetails() {
    List<Property> property=new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader("Owner.txt"))) {
          String line;
          while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int currentPropertyId = Integer.parseInt(parts[0]); 
            Owner owner = new Owner();
            owner.setName(parts[1]);
            String location = parts[2];
            int numRooms = Integer.parseInt(parts[3]);
            double rent = Double.parseDouble(parts[4]);
            property.add(new Property(currentPropertyId, owner, location, numRooms, rent));

        }
        return property;
      } catch (IOException e) {
          e.printStackTrace();
      }

      return null; // Property not found
  }
  public Property getPropertyById(int propertyId) {
      try (BufferedReader reader = new BufferedReader(new FileReader("Owner.txt"))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] parts = line.split(",");
              int currentPropertyId = Integer.parseInt(parts[0]); // Assuming property ID is the first field

              if (currentPropertyId == propertyId) {
                  // Match found, create a Property object
                  Owner owner = new Owner();
                  owner.setName(parts[1]);
                  String location = parts[2]; // Assuming location is the third field
                  int numRooms = Integer.parseInt(parts[3]); // Assuming numRooms is the fourth field
                  double rent = Double.parseDouble(parts[4]); // Assuming rent is the fifth field

                  return new Property(propertyId, owner, location, numRooms, rent);
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      return null;
  }
    private void updateFile(String fileName, List<Property> properties) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Property property : properties) {
                writer.write(String.format("%d,%s,%s,%d,%.2f%n",
                        property.getPropertyId(),
                        property.getOwner().getName(),
                        property.getLocation(),
                        property.getNumRooms(),
                        property.getRent()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
