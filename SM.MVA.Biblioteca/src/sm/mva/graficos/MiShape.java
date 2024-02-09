/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.mva.graficos;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 *  Interfaz que se utiliza para declarar los métodos que tendrán las diferentes
 *  formas
 * @author Manuel Vico
 */
public interface MiShape extends Shape{
    
    /**
     * Método que devuelve el color de la forma
     * @return el color de la forma
     */
    Color getColor();
    /**
     * Método que establece el clor de la forma
     * @param color el color que tomara la forma
     */
    void setColor(Color color);
    
    /**
     * Método que devuelve el grosor de la forma
     * @return el grosor de la forma
     */
    int getGrosor();
    /**
     * Método que establece el grosor de la forma
     * @param grosor El entero que indica el grosor de la forma 
     */
    void setGrosor(int grosor);
    
    /**
     * Método que devuelve si la forma esta rellena o no
     * @return en caso de estar relleno true en caso contrario false
     */
    boolean getRelleno();
    /**
     * Método que establece si rellenar o no la figura
     * @param relleno el booleano correspondiente
     */
    void setRelleno(boolean relleno);
    
    /**
     * Método que devuelve si la forma esta alisada o no
     * @return el booleano correspondiente
     */
    boolean getAlisado();
    /**
     * Método que establece si la forma tiene que estar alisada o no
     * @param alisado el booleano correspondiente
     */
    void setAlisado(boolean alisado);
    
    /**
     * Método que devuelve si la forma es transparente o no
     * @return el booleano correspondiente
     */
    boolean getTransparencia();
    /**
     * Método que establece si la forma es transparente o no
     * @param transparencia el booleano correspondiente
     */
    void setTransparencia(boolean transparencia);
    
    /**
     * Método que se encarga de pintar la figura correspondiente.
     * Así como aplicarle los diferentes atributos seleccionados.
     * 
     * @param g2d objeto Graphics2D utilizado para pintar las formas.
     */
    void paint (Graphics2D g2d);
    
    /**
     * Método que devuelve el punto p1
     * @return El punto p1
     */
    Point2D getP1();
     /* Método para posicionar las formas en la nueva posicion dada.
     * Calcula la distancia del punto dado y la del rectágulo 
     * para posicionar el objeto
     * 
     * @param pos Punto de coordenadas donde posicionar el objeto
     */
    void setLocation(Point2D pos);
}
