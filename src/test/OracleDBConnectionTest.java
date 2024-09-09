package com.godcoder.myhome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kr.or.lx.lsa.LSA;

@SpringBootTest(classes = MyhomeApplication.class)
public class OracleDBConnectionTest {
	
	// MySQL 접속 설정
	static final String DRIVER = "oracle.jdbc.OracleDriver";
	static final String URL = "jdbc:oracle:thin:@192.168.0.13:1521/bls";
	static final String USERNAME = "bls";
	static final String PASSWORD="BLS2024";
	
    @Test
    public void getOracleConnectionTest() {
        
        Connection conn = null;
        Statement stmt = null;
        
        try {
            
            System.out.println("==================== Oracle Connection START ====================");
            
            Class.forName(DRIVER);
            
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
 
            String sql = "SELECT USER_ID, PASSWORD FROM BLS5_SYS_MBER WHERE ROWNUM <= 10";
 
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                
                String userId = rs.getString("USER_ID");
                String userPassword = rs.getString("PASSWORD");
 
                System.out.println("ID : " + userId);
                System.out.println("PASSWORD: " + userPassword);
            }
 
            rs.close();
            stmt.close();
            conn.close();
 
        } catch (SQLException se1) {
            se1.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        System.out.println("==================== Oracle Connection END ====================");
    }
}
