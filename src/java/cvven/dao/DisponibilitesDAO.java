/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvven.dao;

import cvven.entity.Disponibilite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicol
 */
public class DisponibilitesDAO {

    public void chercherDispos(Date date) throws ClassNotFoundException, SQLException {

        
        String sql = "SELECT  tl.id, tl.nom, tl.nb_logements, (\n"
                + "    SELECT  SUM(rl.quantite)\n"
                + "    FROM    reservation_logement rl\n"
                + "            JOIN reservation r ON rl.id_reservation=r.id\n"
                + "    WHERE   rl.id_typelogement=tl.id\n"
                + "            AND r.date_entree<='17/7/2021'\n"
                + "            AND r.date_sortie>='18/7/2021'             \n"
                + ") nb_logements_occupes\n"
                + "FROM    typelogement tl;";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvven", "root", "");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( sql );
        
        List<Disponibilite> disponibilites = new ArrayList<>();
        while (rs.next()) {
            
            long nbLogements = rs.getLong("nb_logements");
            Long nbLogementsOccupes = rs.getLong("nb_logements_occupes");
            long nbLogementsDispo = nbLogements;
            if( nbLogementsOccupes!=null ){
                nbLogementsDispo = nbLogements - nbLogementsOccupes;
            }
            
            Disponibilite dispo = new Disponibilite(rs.getLong("id"), rs.getString("nom"), nbLogementsDispo);
            
            disponibilites.add(dispo);
        }
        con.close();
        
        System.out.println( disponibilites );
    }
}
