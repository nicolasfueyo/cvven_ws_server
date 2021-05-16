/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvven.entity;

/**
 * Type de données décrivant un type de logement et le nb de logements dispos.
 * @author nicol
 */
public class Disponibilite {
    
    private Long idTypeLogement;
    private String typeLogement;
    private Long nbDisponibilites;

    public Disponibilite(Long idTypeLogement, String typeLogement, Long nbDisponibilites) {
        this.idTypeLogement = idTypeLogement;
        this.typeLogement = typeLogement;
        this.nbDisponibilites = nbDisponibilites;
    }

    public Long getIdTypeLogement() {
        return idTypeLogement;
    }

    public void setIdTypeLogement(Long idTypeLogement) {
        this.idTypeLogement = idTypeLogement;
    }

    public String getTypeLogement() {
        return typeLogement;
    }

    public void setTypeLogement(String typeLogement) {
        this.typeLogement = typeLogement;
    }

    public Long getNbDisponibilites() {
        return nbDisponibilites;
    }

    public void setNbDisponibilites(Long nbDisponibilites) {
        this.nbDisponibilites = nbDisponibilites;
    }

    @Override
    public String toString() {
        return "Disponibilite{" + "idTypeLogement=" + idTypeLogement + ", typeLogement=" + typeLogement + ", nbDisponibilites=" + nbDisponibilites + '}';
    }
    
    
}
