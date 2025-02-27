package inventory.repository;

import inventory.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.StringTokenizer;

public class InventoryRepository {

	private static String filename = "data/items.txt";

    private ObservableList<Product> products;
    private ObservableList<Part> allParts;
    private int autoPartId;
    private int autoProductId;

	public InventoryRepository(){
        this.products = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
        createFileIfItDoesntExist();
		readParts();
		readProducts();
	}

    private void createFileIfItDoesntExist()
    {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void readParts(){
		//ClassLoader classLoader = InventoryRepository.class.getClassLoader();
		File file = new File(filename);
		ObservableList<Part> listP = FXCollections.observableArrayList();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=br.readLine())!=null){
				Part part=getPartFromString(line);
				if (part!=null)
					listP.add(part);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.allParts = listP;
	}

	private Part getPartFromString(String line){
		Part item=null;
		if (line==null|| line.equals("")) return null;
		StringTokenizer st=new StringTokenizer(line, ",");
		String type=st.nextToken();
		if (type.equals("I")) {
			int id= Integer.parseInt(st.nextToken());
			this.setAutoPartId(id);
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			int idMachine= Integer.parseInt(st.nextToken());
			item = new InhousePart(id, name, price, inStock, minStock, maxStock, idMachine);
		}
		if (type.equals("O")) {
			int id= Integer.parseInt(st.nextToken());
			this.autoPartId = id;
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			String company=st.nextToken();
			item = new OutsourcedPart(id, name, price, inStock, minStock, maxStock, company);
		}
		return item;
	}

	public void readProducts(){
		//ClassLoader classLoader = InventoryRepository.class.getClassLoader();
		File file = new File(filename);

		ObservableList<Product> listP = FXCollections.observableArrayList();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=br.readLine())!=null){
				Product product=getProductFromString(line);
				if (product!=null)
					listP.add(product);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        this.products = listP;
	}

	private Product getProductFromString(String line){
		Product product=null;
		if (line==null|| line.equals("")) return null;
		StringTokenizer st=new StringTokenizer(line, ",");
		String type=st.nextToken();
		if (type.equals("P")) {
			int id= Integer.parseInt(st.nextToken());
			this.setAutoProductId(id);
			String name= st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			int inStock = Integer.parseInt(st.nextToken());
			int minStock = Integer.parseInt(st.nextToken());
			int maxStock = Integer.parseInt(st.nextToken());
			String partIDs=st.nextToken();

			StringTokenizer ids= new StringTokenizer(partIDs,":");
			ObservableList<Part> list= FXCollections.observableArrayList();
			while (ids.hasMoreTokens()) {
				String idP = ids.nextToken();
				Part part = this.lookupPart(idP);
				if (part != null)
					list.add(part);
			}
			product = new Product(id, name, price, inStock, minStock, maxStock, list);
			product.setAssociatedParts(list);
		}
		return product;
	}

	public void writeAll() {

		//ClassLoader classLoader = InventoryRepository.class.getClassLoader();
		File file = new File(filename);

		BufferedWriter bw = null;
		ObservableList<Part> parts=this.getAllParts();
		ObservableList<Product> products=this.getAllProducts();

		try {
			bw = new BufferedWriter(new FileWriter(file));
			for (Part p:parts) {
				System.out.println(p.toString());
				bw.write(p.toString());
				bw.newLine();
			}

			for (Product pr:products) {
				String line=pr.toString()+",";
				ObservableList<Part> list= pr.getAssociatedParts();
				int index=0;
				while(index<list.size()-1){
					line=line+list.get(index).getPartId()+":";
					index++;
				}
				if (index==list.size()-1)
					line=line+list.get(index).getPartId();
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Add new part to observable list allParts
     * @param part
     */
    public void addPart(Part part) {
        allParts.add(part);
        writeAll();
    }

    /**
     * Add new product to observable list products
     * @param product
     */
    public void addProduct(Product product) {
        products.add(product);
        writeAll();
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

    /**
     * Getter for allParts Observable List
     * @return
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for Product Observable List
     * @return
     */
    public ObservableList<Product> getAllProducts() {
        return products;
    }

	public Part lookupPart (String search){
        for(Part p:allParts) {
            if(p.getName().contains(search) || (p.getPartId()+"").equals(search)) return p;
        }
        return null;
	}

	public Product lookupProduct (String search){
        boolean isFound = false;
        for(Product p: products) {
            if(p.getName().contains(search) || (p.getProductId()+"").equals(search)) return p;
            isFound = true;
        }
        if(isFound == false) {
            Product product = new Product(0, null, 0.0, 0, 0, 0, null);
            return product;
        }
        return null;
	}

	public void updatePart(int partIndex, Part part){
        Part partToModify = allParts.get(partIndex);
        partToModify.setName(part.getName());
        partToModify.setPrice(part.getPrice());
        partToModify.setInStock(part.getInStock());
        partToModify.setMin(part.getMin());
        partToModify.setMax(part.getMax());
		writeAll();
	}

	public void updateProduct(int productIndex, Product product){
        products.set(productIndex, product);
		writeAll();
	}

	public void deletePart(Part part){
        allParts.remove(part);
        for (Product product : products) {
            product.getAssociatedParts().remove(part);
        }
		writeAll();
	}
	public void deleteProduct(Product product){
        products.remove(product);
		writeAll();
	}
}
