import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Character implements ICharacter {
    protected String name;
    protected int strength;
    protected int vitality;
    protected int intelligence;
    private int hideCount;
    private int hpMax;
    private int hpCurrent;
    private Clothing equippedClothing;
    private Weapon equippedWeapon;
    private int skillCooldown = 0;
    private List<Item> inventory;
    protected int stunnedForX = 0;

    public void Refresh() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
        if (stunnedForX > 0) {
            stunnedForX--;
        }
        if (hideCount > 0) {
            hideCount--;
        }
    }

    public List<Item> getInventory() {
        return inventory;
    }

    //Defined differently on each child.
    abstract void Broadcast();

    public int getStrength() {
        return strength;
    }

    public int getVitality() {
        return vitality;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getHideCount() {
        return hideCount;
    }

    public int getStunnedForX() {
        return stunnedForX;
    }

    Character(String name, int strength, int vitality, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.vitality = vitality;
        this.intelligence = intelligence;
        this.inventory = new ArrayList<>();
        this.SetHP(strength, vitality, intelligence);
    }

    //Defined different on the Enemy class.
    public void Die() {
        System.out.println(this.getName() + " is dead!");
        Main.team.remove(this);
        if (Main.team.isEmpty()) {
            System.out.println("Game Over\nYour score is " + Main.score);
            System.exit(0);
        }
    }

    public String getName() {
        return this.name;
    }

    public int getHPCurrent() {
        return hpCurrent;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    protected void SetHP(int strength, int vitality, int intelligence) {
        hpMax = (int) Math.ceil(vitality * 7 + strength * 2 + intelligence);
        hpCurrent = hpMax;
    }

    public List<Weapon> getWeaponsInInventory() {
        List<Weapon> weaponsInInventory = new ArrayList<>();
        for (Item item : inventory
        ) {
            if (item.getType().equals("Sword") || item.getType().equals("Wand") || item.getType().equals("Shield")) {
                weaponsInInventory.add((Weapon) item);
            }
        }
        return weaponsInInventory;
    }

    public List<Clothing> getClothingsInInventory() {
        List<Clothing> clothingsInInventory = new ArrayList<>();
        for (Item item : inventory
        ) {
            if (item.getType().equals("Light Armor") || item.getType().equals("Medium Armor") || item.getType().equals("Heavy Armor")) {
                clothingsInInventory.add((Clothing) item);
            }
        }
        return clothingsInInventory;
    }

    public void Attack(Character target) {
        int damage = this.equippedWeapon.ReturnDamage();
        if (Objects.equals(equippedWeapon.getType(), "Sword")) {
            damage *= strength;
        } else if (Objects.equals(equippedWeapon.getType(), "Shield")) {
            damage *= vitality;
        } else if (Objects.equals(equippedWeapon.getType(), "Wand")) {
            damage *= intelligence;
        }
        System.out.println(this.getName() + " attacks " + target.getName());

        System.out.print(this.getName() + " deals ");
        target.ReduceHP(damage);
    }

    private void ReduceHP(int damageTaken) {
        damageTaken -= equippedClothing.value;
        if (damageTaken < 0) {
            damageTaken = 0;
        }
        this.hpCurrent -= damageTaken;
        if (hpCurrent <= 0) {
            this.Die();
        } else {
            System.out.print(damageTaken + " damage\n");
            System.out.println(this.getName() + " has " + this.getHPCurrent() + " hp left ");
        }
    }

    private void HealHP(int healTaken) {
        this.hpCurrent += healTaken;
        if (hpCurrent > hpMax) {
            hpCurrent = hpMax;
        }
    }

    public void SpecialAction(Character target) {
        switch (equippedWeapon.getType()) {
            case "Sword":
                hideCount = this.equippedWeapon.value * this.strength / 4;
                this.skillCooldown = this.strength;
                System.out.println(getName() + " is hiding for " + hideCount + " rounds!");
                System.out.println(getName() + " went into " + strength + " turns skill cooldown");
                break;
            case "Wand":
                int healAmount = this.equippedWeapon.value * this.intelligence / 2;
                target.HealHP(healAmount);
                this.skillCooldown = this.intelligence;
                System.out.println(this.getName() + " healed " + target.getName() + " for " + healAmount);
                System.out.println(getName() + " went into " + strength + " turns skill cooldown");
                break;
            case "Shield":
                target.stunnedForX = this.equippedWeapon.value * this.vitality / 4;
                this.skillCooldown = this.vitality;
                System.out.println(target.getName() + " is stunned for " + target.stunnedForX + " rounds!");
                System.out.println(getName() + " went into " + strength + " turns skill cooldown");
                break;
        }
    }

    public boolean CanPick(Item itemToPick) {
        int weightCarried = 0;
        for (Item item : inventory) {
            weightCarried += item.getWeight();
        }
        if (itemToPick.getWeight() + weightCarried > strength * 4) {
            System.out.println("I'm Carrying Too much!");
            return false;
        }
        return true;
    }

    public void Pick(Item itemToPick) {
        if (CanPick(itemToPick)) {
            inventory.add(itemToPick);
            if (Main.currentLevel.getGround().contains(itemToPick)) {
                Main.currentLevel.getGround().remove(itemToPick);
            }
        }
    }

    public void Wield(Weapon newWeapon) {
        //If the weapon is from inventory, wield it.
        if (this.inventory.contains(newWeapon)) {
            this.equippedWeapon = newWeapon;
        }
        //If it's from the ground, first check if he/she can pick it.
        else if (CanPick(newWeapon)) {
            Pick(newWeapon);
            this.equippedWeapon = newWeapon;
        }
    }

    public void Wear(Clothing newClothing) {
        //Same with wield situation.
        if (this.inventory.contains(newClothing)) {
            this.equippedClothing = newClothing;
        } else if (CanPick(newClothing)) {
            Pick(newClothing);
            this.equippedClothing = newClothing;
        }
    }

    public void Examine(Item itemToExamine) {
        System.out.println("Examine Item                    : " + itemToExamine.getInfo());
    }

    public void ListInventory() {
        System.out.println("###########################");
        System.out.println("                                : Name | Weight | Value\n");
        System.out.println("Equipped Weapon                 : " + (equippedWeapon == null ? "None" : equippedWeapon.getInfo()));
        System.out.println("Equipped Clothing               : " + (equippedClothing == null ? "None" : equippedClothing.getInfo()));
        System.out.println("Inventory                       : ");

        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Item item : inventory) {
                System.out.println("                                  " + item.getInfo());
            }
        }
        System.out.println("###########################");
    }
}
