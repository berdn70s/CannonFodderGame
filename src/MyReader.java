import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyReader {
    private String path;
    MyReader(String path){
        this.path = path;
    }

    public void Read(){
        try {
            File itemFile = new File(path);
            Scanner sc = new Scanner(itemFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.split(",");
                switch (values[3]) {
                    case "Sword":
                        Main.swordPool.add(new Weapon(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    case "Wand":
                        Main.wandPool.add(new Weapon(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    case "Shield":
                        Main.shieldPool.add(new Weapon(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    case "Light Armor":
                        Main.lightPool.add(new Clothing(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    case "Medium Armor":
                        Main.mediumPool.add(new Clothing(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    case "Heavy Armor":
                        Main.heavyPool.add(new Clothing(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3]));
                        break;
                    default:
                        throw new Exception("Item txt is corrupted or mis-written");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Item pool non-existent.");
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
