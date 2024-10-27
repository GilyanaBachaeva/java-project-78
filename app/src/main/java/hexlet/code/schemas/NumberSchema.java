package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        addChecks("required", value -> !isRequired() || value != null);
    }

    public NumberSchema required() {
        setRequired(true);
        return this;
    }

    public NumberSchema positive() {
        addChecks("positive", value -> value > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addChecks("range", value -> value >= min && value <= max);
        return this;
    }

}
