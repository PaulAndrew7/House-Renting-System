import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import User.*;
import Home.*;
class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String message) {
        super(message);
    }
}
class NoBookedPropertyException extends Exception{
    public NoBookedPropertyException(String message) {
        super(message);
    }
}
public class HouseRenting extends User{
  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    Owner owner = new Owner(); 
    HouseRenting house = new HouseRenting();
    boolean isSignedIn = false;

    System.out.println("Sign in/Sign up: ");
    String opt = sc.nextLine();

    if (opt.equals("Sign up")) {
      house.signup();
      System.out.println("Successfully Signed up! Continue logging in again. Thank you.");
      isSignedIn =  house.signin();

      if (isSignedIn) {
          System.out.println("Sign-in successful. Welcome!");
      } else {
          System.out.println("Sign-in failed. Please check your credentials.");
      }
    } else if (opt.equals("Sign in")) {
      isSignedIn =  house.signin();

      if (isSignedIn) {
          System.out.println("Sign-in successful. Welcome!");
      } else {
          System.out.println("Sign-in failed. Please check your credentials.");
      }
    } else {
      System.out.println("Invalid option. Please choose 'Sign in' or 'Sign up'.");
    }
    Customer customer = new Customer(  house.getUsername());
    while (isSignedIn) {
      System.out.println("1.Rent House\n2.View House\n3.Booked Properties\n4.Exit");
      int option = sc.nextInt();

      switch (option) {
        case 1:
          boolean bool=true;
          while(bool){
            System.out.println("1.Add property\n2.Update property\n3.Delete property\n4.Back");
            int option1 = sc.nextInt();
            switch(option1){
              case 1:
                owner.addProperty();
                break;
              case 2:
                System.out.println("Enter the property id: ");
                int id = sc.nextInt();
                owner.updateProperty(id);
                break;
              case 3:
                System.out.println("Enter the property id: ");
                int delid = sc.nextInt();
                owner.deleteProperty(delid);
                break;
              case 4:
                bool=false;
                break;
            }
          }
            break;
        case 2:
          boolean bool1=true;
          while(bool1){
            System.out.println("1.View all properties\n2.Filter properties\n3.Search\n4.Back");
            int option2 = sc.nextInt();
            switch(option2){
              case 1:
                customer.favourites=customer.getPropertyDetails();
        customer.DisplayProperties(customer.favourites);
                break;
              case 2:
                System.out.println("1.Filter by Rent\n2.Filter by Rooms");
                int option3 = sc.nextInt();
                switch(option3){
                  case 1:
                    customer.DisplayProperties(customer.sortByRent());
                    break;
                  case 2:
                    customer.DisplayProperties(customer.sortByRooms());
                    break;
                }
                break;
              case 3:
                System.out.println("Enter search item: ");
                String item = sc.next();
                try{
                  int room=Integer.parseInt(item);
                  customer.searchItem(room);
                  break;
                }
                catch(NumberFormatException e){}
                    try{
                      double price=Double.parseDouble(item);
                      customer.searchItem(price);
                      break;
                    }
                    catch(NumberFormatException e){}
                    customer.searchItem(item);
                    break;

                  case 4:
                    bool1=false;
                    break;
                }
                }
                break;
        case 3:
          try (BufferedReader reader = new BufferedReader(new FileReader("booked.txt"))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  String[] parts = line.split("-");
                  String username=parts[0];
                if(username.equals(house.getUsername())){
                  System.out.println(line);
                }
                else{
                  try{
                    throw new NoBookedPropertyException("No booked properties");
                  }
                  catch(NoBookedPropertyException e){
                    System.out.println(e);
                  }                }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          break;
                case 4:
                  System.out.println("Logging out...");
                  isSignedIn = false;
                  break;
                default:
              try{
                    throw new InvalidChoiceException("Invalid choice"); 
                  }
              catch(InvalidChoiceException e){
                    System.out.println(e.getMessage());
              }

          }
          
      }

    sc.close();
}
}
