package integration.step1;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    InventoryRepository repository;
    @InjectMocks
    InventoryService service;
    private ObservableList<Part> parts;

    @BeforeEach
    void setUp() {
        parts = FXCollections.observableArrayList();
        when(repository.getAllParts()).thenReturn(parts);
    }

    @Test
    void getParts() {
        List<Part> result = service.getAllParts();
        assertEquals(0, result.size());
        verify(repository, times(1)).getAllParts();
    }

    @Test
    void addPart() {
        InhousePart part = new InhousePart(1, "Part999", 99, 15, 5, 25, 1);
        doAnswer((invocationOnMock) -> {
            parts.add(part);
            return null;
        }).when(repository).addPart(any(Part.class));

        service.addInhousePart(part.getName(), part.getPrice(), part.getInStock(), part.getMin(), part.getMax(), part.getMachineId());
        verify(repository, times(1)).addPart(any());

        List<Part> result = service.getAllParts();
        assertEquals(1, result.size());
        assertEquals(part, result.get(0));
    }
}
