package kagan.comprogramming;

import android.content.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by kagan on 24.05.2017.
 */

public class DbManager {

    static final String DB_URL = "jdbc:mysql://192.168.137.1:3306/project";


    public static void createGroup(Context context, Group group) {
        Connection conn = null;
        try {
            conn = openedConnection();

            String sql = "INSERT INTO groups(group_name,category,group_description,group_imageUri) values(?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, group.getName());
            preparedStmt.setInt(2, group.getCategory());
            preparedStmt.setString(3, group.getDescription());
            preparedStmt.setString(4, group.getImageUri());

            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void createEvent(Context context, Event event, Group group) {
        Connection conn = null;
        try {
            conn = openedConnection();

            String sql = "INSERT INTO event(event_name,event_date,event_city,event_address,event_description,event_imageUri,event_time,e_group_id) values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, event.getName());
            preparedStmt.setString(2, event.getDate());
            preparedStmt.setInt(3, event.getCity());
            preparedStmt.setString(4, event.getAddress());
            preparedStmt.setString(5, event.getDescription());
            preparedStmt.setString(6, event.getImageUri());
            preparedStmt.setString(7, event.getTime());
            preparedStmt.setInt(8, group.getGruop_id());

            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createUser(Context context, Person person) {
        Connection conn = null;
        try {
            conn = openedConnection();

            String sql = "INSERT INTO user(fname,lname,user_city,email,nickname,password,imageUri) values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, person.getFname());
            preparedStmt.setString(2, person.getLname());
            preparedStmt.setInt(3, person.getCity());
            preparedStmt.setString(4, person.getEmail());
            preparedStmt.setString(5, person.getNickname());
            preparedStmt.setString(6, person.getPassword());
            preparedStmt.setString(7, person.getImageUri());

            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void joinEvent(Context context, Event event, Person person) {
        Connection conn = null;
        try {
            conn = openedConnection();

            String sql = "INSERT INTO participant(p_user_id,p_event_id) values(?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1, person.getId());
            preparedStmt.setInt(2, event.getId());

            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Group> loadGroups(Context context, int category) {
        Connection conn = null;
        Statement stmt;
        ArrayList<Group> groups = new ArrayList<>();
        try {
            conn = openedConnection();

            stmt = conn.createStatement();
            String sql = "SELECT group_name,group_description,group_imageUri,group_id from groups where category = " + category;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String group_description = rs.getString("group_description");
                String group_name = rs.getString("group_name");
                String imageUri = rs.getString("group_imageUri");
                int id = rs.getInt("group_id");
                Group group = new Group();
                group.setName(group_name);
                group.setImageUri(imageUri);
                group.setDescription(group_description);
                group.setCategory(category);
                group.setGruop_id(id);
                groups.add(group);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }


//    public static ArrayList<Group> loadmyGroups(Context context, Person person) {
//        Connection conn = null;
//        Statement stmt;
//        ArrayList<Group> groups = new ArrayList<>();
//        try {
//            conn = openedConnection();
//
//            stmt = conn.createStatement();
//            String sql = "SELECT group_name,group_description,group_imageUri from groups where group_id = " +
//                    "SELECT m_group_id from member where m_person_id = " + person.getId();
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while (rs.next()) {
//
//                String group_description = rs.getString("group_description");
//                String group_name = rs.getString("group_name");
//                String imageUri = rs.getString("group_imageUri");
//                int category = rs.getInt("category");
//                Group group = new Group();
//                group.setName(group_name);
//                group.setImageUri(imageUri);
//                group.setDescription(group_description);
//                group.setCategory(category);
//                groups.add(group);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (conn != null)
//                    conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return groups;
//    }

    public static ArrayList<Event> loadEvents(Context context, Group group1) {
        Connection conn = null;
        Statement stmt;
        ArrayList<Event> events = new ArrayList<>();
        try {
            conn = openedConnection();

            stmt = conn.createStatement();
            String sql = "SELECT event_id,event_name,event_date,event_city,event_address,event_description,event_imageUri,event_time from event where e_group_id = " + group1.getGruop_id();
//            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String description = rs.getString("event_description");
                String name = rs.getString("event_name");
                String imageUri = rs.getString("event_imageUri");
                String date = rs.getString("event_date");
                String address = rs.getString("event_address");
                String time = rs.getString("event_time");
                int city = rs.getInt("event_city");
                int id = rs.getInt("event_id");
                Event event = new Event();
                event.setId(id);
                event.setName(name);
                event.setImageUri(imageUri);
                event.setDescription(description);
                event.setCity(city);
                event.setAddress(address);
                event.setTime(time);
                event.setDate(date);
                events.add(event);
            }
//            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    public static boolean loadUser(Context context, Person person) {
        Connection conn = null;
        Statement stmt;
        try {
            conn = openedConnection();

            stmt = conn.createStatement();
            String sql = "SELECT user_id,fname,lname,user_city,email,nickname,password,imageUri from user where nickname = '" +
                    person.getNickname() + "' and password = '" + person.getPassword() + "'";
//            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);


            if (rs.next()) {
                int id = rs.getInt("user_id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int city = rs.getInt("user_city");
                String email = rs.getString("email");
                String nickname = rs.getString("nickname");
                String password = rs.getString("password");
                String uri = rs.getString("imageUri");

                person.setId(id);
                person.setFname(fname);
                person.setLname(lname);
                person.setCity(city);
                person.setEmail(email);
                person.setNickname(nickname);
                person.setPassword(password);
                person.setImageUri(uri);
            } else
                return false;


//            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static ArrayList<Person> loadGoers(Context context, Event event) {
        Connection conn = null;
        Statement stmt;
        ArrayList<Person> persons = new ArrayList<>();
        try {
            conn = openedConnection();

            stmt = conn.createStatement();
            String sql = "SELECT user_id,fname,lname,user_city,email,nickname,password,imageUri from user where user_id in (select p_user_id from " +
                    "participant where p_event_id = " + event.getId() + ")";
//            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("user_id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                int city = rs.getInt("user_city");
                String email = rs.getString("email");
                String nickname = rs.getString("nickname");
                String password = rs.getString("password");
                String uri = rs.getString("imageUri");
                Person person = new Person();
                person.setId(id);
                person.setFname(fname);
                person.setLname(lname);
                person.setCity(city);
                person.setEmail(email);
                person.setNickname(nickname);
                person.setPassword(password);
                person.setImageUri(uri);

                persons.add(person);
            }
//            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return persons;
    }


    private static Connection openedConnection() throws Exception {
        Connection conn;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, "admin2", "admin2");
        return conn;
    }

}
