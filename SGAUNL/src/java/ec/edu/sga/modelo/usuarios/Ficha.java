/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.usuarios;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author edison
 */
@Entity
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @OneToOne
    private Usuario usuario;
    @OneToOne
    private FichaPersonal fichaPersonal;
    @OneToOne
    private FichaMedica fichaMedica;
    @OneToOne
    private FichaSocioeconomica fichaSocio;

    public Ficha() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FichaPersonal getFichaPersonal() {
        return fichaPersonal;
    }

    public void setFichaPersonal(FichaPersonal fichaPersonal) {
        this.fichaPersonal = fichaPersonal;
    }

    public FichaMedica getFichaMedica() {
        return fichaMedica;
    }

    public void setFichaMedica(FichaMedica fichaMedica) {
        this.fichaMedica = fichaMedica;
    }

    public FichaSocioeconomica getFichaSocio() {
        return fichaSocio;
    }

    public void setFichaSocio(FichaSocioeconomica fichaSocio) {
        this.fichaSocio = fichaSocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ficha)) {
            return false;
        }
        Ficha other = (Ficha) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.modelo.user.Ficha[ id=" + id + " ]";
    }
}