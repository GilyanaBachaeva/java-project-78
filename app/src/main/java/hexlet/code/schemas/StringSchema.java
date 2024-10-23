package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        addData("required", value -> !isRequired() || !(value == null || value.isEmpty()));
    }

    public StringSchema required() {
        setRequired(true);
        return this;
    }

    public StringSchema minLength(int length) {
        addData("minLength", value -> value.length() > length);
        return this;
    }

    public StringSchema contains(String search) {
        addData("contains", value -> value.contains(search));
        return this;
    }
}
