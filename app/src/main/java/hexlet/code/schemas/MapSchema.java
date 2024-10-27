package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema() {
        addChecks("required", value -> !isRequired() || !(value == null || value.isEmpty()));
    }

    public MapSchema required() {
        setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        addChecks("sizeof", value -> value.size() >= size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> map) {
        addChecks("shape", value -> map.entrySet().stream().allMatch(k -> {
            Object obj = value.get(k.getKey());
            return k.getValue().isValid((String) obj);
        }));
        return this;
    }
}
