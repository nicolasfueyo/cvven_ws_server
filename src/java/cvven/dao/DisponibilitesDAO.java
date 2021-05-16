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
 * Classe comprenant les fonctions d'accès la la base de données ( DAO = Data Access Object )
 * @author nicol
 */
public class DisponibilitesDAO {

    public Long chercheDispoPourTypeLogement(long idTypeLogement, String date) throws ClassNotFoundException, SQLException {                
        
        // Liste nb logements dispo pour le type et la date voulue
        String sql = String.format(
                "SELECT  typelogement.nb_logements - SUM(reservation_logement.quantite) logements_libres\n" +
                "FROM    reservation        \n" +
                "        LEFT JOIN reservation_logement ON reservation.id=reservation_logement.id_reservation\n" +
                "        LEFT JOIN typelogement ON typelogement.id = reservation_logement.id_typelogement\n" +
                "WHERE   reservation_logement.id_typelogement=%d\n" +
                "        AND reservation.etat='VALIDE'\n" +
                "        AND '%s' BETWEEN reservation.date_entree AND reservation.date_sortie;", idTypeLogement, date);
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvven", "root", "");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( sql );
        
        rs.next();       
        
        Long res =  rs.getObject("logements_libres", Long.class);
        if(  rs.wasNull() ){// Si la requete a renvoyé NULL et pos un nombre.
            return null;
        }else{
            return res;
        }
    }
    
    /**
     * Renvoie les dispos de chaque type de logement pour une date données au format dd/MM/yyyy
     * @param date
     * @return Une liste de Disponibilité.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public List<Disponibilite> chercherDispos(String date) throws ClassNotFoundException, SQLException {

        // Liste types de logement
        String sql = "SELECT * FROM typelogement";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cvven", "root", "");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( sql );       
        
        // Préremplit la liste des dispos
        List<Disponibilite> disponibilites = new ArrayList<>();
        while (rs.next()) {
            
            Disponibilite dispo = new Disponibilite(rs.getLong("id"), rs.getString("nom"), rs.getLong("nb_logements"));
            
            disponibilites.add(dispo);
        }
        con.close();
        
        // Charge les dispos pour chaque type de logement
        for(Disponibilite dispo : disponibilites){
            Long nbDispo = this.chercheDispoPourTypeLogement(dispo.getIdTypeLogement(), date);
            
            if( nbDispo!=null ){
                dispo.setNbDisponibilites( nbDispo );
            }
            
        }
        
        return disponibilites;
    }
}
