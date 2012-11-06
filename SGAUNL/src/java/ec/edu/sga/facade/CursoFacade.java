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
        
        public Curso findCursoByCursoId(Long id){
            Query query = em.createNamedQuery("findCursosAndParalelos");
            query.setParameter("id", id);
            return (Curso)query.getSingleResult();
            
        }
        
        
         public List<Curso> findCursosAndParalelos(){
            Query query = em.createNamedQuery("findCursosAndParalelos");
            return query.getResultList();
            
        }
        
        
//        public List<Paralelo> findParalelosByCursoId2(Long estudentId){
//            Query query = em.createNamedQuery("findParalelosByCursoId");
//            query.setParameter("id", estudentId);
//            return query.getResultList();
//            
//        }
        
        
    
}
