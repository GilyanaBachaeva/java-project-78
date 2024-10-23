package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        addData("required", value -> !isRequired() || value != null);
    }

    public NumberSchema required() {
        setRequired(true);
        return this;
    }

    public NumberSchema positive() {
        addData("positive", value -> value > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addData("range", value -> value >= min && value <= max);
        return this;
    }

}
