
package NominaProducto;

import Controlador.ctrProducto;
import Controlador.ctrProducto1;
import Modelo.clsConsultarPrd;
import Modelo.clsConsultarPrd1;


import Modelo.clsProducto;
import Modelo.clsProducto1;
import Vista.frmPersona;

import Vista.frmProduto;


public class NominaProducto {

   
    public static void main(String[] args) {
        clsProducto1 objPro= new clsProducto1();
        clsConsultarPrd1 objConPro= new clsConsultarPrd1();
        frmPersona frmPro=new frmPersona();
        ctrProducto1 crtl=new ctrProducto1(objPro,objConPro,frmPro);
        crtl.iniciar();
        frmPro.setVisible(true);
    }
        
}
