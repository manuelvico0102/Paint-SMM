/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.mva.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Esta clase define objetos de Line2 a la cual se le agregan unos métodos
 * para facilitar mover los objetos.
 * 
 * @author Manuel Vico
 */
public class MiLinea2D extends Line2D.Float implements MiShape{
    /**
     * Booleano que se encarga de controlar el estado del atributo Relleno
     */
    private boolean relleno = false;
    /**
     *  Booleano que se encarga de controlar el estado del atributo Alisar
     */
    private boolean alisar = false;
    /**
     *  Booleano que se encarga de controlar el estado del atributo Transparencia
     */
    private boolean transp = false;
    /**
     * Variable que indica el grosor de las formas, inicialmente 1
     */
    private int grosor = 1;
    /**
     * Color actual de la forma
     */
    private Color color;
    /**
     * Variable que indica el tipo de antialiasing
     */
    private final RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    /**
     * Variable que indica la transparencia que se le puede aplicar a las formas
     */
    private final Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    /**
     * Variable que se utiliza para configurar el grosor
     */
    private Stroke stroke; 
    
    /**
     * Constructor para crear e inicializar MiLinea2D a partir de dos puntos
     * 
     * @param p1 punto de inicio de la línea
     * @param p2 punto de fin de la línea
     */
    public MiLinea2D(Point2D p1, Point2D p2){
       super(p1, p2);
    }
    
    /**
     * Constructor para crear e inicializar MiLinea2D a partir de dos coordenadas
     * X e Y
     * 
     * @param x1 Primera coordenada X
     * @param y1 Primera coordenada Y
     * @param x2 Segunda coordenada X
     * @param y2 Segunda coordenada Y
     */
    public MiLinea2D(float x1, float y1, float x2, float y2){
       super(x1, y1, x2, y2);
    }
    
    /**
     * Método para comprobar si un punto está cerca de la línea, se supondrá
     * que está cerca cuando la distancia es menor o igual a 2.
     * 
     * @param p Punto para probar si está cerca de la línea
     * @return True si el punto está cerca de la línea, false en caso contrario.
     */
    public boolean isNear(Point2D p){
        if(this.getP1().equals(this.getP2())) return this.getP1().distance(p)<=2.0;
        return this.ptLineDist(p)<=2.0;   
    }
    
    /**
     * Método para comprobar si un punto está contenido en la línea, se supondrá
     * que se contiene cuando el punto es cercano a la línea.
     * 
     * @param p Punto para probar si está contenido en la línea
     * @return True si el punto está contenido en la línea, false en caso contrario.
     */
    @Override
    public boolean contains(Point2D p){
        return isNear(p);
    }
    
    /**
     * Método para posicionar el objeto MiLinea2D en la posicion dada
     * Calcula la distancia del punto dado y el primer punto para posicionar
     * la línea.
     * 
     * @param pos Punto de coordenadas donde posicionar el objeto
     */
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.getX1();
        double dy = pos.getY() - this.getY1();
        Point2D newp2 = new Point2D.Double(this.getX2() + dx, this.getY2() + dy);
        this.setLine(pos, newp2);
    }
    
    /**
     * Método que devuelve el nombre de la forma
     * @return El string del nombre de la forma
     */
    @Override
    public String toString(){
        return "Línea";
    }
    

    /**
     * Método que devuelve el color de la forma
     * @return el color de la forma
     */
    @Override
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Método que establece el clor de la forma
     * @param color el color que tomara la forma
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Método que devuelve el grosor de la forma
     * @return el grosor de la forma
     */
    @Override
    public int getGrosor() {
        return this.grosor;
    }
    
    /**
     * Método que establece el grosor de la forma
     * @param grosor El entero que indica el grosor de la forma 
     */
    @Override
    public void setGrosor(int grosor) {
        this.grosor = grosor;
    }
    
    /**
     * Método que devuelve si la forma esta rellena o no
     * @return en caso de estar relleno true en caso contrario false
     */
    @Override
    public boolean getRelleno() {
        return this.relleno;
    }
    
    /**
     * Método que establece si rellenar o no la figura
     * @param relleno el booleano correspondiente
     */
    @Override
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }
    
    /**
     * Método que devuelve si la forma esta alisada o no
     * @return el booleano correspondiente
     */
    @Override
    public boolean getAlisado() {
        return this.alisar;
    }
    
    /**
     * Método que establece si la forma tiene que estar alisada o no
     * @param alisado el booleano correspondiente
     */
    @Override
    public void setAlisado(boolean alisado) {
        this.alisar = alisado;
    }
    
    /**
     * Método que devuelve si la forma es transparente o no
     * @return el booleano correspondiente
     */
    @Override
    public boolean getTransparencia() {
        return this.transp;
    }  
    
    /**
     * Método que establece si la forma es transparente o no
     * @param transparencia el booleano correspondiente
     */
    @Override
    public void setTransparencia(boolean transparencia) {
        this.transp = transparencia;
    }
    
    /**
     * Método que se encarga de pintar la figura correspondiente.
     * Así como aplicarle los diferentes atributos seleccionados.
     * 
     * @param g2d objeto Graphics2D utilizado para pintar las formas.
     */
    @Override
    public void paint(Graphics2D g2d) {
        g2d.setPaint(color);
        
        stroke = new BasicStroke(grosor);
        g2d.setStroke(stroke);
        
        if(alisar)
            g2d.setRenderingHints(render);
        
        if(transp)
            g2d.setComposite(comp);
        
        if(relleno)
            g2d.fill(this);
        g2d.draw(this);
    }
}
