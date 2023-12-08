import java.util.concurrent.ThreadLocalRandom;

public class Fighter extends Character {
    Fighter(String name, int strength, int vitality, int intelligence) {
        super(name, strength, vitality, intelligence);
        super.Wield(new Weapon("Wooden Sword", 1,1,"Sword"));
        super.Wear(new Clothing("Deerskin Armor",2,3,"Light Armor"));
        Broadcast();
    }

    @Override
    void Broadcast() {
        Weapon weapon = super.getEquippedWeapon();
        System.out.println("Fighter "+ super.getName() +
                " created with S: " + super.getStrength() +
                ", V: "+ super.getVitality() +
                ", I: "+super.getIntelligence()+
                ". The HP is " + super.getHPCurrent() +
                ". Fighter wields " + weapon.getName() +
                ", with " + weapon.getValue() +
                " damage and " + weapon.getWeight() +
                " units of weight.");
    }
}
