import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValidatorTest {

    @Test
    public void testIsValidWithoutRequired() {
        //Проверяет, что без вызова метода required() пустая строка и null считаются валидными.
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true
    }

    @Test
    public void testIsValidWithRequired() {
        //Проверяет, что после вызова метода required() пустая строка и null становятся невалидными.
        Validator v = new Validator();
        StringSchema schema = (StringSchema) v.string().required();

        assertFalse(schema.isValid(null)); // false
        assertFalse(schema.isValid("")); // false
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testMinLength() {
        //Проверяет, что строка должна иметь минимальную длину, установленную методом minLength()
        Validator v = new Validator();
        final int minLeng = 5;
        StringSchema schema = v.string().required().minLength(minLeng);

        assertFalse(schema.isValid("1234")); // false
        assertTrue(schema.isValid("123456")); // true
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testContains() {
        //Проверяет, что строка должна содержать определённую подстроку, установленную методом contains()
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
        final int minLeng = 5;
        StringSchema schema = v.string().required().minLength(minLeng).contains("hex");

        assertFalse(schema.isValid("")); // false
        assertFalse(schema.isValid("1234")); // false
        assertFalse(schema.isValid("hello")); // false
        assertTrue(schema.isValid("hexlet")); // true
        assertFalse(schema.isValid("hex")); // false
    }

    @Test
    public void testLastValidationTakesPrecedence() {
        //Проверяет, что если один валидатор вызывается несколько раз, последний имеет приоритет
        Validator v = new Validator();
        final int minLeng = 10;
        final int minLeng1 = 4;
        StringSchema schema = v.string().minLength(minLeng).minLength(minLeng1);

        assertTrue(schema.isValid("Hexlet")); // true
        assertFalse(schema.isValid("Hex")); // false
    }
    @Test
    public void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();
        final int randNumb = 5;
        final int randNumb1 = 10;
        final int randNumb2 = -10;
        final int randNumb3 = 0;
        final int randNumb4 = 4;
        final int randNumb5 = 11;

        assertTrue(schema.isValid(randNumb)); // true
        assertTrue(schema.isValid(null)); // true
        assertTrue(schema.positive().isValid(null)); // true

        schema.required();
        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(randNumb1)); // true

        assertFalse(schema.isValid(randNumb2)); // false
        assertFalse(schema.isValid(randNumb3)); // false

        schema.range(randNumb, randNumb1);
        assertTrue(schema.isValid(randNumb)); // true
        assertTrue(schema.isValid(randNumb1)); // true
        assertFalse(schema.isValid(randNumb4)); // false
        assertFalse(schema.isValid(randNumb5)); // false
    }
    @Test
    public void testRequired() {
        MapSchema schema = new MapSchema().required();

        // Проверяем, что null не валиден
        assertFalse(schema.isValid(null));

        // Проверяем, что пустая карта не валидна
        assertFalse(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testOptional() {
        MapSchema schema = new MapSchema();

        // Проверяем, что null валиден, если не обязательный
        assertTrue(schema.isValid(null));

        // Проверяем, что пустая карта валидна, если не обязательный
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testSizeOf() {
        MapSchema schema = new MapSchema().sizeof(2);

        // Проверяем, что карта с размером меньше 2 не валидна
        Map<String, String> smallMap = new HashMap<>();
        smallMap.put("key1", "value1");
        assertFalse(schema.isValid(smallMap));

        // Проверяем, что карта с размером 2 валидна
        Map<String, String> validMap = new HashMap<>();
        validMap.put("key1", "value1");
        validMap.put("key2", "value2");
        assertTrue(schema.isValid(validMap));
    }

    @Test
    public void testRequired2() {
        MapSchema schema = new MapSchema().required();

        // Проверяем, что null не валиден
        assertFalse(schema.isValid(null));

        // Проверяем, что пустая карта не валидна
        assertFalse(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testOptional2() {
        MapSchema schema = new MapSchema();

        // Проверяем, что null валиден, если не обязательный
        assertTrue(schema.isValid(null));

        // Проверяем, что пустая карта валидна, если не обязательный
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    public void testSizeOf2() {
        MapSchema schema = new MapSchema().sizeof(2);

        // Проверяем, что карта с размером меньше 2 не валидна
        Map<String, String> smallMap = new HashMap<>();
        smallMap.put("key1", "value1");
        assertFalse(schema.isValid(smallMap));

        // Проверяем, что карта с размером 2 валидна
        Map<String, String> validMap = new HashMap<>();
        validMap.put("key1", "value1");
        validMap.put("key2", "value2");
        assertTrue(schema.isValid(validMap));
    }
}
