package integration.step2;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        InhousePart part = mock(InhousePart.class);
        when(part.getName()).thenReturn("Part999");
        when(part.getPrice()).thenReturn(99.0);
        when(part.getInStock()).thenReturn(15);
        when(part.getMin()).thenReturn(5);
        when(part.getMax()).thenReturn(25);
        when(part.getMachineId()).thenReturn(1);

        service.addInhousePart(part.getName(), part.getPrice(), part.getInStock(), part.getMin(), part.getMax(), part.getMachineId());

        List<Part> result = service.getAllParts();
        assertEquals(1, result.size());
    }
}
