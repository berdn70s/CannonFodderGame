import java.util.concurrent.ThreadLocalRandom;

public class Healer extends Character {
    Healer(String name, int strength, int vitality, int intelligence) {
        super(name, strength, vitality, intelligence);
        super.Wield(new Weapon("Shaman's Wand", 1, 1, "Wand"));
        super.Wear(new Clothing("Deerskin Armor", 2, 3, "Light Armor"));
        Broadcast();
    }
    @Override
    void Broadcast() {
        Weapon weapon = super.getEquippedWeapon();
        System.out.println("Healer " + super.getName() +
                " created with S: " + super.getStrength() +
                ", V: " + super.getVitality() +
                ", I: " + super.getIntelligence() +
                ". The HP is " + super.getHPCurrent() +
                ". Healer wields " + weapon.getName() +
                ", with " + weapon.getValue() +
                " damage and " + weapon.getWeight() +
                " units of weight.");
    }

}
