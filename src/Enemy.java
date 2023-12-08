import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Character {
    Enemy(String name, int strength, int vitality, int intelligence) {
        super(name, strength, vitality, intelligence);
        super.Wield(new Weapon("Scimitar", 2, 2, "Sword"));
        super.Wear(new Clothing("Deerskin Armor", 2, 3, "Light Armor"));
    }

    @Override
    public void Refresh() {
        if (stunnedForX > 0) {
            stunnedForX--;
        }
    }

    @Override
    void Broadcast() {
        //Empty. We don't want to broadcast enemy births.
    }

    //Overrides Character Class/Die Method
    public void Die() {
        Main.score++;
        Main.currentLevel.getEnemyList().remove(this);
        System.out.println(this.getName() + " is dead!");
        int dropPool = ThreadLocalRandom.current().nextInt(0, 6);
        int drop;
        switch (dropPool) {
            case 0:
                drop = ThreadLocalRandom.current().nextInt(0, Main.swordPool.size());
                Main.currentLevel.itemDrop(Main.swordPool.get(drop));
                break;
            case 1:
                drop = ThreadLocalRandom.current().nextInt(0, Main.wandPool.size());
                Main.currentLevel.itemDrop(Main.wandPool.get(drop));
                break;
            case 2:
                drop = ThreadLocalRandom.current().nextInt(0, Main.shieldPool.size());
                Main.currentLevel.itemDrop(Main.shieldPool.get(drop));
                break;
            case 3:
                drop = ThreadLocalRandom.current().nextInt(0, Main.lightPool.size());
                Main.currentLevel.itemDrop(Main.lightPool.get(drop));
                break;
            case 4:
                drop = ThreadLocalRandom.current().nextInt(0, Main.mediumPool.size());
                Main.currentLevel.itemDrop(Main.mediumPool.get(drop));
                break;
            case 5:
                drop = ThreadLocalRandom.current().nextInt(0, Main.heavyPool.size());
                Main.currentLevel.itemDrop(Main.heavyPool.get(drop));
                break;
            default:
                break;

        }
    }

    //Overloads it's parent's Attack function. This one does not take args
    public void Attack() {
        if (stunnedForX > 0 && Main.currentLevel.getEnemyList().size() > 1) {
            Main.currentLevel.EnemyAttacks();
        } else {
            List<Character> heroes = Main.team;
            int target = ThreadLocalRandom.current().nextInt(0, Main.team.size());
            if(heroes.get(target).getHideCount() == 0){
                super.Attack(heroes.get(target));
            } else {
                Main.currentLevel.EnemyAttacks();
            }
        }
    }


}
