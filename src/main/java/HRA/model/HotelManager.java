package HRA.model;

import java.util.*;

public class HotelManager {

    private String username;
    private List<Room> roomList;
    private String imageName1;
    private String imageName2;
    private List<String> facilities;
    private String hotelName;

    public HotelManager(){

    }

    public HotelManager(String username, List<Room> roomList, String imageName1, String imageName2, List<String> facilities, String hotelName){
       this.username = username;
       this.roomList = roomList;
       this.imageName1 = imageName1;
       this.imageName2 = imageName2;
       this.facilities = facilities;
       this.hotelName = hotelName;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public String getImageName1() {
        return imageName1;
    }

    public void setImageName1(String imageName1) {
        this.imageName1 = imageName1;
    }

    public String getImageName2() {
        return imageName2;
    }

    public void setImageName2(String imageName2) {
        this.imageName2 = imageName2;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + imageName1.hashCode();
        result = 31 * result + imageName2.hashCode();
        return result;
    }
    @Override
    public String toString() {
        return "HotelManager{" +
                "username='" + username + '\'' +
                ", roomList=" + roomList +
                ", imageName1='" + imageName1 + '\'' +
                ", imageName2='" + imageName2 + '\'' +
                ", facilities=" + facilities +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
