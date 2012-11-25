/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "EspecialidadGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "Especialidad", initialValue = 1, allocationSize = 1)
public class Especialidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "EspecialidadGenerador")
    private Long id;
    private String nombreEspecialidad;
    private String descripcionEspecialidad;
    @OneToMany(mappedBy = "especialidad")
    private List<Curso> cursos;

    //-------------------------------CONSTRUCTORES----------------------//

    public Especialidad() {
    }

    public Especialidad(String nombreEspecialidad, String descripcionEspecialidad, List<Curso> cursos) {
        this.nombreEspecialidad = nombreEspecialidad;
        this.descripcionEspecialidad = descripcionEspecialidad;
        this.cursos = cursos;
    }
    
    
    //----------------------GETERS AND SETTERS-----------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getDescripcionEspecialidad() {
        return descripcionEspecialidad;
    }

    public void setDescripcionEspecialidad(String descripcionEspecialidad) {
        this.descripcionEspecialidad = descripcionEspecialidad;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    //--------------------------METODOS----------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especialidad)) {
            return false;
        }
        Especialidad other = (Especialidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreEspecialidad;
    }
}
