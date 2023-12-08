public class Weapon extends Item {

    Weapon(String name, int weight, int value, String type) {
        super(name, weight, value, type);
    }

    public int ReturnDamage(){
        return super.getValue();
    }


}

