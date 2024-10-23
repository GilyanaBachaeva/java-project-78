package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Number> {
    private boolean isPositive = false;
    private Integer minValue = null;
    private Integer maxValue = null;

    public NumberSchema positive() {
        this.isPositive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
        return this;
    }

    @Override
    public boolean isValid(Number value) {
        if (!super.isValid(value)) {
            return false;
        }
        if (value instanceof Number) {
            if (isPositive && value.doubleValue() <= 0) {
                return false;
            }
            if (minValue != null && value.intValue() < minValue) {
                return false;
            }
            if (maxValue != null && value.intValue() > maxValue) {
                return false;
            }
        }
        return true;
    }
}
