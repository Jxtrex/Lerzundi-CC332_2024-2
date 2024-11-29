package Proyecto_Individual;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+=================================================================+\r\n" + //
                        "|  ____                                  _               _        |\r\n" + //
                        "| |  _ \\  _ __  ___   _   _   ___   ___ | |_  ___     __| |  ___  |\r\n" + //
                        "| | |_) || '__|/ _ \\ | | | | / _ \\ / __|| __|/ _ \\   / _` | / _ \\ |\r\n" + //
                        "| |  __/ | |  | (_) || |_| ||  __/| (__ | |_| (_) | | (_| ||  __/ |\r\n" + //
                        "| |_|__  |_|   \\___/  \\__, |_\\___| \\___| \\__|\\___/   \\__,_| \\___| |\r\n" + //
                        "| |  _ \\  __ _  _ __  |___/| |  ___ | |  __ _                     |\r\n" + //
                        "| | |_) |/ _` || '__|/ _` || | / _ \\| | / _` |                    |\r\n" + //
                        "| |  __/| (_| || |  | (_| || ||  __/| || (_| |                    |\r\n" + //
                        "| |_|    \\__,_||_|   \\__,_||_| \\___||_| \\__,_|                    |\r\n" + //
                        "+=================================================================+");
        while (true) {
            // First menu
            System.out.println("===========================");
            System.out.println("1. Generate Random Matrix");
            System.out.println("2. Read Matrix From File");
            System.out.println("3. Exit");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                generateRandomMatrix();
                showJacobiMenu(scanner);
            } else if (choice == 2) {
                readMatrixFromFile();
                showJacobiMenu(scanner);
            } else if (choice == 3) {
                System.out.println("Finalizando la ejecución del programa...");
                break;
            } else {
                System.out.println("Opción inválida, inténtelo nuevamente.");
            }
        }

        scanner.close();
    }

    // First-level menu options
    public static void generateRandomMatrix() {
        // Empty function for generating random matrix
        System.out.println("Generating random matrix...");
    }

    public static void readMatrixFromFile() {
        // Empty function for reading matrix from file
        System.out.println("Reading matrix from file...");
    }

    // Second-level menu
    public static void showJacobiMenu(Scanner scanner) {
        while (true) {
            System.out.println("========================");
            System.out.println("1. Apply Jacobi Method");
            System.out.println("2. Exit");
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                applyJacobiMethod();
                break; // After applying Jacobi method, return to the previous menu
            } else if (choice == 2) {
                System.out.println("Volviendo al menú principal...");
                break; // Exit the second menu
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void applyJacobiMethod() {
        // Empty function for applying Jacobi method
        System.out.println("Aplicando el método de Jacobi...");
    }
}
