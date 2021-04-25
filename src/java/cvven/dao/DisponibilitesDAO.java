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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicol
 */
public class DisponibilitesDAO {

    public List<Disponibilite> chercherDispos(Date date) throws ClassNotFoundException, SQLException {

        // Calcule date +1 jours
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date lendemain = c.getTime();
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        String strDate = format.format(date);
        String strDemain = format.format(lendemain);
        
        String sql = String.format(
                "SELECT  tl.id, tl.nom, tl.nb_logements, ( "
                + "    SELECT  SUM(rl.quantite) "
                + "    FROM    reservation_logement rl "
                + "            JOIN reservation r ON rl.id_reservation=r.id "
                + "    WHERE   rl.id_typelogement=tl.id "
                + "            AND r.date_entree<='%s' "
                + "            AND r.date_sortie>='%s '             "
                + "            AND r.etat='VALIDE' "
                + ") nb_logements_occupes "
                + "FROM    typelogement tl;", strDate, strDemain);

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
        
        return disponibilites;
    }
}
