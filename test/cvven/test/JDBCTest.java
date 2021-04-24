/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvven.test;

import cvven.dao.DisponibilitesDAO;
import java.sql.SQLException;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nicol
 */
public class JDBCTest {
    

    @Test
    public void test() throws ClassNotFoundException, SQLException{
        DisponibilitesDAO dao = new DisponibilitesDAO();
        
        dao.chercherDispos(new Date(2021, 7, 17));
    }
}
