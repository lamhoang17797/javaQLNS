package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConnectData {
    
    static Connection conn = null; 
    // xu ly ngoai le khi tuong tac voi csdl 
    public void displayError(SQLException ex){ 
        System.out.println(" Error Message:" + ex.getMessage()); 
        System.out.println(" SQL State:" + ex.getSQLState()); 
        System.out.println(" Error Code:" + ex.getErrorCode()); 
    }  
    
//    Mở kết nối
    public void open(){
        try{ 
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=QLNS;user=winchester;password=umbella123";
            conn = DriverManager.getConnection(dbURL);
        } catch(SQLException ex){
            displayError(ex); 
        } 
    } 
    
//    Đóng kết nối
    public void close(){
       try { 
           if(conn!=null) 
               conn.close(); 
       } catch (SQLException ex) { 
           displayError(ex); 
       } 
    } 
//    Thực thi lệnh select
    public ResultSet Showdata(String sql){
       open();
       ResultSet rs = null; 
       try { 
           Statement stm = (Statement) conn.createStatement(); 
           rs = stm.executeQuery(sql); 
       } catch (SQLException ex) { 
           displayError(ex); 
       }
       return rs; 
    } 
//    Thực thi lệnh insert,update,delete
    public void ExecuteQuery(String sql,String msg,int i)
    {
        open();
        try
        {
            Statement stm = (Statement) conn.createStatement();
            stm.executeUpdate(sql);
            if(i == 1){
                JOptionPane.showMessageDialog(null,msg+" Thành công");
            }
        }
        catch (SQLException ex)
        {
            displayError(ex); 
        }
    }
    
}
