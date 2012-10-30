/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.matriculacion.Curso;
import ec.edu.sga.modelo.matriculacion.Paralelo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author lucho
 */
@Stateless
public class CursoFacade extends AbstractFacade<Curso> {
    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }
    
        @Override
        public List<Curso> findAll(){
            Query query = em.createNamedQuery("Curso.findAll");
            return query.getResultList();
        }
        
        public Curso findParalelosByEstudentId(Long estudentId){
            Query query = em.createNamedQuery("findParalelosByEstudentId");
            query.setParameter("id", estudentId);
            return (Curso)query.getSingleResult();
            
        }
        
        
    
}
