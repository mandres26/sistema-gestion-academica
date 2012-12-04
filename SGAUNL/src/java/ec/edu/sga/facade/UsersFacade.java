/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.facade;

import ec.edu.sga.modelo.usuarios.Users;
import ec.edu.sga.modelo.usuarios.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author edison
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users>{

    @PersistenceContext(unitName = "SGAUNLPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

     public Users buscarUser(Users user) {

        Query query = em.createNamedQuery("Users.buscarUsuario");
        query.setParameter("usuario", user.getUsuario());
        query.setParameter("pass", user.getPassword());

        return (Users)query.getSingleResult();
    }

}
