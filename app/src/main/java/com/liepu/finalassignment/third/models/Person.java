package com.liepu.finalassignment.third.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Person implements Parcelable {

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    String id, name, address, gender;
    ArrayList<Song> songPlaylist;
    ArrayList<Artist> artistPlaylist;
    Phone phoneList;

    //Constructor
    public Person(String id,
                  String name,
                  String address,
                  String gender,
                  ArrayList<Song> songs,
                  ArrayList<Artist> artists,
                  Phone phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.songPlaylist = songs;
        this.artistPlaylist = artists;
        this.phoneList = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<Song> getSongPlaylist() {
        return songPlaylist;
    }

    public ArrayList<Artist> getArtistPlaylist() {
        return artistPlaylist;
    }

    public Phone getPhoneList() {
        return phoneList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSongPlaylist(ArrayList<Song> songPlaylist) {
        this.songPlaylist = songPlaylist;
    }

    public void setArtistPlaylist(ArrayList<Artist> artistPlaylist) {
        this.artistPlaylist = artistPlaylist;
    }

    public void setPhoneList(Phone phoneList) {
        this.phoneList = phoneList;
    }

    //parcelling part
    protected Person(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        gender = in.readString();
        songPlaylist = new ArrayList<>();
        in.readList(songPlaylist, Song.class.getClassLoader());
        artistPlaylist = new ArrayList<>();
        in.readList(artistPlaylist, Artist.class.getClassLoader());
        phoneList = in.readParcelable(Phone.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeList(songPlaylist);
        parcel.writeList(artistPlaylist);
        parcel.writeParcelable(phoneList, i);
    }
}
