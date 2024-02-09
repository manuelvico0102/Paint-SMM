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
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Esta clase define objetos de MiRectanguloR a la cual se le agrega un método
 * para facilitar mover los objetos. 
 * 
 * @author Manuel Vico
 */
public class MiRectanguloR extends RoundRectangle2D.Float implements MiShape{
    
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
     * Variable que almacena el primer punto de la figura
     */
    private Point2D p1;
    
    /**
     * Constructor para crear e inicializar un objeto MiRectanguloR donde se 
     * especifican las coordenadas, así como su ancho, su altura, el ancho del
     * arco y la altura del arco
     * 
     * @param x Coordenada x
     * @param y Coordenada y
     * @param width ancho del rectangulo
     * @param height altura del rectangulo
     * @param arcw ancho del arco
     * @param arch altura del arco
     */
    public MiRectanguloR(float x, float y, float width, float height, float arcw, float arch){
        super(x, y, width, height, arcw, arch);
        this.p1 = new Point2D.Double(x, y);
    }
    
    /**
     * Método para posicionar el objeto MiRectanguloR en la nueva posicion dada.
     * Calcula la distancia del punto dado y la del rectágulo 
     * para posicionar el objeto
     * 
     * @param pos Punto de coordenadas donde posicionar el objeto
     */
    @Override
    public void setLocation(Point2D pos){
        this.setFrame(pos.getX(), pos.getY(), this.getWidth(), this.getHeight());
        this.p1 = pos;
    }
    
    /**
     * Método que devuelve el punto p1
     * @return El punto p1
     */
    @Override
    public Point2D getP1(){
        return this.p1;
    }
    
    /**
     * Método que devuelve el nombre de la forma
     * @return El string del nombre de la forma
     */
    @Override
    public String toString(){
        return "Rectángulo Redondeado";
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

