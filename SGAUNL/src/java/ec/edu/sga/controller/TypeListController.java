/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.sga.controller;

import ec.edu.sga.modelo.usuarios.TipoSangre;
import ec.edu.sga.modelo.usuarios.TipoSexo;
import javax.inject.Named;

/**
 *
 * @author edison
 */
@Named(value = "typeListController")

public class TypeListController {

    /**
     * Creates a new instance of TypeListController
     */
    public TypeListController() {
    }
    
    public TipoSexo[] getTypeListSexo(){
        return TipoSexo.values();
    }
    
    public TipoSangre[] getTypeListSangre(){
        return TipoSangre.values();
    }
}
