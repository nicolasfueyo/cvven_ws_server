/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvven.ws;

import cvven.dao.DisponibilitesDAO;
import cvven.entity.Disponibilite;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author nicol
 */
@WebService(serviceName = "DisposWS")
public class DisposWS {

    @WebMethod(operationName = "rechercheDisposParDate")
    public List<Disponibilite> rechercheDisposParDate(@WebParam(name = "date") String date) throws Exception {
        
        DisponibilitesDAO dao = new DisponibilitesDAO();
        
        // Renvoie la liste des dispos.
        return dao.chercherDispos(date);
    }    
}
