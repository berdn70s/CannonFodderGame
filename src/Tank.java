import java.util.concurrent.ThreadLocalRandom;

public class Tank extends Character {


    Tank(String name, int strength, int vitality, int intelligence) {
        super(name, strength, vitality, intelligence);
        super.Wield(new Weapon("Buckler", 1, 1, "Shield"));
        super.Wear(new Clothing("Wolfskin Armor", 2, 4, "Light Armor"));
        Broadcast();
    }


    @Override
    void Broadcast() {
        Weapon weapon = super.getEquippedWeapon();
        System.out.println("Tank " + super.getName() +
                " created with S: " + super.getStrength() +
                ", V: " + super.getVitality() +
                ", I: " + super.getIntelligence() +
                ". The HP is " + super.getHPCurrent() +
                ". Tank wields " + weapon.getName() +
                ", with " + weapon.getValue() +
                " damage and " + weapon.getWeight() +
                " units of weight.");
    }
}
