package Home;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Home.Owner;
import Home.Property;
import User.*;
public class Customer {
  String username;
  public List<Property> favourites;
  public Property booked;
  String filename = "Owner.txt";
  public Customer(String username) {
    this.username = username;
    favourites = new ArrayList<>();
  }

  public void DisplayProperties(List<Property> prop) {
    Scanner sc = new Scanner(System.in);
    for (Property p : prop) {
      System.out.println("Property ID: " + p.getPropertyId());
      System.out.println("Location: " + p.getLocation());
      System.out.println("No.of Rooms: " + p.getNumRooms());
      System.out.println("Rent: " + p.getRent());
      System.out.println("Owner: " + p.getOwner().getName());
      System.out.println("------------------------------------");
    }
    System.out.println("Book now? (y/n)");
    char ch = sc.next().charAt(0);
    if (ch == 'y') {
      System.out.println("Enter the property ID to book: ");
      int book = sc.nextInt();
      bookProperty(book);
    }
    if (ch == 'n') {
      System.out.println("Thank you for visiting!");
    }
  }

  public List<Property> sortByRent() {
    List<Property> sorted = new ArrayList<>();
    sorted.addAll(getPropertyDetails());
    sorted.sort((p1, p2) -> Double.compare(p1.getRent(), p2.getRent()));
    return sorted;
  }

  public List<Property> sortByRooms() {
    List<Property> sorted = new ArrayList<>();
    sorted.addAll(getPropertyDetails());
    ;
    sorted.sort((p1, p2) -> p1.getNumRooms() - p2.getNumRooms());
    return sorted;
  }

  public <T extends Comparable<T>> void searchItem(T item) {
    Scanner sc = new Scanner(System.in);
    List<Property> prop = getPropertyDetails();
    if (item instanceof String) {
      for (Property p : prop) {
        if (p.getLocation().equals(item)) {
          System.out.println("Property ID: " + p.getPropertyId());
          System.out.println("Location: " + p.getLocation());
          System.out.println("No.of Rooms: " + p.getNumRooms());
          System.out.println("Rent: " + p.getRent());
          System.out.println("Owner: " + p.getOwner().getName());
          System.out.println("------------------------------------");
        }
      }
    }
    if (item instanceof Integer) {
      for (Property p : prop) {
        if (p.getNumRooms() <= (Integer) item) {
          System.out.println("Property ID: " + p.getPropertyId());
          System.out.println("Location: " + p.getLocation());
          System.out.println("No.of Rooms: " + p.getNumRooms());
          System.out.println("Rent: " + p.getRent());
          System.out.println("Owner: " + p.getOwner().getName());
          System.out.println("------------------------------------");
        }
      }
    }
    if (item instanceof Double) {
      for (Property p : prop) {
        if (p.getRent() <= (Double) item) {
          System.out.println("Property ID: " + p.getPropertyId());
          System.out.println("Location: " + p.getLocation());
          System.out.println("No.of Rooms: " + p.getNumRooms());
          System.out.println("Rent: " + p.getRent());
          System.out.println("Owner: " + p.getOwner().getName());
          System.out.println("------------------------------------");
        }
      }
    }
    System.out.println("Book now? (y/n)");
    char ch = sc.next().charAt(0);
    if (ch == 'y') {
      System.out.println("Enter the property ID to book: ");
      int book = sc.nextInt();
      bookProperty(book);
    }
    if (ch == 'n') {
      System.out.println("Thank you for visiting!");
    }
  }

  public List<Property> getPropertyDetails() {
    List<Property> property = new ArrayList<>();
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

  private void bookProperty(int propertyId) {
    Owner owner = new Owner();
    booked = owner.getPropertyById(propertyId);
    if (booked != null) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("booked.txt", true))) {
        writer.write(String.format("%s-%d,%s,%s,%d,%.2f%n",username,
            booked.getPropertyId(),
            booked.getOwner().getName(),
            booked.getLocation(),
            booked.getNumRooms(),
            booked.getRent()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    owner.deleteProperty(booked.getPropertyId());
  }
}