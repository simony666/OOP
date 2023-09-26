/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Performance;

/**
 *
 * @author Zy
 */
public class PopPerformance extends PerformanceType{
    private String typeName = "Pop"; // Type name for Dance Performance
    private String description = "Artists or groups known for their catchy songs, choreographed dance routines, and a focus on entertainment and showmanship";
    
    @Override
    public String getTypeName() {
        return typeName;
    }
    
    @Override
    public String getDescription(){
        return description;
    }
}
