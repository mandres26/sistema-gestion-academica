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
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "QuimestreGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Quimestre", initialValue = 1, allocationSize = 1)
public class Quimestre implements Serializable {
    @OneToOne
    private RegistroCalificaciones registroCalificaciones;
    
    
    //--------------------------------ATRIBUTOS--------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator="QuimestreGenerador")
    private Long id;
    
    private String nombreQuimestre;
    private Boolean estadoQuimestre;

    
    
     //------------------------------CONSTRUCTORES----------------------------//
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistroCalificaciones getRegistroCalificaciones() {
        return registroCalificaciones;
    }

    public void setRegistroCalificaciones(RegistroCalificaciones registroCalificaciones) {
        this.registroCalificaciones = registroCalificaciones;
    }

    public String getNombreQuimestre() {
        return nombreQuimestre;
    }

    public void setNombreQuimestre(String nombreQuimestre) {
        this.nombreQuimestre = nombreQuimestre;
    }

    public Boolean getEstadoQuimestre() {
        return estadoQuimestre;
    }

    public void setEstadoQuimestre(Boolean estadoQuimestre) {
        this.estadoQuimestre = estadoQuimestre;
    }
    
    

    
    //--------------------------------MÉTODOS---------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quimestre)) {
            return false;
        }
        Quimestre other = (Quimestre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.Quimestre[ id=" + id + " ]";
    }
    
}
