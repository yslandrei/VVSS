package integration.step1;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryRepositoryTest {
    InventoryRepository repository;

    @BeforeEach
    public void setUp() {
        //repository = new InventoryRepository("C:\\Users\\GIGABYTE\\OneDrive\\Desktop\\Facultate\\Semestrul 6\\VVSS\\infinity_inventory_vvss\\data\\items.txt");
        repository = new InventoryRepository();
    }

    @AfterEach
    public void tearDown() {
        repository.getAllParts().clear();
        repository.writeAll();
    }

    @Test
    public void getAll() {
        assertEquals(0, repository.getAllParts().size());
    }

    @Test
    public void add() {
        Part part = getPart(1, "Part999", 99, 15, 5, 25, 1);
        repository.addPart(part);
        List<Part> parts = repository.getAllParts();
        assertEquals(1, parts.size());
        assertEquals(part.getPartId(), parts.get(0).getPartId());
        assertEquals(part.getName(), parts.get(0).getName());
        assertEquals(part.getInStock(), parts.get(0).getInStock());
        assertEquals(part.getPrice(), parts.get(0).getPrice());
        assertEquals(part.getMin(), parts.get(0).getMin());
        assertEquals(part.getMax(), parts.get(0).getMax());
    }

    Part getPart(int partId, String partName, double price, int inStock, int min, int max, int machineId) {
        InhousePart part = mock(InhousePart.class);
        when(part.getPartId()).thenReturn(partId);
        when(part.getName()).thenReturn(partName);
        when(part.getPrice()).thenReturn(price);
        when(part.getInStock()).thenReturn(inStock);
        when(part.getMin()).thenReturn(min);
        when(part.getMax()).thenReturn(max);
        return part;
    }
}
