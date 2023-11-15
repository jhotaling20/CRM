package program.software;

import java.util.*;

public class Material {
    public Map<String, Float> materials = new HashMap<>();
    public Material(String material_name, Float material_price){
        materials.put(material_name, material_price);
    }
    public Float getValue(String material_name){
        return materials.get(material_name);
    }

}
