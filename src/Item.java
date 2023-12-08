public abstract class Item {
    protected String name;
    protected int weight;
    protected int value;
    String type;

    Item(String name, int weight, int value, String type) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.type = type;
    }

    String getInfo() {
        return this.getName() + " | " + this.getWeight() + " | " + this.getValue();
    }

    String getName() {
        return this.name;
    }

    int getWeight() {
        return this.weight;
    }

    int getValue() {
        return this.value;
    }

    String getType() {
        return this.type;
    }
}
