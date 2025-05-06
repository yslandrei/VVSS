package integration.step1;

import inventory.model.InhousePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartTest {
    InhousePart part;

    @BeforeEach
    void setUp() {
        part = new InhousePart(1, "Part999", 99, 15, 5, 25, 1);
    }

    @Test
    void getPrice() {
        assertEquals(99, part.getPrice());
    }

    @Test
    void setPrice() {
        part.setPrice(100);
        assertEquals(100, part.getPrice());
    }
}
