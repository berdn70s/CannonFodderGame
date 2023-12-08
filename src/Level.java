import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Level {
    private List<Character> team;
    public static List<Item> ground;
    private int level;
    private List<Enemy> enemyList;

    //This is an overload init, just to instantiate at the start of the game.
    Level() {
        this.team = null;
        ground = new ArrayList<>();
        this.enemyList = null;
    }

    //This is actual init.
    Level(int level, List<Character> team, CharacterCreator cc) {
        this.team = team;
        this.level = level;
        ground = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        for (int i = 1; i <= Math.pow(2, level); i++) {
            enemyList.add(cc.enemyCreate(i));
        }
        System.out.println("Creating Level " + (level) + ", with " + (int) Math.pow(2, level) + " soldiers.");
        System.out.println("Entering level " + level + ", " + WhoEnters(team));
    }
    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void RoundEnd(){
        for (Enemy enemy: enemyList
        ) {
            enemy.Refresh();
        }
        for (Character character: team
        ) {
            character.Refresh();
        }
    }

    public void EnemyAttacks(){
        int whichEnemy = ThreadLocalRandom.current().nextInt(0, enemyList.size());
        this.enemyList.get(whichEnemy).Attack();
    }
    public boolean isLevelClear() {
        return enemyList.isEmpty();
    }

    private String WhoEnters(List<Character> team) {
        String e = " enters. ";
        String line = " ";
        for (int i = 0; i < team.size(); i++) {
            line += team.get(i).getName() + e;
        }
        for (int j = 0; j < enemyList.size(); j++) {
            line += enemyList.get(j).getName() + e;
        }
        return line;
    }

    public List<Character> getAttackTargets(Character caster) {
        List<Character> targets = new ArrayList<>();
        targets.addAll(team);
        targets.addAll(enemyList);
        targets.remove(caster);
        return targets;
    }

    public List<Item> getGround() {
        return ground;
    }

    public List<Weapon> getWeaponsOnTheGround() {
        List<Weapon> weaponsOnTheGround = new ArrayList<>();

        for (Item item : ground
        ) {
            if (item.getType().equals("Sword") || item.getType().equals("Wand") || item.getType().equals("Shield")) {
                weaponsOnTheGround.add((Weapon) item);
            }
        }
        return weaponsOnTheGround;
    }

    public List<Clothing> getClothingsOnTheGround() {
        List<Clothing> clothingsOnTheGround = new ArrayList<>();

        for (Item item : ground
        ) {
            if (item.getType().equals("Light Armor") || item.getType().equals("Medium Armor") || item.getType().equals("Heavy Armor")) {
                clothingsOnTheGround.add((Clothing) item);
            }
        }
        return clothingsOnTheGround;
    }

    public void itemDrop(Item item) {
        ground.add(item);
        System.out.println(item.getName() + " is dropped!!");
    }


}
