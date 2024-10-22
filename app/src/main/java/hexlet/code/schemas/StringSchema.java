package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private Integer minLength = null;
    private String contains = null;

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        this.contains = substring;
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (!super.isValid(value)) {
            return false;
        }
        if (minLength != null && (value == null || value.length() < minLength)) {
            return false;
        }
        if (contains != null && (value == null || !value.contains(contains))) {
            return false;
        }
        return true;
    }
}
