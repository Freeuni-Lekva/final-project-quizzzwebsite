package com.example.quizzzwebsite;

import DBConnect.DataSrc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementManager {
    private static int numAnnouncements = 0;
    private static boolean numAnnouncementsChanged = true;

    public static void addAnnouncement(String title,String text,String userName) throws SQLException {
        Connection con = DataSrc.getConnection();
        PreparedStatement statement = con.prepareStatement("insert into announcements(title,creatorName,text,date)" +
                " values (?,?,?,?);");
        statement.setString(1,title);
        statement.setString(2,userName);
        statement.setString(3,text);
        statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        statement.executeUpdate();
        numAnnouncements++;
    }

    public static List<Announcement> getAllAnnouncements() throws SQLException {
        Connection con = DataSrc.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from " +
                "announcements order by date desc;");
        ResultSet rs = statement.executeQuery();
        List<Announcement> list = new ArrayList<Announcement>();
        while(rs.next()){
            list.add(new Announcement(rs.getString(1),
                    rs.getString(3), rs.getString(4),
                    rs.getDate(5)));
        }
        return list;
    }

    public static List<Announcement> getAnnouncements(int number) throws SQLException {
        Connection con = DataSrc.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from " +
                "announcements order by date desc limit ?;");
        statement.setInt(1,number);
        ResultSet rs = statement.executeQuery();
        List<Announcement> list = new ArrayList<Announcement>();
        while(rs.next()){
            list.add(new Announcement(rs.getString(1),
                    rs.getString(3), rs.getString(4),
                    rs.getDate(5)));
        }
        return list;
    }

    public static int getNumAnnouncements() throws SQLException {
        if(numAnnouncementsChanged){
            Connection con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("select * from announcements;");
            ResultSet rs = statement.executeQuery();
            numAnnouncements = 0;
            while(rs.next()){
                numAnnouncements++;
            }
            numAnnouncementsChanged = false;
        }
        return numAnnouncements;
    }

}
