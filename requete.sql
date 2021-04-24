SELECT  tl.id, tl.nom, tl.nb_logements, (
    SELECT  SUM(rl.quantite)
    FROM    reservation_logement rl
            JOIN reservation r ON rl.id_reservation=r.id
    WHERE   rl.id_typelogement=tl.id
            AND r.date_entree<='24/04/2021'
            AND r.date_sortie>='25/04/2021' 
            AND r.etat='VALIDE'
) nb_logements_occupes
FROM    typelogement tl;