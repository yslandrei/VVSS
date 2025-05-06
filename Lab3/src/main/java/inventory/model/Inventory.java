
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Inventory {

    // Declare fields
    private ObservableList<Product> products;
    private ObservableList<Part> parts;
    private int autoPartId;
    private int autoProductId;
    public Inventory(){
        this.products = FXCollections.observableArrayList();
        this.parts = FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Remove product from observable list products
     * @param product
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     * @param searchItem
     * @return
     */
    public Product lookupProduct(String searchItem) throws Exception {
        boolean isFound = false; // 1
        boolean isIdMatch = false;
        Product foundProduct = null;

        try {
            for (Product p : products) { // 2
                if (p.getName().contains(searchItem)) { // 3
                    if (p.getInStock() > 0) { // 4
                        foundProduct = p;
                        isFound = true; // 5
                        break;
                    }
                    isFound = true;
                    foundProduct = p;
                }
                if (String.valueOf(p.getProductId()).equals(searchItem)) { // 6
                    isIdMatch = true; // 7
                    isFound = true;
                    foundProduct = p;
                }
            }
        }
        finally{
            if (!isFound && !isIdMatch) { // 8
                throw new Exception("Couldn't find the product!");
                //return new Product(0, null, 0.0, 0, 0, 0, null); //9
            } else if (isFound && foundProduct.getInStock() <= 0) { // 10
                /*11*/return new Product(foundProduct.getProductId(), foundProduct.getName(), 0.0, 0, 0, 0, foundProduct.getAssociatedParts());
            }

            return foundProduct; // 12
        }
        // 13
    }


    /**
     * Update product at given index
     * @param index
     * @param product
     */
    public void updateProduct(int index, Product product) {
        products.set(index, product);
    }

    /**
     * Getter for Product Observable List
     * @return
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        products=list;
    }

    /**
     * Add new part to observable list allParts
     * @param part
     */
    public void addPart(Part part) {
        parts.add(part);
    }

    /**
     * Removes part passed as parameter from allParts
     * @param part
     */
    public void deletePart(Part part) {
        parts.remove(part);
    }

    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchItem
     * @return
     */
    public Part lookupPart(String searchItem) {
        for(Part p: parts) {
            if(p.getName().contains(searchItem) || (p.getPartId()+"").equals(searchItem)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Update part at given index
     * @param index
     * @param part
     */
    public void updatePart(int index, Part part) {
        parts.set(index, part);
    }

    /**
     * Getter for allParts Observable List
     * @return
     */
    public ObservableList<Part> getParts() {
        return parts;
    }

    public List<Part> getPartsList() {
        return parts;
    }

    /**
     *
     * @param list
     */
    public void setParts(ObservableList<Part> list) {
        parts =list;
    }

    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return
     */
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }

    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return
     */
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }

}
