import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValidatorTest {

    @Test
    public void testIsValidWithoutRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true
    }

    @Test
    public void testIsValidWithRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();

        assertFalse(schema.isValid(null)); // false
        assertFalse(schema.isValid("")); // false
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5);

        assertFalse(schema.isValid("1234")); // false
        assertTrue(schema.isValid("12345")); // true
        assertTrue(schema.isValid("valid string")); // true
    }

    @Test
    public void testContains() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().contains("hex");

        assertTrue(schema.isValid("hexlet")); // true
        assertFalse(schema.isValid("hello")); // false
        assertFalse(schema.isValid("")); // false
        assertFalse(schema.isValid(null)); // false
    }

    @Test
    public void testMultipleValidations() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5).contains("hex");

        assertFalse(schema.isValid("")); // false
        assertFalse(schema.isValid("1234")); // false
        assertFalse(schema.isValid("hello")); // false
        assertTrue(schema.isValid("hexlet")); // true
        assertFalse(schema.isValid("hex")); // false
    }

    @Test
    public void testLastValidationTakesPrecedence() {
        Validator v = new Validator();
        StringSchema schema = v.string().minLength(10).minLength(4);

        assertTrue(schema.isValid("Hexlet")); // true
        assertFalse(schema.isValid("Hex")); // false
    }
}
