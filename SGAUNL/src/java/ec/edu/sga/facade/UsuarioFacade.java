/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.Ficha;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author edison
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorClave(String clave) {

        Query query = em.createNamedQuery("Usuario.buscarPorClave");
        query.setParameter("clave", clave);

        return query.getResultList();
    }

    public Usuario buscarPorId(Long id) {

        Query query = em.createNamedQuery("Usuario.buscarPorId");
        query.setParameter("id", id);

        return (Usuario) query.getSingleResult();
    }

    public Usuario getLogin(String login, String clave) {
        try {
            Query query = em.createNamedQuery("Usuario.findLogin");
            query.setParameter("login", login);
            query.setParameter("clave", clave);
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
