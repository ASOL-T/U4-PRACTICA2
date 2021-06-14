/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alan
 */
public class ArbolGeneral {

    NodoGeneral raiz;

    ArbolGeneral() {
        raiz = null;
    }

    public boolean insertar(char dato, String path) {
        if (raiz == null) {
            raiz = new NodoGeneral(dato);
            if (raiz == null) {
                return false;
            }
            return true;
        }
        if (path.isEmpty()) {
            return false;
        }
        NodoGeneral padre = buscarNodo(path);
        if (padre == null) {
            return false;
        }

        NodoGeneral hijoYaExiste = buscarNodo(path + "/" + dato);
        if (hijoYaExiste != null) {
            return false;
        }

        NodoGeneral nuevo = new NodoGeneral(dato);
        return padre.enlazar(nuevo);
    }

    private NodoGeneral buscarNodo(String path) {
        path = path.substring(1);
        String vector[] = path.split("/");

        if (vector[0].charAt(0) == raiz.dato) {
            if (vector.length == 1) {
                return raiz;
            }
            NodoGeneral padre = raiz;
            for (int i = 1; i < vector.length; i++) {
                padre = padre.obtenerHijo(vector[i].charAt(0));
                if (padre == null) {
                    return null;
                }
            }
            return padre;
        }
        return null;
    }

    public boolean eliminar(String path) {
        if (raiz == null) {
            return false;
        }

        NodoGeneral hijo = buscarNodo(path);
        if (hijo == null) {
            return false;
        }

        if (!hijo.esHoja()) {
            return false;
        }

        if (hijo == raiz) {
            raiz = null;
            return true;
        }
        String pathPadre = obtenerPathPadre(path);
        NodoGeneral padre = buscarNodo(path);

        return padre.desenlazar(hijo);
    }

    private String obtenerPathPadre(String path) {
        int posicionAntesUltimaDiagonal = path.lastIndexOf("/");
        return path.substring(0, posicionAntesUltimaDiagonal);
    }

    private NodoGeneral buscarNodoRecursivo(String path) {
        if (path.isEmpty()) {
            return null;
        }
        path = path.substring(1);
        String vector[] = path.split("/");
        if (raiz.dato == vector[0].charAt(0)) {
            if (vector.length == 1) {
                return raiz;
            }
            for (NodoHijo temp = raiz.ini; temp != null; temp = temp.sig) {
                if (temp.direccionHijo.dato == vector[1].charAt(0)) {
                    if (vector.length == 2) {
                        return temp.direccionHijo;
                    }
                    return buscarNodoRecursivo(temp.direccionHijo, path.substring(3));
                }
            }
        }
        return null;
    }

    private NodoGeneral buscarNodoRecursivo(NodoGeneral nodoEncontrado, String path) {
        if (path.isEmpty()) {
            return nodoEncontrado;
        }
        path = path.substring(1);
        String vector[];
        if (path.length() == 1) {
            vector = new String[1];
            vector[0] = path;
        } else {
            vector = path.split("/");
        }
        for (NodoHijo temp = nodoEncontrado.ini; temp != null; temp = temp.sig) {
            if (temp.direccionHijo.dato == vector[0].charAt(0)) {
                buscarNodoRecursivo(temp.direccionHijo, path.substring(1));
            }
        }
        return null;
    }
}
