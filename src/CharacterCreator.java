import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
public class CharacterCreator {
    public Character characterCreate(String type, String name) throws Exception {
        if (Objects.equals(type, "Fighter")) {
            int randomStr = ThreadLocalRandom.current().nextInt(6, 10 + 1);
            int randomVit = ThreadLocalRandom.current().nextInt(3, 7 + 1);
            int randomInt = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            return new Fighter(name, randomStr, randomVit, randomInt);
        } else if (Objects.equals(type, "Tank")) {
            int randomStr = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            int randomVit = ThreadLocalRandom.current().nextInt(6, 10 + 1);
            int randomInt = ThreadLocalRandom.current().nextInt(3, 7 + 1);
            return new Tank(name, randomStr, randomVit, randomInt);
        } else if (Objects.equals(type, "Healer")) {
            int randomStr = ThreadLocalRandom.current().nextInt(3, 7 + 1);
            int randomVit = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            int randomInt = ThreadLocalRandom.current().nextInt(6, 10 + 1);
            return new Healer(name, randomStr, randomVit, randomInt);
        } else throw new Exception(new Error("Invalid entry given on character type."));
    }

    public Enemy enemyCreate(int enemyNumber) {
        int randomStr = ThreadLocalRandom.current().nextInt(1, 5 + 1);
        int randomVit = ThreadLocalRandom.current().nextInt(1, 5 + 1);
        int randomInt = ThreadLocalRandom.current().nextInt(1, 5 + 1);
        return new Enemy("Enemy" + enemyNumber, randomStr, randomVit, randomInt);
    }
}
