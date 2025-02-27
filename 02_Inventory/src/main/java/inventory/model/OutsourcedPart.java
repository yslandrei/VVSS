
package inventory.model;


public class OutsourcedPart extends Part {
    
    // Declare fields
    private String companyName;

    // Constructor
    public OutsourcedPart(int partId, String name, double price, int inStock, int min, int max, String companyName) {
        super(partId, name, price, inStock, min, max);
        this.companyName = companyName;
    }
    
    // Getter
    public String getCompanyName() {
        return companyName;
    }
    
    // Setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
     * @param companyName
     * @return
     */
    public static String isValidPart(String name, double price, int inStock, int min, int max, String companyName,
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
        if(companyName.equals("")) {
            errorMessage += "A Company name has not been entered. ";
        }
        return errorMessage;
    }

    @Override
    public String toString() {
        return "O,"+super.toString()+","+getCompanyName();
    }

}
