package kagan.comprogramming;

import android.os.AsyncTask;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kagan on 14.05.2017.
 */

public class MyTask extends AsyncTask<Void, Void, Void> {

    static final String DB_URL = "jdbc:mysql://192.168.1.20:3306/student";


    String name;

    @Override
    protected Void doInBackground(Void... voids) {


        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting");
            conn = DriverManager.getConnection(DB_URL, "admin2", "admin2");

            System.out.println("name = adadada");
            Statement st = conn.createStatement();
            String sql = "select * from student";

            final ResultSet rs = st.executeQuery(sql);
            rs.next();
            name = rs.getString(0);


        } catch (SQLException e) {
            System.out.println("SQL Excepti");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception conn");
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Close conn");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void v) {

        System.out.println("onPostExecute");
        MainActivity.textView.setText(name);
        MainActivity.textView.setVisibility(View.VISIBLE);
        super.onPostExecute(v);

    }


}
