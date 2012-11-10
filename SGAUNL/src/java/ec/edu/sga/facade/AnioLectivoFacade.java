/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.matriculacion.AnioLectivo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lucho
 */
@Stateless
public class AnioLectivoFacade extends AbstractFacade<AnioLectivo> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnioLectivoFacade() {
        super(AnioLectivo.class);
    }
    
    //método para encontrar el año activo
    public AnioLectivo findAnioActivate(Boolean parameter){
        Query query=em.createNamedQuery("AnioLectivo.findAnioActivo");
        query.setParameter("parameter", parameter);
        return (AnioLectivo) query.getSingleResult();
    }
    
    
    
}
