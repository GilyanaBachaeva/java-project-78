package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;
import java.util.LinkedHashMap;

public abstract class BaseSchema<T> {
    private Map<String, Predicate<T>> data = new LinkedHashMap<>();
    private boolean isRequired;

    /**
     * Проверяет, является ли объект обязательным для валидации.
     * <p>
     * Этот метод позволяет подклассам узнать, является ли объект обязательным.
     * Если объект обязателен, то он не должен быть null при проверке валидности.
     * </p>
     *
     * @return true, если объект обязателен, иначе false
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Устанавливает статус обязательности для объекта.
     *
     * @param required true, если объект должен быть обязательным, иначе false
     */
    public void setRequired(boolean required) {
        this.isRequired = required;
    }

    public final boolean isValid(T obj) {
        if (obj == null) {
            return !isRequired;
        } else {
            return data.values().stream().allMatch(condition -> condition.test(obj));
        }
    }
    protected final void addChecks(String checkName, Predicate<T> predicate) {
        data.put(checkName, predicate);
    }
}
