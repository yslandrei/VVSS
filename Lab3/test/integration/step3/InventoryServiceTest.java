package integration.step3;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    InventoryRepository repository;
    InventoryService service;

    @BeforeEach
    void setUp() {
        //repository = new InventoryRepository("C:\\Users\\GIGABYTE\\OneDrive\\Desktop\\Facultate\\Semestrul 6\\VVSS\\infinity_inventory_vvss\\data\\items.txt");
        repository = new InventoryRepository();
        service = new InventoryService(repository);
    }

    @AfterEach
    void tearDown() {
        repository.getAllParts().clear();
        repository.writeAll();
    }

    @Test
    void getParts() {
        List<Part> result = service.getAllParts();
        assertEquals(0, result.size());
    }

    @Test
    void addPart() {
        InhousePart part = new InhousePart(1, "Part999", 99, 15, 5, 25, 1);
        service.addInhousePart(part.getName(), part.getPrice(), part.getInStock(), part.getMin(), part.getMax(), part.getMachineId());
        List<Part> result = service.getAllParts();
        assertEquals(1, result.size());
    }
}
