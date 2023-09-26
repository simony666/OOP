/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Performance;

/**
 *
 * @author Zy
 */

public class MusicPerformance extends PerformanceType {
    private String typeName = "Music"; // Type name for Music Performance
    private String description = "An evening of musical entertainment with various genres, including jazz, rock, and pop";

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public String getDescription(){
        return description;
    }
}