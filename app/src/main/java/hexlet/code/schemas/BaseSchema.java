package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean isRequired = false;
    private Integer minLength = null;
    private String contains = null;

    public boolean isValid(T value) {
        if (isRequired && (value == null || (value instanceof String && ((String) value).isEmpty()))) {
            return false;
        }
        return true;
    }

    public BaseSchema<T> required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return (StringSchema) this;
    }

    public StringSchema contains(String substring) {
        this.contains = substring;
        return (StringSchema) this;
    }
}
