package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Integer size;

    public MapSchema sizeof(int size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean isValid(Map<?, ?> value) {
        try {
            validateRequired(value);
            if (size != null && value.size() != size) {
                return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void validateRequired(Map<?,?> value) {
        if (isRequired && value == null) {
            throw new IllegalArgumentException("Value is required and cannot be null.");
        }
    }
}
