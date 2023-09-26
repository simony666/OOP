/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Performance;

/**
 *
 * @author Zy
 */
public class DancePerformance implements PerformanceType{
    private String typeName = "Dance"; // Type name for Dance Performance
    private String description = "A dynamic and energetic dance showcase featuring talented dancers from around the world";
    
    @Override
    public String getTypeName() {
        return typeName;
    }
    
    @Override
    public String getDescription(){
        return description;
    }
}