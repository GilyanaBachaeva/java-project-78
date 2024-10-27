package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        addChecks("required", value -> !isRequired() || !(value == null || value.isEmpty()));
    }

    public StringSchema required() {
        setRequired(true);
        return this;
    }

    public StringSchema minLength(int length) {
        addChecks("minLength", value -> value.length() > length);
        return this;
    }

    public StringSchema contains(String search) {
        addChecks("contains", value -> value.contains(search));
        return this;
    }
}
