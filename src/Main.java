import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Item> swordPool;
    public static List<Item> wandPool;
    public static List<Item> shieldPool;
    public static List<Item> lightPool;
    public static List<Item> mediumPool;
    public static List<Item> heavyPool;
    public static List<Character> team = new ArrayList<>();
    public static int score;
    public static Level currentLevel = new Level();


    public static void main(String[] args) throws Exception {

        //Read Item Pool - START
        swordPool = new ArrayList<>();
        wandPool = new ArrayList<>();
        shieldPool = new ArrayList<>();
        lightPool = new ArrayList<>();
        mediumPool = new ArrayList<>();
        heavyPool = new ArrayList<>();
        MyReader myReader = new MyReader("src/itemPool.csv");
        myReader.Read();
        //Read Item Pool - END

        //Creation of Characters - START
        CharacterCreator cc = new CharacterCreator();
        //todo ask for character names
        Fighter fighter = (Fighter) cc.characterCreate("Fighter", "FighterDog");
        Healer healer = (Healer) cc.characterCreate("Healer", "HealerBird");
        Tank tank = (Tank) cc.characterCreate("Tank", "TankCat");
        //Creation of Characters - END

        team.add(fighter);
        team.add(healer);
        team.add(tank);
        int levelCount = 0;
        currentLevel = new Level(levelCount, team, cc);

        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            Character pickedCharacter;
            //User Interface - START

            System.out.print("Character Selection: ");
            for (int i = 0; i < team.size(); i++) {
                System.out.print("[" + (i + 1) + "]: " + team.get(i).getName() + " | ");
            }
            if (currentLevel.isLevelClear()) {
                System.out.print("[NEXT]: Next Level");
            }
            System.out.println("");

            String input = inputScanner.next();

            if (currentLevel.isLevelClear()) {
                if (input.equals("NEXT")) {
                    levelCount++;
                    currentLevel = new Level(levelCount, team, cc);
                    continue;
                }
            }

            try {
                int characterInput = Integer.parseInt(input);
                if (0 < characterInput && characterInput <= team.size()) {
                    if (team.get(characterInput - 1).stunnedForX == 0) {
                        pickedCharacter = team.get(characterInput - 1);
                    } else {
                        System.out.println(team.get(characterInput - 1).getName() + " is stunned for " + team.get(characterInput - 1).getStunnedForX() + " more turns.");
                        continue;
                    }
                } else {
                    System.out.println("Please type valid input.");
                    inputScanner.nextLine();
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please type valid input.");
                inputScanner.nextLine();
                continue;
            }
            System.out.println("Picked Character: " + pickedCharacter.getName());
            System.out.println("List of actions: [1]: Attack | [2]: Special Action | [3]: Pick | [4]: Wield | [5]: Wear | [6]: Examine | [7]: List Inventory");
            try {
                int actionInput = inputScanner.nextInt();
                List<Item> itemsOnTheGround = currentLevel.getGround();
                if (0 < actionInput && actionInput <= 7) {
                    int targetInput;
                    List<Character> targets;
                    switch (actionInput) {
                        case 1:
                            // ATTACK //

                            if (pickedCharacter.getHideCount() > 0) {
                                System.out.println(pickedCharacter.getName() + " is hiding for " + pickedCharacter.getHideCount() + " more turns!");
                                continue;
                            }

                            System.out.println("Select a target");
                            targets = currentLevel.getAttackTargets(pickedCharacter);
                            int i = 1;
                            for (Character c : targets) {
                                System.out.println(i + ": " + c.getName());
                                i++;
                            }
                            targetInput = inputScanner.nextInt();
                            if (0 < targetInput && targetInput <= targets.size()) {
                                pickedCharacter.Attack(targets.get(targetInput - 1));
                                if (currentLevel.isLevelClear()) {
                                    System.out.println("Level is clear! Type NEXT on Character Selection Menu to jump to the next level.");
                                } else {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");

                                }
                            }
                            break;
                        case 2:
                            // SPECIAL ACTION //

                            System.out.println("Select a target");
                            targets = currentLevel.getAttackTargets(pickedCharacter);
                            int j = 1;
                            if (pickedCharacter.getEquippedWeapon().getType().equals("Wand")) {
                                targets.add(pickedCharacter);
                            }
                            for (Character c : targets) {
                                System.out.println(j + ": " + c.getName());
                                j++;
                            }
                            targetInput = inputScanner.nextInt();
                            if (0 < targetInput && targetInput <= targets.size()) {
                                pickedCharacter.SpecialAction(targets.get(targetInput - 1));

                                if (!currentLevel.isLevelClear()) {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");
                                }
                            }
                            break;
                        case 3:
                            // PICK //

                            int k = 1;

                            if (itemsOnTheGround.isEmpty()) {
                                System.out.println("Nothing on the ground.");
                            } else {
                                for (Item item : itemsOnTheGround
                                ) {
                                    System.out.println(k + ": " + item.getName());
                                    k++;
                                }
                                targetInput = inputScanner.nextInt();
                                if (0 < targetInput && targetInput <= itemsOnTheGround.size()) {
                                    pickedCharacter.Pick(itemsOnTheGround.get(targetInput - 1));
                                }
                                if (!currentLevel.isLevelClear()) {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");
                                }
                            }
                            break;
                        case 4:
                            // WIELD //

                            int m = 1;
                            int n = 1;

                            List<Weapon> weaponsOnTheGround = currentLevel.getWeaponsOnTheGround();
                            List<Weapon> weaponsInInventory = pickedCharacter.getWeaponsInInventory();

                            if (weaponsOnTheGround.isEmpty()) {
                                System.out.println("Nothing on the ground.");
                            } else {
                                for (Weapon weapon : weaponsOnTheGround
                                ) {
                                    System.out.println(m + ": " + weapon.getName() + "(Ground)");
                                    m++;
                                }
                            }

                            for (Weapon weapon : weaponsInInventory) {
                                System.out.println(m + ": " + weapon.getName());
                                m++;
                                n++;
                            }

                            targetInput = inputScanner.nextInt();
                            if (0 < targetInput && targetInput <= m) {

                                if (targetInput <= (m - n)) {
                                    pickedCharacter.Wield(weaponsOnTheGround.get(targetInput - 1));
                                } else {
                                    pickedCharacter.Wield(weaponsInInventory.get(n - 2));
                                }
                                if (!currentLevel.isLevelClear()) {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");
                                }
                            }
                            break;
                        case 5:
                            // WEAR //

                            int p = 1;
                            int r = 1;

                            List<Clothing> clothingsOnTheGround = currentLevel.getClothingsOnTheGround();
                            List<Clothing> clothingsInTheInventory = pickedCharacter.getClothingsInInventory();

                            if (clothingsOnTheGround.isEmpty()) {
                                System.out.println("Nothing on the ground.");
                            } else {
                                for (Clothing clothing : clothingsOnTheGround) {
                                    System.out.println(p + ": " + clothing.getName() + "(Ground)");
                                    p++;
                                }
                            }
                            for (Clothing clothing : clothingsInTheInventory) {
                                System.out.println(p + ": " + clothing.getName());
                                p++;
                                r++;
                            }
                            targetInput = inputScanner.nextInt();
                            if (0 < targetInput && targetInput < p) {
                                if (targetInput <= (p - r)) {
                                    pickedCharacter.Wear(clothingsOnTheGround.get(targetInput - 1));
                                } else {
                                    pickedCharacter.Wear(clothingsInTheInventory.get(r - 2));
                                }
                                if (!currentLevel.isLevelClear()) {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");
                                }
                            }
                            break;
                        case 6:
                            // EXAMINE //

                            int a = 1;
                            int b = 1;

                            for (Item item : itemsOnTheGround) {
                                System.out.println(a + ": " + item.getName());
                                a++;
                            }

                            for (Item item : pickedCharacter.getInventory()) {
                                System.out.println(b + ": " + item.getName());
                                a++;
                                b++;
                            }
                            targetInput = inputScanner.nextInt();
                            if (0 < targetInput && targetInput <= a) {
                                if (targetInput <= (a - b)) {
                                    pickedCharacter.Examine(itemsOnTheGround.get(targetInput - 1));
                                } else {
                                    pickedCharacter.Examine(pickedCharacter.getInventory().get(targetInput - 1));
                                }
                                if (!currentLevel.isLevelClear()) {
                                    currentLevel.EnemyAttacks();
                                    currentLevel.RoundEnd();
                                    System.out.println(" --------------- End of round --------------- ");
                                }
                            }
                            break;
                        case 7:
                            // LIST INVENTORY //

                            pickedCharacter.ListInventory();
                            break;
                    }
                } else {
                    System.out.println("Please type valid input.");
                    inputScanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Please type valid input.");
                inputScanner.nextLine();
            }

            //User Interface - END
        }
    }
}
