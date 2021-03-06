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
@TableGenerator(name = "RegistroAsistenciaGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "RegistroAsistencia", initialValue = 1, allocationSize = 1)
public class RegistroAsistencia implements Serializable {
   
    //---------------------------------ATRIBUTOS------------------------------//
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="RegistroAsistenciaGenerador")
    private Long id;
    
    
    private Long numeroAsistencias;
    private Long numeroFaltasInjunstificadas;
    private Long numeroFaltasJustificadas;
    private Long totalDiasLaborables;

    
     //------------------------------CONSTRUCTORES----------------------------//
    
    
    
    //----------------------------GETTERS AND SETTERS-------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroAsistencias() {
        return numeroAsistencias;
    }

    public void setNumeroAsistencias(Long numeroAsistencias) {
        this.numeroAsistencias = numeroAsistencias;
    }

    public Long getNumeroFaltasInjunstificadas() {
        return numeroFaltasInjunstificadas;
    }

    public void setNumeroFaltasInjunstificadas(Long numeroFaltasInjunstificadas) {
        this.numeroFaltasInjunstificadas = numeroFaltasInjunstificadas;
    }

    public Long getNumeroFaltasJustificadas() {
        return numeroFaltasJustificadas;
    }

    public void setNumeroFaltasJustificadas(Long numeroFaltasJustificadas) {
        this.numeroFaltasJustificadas = numeroFaltasJustificadas;
    }

    public Long getTotalDiasLaborables() {
        return totalDiasLaborables;
    }

    public void setTotalDiasLaborables(Long totalDiasLaborables) {
        this.totalDiasLaborables = totalDiasLaborables;
    }

    
    
    //-------------------------------METODOS---------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroAsistencia)) {
            return false;
        }
        RegistroAsistencia other = (RegistroAsistencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.sga.otras.RegistroAsistencia[ id=" + id + " ]";
    }
    
}
