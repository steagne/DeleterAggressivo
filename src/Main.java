import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String currDir;
        File dir;
        ArrayList<File> files;
        ArrayList<File> toDeleteList;

        System.out.println("Program dir: " + System.getProperty("user.dir"));
        currDir = "D:\\Steurendo\\Downloads\\Derry_Londondublin\\";
        System.out.println("Working dir: " + currDir);

        dir = new File(currDir);
        files = new ArrayList<>(List.of(Objects.requireNonNull(dir.listFiles())));
        toDeleteList = new ArrayList<>();
        for (File f : files)
            if (f.isFile()) {
                try {
                    System.out.println("\t>" + f.getName());
                    if (f.getName().matches("^.+[(]1[)]\\.jpg$")){
                        String fname;
                        File twin;

                        fname = f.getName().replaceAll("[(]1[)]", "");
                        System.out.printf("\t\tChecking if similar file %s exists...\n", fname);
                        twin = new File(currDir + "\\" + fname);
                        if (twin.exists()){
                            long sz1, sz2;

                            sz1 = Files.size(f.getAbsoluteFile().toPath());
                            sz2 = Files.size(twin.getAbsoluteFile().toPath());
                            System.out.println("\t\tFound! Comparing sizes...");
                            System.out.println("\t\t-" + f.getName() + "\t" + sz1);
                            System.out.println("\t\t-" + twin.getName() + "\t" + sz2);
                            if (sz1 > sz2)
                                f = twin;
                            System.out.println("\t\tSmaller file: " + f.getName());
                            toDeleteList.add(f);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        if (toDeleteList.size() == 0){
            System.out.println("Nothing to delete here!");
            return;
        }

        Scanner in;
        String val;

        System.out.printf("Delete %d files? (y or n) >", toDeleteList.size());
        in = new Scanner(System.in);
        val = in.next();
        if (val.equalsIgnoreCase("y"))
            for (File f : toDeleteList) {
                try {
                    System.out.printf("Deleting %s...\n", f.getName());
                    Files.delete(f.getAbsoluteFile().toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
