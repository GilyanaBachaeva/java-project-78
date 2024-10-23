import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValidatorTest {

    @Test
    public void testIsValidWithoutRequired() { //Проверяет, что без вызова метода required() пустая строка и null считаются валидными.
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true
    }

    @Test
    public void testIsValidWithRequired() { //Проверяет, что после вызова метода required() пустая строка и null становятся невалидными.
        Validator v = new Validator();
        StringSchema schema = (StringSchema) v.string().required();

        assertFalse(schema.isValid(null)); // false
        assertFalse(schema.isValid("")); // false
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testMinLength() { //Проверяет, что строка должна иметь минимальную длину, установленную методом minLength()
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5);

        assertFalse(schema.isValid("1234")); // false
        assertTrue(schema.isValid("12345")); // true
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testContains() { //Проверяет, что строка должна содержать определённую подстроку, установленную методом contains()
        Validator v = new Validator();
        StringSchema schema = v.string().required().contains("hex");

        assertTrue(schema.isValid("hexlet")); // true
        assertFalse(schema.isValid("hello")); // false
        assertFalse(schema.isValid("")); // false
        assertFalse(schema.isValid(null)); // false
    }

    @Test
    public void testMultipleValidations() { //Проверяет, что все условия валидации работают вместе
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5).contains("hex");

        assertFalse(schema.isValid("")); // false
        assertFalse(schema.isValid("1234")); // false
        assertFalse(schema.isValid("hello")); // false
        assertTrue(schema.isValid("hexlet")); // true
        assertFalse(schema.isValid("hex")); // false
    }

    @Test
    public void testLastValidationTakesPrecedence() { //Проверяет, что если один валидатор вызывается несколько раз, последний имеет приоритет
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(10).minLength(4);

        assertTrue(schema.isValid("Hexlet")); // true
        assertFalse(schema.isValid("Hex")); // false
    }
    @Test
    public void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(5)); // true
        assertTrue(schema.isValid(null)); // true
        assertTrue(schema.positive().isValid(null)); // true

        schema.required();
        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(10)); // true

        assertFalse(schema.isValid(-10)); // false
        assertFalse(schema.isValid(0)); // false

        schema.range(5, 10);
        assertTrue(schema.isValid(5)); // true
        assertTrue(schema.isValid(10)); // true
        assertFalse(schema.isValid(4)); // false
        assertFalse(schema.isValid(11)); // false
    }
    @Test
    public void testValidMapWithoutRequired() {
        MapSchema schema = new MapSchema();
        assertTrue(schema.isValid(null)); // Проверяет, что null значение проходит валидацию, если не установлено обязательное условие.
    }

    @Test
    public void testValidMapWithRequired() { // Проверяет, что null значение не проходит валидацию, если установлено обязательное условие, и что пустая карта проходит валидацию.
        MapSchema schema = (MapSchema) new MapSchema().required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testValidMapWithOneEntry() { // Проверяет, что карта с одной парой ключ-значение проходит валидацию, если установлено обязательное условие.
        MapSchema schema = (MapSchema) new MapSchema().required();
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));
    }

    @Test
    public void testInvalidMapSize() { // Проверяет, что карта с одной парой ключ-значение не проходит валидацию, если установлено ограничение на размер в 2.
        MapSchema schema = new MapSchema().required().sizeof(2);
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertFalse(schema.isValid(data));
    }

    @Test
    public void testValidMapSize() { // Проверяет, что карта с двумя парами ключ-значение проходит валидацию, если установлено ограничение на размер в 2.
        MapSchema schema = new MapSchema().required().sizeof(2);
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }

    @Test
    public void testValidMapWithDifferentSize() { //  Проверяет, что карта с одной парой ключ-значение не проходит валидацию, если установлено ограничение на размер в 2.
        MapSchema schema = new MapSchema().required().sizeof(2);
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertFalse(schema.isValid(data));
    }
}
