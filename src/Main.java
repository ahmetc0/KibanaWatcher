import java.io.IOException;

/********************************************************/
 /*                                                      */
 /*                     Ahmet Ceyhan                     */
 /*               ahmetceyhan0@hotmail.com               */
 /*                                                      */
 /********************************************************/
public class Main {

    static KibanaWatcher kibanaWatcher;

    public static void main(String[] args){
        kibanaWatcher = new KibanaWatcher();
        kibanaWatcher.run();
    }
 }
