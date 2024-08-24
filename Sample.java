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
public class Sample {
  public static void main(String args[]) {
    User user = new User();
    user.signin();
    System.out.println("Welcome " + user.getUsername());
  }
}