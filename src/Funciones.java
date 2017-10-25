
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class Funciones {
    Integer counter =0;
    String values ="";
    
    void SubSistemaMensaje(String strMensaje){
            //Añade al usuario al mensaje
            JOptionPane.showMessageDialog(null, strMensaje );  
        // JOptionPane.showMessageDialog(null, strMensaje,"Error voidAutoCommit",JOptionPane.INFORMATICO_ACEPT);  
    }
    String fnIntSistemaSolicitaDatos(String strMensaje){
        //Añade el usuario al mensaje
        return JOptionPane.showInputDialog(null, strMensaje);
    }
    int fnIntSistemaPregunta(String strMensaje){
        //Añade el usuario al Mensaje
         //JOptionPane.showMessageDialog(null, "Error voidAutoCommit"); 
        return  JOptionPane.showConfirmDialog(null, strMensaje);      
    }
    //Valida que el caracter sea un numero
    boolean fnBoolIsNumeric(char cCaracter){
        //Variable de resultados
        boolean bResult=false;
        
        //Valida uno de los caracteres
        if(cCaracter == '0')
            bResult = true;
        else
            if(cCaracter =='1')
                bResult = true;
        else
            if(cCaracter =='2')
                bResult = true;
        else
            if(cCaracter =='3')
                bResult = true;
        else
            if(cCaracter =='4')
                bResult = true;
        else
            if(cCaracter =='5')
                bResult = true;
        else
            if(cCaracter =='6')
                bResult = true;
        else
            if(cCaracter =='7')
                bResult = true;
        else
            if(cCaracter =='8')
                bResult = true;
        else
            if(cCaracter =='9')
                bResult = true;
        
        //Valor de retorno
        return bResult;
    }
    boolean fnBoolIsInteger(String sCadena){
        //Valor de Resultado
        boolean bResult = true;
        int iCuenta;
        if(sCadena.isEmpty())
            bResult = false;
        else
            
            for(iCuenta=0;iCuenta < sCadena.length();iCuenta++)
                if(! fnBoolIsNumeric(sCadena.charAt(iCuenta)))
                {
                    bResult = false;
                    break;
                }
    //Retorna el valor
    return bResult;
    }
    boolean fnBoolIsDecimal(String sCadena){
        //Valor de Resultado
        boolean bResult = true;
        boolean bPunto = false;
        int iCuenta;
        
        if(sCadena.isEmpty())
            bResult = false;
        else
            for(iCuenta=0;iCuenta < sCadena.length();iCuenta++)
                //Verifica  que sea un punto
                if(sCadena.charAt(iCuenta)==',')
                    //Verifica  que no haya  ya un punto
                    if(bPunto)
                    {
                    bResult = false;
                    break;
                    }
        else
                        bPunto =true;
        else
                    if(! fnBoolIsNumeric(sCadena.charAt(iCuenta)))
                    {
                     bResult = false;
                     break;
                    }
                   //Retorna el valor
                   return bResult;
    }
     public String getStringOfNumber(Integer $num){
        this.counter = $num;
        return doThings($num);
    }
   
    private String doThings (Integer _counter){
        //Limite
        if(_counter > 2000000)
            return "DOS MILLONES";
        
        switch(_counter){
            case 0: return "Cero";
            case 1: return "Uno";
            case 2: return "Dos";
            case 3: return "Tres";
            case 4: return "Cuatro";
            case 5: return "Cinco";
            case 6: return "Seis";
            case 7: return "Siete";
            case 8: return "Ocho";
            case 9: return "Nueve";
            case 10: return "Diez";
            case 11: return "Once";
            case 12: return "Doce";
            case 13: return "Trece";
            case 14: return "Catorce";
            case 15: return "Quince";
            case 20: return "Veinte";
            case 30: return "Treinte";
            case 40: return "Cuarenta";
            case 50: return "Cincuenta";
            case 60: return "Sesenta";
            case 70: return "Setenta";
            case 80: return "Ochenta";
            case 90: return "Noventa";
            case 100: return "Cien";
                
            case 200: return "Doscientos";
            case 300: return "Trecientos";
            case 400: return "Cuatrocientos";
            case 500: return "Qunientos";
            case 600: return "Seiscientos";
            case 700: return "Setecientos";
            case 800: return "Ochocientos";
            case 900: return "Novecientos";
            case 1000: return "Mil";
         
            case 10000: return "UN MILLON";
            case 20000: return "DOS MILLONES";
        }
        if (_counter < 20)
        {
            return "DIECI" + doThings(_counter-10);
        }
        if(_counter < 30){
            return "VEINTI" + doThings (_counter - 20);
        }
        if(_counter < 100){
            return  doThings ((int)(_counter/10)*10) + " Y " + doThings(_counter%10);
        }
        if(_counter < 200){
            return "CIENTO" + doThings (_counter - 100);
        }
        if(_counter < 1000){
            return  doThings ((int)(_counter/100)*100) + " " + doThings(_counter%100);
        }
        if(_counter < 2000){
            return "MIL" + doThings (_counter % 100);
        }
        if(_counter < 1000000){
            String var;
            var= doThings((int)(_counter/1000)) + " MIL ";
        if(_counter % 1000!=0){
            var += "" +doThings(_counter % 1000);
        }    
        return var;
        }
        if(_counter < 2000000){
            return "UN MILLON" + doThings (_counter % 1000000);
        }
        return  "";
    }
}
