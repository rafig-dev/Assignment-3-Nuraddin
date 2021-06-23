import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            File[] filesDestination;
            System.out.println("Please enter source directory: ");
            String source = sc.nextLine();
            File sourceDirectory = new File(source);

            System.out.println("Please enter destination directory: ");
            String destination = sc.nextLine();
            File destinationDirectory = new File(destination);

            if (!destinationDirectory.isDirectory()) {
                boolean bool = destinationDirectory.mkdir();
                if (bool) {
                    System.out.println("Folder created!");
                } else {
                    System.out.println("Error found!");
                }
            }

            filesDestination = destinationDirectory.listFiles();
            for (File deletedFiles : filesDestination) {
                deleteFiles(deletedFiles);
            }

            System.out.println("--------------------------------------");

            copyFiles(sourceDirectory, destinationDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFiles(File source, File destination) throws IOException {
        File[] files;

        if (!source.isDirectory()) {
            boolean bool = destination.mkdir();
            if (bool) {
                System.out.println("Folder created!");
            } else {
                System.out.println("Error found!");
            }
        }
        files = source.listFiles();

        for (File file : files) {
            System.out.println(">Started: " + file.getAbsolutePath());
            Path originalPath = file.toPath();
            Path copied = new File(destination + "/" + file.getName()).toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

            File copiedFile = copied.toFile();

            if (file.isDirectory()) {
                copyFiles(file, copiedFile);
            }
            System.out.println(">Finished: " + file.getAbsolutePath());
            if (!file.isDirectory()) {
                System.out.println(">Total " + file.length() + " bytes were copied!");
            }
        }
    }

    public static void deleteFiles(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    deleteFiles(file);
                }
            }
        }

        if (directory.delete()) {
            System.out.println(directory + " is deleted");
        } else {
            System.out.println("Directory not deleted");
        }
    }
}