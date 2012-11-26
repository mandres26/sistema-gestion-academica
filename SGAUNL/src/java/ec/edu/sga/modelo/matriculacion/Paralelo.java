/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.modelo.matriculacion;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

/**
 *
 * @author lucho
 */
@Entity
@TableGenerator(name = "ParaleloGenerador", table = "GeneradorIdentificador", pkColumnName = "nombre",
valueColumnName = "valor", pkColumnValue = "paralelo", initialValue = 1, allocationSize = 1)
@NamedQueries(value = {
    @NamedQuery(name = "Paralelo.findAllParalelos", query = "select p from Paralelo p"),
    @NamedQuery(name = "Paralelo.findAllParalelosByCursoId",
    query = "select p from Paralelo p where p.curso.id =:cursoId")
})
public class Paralelo implements Serializable {

    //----------------------------ATRIBUTOS-----------------------------------//
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ParaleloGenerador")
    private Long id;
    private String nombreParalelo;
    private int numeroCupos;
    @ManyToOne
    private Curso curso;

    //-----------------------------CONTRUCTORES----------------------------------//
    public Paralelo() {
    }

    public Paralelo(String nombreParalelo, int numeroCupos, Curso curso) {
        this.nombreParalelo = nombreParalelo;
        this.numeroCupos = numeroCupos;
        this.curso = curso;
    }

    //----------------------------GETTERS AND SETTERS----------------------------//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreParalelo() {
        return nombreParalelo;
    }

    public void setNombreParalelo(String nombreParalelo) {
        this.nombreParalelo = nombreParalelo;
    }

    public int getNumeroCupos() {
        return numeroCupos;
    }

    public void setNumeroCupos(int numeroCupos) {
        this.numeroCupos = numeroCupos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        System.out.println("CLASE PARALELO - SET CURSO: " + curso);
        this.curso = curso;
    }

    //--------------------------------MÉTODOS--------------------------------//
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paralelo)) {
            return false;
        }
        Paralelo other = (Paralelo) object;
        if ((this.nombreParalelo == null && other.nombreParalelo != null) || (this.nombreParalelo != null && !this.nombreParalelo.equals(other.nombreParalelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreParalelo;
    }
}
