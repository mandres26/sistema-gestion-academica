/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.academico;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "SupletorioGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Supletorio", initialValue = 1, allocationSize = 1)
public class Supletorio implements Serializable {
    
    
    //-------------------------ATRIBUTOS---------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="SupletorioGenerador")
    private Long id;

    
    
    
    
     //------------------------------CONSTRUCTORES----------------------------//
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    //--------------------------------MÉTODOS----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supletorio)) {
            return false;
        }
        Supletorio other = (Supletorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.Supletorio[ id=" + id + " ]";
    }
    
}
