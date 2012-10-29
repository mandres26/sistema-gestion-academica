/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "AnioLectivoGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "AnioLectivo", initialValue = 1, allocationSize = 1)
public class AnioLectivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="AnioLectivoGenerador")
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    @OneToMany(mappedBy = "anioLectivo")
    private List<Matricula> matriculas;
    private Long duracion;

    //---------------------------CONSTRUCTORES--------------------------//
    public AnioLectivo() {
        fechaInicio = new Date();
        fechaFin = new Date();
        matriculas = new ArrayList<Matricula>();
    }

    //----------------------------GETERS AND SETERS--------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    //----------------------------MÉTODOS-------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnioLectivo)) {
            return false;
        }
        AnioLectivo other = (AnioLectivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + (fechaInicio.getYear() + 1900) + " - " + (fechaFin.getYear() + 1900);
    }
}
