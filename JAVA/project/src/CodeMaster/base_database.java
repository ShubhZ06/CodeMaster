package CodeMaster;

import java.sql.Connection;
import java.sql.DriverManager;

public class base_database {
    public static Connection connectDB()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/codemaster", "root", "");
            return connection;
        }
        catch(Exception e){e.printStackTrace();}
        return null;
        
    }
    
}
