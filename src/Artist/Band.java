/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Artist;

/**
 *
 * @author Zy
 */
import java.util.ArrayList;
import java.util.List;

public class Band {
    private String name;                  // Band name
    private List<Artist> members;         // List of artists who are members of the band

    public Band(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        // Initialize other attributes as needed
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getMembers() {
        return members;
    }

    // Methods to add, remove, or retrieve members of the band
    public void addMember(Artist artist) {
        members.add(artist);
        artist.setBandName(this.name); // Set the bandName attribute in the artist class
    }

    public void removeMember(Artist artist) {
        members.remove(artist);
        artist.setBandName(null);      // Clear the bandName attribute in the artist class
    }
}

