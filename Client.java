import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            ServerInterface server = (ServerInterface) Naming.lookup("rmi://localhost:1099/Server");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("time")) {
                System.out.println(server.getDate());
            } else if (input.startsWith("zipfile/")) {
                System.out.println(server.getFile(input));
            } else {
                System.out.println("Invalid input");
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
