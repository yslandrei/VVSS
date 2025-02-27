package inventory.model;

public class InhousePart extends Part {

    // Declare fields
    private int machineId;

    // Constructor
    public InhousePart(int partId, String name, double price, int inStock, int min, int max, int machineId) {
        super(partId, name, price, inStock, min, max);
        this.machineId = machineId;
    }
    
    // Getter
    public int getMachineId() {
        return machineId;
    }
    
    // Setter
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Generate an error message for invalid values in a part
     * Valid part will return an empty string
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     * @param errorMessage
     * @param machineId
     * @return
     */
    public static String isValidPart(String name, double price, int inStock, int min, int max, int machineId,
                                     String errorMessage) {
        if(name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if(price < 0.01) {
            errorMessage += "The price must be greater than 0. ";
        }
        if(inStock < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if(min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if(machineId > 0) {
            errorMessage += "The machine id must be greater than 0. ";
        }
        return errorMessage;
    }

    @Override
    public String toString() {
        return "I,"+super.toString()+","+getMachineId();
    }
}


