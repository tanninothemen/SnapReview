package vn.com.snapreview;

import java.io.Serializable;
import java.sql.Blob;

public class Product implements Serializable {
    private int Id;
    private byte[] ProductImage;
    private String ProductName;
    private String ProductBrand;
    private String ProductUsedTime;
    private String ProductDesign;
    private String ProductConfiguration;
    private String ProductCamera;
    private String ProductSound;
    private String ProductBattery;

    public Product(int id, byte[] productImage, String productName, String productBrand, String productUsedTime, String productDesign, String productConfiguration, String productCamera, String productSound, String productBattery) {
        Id = id;
        ProductImage = productImage;
        ProductName = productName;
        ProductBrand = productBrand;
        ProductUsedTime = productUsedTime;
        ProductDesign = productDesign;
        ProductConfiguration = productConfiguration;
        ProductCamera = productCamera;
        ProductSound = productSound;
        ProductBattery = productBattery;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public byte[] getProductImage() {
        return ProductImage;
    }

    public void setProductImage(byte[] productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductBrand() {
        return ProductBrand;
    }

    public void setProductBrand(String productBrand) {
        ProductBrand = productBrand;
    }

    public String getProductUsedTime() {
        return ProductUsedTime;
    }

    public void setProductUsedTime(String productUsedTime) {
        ProductUsedTime = productUsedTime;
    }

    public String getProductDesign() {
        return ProductDesign;
    }

    public void setProductDesign(String productDesign) {
        ProductDesign = productDesign;
    }

    public String getProductConfiguration() {
        return ProductConfiguration;
    }

    public void setProductConfiguration(String productConfiguration) {
        ProductConfiguration = productConfiguration;
    }

    public String getProductCamera() {
        return ProductCamera;
    }

    public void setProductCamera(String productCamera) {
        ProductCamera = productCamera;
    }

    public String getProductSound() {
        return ProductSound;
    }

    public void setProductSound(String productSound) {
        ProductSound = productSound;
    }

    public String getProductBattery() {
        return ProductBattery;
    }

    public void setProductBattery(String productBattery) {
        ProductBattery = productBattery;
    }
}
