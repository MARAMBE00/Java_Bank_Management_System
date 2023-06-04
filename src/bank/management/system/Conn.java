package bank.management.system;

import java.sql.*;

public class Conn {

    Connection c;
    Statement s;

    public Conn(){
        try{
            c = DriverManager.getConnection("jdbc:mysql:///bank_management_system_java", "root", "");
            s = c.createStatement();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
