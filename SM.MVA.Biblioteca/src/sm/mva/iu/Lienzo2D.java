/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.mva.iu;

import sm.mva.graficos.Estado;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import sm.mva.eventos.LienzoEvent;
import sm.mva.eventos.LienzoListener;
import sm.mva.graficos.MiArea;
import sm.mva.graficos.MiElipse2D;
import sm.mva.graficos.MiGeneralPath;
import sm.mva.graficos.MiLinea2D;
import sm.mva.graficos.MiQuadCurve2D;
import sm.mva.graficos.MiRectangulo;
import sm.mva.graficos.MiRectanguloR;
import sm.mva.graficos.MiShape;

/**
 * Clase que define Lienzo2D, representando un panel de dibujo.
 * Contiene diferentes propiedades para configurar el estado 
 * del Lienzo2D. Y también se encarga de pintar las diferentes formas y 
 * de que se puedan editar/mover.
 * 
 * @author Manuel Vico
 */
public class Lienzo2D extends javax.swing.JPanel {
    
    /**
     * Booleano que se encarga de controlar el estado del atributo Relleno
     */
    private boolean relleno = false;
    
    /**
     *  Booleano que se encarga de controlar el estado del atributo Editar
     */
    private boolean editar = false;
    
    /**
     *  Booleano que se encarga de controlar el estado del atributo Alisar
     */
    private boolean alisar = false;
    
    /**
     *  Booleano que se encarga de controlar el estado del atributo Transparencia
     */
    private boolean transparencia = false;
    
    /**
     * Color actual de las formas
     */
    private Color color = Color.black;
    
    /**
     * Forma actual seleccionada a dibujar, inicializado a Linea
     */
    private Estado estado = sm.mva.graficos.Estado.Linea;
    
    /**
     * Punto auxiliar usado para almacenar el punto de MousePressed
     */
    private Point2D aux;
    
    /**
     * Segundo punto auxiliar usado para almacenar el segundo punto de la curva
     */
    private Point2D aux2;
    
    /**
     * Variable que indica el paso por cual paso esta actualmente la forma curva
     */
    private int paso = 1;
    
    /**
     * Atributo que representa la imagen que se va a dibujar
     */
    private BufferedImage img;
    
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
     * Variable que indica el grosor de las formas, inicialmente 1
     */
    private float grosor = 1.0f;
    
    /**
     * Lista de los MiShape que se han dibujado
     */
    private List<MiShape> vectorFiguras = new ArrayList();
    /**
     * Atributo que toma el valor de cualquiera de las formas a dibujarse
     */
    private MiShape forma;
    
    private ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();
    
    /**
     * Constructor de Lienzo2D que inicializa sus componentes.
     */
    public Lienzo2D() {
        initComponents();
    }
    
    /**
     * Método que se encarga de pintar las diferentes formas en el Lienzo2D.
     * Así como aplicarle los diferentes atributos seleccionados. Además, se
     * define una zona de recorte para que no se pinte fuera de ella, dicha zona
     * es el tamaño de la imagen.
     * 
     * @param g objeto Graphics utilizado para pintar las formas.
     */
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        if(img!=null){
            g2d.drawImage(img,0,0,this);
            g2d.clip(new Rectangle(0,0,img.getWidth(),img.getHeight()));
        }
        
        for (MiShape s : vectorFiguras) { //Para cada figura del vector
            s.paint(g2d);
        }
    }
    
    private void establecerAtributos(MiShape s){
        s.setColor(color);
        s.setAlisado(alisar);
        s.setGrosor((int) grosor);
        s.setRelleno(relleno);
        s.setTransparencia(transparencia);
    }
    
    /**
     * Método que establece el color del cual se pintarán las formas
     * 
     * @param color el color del que se pintarán.
     */
    public void setColor(Color color){
        this.color = color;
    }
    /**
     * Método que devuelve el color actual de las formas
     * 
     * @return color actual
     */
    public Color getColor(){
        return this.color;
    }
    
    /**
     * Método que establece el atributo Relleno
     * 
     * @param relleno estado del atributo, true=activo false=desactivado
     */
    public void setRelleno(Boolean relleno){
        this.relleno = relleno;
    }
    
    /**
     * Método que devuelve el estado del atributo Relleno
     * 
     * @return el estado del atributo Relleno
     */
    public boolean getRelleno(){
        return this.relleno;
    }
    
    /**
     * Método que establece el estado, es decir la forma que seleccionada a dibujar
     * 
     * @param estado la forma que quiere ser seleccionada
     */
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    
    /**
     * Método que devuelve la forma actual
     * 
     * @return la forma actual
     */
    public Estado getEstado(){
        return this.estado;
    }
    
    /**
     * Método que establece el atributo editar
     * 
     * @param editar estado del atributo, true=activo false=desactivado
     */
    public void setEditar(Boolean editar){
        this.editar = editar;
    }
    
    /**
     * Método que devuelve el estado del atributo Editar
     * 
     * @return el estado del atributo Editar
     */
    public boolean getEditar(){
        return this.editar;
    }
    
    /**
     * Método que establece el atributo Alisar
     * 
     * @param alisar estado del atributo, true=activo false=desactivado
     */
    public void setAlisar(boolean alisar) {
        this.alisar = alisar;
    }
    
    /**
     * Método que devuelve el estado del atributo Alisar
     * 
     * @return el estado del atributo Alisar
     */
    public boolean getAlisar() {
        return alisar;
    }
    
    /**
     * Método que establece el atributo Transparencia
     * 
     * @param transparencia estado del atributo, true=activo false=desactivado
     */

    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
    }
    
    /**
     * Método que devuelve el estado del atributo Transparencia
     * 
     * @return el estado del atributo Transparencia
     */
    public boolean getTransparencia() {
        return transparencia;
    }
    
    /**
     * Método que establece el valor del atributo grosor
     * 
     * @param grosor valor del estado del atributo
     */
    public void setGrosor(float grosor) {
        this.grosor = grosor;
    }
    
    /**
     * Método que devuelve el valor del atributo Grosor
     * 
     * @return el valor del atributo Grosor
     */
    public float getGrosor() {
        return grosor;
    }
    
    /**
     * Método que establece la imagen que se muestra en el lienzo y ajusta el
     * tamaño preferido según el tamaño de la imagen
     * 
     * @param img la imagen a mostrar en el lienzo
     */
    public void setImagen(BufferedImage img) {
        this.img = img;
        if(img!=null){
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        }
    }
    
    /**
     * Método que devuelve la imagen actual
     * 
     * @return la imagen actual
     */
    public BufferedImage getImagen() {
        return img;
    }
    
    /**
     * Devuelve la imagen actual del lienzo, si se ha dibujado
     * se devuelve con las formas dibujadas, en caso contrario solo devuelve
     * la imagen. Además, desactiva la propiedad "opaco" en el caso de 
     * que la imagen tenga alfa.
     * 
     * @param pintaVector indica si se ha pintado en la imagen
     * @return Si pintaVector true se devuelve la imagen dibujada en caso contrario
     * se devuelve la imagen original
     */
    public BufferedImage getImagen(boolean pintaVector) {
        if(pintaVector){
            BufferedImage imgout = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
            boolean opacoActual = this.isOpaque();
            //Desactivar propiedad opaco si tiene alfa
            if (img.getColorModel().hasAlpha()) {
                this.setOpaque(false);
            }
            this.paint(imgout.createGraphics());
            this.setOpaque(opacoActual);
            return imgout;
        }else{
            return img;
        }
    }
    
    /**
     * Devuelve la imagen actual del lienzo, con las formas seleccionadas
     * volcadas, en caso de que la lista de formas a volcar este vacía 
     * se devuelve solola imagen. Además, desactiva la propiedad "opaco" 
     * en el caso de que la imagen tenga alfa.
     * 
     * @param nvShape lista de formas a volcar
     * @return Si lista no esta vacía se devuelve la imagen dibujada en caso contrario
     * se devuelve la imagen original
     */
    public BufferedImage getImagen(List<MiShape> nvShape) {
        if(!nvShape.isEmpty()){
            BufferedImage imgout = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
            boolean opacoActual = this.isOpaque();
            if (img.getColorModel().hasAlpha()) {
                this.setOpaque(false);
            }
            
            //Hace lo mismo que el paint pero solo con las figuras seleccionadas
            Graphics2D g2dImagen = imgout.createGraphics();
            if (img != null) {
                g2dImagen.drawImage(img, 0, 0, this);
                g2dImagen.clip(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
            }

            for (MiShape s : nvShape) { //Para cada figura del vector
                s.paint(g2dImagen);
            }
            
            this.setOpaque(opacoActual);
            return imgout;
        }else{
            return img;
        }
    }
    
    /**
     * Método que devuelve la lista de formas
     * @return la lista de formas
     */
    public List<MiShape> getvShape() {
        return vectorFiguras;
    }
    /**
     * Método que establece la lista de formas a la lista que reciba
     * como parámetro
     * @param vShape la nueva lista de formas
     */
    public void setvShape(List<MiShape> vShape) {
        this.vectorFiguras = vShape;
    }
    
    
    /**
     * Método que devuelve la figura que se ha seleccionado con el punto de 
     * coordenadas dado.
     * 
     * @param p Punto de coordenadas donde se busca la figura
     * @return la figura seleccionada en esas coordenadas, en caso de no haber null
     */
    private MiShape getFiguraSeleccionada(Point2D p){
        for(MiShape forma:vectorFiguras)
            if(forma.contains(p)) return forma;
        return null;
    }
    
    /**
     * Método para vaciar/Limpiar el vector que contiene las formas dibujadas
     */
    public void Limpiar() {
        vectorFiguras.clear();
    }
    
    /**
     * Método para dibujar una cara sonriente a partir de las coordenadas
     * de un evento de ratón
     * 
     * @param evt un evento de ratón
     * @return el dibujo de una cara sonriente en las coordenadas del evento
     */
    private Area cara(java.awt.event.MouseEvent evt){      
        Area cara = new Area(new MiElipse2D(evt.getPoint().x-25, evt.getPoint().y-25, 50, 50));
        Area ojoi = new Area(new MiElipse2D(evt.getPoint().x-15, evt.getPoint().y-10, 10, 10));
        Area ojod = new Area(new MiElipse2D(evt.getPoint().x+5, evt.getPoint().y-10, 10, 10));
        Area boca = new Area(new MiLinea2D(evt.getPoint().x-15, evt.getPoint().y+5, evt.getPoint().x+15, evt.getPoint().y+5));
        Area bocainf = new Area(new QuadCurve2D.Float(evt.getPoint().x-15, evt.getPoint().y+5, evt.getPoint().x, evt.getPoint().y+25, evt.getPoint().x+15, evt.getPoint().y+5));
        boca.add(bocainf);
        cara.subtract(ojoi);
        cara.subtract(ojod);
        cara.subtract(boca);
        return cara;
    }
    
    /**
     * Método que añade un listener al listener de eventos del lienzo
     * @param listener el nuevo listener
     */
    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            lienzoEventListeners.add(listener);
        }
    }
    
    /** 
     * Método para notificar que se ha añadido una nueva forma
     * @param evt 
     */
    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Este método se encarga de gestionar el evento MousePressed.
     * Si la opción editar está habilitada, se selecionará la figura
     * correspondiente en esa posición.
     * En caso de estar deshabilitada, comprobará el estado de la forma a 
     * dibujar y creará el objeto correspondiente en la posición del evento,
     * añadiendolo al vector de formas 
     * 
     * @param evt evento de MousePressed
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if(editar){
            aux = evt.getPoint();
            forma = getFiguraSeleccionada(evt.getPoint());
            if(forma != null){
                this.establecerAtributos(forma);
                this.repaint();
            }
        }else{
            switch (estado){
                case Trazo:
                    forma = new MiGeneralPath();
                    ((MiGeneralPath)forma).moveTo(evt.getPoint().x, evt.getPoint().y);
                    aux = evt.getPoint();
                    break; 
                case Linea:
                    forma = new MiLinea2D(evt.getPoint(), evt.getPoint());
                    aux = evt.getPoint();
                    break;
                case Rectangulo:
                    forma = new MiRectangulo(evt.getPoint().x, evt.getPoint().y,0,0);
                    aux = evt.getPoint();
                    break;
                case Elipse:
                    forma = new MiElipse2D(evt.getPoint().x, evt.getPoint().y, 0,0);
                    aux = evt.getPoint();
                    break;
                case Curva:
                    if(paso==1){
                        forma = new MiQuadCurve2D(evt.getPoint().x, evt.getPoint().y,evt.getPoint().x, evt.getPoint().y,evt.getPoint().x, evt.getPoint().y);       
                        aux = evt.getPoint();
                    }
                    break;
                case Smile:
                    forma = new MiArea(this.cara(evt));
                    aux = evt.getPoint();
                    this.repaint();
                    break;
                case RectanguloR:
                    forma = new MiRectanguloR(evt.getPoint().x, evt.getPoint().y,0,0,20,20);
                    aux = evt.getPoint();
                    break;
                default:
                    break;
            }
            
            if(paso!=2){
                vectorFiguras.add(forma);
                this.establecerAtributos(forma);
                this.notifyShapeAddedEvent(new LienzoEvent(this, forma, color));
            }       
        }
    }//GEN-LAST:event_formMousePressed
    
    /**
     * Este método se encarga de gestionar el evento MouseDragged.
     * Si la opción editar esta habilitada, moverá la figura, seleccionada
     * previamente, por la posición del evento del ratón.
     * En el caso de que este deshabilitada, comprobará el estado de la forma a 
     * dibujar, y lo dibujará con las variables tomadas en el evento MousePressed
     * y la variable de este evento. Y por último se repintará el lienzo.
     * En el caso de la curva cuenta con dos pasos, el primer paso para dibujar
     * la "linea" y el segundo paso para establecer el punto de control de la curva.
     * 
     * @param evt evento de MouseDragged
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if(editar){
            if (forma != null) {
                Point2D p = new Point2D.Double(evt.getPoint().getX() - aux.getX(), evt.getPoint().getY() - aux.getY());
                Point2D corner = forma.getP1();
                corner.setLocation(corner.getX() + p.getX(), corner.getY() + p.getY());
                forma.setLocation(corner);
                aux = evt.getPoint();
            }
        }else{
            switch (estado){
                case Trazo:
                    ((MiGeneralPath)forma).lineTo(evt.getPoint().x, evt.getPoint().y);
                    break;
                case Linea:
                    ((MiLinea2D)forma).setLine(((MiLinea2D)forma).getP1(), evt.getPoint());
                    break;
                case Rectangulo:
                    ((Rectangle)forma).setFrameFromDiagonal(aux, evt.getPoint());
                    break;
                case Elipse:
                    ((MiElipse2D)forma).setFrameFromDiagonal(aux, evt.getPoint());
                    break;
                case Curva:
                    if(paso==1){    
                        ((MiQuadCurve2D)forma).setCurve(aux.getX(), aux.getY(),aux.getX(), aux.getY(),evt.getPoint().x, evt.getPoint().y);
                        aux2 = evt.getPoint();
                    }else if(paso == 2){
                        ((MiQuadCurve2D)forma).setCurve(aux.getX(), aux.getY(),evt.getPoint().x, evt.getPoint().y, aux2.getX(), aux2.getY());
                    }
                    break;
                case RectanguloR:
                    ((MiRectanguloR)forma).setFrameFromDiagonal(aux, evt.getPoint());
                    break;
                default:
                    break;
            }
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Este método se encarga de gestionar el evento MouseReleased
     * En concreto se encarga de gestionar el dibujo de la curva, ya que al 
     * tener dos pasos, podemos controlar cuando pasar de un paso a otro
     * con este evento.
     * 
     * @param evt evento de MouseReleased
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if(editar){
            paso=1;
        }else{
            switch (estado){
                case Curva:
                   if(paso==1)paso = 2;
                   else paso = 1;
                break;
            }
       }
    }//GEN-LAST:event_formMouseReleased



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
