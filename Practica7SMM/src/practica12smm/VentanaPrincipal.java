/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practica12smm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.SepiaOp;
import sm.image.TintOp;
import sm.mva.eventos.LienzoAdapter;
import sm.mva.eventos.LienzoEvent;
import sm.mva.graficos.MiShape;
import sm.mva.imagen.PosterizarOp;
import sm.mva.imagen.RojoOp;
import sm.mva.imagen.TonoColoresOp;
import sm.mva.iu.Lienzo2D;
import sm.sound.SMClipPlayer;
import sm.sound.SMSoundRecorder;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Esta clase define una Ventana Principal de la aplicación
 * La Ventana Principal es la interfaz principal de la aplicación
 * donde se pueden realizar las acciones principales.
 * Entre las opciones que tiene se encuentra nuevo archivo, abrir y guardar, así
 * como opciones para seleccionar diferentes figuras a dibujar y atributos para estas.
 * 
 * @author Manuel Vico
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    
    /**
     * Array que define un patron de discontinuidad
     */
    private final float patronDiscontinuidad[] = {15.0f, 15.0f};
    
    /**
     * Objeto Stroke que define el estilo de trazo para delimitar la imagen
     */
    private final Stroke trazo = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER, 1.0f,patronDiscontinuidad, 0.0f);
    
    /**
     * Objeto BufferedImage fuente
     */
    private BufferedImage imgFuente = null;
    
    /**
     * objeto dialogo para mostrar la función con un punto de inflexión
     */
    private dialogDiagrama dialogo = new dialogDiagrama();
    
    /**
     * Objeto SMClipPlayer para reproducir clips de sonido
     */
    SMClipPlayer player = null;
    
    /**
     * Objeto SMSoundRecorder para grabar sonidos
     */
    SMSoundRecorder recorder = null;
    
    /**
     * Constructor de la clase VentanaPrincipal
     * Crea una nueva instancia, inicializa los componentes, establece el 
     * tamaño de la ventana, el título y los tooltips
     */
    public VentanaPrincipal() {
        initComponents();
        this.setSize(1280, 720);
        this.setTitle("Práctica 12");
        this.ToolTips();
        lista.setModel(new DefaultListModel());
        
    }
    
    /**
     * Método para establecer el texto de la etiqueta de las coordenadas
     * @param text el texto que se establecerá en la etiqueta
     */
    public void setTextCoordenadas(String text){
        this.eCoordenadas.setText(text);
    }
    
    /**
     * Método que devuelve el texto de la etiqueta coordenadas
     * @return devuelve el texto de la etiqueta
     */
    public String getTextCoordenadas(){
        return this.eCoordenadas.getText();
    }
    
    /**
     * Método que devuelve el texto de la etiqueta RGB
     * @return devuelve el texto de la etiqueta
     */
    public String getTextRGB() {
        return this.eRGB.getText();
    }
    
    /**
     * Método para establecer el texto de la etiqueta de RGB
     * @param text el texto que se establecerá en la etiqueta
     */
    public void setTextRGB(String text) {
        this.eRGB.setText(text);
    }
    
    
    
    /**
     * Método que devuelve el Lienzo2D de la ventana interna seleccionada
     * @return el Lienzo2D de la ventana interna seleccionada o null si no hay ninguna ventana interna selccionada
     */
    public Lienzo2D getLienzoSeleccionado(){
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        return vi!=null ? vi.getLienzo2D() : null;
    }
    
    /**
     * Método que establece los tooltips para los distintos botones
     * y elementos de la ventana principal
     */
    private void ToolTips(){
        bNuevo.setToolTipText("Nuevo");
        bAbrir.setToolTipText("Abrir");
        bGuardar.setToolTipText("Guardar");
        bTrazo.setToolTipText("Trazo Libre");
        bLinea.setToolTipText("Linea");
        bRectangulo.setToolTipText("Rectángulo");
        bElipse.setToolTipText("Elipse");
        bCurva.setToolTipText("Curva");
        bSmile.setToolTipText("Smile");
        bNegro.setToolTipText("Color: Negro");
        bRojo.setToolTipText("Color: Rojo");
        bAmarillo.setToolTipText("Color: Amarillo");
        bVerde.setToolTipText("Color: Verde");
        bAzul.setToolTipText("Color: Azul");
        eOtros.setToolTipText("Otros colores");
        bEditar.setToolTipText("Editar");
        bRellenar.setToolTipText("Rellenar");
        bTransp.setToolTipText("Transparencia");
        bAlisar.setToolTipText("Alisar");
        sGrosor.setToolTipText("Grosor");
    }
    
    /**
     * Clase interna que implementa un manejador para la ventana interna
     * Este manejador se encarga de actualizar los estados de los diferentes
     * elementos de la ventana principal según el estado correspondiente
     * de estos en la ventana interna
     */
    private class ManejadorVentanaInterna extends InternalFrameAdapter{
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt){
            VentanaInternaImagen vi = (VentanaInternaImagen) evt.getInternalFrame();
            bTrazo.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Trazo);
            bLinea.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Linea);
            bRectangulo.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Rectangulo);
            bElipse.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Elipse);
            bCurva.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Curva);
            bSmile.setSelected(vi.getLienzo2D().getEstado()==sm.mva.graficos.Estado.Smile);
            bEditar.setSelected(vi.getLienzo2D().getEditar());
            bRellenar.setSelected(vi.getLienzo2D().getRelleno());
            bTransp.setSelected(vi.getLienzo2D().getTransparencia());
            bAlisar.setSelected(vi.getLienzo2D().getAlisar());
            sGrosor.getModel().setValue((int) vi.getLienzo2D().getGrosor());
            
            DefaultListModel modelo = new DefaultListModel();
            modelo.addAll(vi.getLienzo2D().getvShape());
            lista.setModel(modelo);
        }
        
        @Override
        public void internalFrameClosing(InternalFrameEvent evt) {
            ((DefaultListModel) lista.getModel()).removeAllElements();
        }
    }
    
    /**
     * Clase interna que implementa un manejador para el lienzo
     * Este manejador se encarga de añadir a la lista de formas, la forma
     * correspondiente
     */
    public class MiManejadorLienzo extends LienzoAdapter {

        @Override
        public void shapeAdded(LienzoEvent evt) {
            //System.out.println("Figura "+evt.getForma() +" añadida");
            Shape s = evt.getForma();
            ((DefaultListModel)lista.getModel()).addElement(s);
        }
    }
    
    /**
     * Clase interna que implementa un manejador para el audio
     * Este manejador se encarga de habilitar y deshabilitar el boton play
     * según esta iniciado o no.
     */
    class ManejadorAudio implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                bPlay.setEnabled(false);
            }
            if (event.getType() == LineEvent.Type.STOP) {
                bPlay.setEnabled(true);
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
            }
        }
    }
    /**
     * Clase interna que implementa un manejador para el video
     * Este manejador se encarga de habilitar y deshabilitar el boton play
     * y pausa según esta iniciado o no.
     */
    private class VideoListener extends MediaPlayerEventAdapter {

        public void playing(MediaPlayer mediaPlayer) {
            bPausa.setEnabled(true);
            bPlay.setEnabled(false);
        }

        public void paused(MediaPlayer mediaPlayer) {
            bPausa.setEnabled(false);
            bPlay.setEnabled(true);
        }

        public void finished(MediaPlayer mediaPlayer) {
            this.paused(mediaPlayer);
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

        gForma = new javax.swing.ButtonGroup();
        gColor = new javax.swing.ButtonGroup();
        jSplitPane2 = new javax.swing.JSplitPane();
        dEscritorio = new javax.swing.JDesktopPane();
        pLista = new javax.swing.JPanel();
        lista = new javax.swing.JList<>();
        pBVolcar = new javax.swing.JPanel();
        bVolcar = new javax.swing.JButton();
        pBarras = new javax.swing.JPanel();
        bHerramientas = new javax.swing.JToolBar();
        bNuevo = new javax.swing.JButton();
        bAbrir = new javax.swing.JButton();
        bGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        bTrazo = new javax.swing.JToggleButton();
        bLinea = new javax.swing.JToggleButton();
        bRectangulo = new javax.swing.JToggleButton();
        bElipse = new javax.swing.JToggleButton();
        bCurva = new javax.swing.JToggleButton();
        bSmile = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        bEditar = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        pColores = new javax.swing.JPanel();
        bNegro = new javax.swing.JToggleButton();
        bRojo = new javax.swing.JToggleButton();
        bAmarillo = new javax.swing.JToggleButton();
        bVerde = new javax.swing.JToggleButton();
        bAzul = new javax.swing.JToggleButton();
        eOtros = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        bRellenar = new javax.swing.JToggleButton();
        bTransp = new javax.swing.JToggleButton();
        bAlisar = new javax.swing.JToggleButton();
        sGrosor = new javax.swing.JSpinner();
        jToolBar2 = new javax.swing.JToolBar();
        bPlay = new javax.swing.JButton();
        bPausa = new javax.swing.JButton();
        listaReproduccion = new javax.swing.JComboBox<>();
        bRecord = new javax.swing.JButton();
        bCamara = new javax.swing.JButton();
        bCaptura = new javax.swing.JButton();
        pSur = new javax.swing.JPanel();
        pBarraEstado = new javax.swing.JPanel();
        eEstado = new javax.swing.JLabel();
        eCoordenadas = new javax.swing.JLabel();
        eRGB = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        pBrillo = new javax.swing.JPanel();
        sliderBrillo = new javax.swing.JSlider();
        sliderContraste = new javax.swing.JSlider();
        pFiltros = new javax.swing.JPanel();
        seleccionMascara = new javax.swing.JComboBox<>();
        pTransformaciones = new javax.swing.JPanel();
        bContNormal = new javax.swing.JButton();
        bContIlum = new javax.swing.JButton();
        bCuadratica = new javax.swing.JToggleButton();
        sliderCuadratica = new javax.swing.JSlider();
        bContOsc = new javax.swing.JButton();
        bNegativo = new javax.swing.JButton();
        bSpline = new javax.swing.JToggleButton();
        pSplineSliders = new javax.swing.JPanel();
        sliderA = new javax.swing.JSlider();
        sliderB = new javax.swing.JSlider();
        pRotEsc = new javax.swing.JPanel();
        boton180 = new javax.swing.JButton();
        bAumentar = new javax.swing.JButton();
        bDisminuir = new javax.swing.JButton();
        pColor = new javax.swing.JPanel();
        bBandas = new javax.swing.JButton();
        seleccionEspaciosColores = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        bCombinar = new javax.swing.JButton();
        bTintar = new javax.swing.JToggleButton();
        bTonoRojo = new javax.swing.JToggleButton();
        bSepia = new javax.swing.JButton();
        bEcualizar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        sliderMezcla = new javax.swing.JSlider();
        sliderUmbral = new javax.swing.JSlider();
        sliderPosterizar = new javax.swing.JSlider();
        sliderColores = new javax.swing.JSlider();
        mMenu = new javax.swing.JMenuBar();
        mArchivo = new javax.swing.JMenu();
        miNuevo = new javax.swing.JMenuItem();
        miAbrir = new javax.swing.JMenuItem();
        miGuardar = new javax.swing.JMenuItem();
        miDuplicar = new javax.swing.JMenuItem();
        miAbrirAudio = new javax.swing.JMenuItem();
        miGrabar = new javax.swing.JMenuItem();
        miAbrirVideo = new javax.swing.JMenuItem();
        mImagen = new javax.swing.JMenu();
        miRescaleOp = new javax.swing.JMenuItem();
        miConvolve = new javax.swing.JMenuItem();
        miAffineTransform = new javax.swing.JMenuItem();
        miLookupOp = new javax.swing.JMenuItem();
        miBandCombine = new javax.swing.JMenuItem();
        miColorConvert = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setAutoscrolls(true);
        jSplitPane2.setMinimumSize(new java.awt.Dimension(70, 155));
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setPreferredSize(new java.awt.Dimension(1265, 205));

        dEscritorio.setPreferredSize(new java.awt.Dimension(1100, 155));

        javax.swing.GroupLayout dEscritorioLayout = new javax.swing.GroupLayout(dEscritorio);
        dEscritorio.setLayout(dEscritorioLayout);
        dEscritorioLayout.setHorizontalGroup(
            dEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        dEscritorioLayout.setVerticalGroup(
            dEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(dEscritorio);

        pLista.setPreferredSize(new java.awt.Dimension(65, 155));
        pLista.setLayout(new java.awt.BorderLayout());

        lista.setToolTipText("Lista figuras");
        lista.setMinimumSize(new java.awt.Dimension(65, 175));
        lista.setPreferredSize(new java.awt.Dimension(65, 485));
        pLista.add(lista, java.awt.BorderLayout.CENTER);

        pBVolcar.setLayout(new java.awt.BorderLayout());

        bVolcar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/volcar.png"))); // NOI18N
        bVolcar.setToolTipText("Volcar");
        bVolcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVolcarActionPerformed(evt);
            }
        });
        pBVolcar.add(bVolcar, java.awt.BorderLayout.WEST);

        pLista.add(pBVolcar, java.awt.BorderLayout.SOUTH);

        jSplitPane2.setRightComponent(pLista);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.LINE_START);

        pBarras.setLayout(new java.awt.BorderLayout());

        bHerramientas.setRollover(true);

        bNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        bNuevo.setFocusable(false);
        bNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNuevoActionPerformed(evt);
            }
        });
        bHerramientas.add(bNuevo);

        bAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        bAbrir.setFocusable(false);
        bAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAbrirActionPerformed(evt);
            }
        });
        bHerramientas.add(bAbrir);

        bGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        bGuardar.setFocusable(false);
        bGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGuardarActionPerformed(evt);
            }
        });
        bHerramientas.add(bGuardar);
        bHerramientas.add(jSeparator1);

        gForma.add(bTrazo);
        bTrazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/trazo.png"))); // NOI18N
        bTrazo.setFocusable(false);
        bTrazo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bTrazo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTrazoActionPerformed(evt);
            }
        });
        bHerramientas.add(bTrazo);

        gForma.add(bLinea);
        bLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        bLinea.setSelected(true);
        bLinea.setFocusable(false);
        bLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLineaActionPerformed(evt);
            }
        });
        bHerramientas.add(bLinea);

        gForma.add(bRectangulo);
        bRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        bRectangulo.setFocusable(false);
        bRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRectanguloActionPerformed(evt);
            }
        });
        bHerramientas.add(bRectangulo);

        gForma.add(bElipse);
        bElipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        bElipse.setFocusable(false);
        bElipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bElipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bElipseActionPerformed(evt);
            }
        });
        bHerramientas.add(bElipse);

        gForma.add(bCurva);
        bCurva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/curva.png"))); // NOI18N
        bCurva.setFocusable(false);
        bCurva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bCurva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bCurva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCurvaActionPerformed(evt);
            }
        });
        bHerramientas.add(bCurva);

        gForma.add(bSmile);
        bSmile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/smile.png"))); // NOI18N
        bSmile.setFocusable(false);
        bSmile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSmile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSmile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSmileActionPerformed(evt);
            }
        });
        bHerramientas.add(bSmile);

        gForma.add(jToggleButton1);
        jToggleButton1.setText("RectRedondo");
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        bHerramientas.add(jToggleButton1);

        gForma.add(bEditar);
        bEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/seleccion.png"))); // NOI18N
        bEditar.setFocusable(false);
        bEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditarActionPerformed(evt);
            }
        });
        bHerramientas.add(bEditar);
        bHerramientas.add(jSeparator2);

        pColores.setMaximumSize(new java.awt.Dimension(40, 40));
        pColores.setPreferredSize(new java.awt.Dimension(45, 30));
        pColores.setLayout(new java.awt.GridLayout(2, 3));

        bNegro.setBackground(java.awt.Color.black);
        gColor.add(bNegro);
        bNegro.setMaximumSize(new java.awt.Dimension(40, 6));
        bNegro.setPreferredSize(new java.awt.Dimension(15, 15));
        bNegro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNegroActionPerformed(evt);
            }
        });
        pColores.add(bNegro);

        bRojo.setBackground(java.awt.Color.red);
        gColor.add(bRojo);
        bRojo.setPreferredSize(new java.awt.Dimension(15, 15));
        bRojo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRojoActionPerformed(evt);
            }
        });
        pColores.add(bRojo);

        bAmarillo.setBackground(java.awt.Color.yellow);
        gColor.add(bAmarillo);
        bAmarillo.setPreferredSize(new java.awt.Dimension(15, 15));
        bAmarillo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAmarilloActionPerformed(evt);
            }
        });
        pColores.add(bAmarillo);

        bVerde.setBackground(java.awt.Color.green);
        gColor.add(bVerde);
        bVerde.setPreferredSize(new java.awt.Dimension(15, 15));
        bVerde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVerdeActionPerformed(evt);
            }
        });
        pColores.add(bVerde);

        bAzul.setBackground(java.awt.Color.blue);
        gColor.add(bAzul);
        bAzul.setPreferredSize(new java.awt.Dimension(15, 15));
        bAzul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAzulActionPerformed(evt);
            }
        });
        pColores.add(bAzul);

        eOtros.setText(" +");
        eOtros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eOtrosMouseClicked(evt);
            }
        });
        pColores.add(eOtros);

        bHerramientas.add(pColores);
        bHerramientas.add(jSeparator3);

        bRellenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rellenar.png"))); // NOI18N
        bRellenar.setFocusable(false);
        bRellenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bRellenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bRellenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRellenarActionPerformed(evt);
            }
        });
        bHerramientas.add(bRellenar);

        bTransp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/transparencia.png"))); // NOI18N
        bTransp.setFocusable(false);
        bTransp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bTransp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bTransp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTranspActionPerformed(evt);
            }
        });
        bHerramientas.add(bTransp);

        bAlisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        bAlisar.setFocusable(false);
        bAlisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bAlisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bAlisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAlisarActionPerformed(evt);
            }
        });
        bHerramientas.add(bAlisar);

        sGrosor.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));
        sGrosor.setPreferredSize(new java.awt.Dimension(70, 25));
        sGrosor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sGrosorStateChanged(evt);
            }
        });
        bHerramientas.add(sGrosor);

        pBarras.add(bHerramientas, java.awt.BorderLayout.NORTH);

        jToolBar2.setRollover(true);

        bPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play24x24.png"))); // NOI18N
        bPlay.setFocusable(false);
        bPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPlayActionPerformed(evt);
            }
        });
        jToolBar2.add(bPlay);

        bPausa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pausa24x24.png"))); // NOI18N
        bPausa.setFocusable(false);
        bPausa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPausa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPausaActionPerformed(evt);
            }
        });
        jToolBar2.add(bPausa);

        listaReproduccion.setPreferredSize(new java.awt.Dimension(144, 22));
        jToolBar2.add(listaReproduccion);

        bRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        bRecord.setFocusable(false);
        bRecord.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bRecord.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRecordActionPerformed(evt);
            }
        });
        jToolBar2.add(bRecord);

        bCamara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Camara.png"))); // NOI18N
        bCamara.setFocusable(false);
        bCamara.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bCamara.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bCamara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCamaraActionPerformed(evt);
            }
        });
        jToolBar2.add(bCamara);

        bCaptura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Capturar.png"))); // NOI18N
        bCaptura.setFocusable(false);
        bCaptura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bCaptura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bCaptura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCapturaActionPerformed(evt);
            }
        });
        jToolBar2.add(bCaptura);

        pBarras.add(jToolBar2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pBarras, java.awt.BorderLayout.NORTH);

        pSur.setPreferredSize(new java.awt.Dimension(773, 105));
        pSur.setLayout(new java.awt.BorderLayout());

        pBarraEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pBarraEstado.setLayout(new java.awt.GridLayout(1, 0));

        eEstado.setText("Barra de estado");
        eEstado.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        eEstado.setPreferredSize(new java.awt.Dimension(88, 18));
        pBarraEstado.add(eEstado);
        pBarraEstado.add(eCoordenadas);
        pBarraEstado.add(eRGB);

        pSur.add(pBarraEstado, java.awt.BorderLayout.SOUTH);

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setRollover(true);
        jToolBar1.setMinimumSize(new java.awt.Dimension(256, 60));
        jToolBar1.setPreferredSize(new java.awt.Dimension(1030, 90));

        pBrillo.setBorder(javax.swing.BorderFactory.createTitledBorder("Brillo y Contraste"));
        pBrillo.setOpaque(false);
        pBrillo.setPreferredSize(new java.awt.Dimension(150, 90));
        pBrillo.setLayout(new java.awt.GridLayout(1, 0));

        sliderBrillo.setMaximum(255);
        sliderBrillo.setMinimum(-255);
        sliderBrillo.setToolTipText("Brillo");
        sliderBrillo.setValue(0);
        sliderBrillo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sliderBrillo.setOpaque(true);
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });
        sliderBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusLost(evt);
            }
        });
        pBrillo.add(sliderBrillo);

        sliderContraste.setMaximum(20);
        sliderContraste.setToolTipText("Contraste");
        sliderContraste.setValue(10);
        sliderContraste.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderContrasteStateChanged(evt);
            }
        });
        sliderContraste.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusLost(evt);
            }
        });
        pBrillo.add(sliderContraste);

        jToolBar1.add(pBrillo);

        pFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));
        pFiltros.setMinimumSize(new java.awt.Dimension(45, 45));
        pFiltros.setPreferredSize(new java.awt.Dimension(108, 90));
        pFiltros.setLayout(new java.awt.BorderLayout());

        seleccionMascara.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Detector de fronteras laplaciano", "Media 5x5", "Media 7x7", "Emb. Horizontal 5x1", "Emb. Horizontal 7x1", "Emb. Horizontal 10x1" }));
        seleccionMascara.setToolTipText("Filtros");
        seleccionMascara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionMascaraActionPerformed(evt);
            }
        });
        pFiltros.add(seleccionMascara, java.awt.BorderLayout.CENTER);

        jToolBar1.add(pFiltros);

        pTransformaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Transformaciones"));
        pTransformaciones.setPreferredSize(new java.awt.Dimension(300, 90));
        pTransformaciones.setLayout(new java.awt.GridLayout(2, 3));

        bContNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste.png"))); // NOI18N
        bContNormal.setToolTipText("Contraste");
        bContNormal.setPreferredSize(new java.awt.Dimension(30, 45));
        bContNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bContNormalActionPerformed(evt);
            }
        });
        pTransformaciones.add(bContNormal);

        bContIlum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iluminar.png"))); // NOI18N
        bContIlum.setToolTipText("Iluminar");
        bContIlum.setPreferredSize(new java.awt.Dimension(30, 45));
        bContIlum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bContIlumActionPerformed(evt);
            }
        });
        pTransformaciones.add(bContIlum);

        bCuadratica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cuadratica.png"))); // NOI18N
        bCuadratica.setToolTipText("Cuadrática");
        bCuadratica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCuadraticaActionPerformed(evt);
            }
        });
        pTransformaciones.add(bCuadratica);

        sliderCuadratica.setMaximum(255);
        sliderCuadratica.setToolTipText("valor m");
        sliderCuadratica.setValue(128);
        sliderCuadratica.setEnabled(false);
        sliderCuadratica.setPreferredSize(new java.awt.Dimension(30, 45));
        sliderCuadratica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderCuadraticaStateChanged(evt);
            }
        });
        pTransformaciones.add(sliderCuadratica);

        bContOsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/oscurecer.png"))); // NOI18N
        bContOsc.setToolTipText("Oscurecer");
        bContOsc.setPreferredSize(new java.awt.Dimension(30, 45));
        bContOsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bContOscActionPerformed(evt);
            }
        });
        pTransformaciones.add(bContOsc);

        bNegativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/negativo.png"))); // NOI18N
        bNegativo.setToolTipText("Negativo");
        bNegativo.setPreferredSize(new java.awt.Dimension(30, 45));
        bNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNegativoActionPerformed(evt);
            }
        });
        pTransformaciones.add(bNegativo);

        bSpline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/lineal.png"))); // NOI18N
        bSpline.setToolTipText("F. Punto inflexión");
        bSpline.setPreferredSize(new java.awt.Dimension(30, 45));
        bSpline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSplineActionPerformed(evt);
            }
        });
        pTransformaciones.add(bSpline);

        pSplineSliders.setMinimumSize(new java.awt.Dimension(30, 45));
        pSplineSliders.setPreferredSize(new java.awt.Dimension(30, 40));
        pSplineSliders.setLayout(new java.awt.BorderLayout());

        sliderA.setMaximum(255);
        sliderA.setToolTipText("valor A");
        sliderA.setValue(128);
        sliderA.setEnabled(false);
        sliderA.setPreferredSize(new java.awt.Dimension(75, 12));
        sliderA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderAStateChanged(evt);
            }
        });
        pSplineSliders.add(sliderA, java.awt.BorderLayout.NORTH);

        sliderB.setMaximum(255);
        sliderB.setToolTipText("valor B");
        sliderB.setValue(128);
        sliderB.setEnabled(false);
        sliderB.setPreferredSize(new java.awt.Dimension(75, 12));
        sliderB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBStateChanged(evt);
            }
        });
        pSplineSliders.add(sliderB, java.awt.BorderLayout.SOUTH);

        pTransformaciones.add(pSplineSliders);

        jToolBar1.add(pTransformaciones);

        pRotEsc.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotación y Escalado"));
        pRotEsc.setMinimumSize(new java.awt.Dimension(76, 70));
        pRotEsc.setPreferredSize(new java.awt.Dimension(200, 70));
        pRotEsc.setLayout(new java.awt.GridLayout(1, 0));

        boton180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion180.png"))); // NOI18N
        boton180.setToolTipText("Giro 180º");
        boton180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton180ActionPerformed(evt);
            }
        });
        pRotEsc.add(boton180);

        bAumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aumentar.png"))); // NOI18N
        bAumentar.setToolTipText("Aumentar");
        bAumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAumentarActionPerformed(evt);
            }
        });
        pRotEsc.add(bAumentar);

        bDisminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/disminuir.png"))); // NOI18N
        bDisminuir.setToolTipText("Disminuir");
        bDisminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDisminuirActionPerformed(evt);
            }
        });
        pRotEsc.add(bDisminuir);

        jToolBar1.add(pRotEsc);

        pColor.setBorder(javax.swing.BorderFactory.createTitledBorder("Color"));
        pColor.setLayout(new java.awt.GridLayout(1, 0));

        bBandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bandas.png"))); // NOI18N
        bBandas.setToolTipText("Bandas");
        bBandas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bBandas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bBandas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBandasActionPerformed(evt);
            }
        });
        pColor.add(bBandas);

        seleccionEspaciosColores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sRGB", "YCC", "Grey" }));
        seleccionEspaciosColores.setToolTipText("Espacios de Color");
        seleccionEspaciosColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionEspaciosColoresActionPerformed(evt);
            }
        });
        pColor.add(seleccionEspaciosColores);

        jToolBar1.add(pColor);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 90));
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        bCombinar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/combinar.png"))); // NOI18N
        bCombinar.setToolTipText("Combinar");
        bCombinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCombinarActionPerformed(evt);
            }
        });
        jPanel1.add(bCombinar);

        bTintar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tintar.png"))); // NOI18N
        bTintar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTintarActionPerformed(evt);
            }
        });
        jPanel1.add(bTintar);

        bTonoRojo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rojo.png"))); // NOI18N
        bTonoRojo.setToolTipText("Tono Rojo");
        bTonoRojo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTonoRojoActionPerformed(evt);
            }
        });
        jPanel1.add(bTonoRojo);

        bSepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepia.png"))); // NOI18N
        bSepia.setToolTipText("Sepia");
        bSepia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSepiaActionPerformed(evt);
            }
        });
        jPanel1.add(bSepia);

        bEcualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ecualizar.png"))); // NOI18N
        bEcualizar.setToolTipText("Ecualizar");
        bEcualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEcualizarActionPerformed(evt);
            }
        });
        jPanel1.add(bEcualizar);
        jPanel1.add(jSeparator4);

        sliderMezcla.setToolTipText("Valor alfa");
        sliderMezcla.setEnabled(false);
        sliderMezcla.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderMezclaStateChanged(evt);
            }
        });
        jPanel1.add(sliderMezcla);

        sliderUmbral.setToolTipText("valor umbral");
        sliderUmbral.setEnabled(false);
        sliderUmbral.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderUmbralStateChanged(evt);
            }
        });
        jPanel1.add(sliderUmbral);

        sliderPosterizar.setMaximum(20);
        sliderPosterizar.setMinimum(2);
        sliderPosterizar.setToolTipText("Posterizar");
        sliderPosterizar.setValue(11);
        sliderPosterizar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderPosterizarStateChanged(evt);
            }
        });
        sliderPosterizar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderPosterizarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderPosterizarFocusLost(evt);
            }
        });
        jPanel1.add(sliderPosterizar);

        sliderColores.setMaximum(360);
        sliderColores.setToolTipText("Cambiar colores HSB");
        sliderColores.setValue(180);
        sliderColores.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderColoresStateChanged(evt);
            }
        });
        sliderColores.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderColoresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderColoresFocusLost(evt);
            }
        });
        jPanel1.add(sliderColores);

        jToolBar1.add(jPanel1);

        pSur.add(jToolBar1, java.awt.BorderLayout.NORTH);

        getContentPane().add(pSur, java.awt.BorderLayout.SOUTH);

        mArchivo.setText("Archivo");

        miNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        miNuevo.setText("Nuevo");
        miNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNuevoActionPerformed(evt);
            }
        });
        mArchivo.add(miNuevo);

        miAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        miAbrir.setText("Abrir");
        miAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAbrirActionPerformed(evt);
            }
        });
        mArchivo.add(miAbrir);

        miGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        miGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        miGuardar.setText("Guardar");
        miGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGuardarActionPerformed(evt);
            }
        });
        mArchivo.add(miGuardar);

        miDuplicar.setText("Duplicar");
        miDuplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDuplicarActionPerformed(evt);
            }
        });
        mArchivo.add(miDuplicar);

        miAbrirAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/openAudio24x24.png"))); // NOI18N
        miAbrirAudio.setText("Abrir Audio");
        miAbrirAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAbrirAudioActionPerformed(evt);
            }
        });
        mArchivo.add(miAbrirAudio);

        miGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        miGrabar.setText("Grabar");
        mArchivo.add(miGrabar);

        miAbrirVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/AbrirVideo.png"))); // NOI18N
        miAbrirVideo.setText("Abrir Video");
        miAbrirVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAbrirVideoActionPerformed(evt);
            }
        });
        mArchivo.add(miAbrirVideo);

        mMenu.add(mArchivo);

        mImagen.setText("Imagen");

        miRescaleOp.setText("Operador RescaleOp");
        miRescaleOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRescaleOpActionPerformed(evt);
            }
        });
        mImagen.add(miRescaleOp);

        miConvolve.setText("Operador ConvolveOp");
        miConvolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miConvolveActionPerformed(evt);
            }
        });
        mImagen.add(miConvolve);

        miAffineTransform.setText("Operador AffineTransformOp");
        miAffineTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAffineTransformActionPerformed(evt);
            }
        });
        mImagen.add(miAffineTransform);

        miLookupOp.setText("Operador LookupOp");
        miLookupOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLookupOpActionPerformed(evt);
            }
        });
        mImagen.add(miLookupOp);

        miBandCombine.setText("Operador BandCombineOp");
        miBandCombine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miBandCombineActionPerformed(evt);
            }
        });
        mImagen.add(miBandCombine);

        miColorConvert.setText("Operador ColorConvertOp");
        miColorConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miColorConvertActionPerformed(evt);
            }
        });
        mImagen.add(miColorConvert);

        mMenu.add(mImagen);

        setJMenuBar(mMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Cambia el color de las figuras del lienzo seleccionado a negro 
     * al hacer clic en el botón correspondiente. Si no hay ningún lienzo 
     * seleccionado, no hace nada.
     *
     * @param evt evento de acción del botón negro
     */
    private void bNegroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNegroActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setColor(Color.BLACK);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bNegroActionPerformed

    /**
     * Cambia el color de las figuras del lienzo seleccionado a rojo 
     * al hacer clic en el botón correspondiente. Si no hay ningún lienzo 
     * seleccionado, no hace nada.
     *
     * @param evt evento de acción del botón rojo
     */
    private void bRojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRojoActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setColor(Color.RED);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bRojoActionPerformed
    
    /**
     * Cambia el color de las figuras del lienzo seleccionado a azul 
     * al hacer clic en el botón correspondiente. Si no hay ningún lienzo 
     * seleccionado, no hace nada.
     *
     * @param evt evento de acción del botón azul
     */
    private void bAzulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAzulActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setColor(Color.BLUE);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bAzulActionPerformed
    
    /**
     * Cambia el color de las figuras del lienzo seleccionado a amarillo 
     * al hacer clic en el botón correspondiente. Si no hay ningún lienzo 
     * seleccionado, no hace nada.
     *
     * @param evt evento de acción del botón amarillo
     */
    private void bAmarilloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAmarilloActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setColor(Color.YELLOW);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bAmarilloActionPerformed
    
    /**
     * Cambia el color de las figuras del lienzo seleccionado a verde 
     * al hacer clic en el botón correspondiente. Si no hay ningún lienzo 
     * seleccionado, no hace nada.
     *
     * @param evt evento de acción del botón verde
     */
    private void bVerdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVerdeActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setColor(Color.GREEN);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bVerdeActionPerformed
    
    /**
     * Método que crea una nueva ventana interna con su lienzo correspondiente,
     * al cual se agrega un manejador. Este método crea una imagen en blanco 
     * con un marco discontinuo. A dicha imagen hay que pasarle los valores
     * de anchura y altura obligatoriamente.
     * 
     * @param evt evento de acción del botón nuevo
     */
    private void miNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNuevoActionPerformed
        String ancho = JOptionPane.showInputDialog(this, "Introduzca el ancho deseado (en píxeles):", "Nuevo", JOptionPane.PLAIN_MESSAGE);
        String altura = JOptionPane.showInputDialog(this, "Introduzca la altura deseado (en píxeles):", "Nuevo", JOptionPane.PLAIN_MESSAGE);
        if(ancho != null && altura != null){
            try{
                int anc = Integer.parseInt(ancho);
                int alt = Integer.parseInt(altura);
                VentanaInternaImagen vi = new VentanaInternaImagen(this);
                vi.setSize(450, 450);
                this.dEscritorio.add(vi);
                vi.setVisible(true);
                vi.addInternalFrameListener(new ManejadorVentanaInterna());
                vi.getLienzo2D().addLienzoListener(new MiManejadorLienzo());
                
                BufferedImage img;
                img = new BufferedImage(anc, alt, BufferedImage.TYPE_INT_ARGB);
                //Hago uso de Graphics2D para cambiar el color de los pixeles a blanco
                Graphics2D g2d = img.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
                //Dibujo el trazo para distinguir la zona
                g2d.setColor(Color.BLACK);
                g2d.setStroke(trazo);
                g2d.drawRect(0, 0, img.getWidth(), img.getHeight());
                g2d.dispose();
                vi.getLienzo2D().setImagen(img);
            } catch (Exception ex){
                System.err.println("El tamaño debe ser un número entero válido");
                JOptionPane.showMessageDialog(this, "El tamaño debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_miNuevoActionPerformed
    
    /**
     * Método que abre un diálogo para seleccionar una imagen. La imagen
     * seleccionada se abre en la ventana interna. Además se dibuja un marco
     * a la imagen para delimitar la zona de dibujo.
     * 
     * @param evt 
     * @throws Exception si ocurre un error al leer la imagen
     */
    private void miAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAbrirActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if(resp == JFileChooser.APPROVE_OPTION){
            try{
                File f = dlg.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                Graphics2D g2d = img.createGraphics();
                g2d.setColor(Color.BLACK);
                g2d.setStroke(trazo);
                g2d.drawRect(0, 0, img.getWidth(), img.getHeight());
                g2d.dispose();
                VentanaInternaImagen vi = new VentanaInternaImagen(this);
                vi.getLienzo2D().setImagen(img);
                this.dEscritorio.add(vi);
                vi.setTitle(f.getName());
                vi.setVisible(true);
                vi.addInternalFrameListener(new ManejadorVentanaInterna());
                vi.getLienzo2D().addLienzoListener(new MiManejadorLienzo());
            }catch(Exception ex){
                System.err.println("Error al leer la imagen");
                JOptionPane.showMessageDialog(this, "No se pudo leer la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_miAbrirActionPerformed
    
    /**
     * Método que guarda una imagen de la ventana interna activa en el formato
     * que elijas, por defecto .jpg
     * Si no hay una ventana activa no hace nada. Muestra un diálogo de guardado
     * para guardarlo en la ruta deseada y para poner el nombre que queramos.
     * Este método abre un diálogo para seleccionar la extensión en la que
     * deseas guardar la imagen por defecto .jpg.
     * 
     * @param evt evento de acción del botón guardar
     * @throws Exception si ocurre un error al guardar la imagen
     */
    private void miGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGuardarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) dEscritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen(true);
            if (img != null) {
                JFileChooser dlg = new JFileChooser();
                dlg.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Image", "jpg"));
                dlg.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    File f = dlg.getSelectedFile();
                    String extension = "";
                    String tiposArchivos = dlg.getFileFilter().getDescription();
                    if (tiposArchivos.equals("PNG Image"))
                        extension = ".png";
                    else 
                        extension = ".jpg";
                    
                    try {
                        File fileToSave = new File(dlg.getSelectedFile().getAbsolutePath() + extension);
                        ImageIO.write(img, extension.substring(1), fileToSave);
                        vi.setTitle(fileToSave.getName());
                    } catch (Exception ex) {
                        System.err.println("Error al guardar la imagen");
                        JOptionPane.showMessageDialog(this, "No se pudo guardar", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_miGuardarActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Linea"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Línea.
     */
    private void bLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLineaActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Linea);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Linea");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bLineaActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Rectangulo"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Rectángulo.
     */
    private void bRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRectanguloActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Rectangulo);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Rectángulo");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bRectanguloActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Elipse"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Elipse.
     */
    private void bElipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bElipseActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Elipse);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Elipse");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bElipseActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Trazo"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Trazo.
     */
    private void bTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTrazoActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Trazo);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Trazo");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bTrazoActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Curva"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Curva.
     */
    private void bCurvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCurvaActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Curva);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Curva");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_bCurvaActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Smile"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Trazo.
     */
    private void bSmileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSmileActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.Smile);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Smile");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
        
    }//GEN-LAST:event_bSmileActionPerformed
    
    /**
     * Método que establece el Estado de la ventana interna en "Smile"
     * Desactiva la opción de editar ya que a la hora de dibujar
     * se comprueba primero si se esta editando, si no se pone a false no permite
     * el dibujo.
     * Cambia el cursor a una cruz, actualiza la etiqueta y repinta el lienzo.
     * 
     * @param evt evento de acción del botón Smile.
     */
    private void bEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEditar(this.bEditar.isSelected());
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Editar");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }//GEN-LAST:event_bEditarActionPerformed
    
    /**
     * Método que activa o desactiva el atributo de relleno de la ventana 
     * interna seleccionada.
     * 
     * @param evt evento de acción del botón Rellenar.
     */
    private void bRellenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRellenarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setRelleno(this.bRellenar.isSelected());
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bRellenarActionPerformed
    
    /**
     * Método que activa o desactiva el atributo de transparencia de la ventana
     * interna seleccionada.
     * 
     * @param evt evento de acción del botón de Transparencia.
     */
    private void bTranspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTranspActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setTransparencia(this.bTransp.isSelected());
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bTranspActionPerformed
    
    /**
     * Método que activa o desactiva el atributo de alisar de la ventana
     * interna seleccionada.
     * 
     * @param evt evento de acción del botón de Alisar.
     */
    private void bAlisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlisarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setAlisar(this.bAlisar.isSelected());
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_bAlisarActionPerformed
    
    /**
     * Método que tiene la misma función que miNuevo, simplemente cambia
     * que este botón esta situado en la barra de herramientas
     * 
     * @param evt evento de acción del botón de Nuevo
     */
    private void bNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNuevoActionPerformed
        this.miNuevoActionPerformed(evt);
    }//GEN-LAST:event_bNuevoActionPerformed
    
    /**
     * Método que tiene la misma función que miAbrir, simplemente cambia
     * que este botón esta situado en la barra de herramientas
     * 
     * @param evt evento de acción del botón de Abrir
     */
    private void bAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAbrirActionPerformed
        this.miAbrirActionPerformed(evt);
    }//GEN-LAST:event_bAbrirActionPerformed

    /**
     * Método que tiene la misma función que miGuardar, simplemente cambia
     * que este botón esta situado en la barra de herramientas
     * 
     * @param evt evento de acción del botón de Guardar
     */
    private void bGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGuardarActionPerformed
        this.miGuardarActionPerformed(evt);
    }//GEN-LAST:event_bGuardarActionPerformed
    
    /**
     * Método que establece el grosor, a partir del componente JSpinner,
     * de las distintas figuras de la ventana interna seleccionada.
     * 
     * @param evt evento de estado cambiado del spinner Grosor
     */
    private void sGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sGrosorStateChanged
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            vi.getLienzo2D().setGrosor((float)sGrosor.getValue().hashCode());
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_sGrosorStateChanged

    /**
     * Cambia el color de las figuras del lienzo seleccionado al color
     * que se seleccione en el JColorChooser al hacer clic en la etiqueta
     * correspondiente. Si no hay ningún lienzo seleccionado, no hace nada.
     *
     * @param evt evento de clic de ratón de la etiqueta eOtros
     */
    private void eOtrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eOtrosMouseClicked
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if(vi != null){
            Color color = JColorChooser.showDialog(this, "Elije un color", Color.WHITE);
            vi.getLienzo2D().setColor(color);
            vi.getLienzo2D().repaint();
        }
    }//GEN-LAST:event_eOtrosMouseClicked

    private void miAffineTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAffineTransformActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    //AffineTransform at = AffineTransform.getScaleInstance(1.5, 1.5);
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(180),img.getWidth()/2, img.getHeight()/2);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miAffineTransformActionPerformed

    private void miLookupOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLookupOpActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    byte funcionT[] = new byte[256];
                    for (int x = 0; x < 256; x++) {
                        funcionT[x] = (byte) (255 - x); // Negativo
                    }
                    
                    LookupTable tabla = new ByteLookupTable(0, funcionT);
                    LookupOp lop = new LookupOp(tabla, null);
                    
                    lop.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miLookupOpActionPerformed

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        int brillo = this.sliderBrillo.getValue();
        RescaleOp rop = new RescaleOp(1.0F, brillo, null);
        this.aplicarRescaleOp(rop);
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void sliderBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusGained
        this.ganaFocus();
    }//GEN-LAST:event_sliderBrilloFocusGained

    private void sliderBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusLost
        imgFuente = null;
        this.sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderBrilloFocusLost

    private void seleccionMascaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionMascaraActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            
            Kernel k = this.getKernel(this.seleccionMascara.getSelectedIndex());
            if (img != null && k != null) {
                try {
                    ConvolveOp cop = new ConvolveOp(k);
                    
                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);

                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_seleccionMascaraActionPerformed
    
    private void boton180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton180ActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(180),img.getWidth()/2, img.getHeight()/2);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }//GEN-LAST:event_boton180ActionPerformed

    private void bAumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAumentarActionPerformed
        AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
        this.aplicarEscalado(at);
    }//GEN-LAST:event_bAumentarActionPerformed

    private void bDisminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDisminuirActionPerformed
        AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
        this.aplicarEscalado(at);
    }//GEN-LAST:event_bDisminuirActionPerformed

    private void sliderContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderContrasteStateChanged
        float contraste = this.sliderContraste.getValue()/10.0F;
        RescaleOp rop = new RescaleOp(contraste, 0.0F, null);
        this.aplicarRescaleOp(rop);
    }//GEN-LAST:event_sliderContrasteStateChanged

    private void sliderContrasteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusGained
        this.ganaFocus();
    }//GEN-LAST:event_sliderContrasteFocusGained

    private void sliderContrasteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusLost
        imgFuente = null;
        this.sliderContraste.setValue(10);
    }//GEN-LAST:event_sliderContrasteFocusLost

    private void miRescaleOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRescaleOpActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    RescaleOp rop = new RescaleOp(1.0F, -100.0F, null);
                    rop.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miRescaleOpActionPerformed

    private void miConvolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miConvolveActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    float filtro[] = {0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f};
                    Kernel k = new Kernel(3, 3, filtro);
                    ConvolveOp cop = new ConvolveOp(k);
                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miConvolveActionPerformed

    private void bContNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bContNormalActionPerformed
        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
        this.aplicarLookup(tabla);
    }//GEN-LAST:event_bContNormalActionPerformed

    private void bContIlumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bContIlumActionPerformed
        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_LOGARITHM);
        this.aplicarLookup(tabla);
    }//GEN-LAST:event_bContIlumActionPerformed

    private void bContOscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bContOscActionPerformed
        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
        this.aplicarLookup(tabla);
    }//GEN-LAST:event_bContOscActionPerformed

    private void bVolcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVolcarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) dEscritorio.getSelectedFrame();
        if (vi != null) {
            //Guardar las figuras seleccionas en un vector
            List<MiShape> nvShape = lista.getSelectedValuesList();
            int[] indices = lista.getSelectedIndices();
            
            //Volcamos las figuras en la imagen
            BufferedImage img = vi.getLienzo2D().getImagen(nvShape);
            vi.getLienzo2D().setImagen(img);
            
            //Borramos las figuras seleccionas del vShape y de la lista
            nvShape = vi.getLienzo2D().getvShape();
            DefaultListModel modelo = (DefaultListModel) lista.getModel();
            for(int i = indices.length - 1; i >= 0; i--){
                nvShape.remove(indices[i]);
                modelo.remove(indices[i]); 
            }
            vi.getLienzo2D().setvShape(nvShape);
        }
    }//GEN-LAST:event_bVolcarActionPerformed

    private void bSplineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSplineActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            if (bSpline.isSelected()) {
                this.sliderA.setEnabled(true);
                this.sliderB.setEnabled(true);
                this.ganaFocus();
                this.dialogo.setVisible(true);
                this.dialogo.setAlwaysOnTop(true);
            } else {
                imgFuente = null;
                this.sliderA.setValue(128);
                this.sliderB.setValue(128);
                this.sliderA.setEnabled(false);
                this.sliderB.setEnabled(false);
                this.dialogo.setVisible(false);
            }
        }else{
            this.bSpline.setSelected(false);
        }
    }//GEN-LAST:event_bSplineActionPerformed

    private void sliderAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderAStateChanged
        double vA = (double) this.sliderA.getValue();
        double vB = (double) this.sliderB.getValue();
        LookupTable tabla = this.SplineLineaInflexion(vA, vB);
        
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    LookupOp lop = new LookupOp(tabla, null);
                    lop.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();

                    dialogo.cambioFuncion(vA, vB);
                    //dialogo.setVisible(true);
                    dialogo.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }//GEN-LAST:event_sliderAStateChanged

    private void sliderBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBStateChanged
        double vA = (double) this.sliderA.getValue();
        double vB = (double) this.sliderB.getValue();
        LookupTable tabla = this.SplineLineaInflexion(vA, vB);
        
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    LookupOp lop = new LookupOp(tabla, null);
                    lop.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();

                    dialogo.cambioFuncion(vA, vB);
                    //dialogo.setVisible(true);
                    dialogo.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }//GEN-LAST:event_sliderBStateChanged

    private void bNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNegativoActionPerformed
        LookupTable tabla = this.negativo();
        this.aplicarLookup(tabla);
    }//GEN-LAST:event_bNegativoActionPerformed

    private void bCuadraticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCuadraticaActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            if (bCuadratica.isSelected()) {
                this.sliderCuadratica.setEnabled(true);
                this.ganaFocus();
            } else {
                imgFuente = null;
                this.sliderCuadratica.setValue(128);
                this.sliderCuadratica.setEnabled(false);
            }
        }
    }//GEN-LAST:event_bCuadraticaActionPerformed

    private void sliderCuadraticaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderCuadraticaStateChanged
        LookupTable tabla = this.cuadratica((double)this.sliderCuadratica.getValue());
        
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    LookupOp lop = new LookupOp(tabla, null);
                    lop.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }//GEN-LAST:event_sliderCuadraticaStateChanged

    private void miDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDuplicarActionPerformed
        //Cogemos la ventana activa
        VentanaInternaImagen actual = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        
        //Comprobamos que hay una ventana actual
        if(actual != null){
            //Creamos una ventana
            VentanaInternaImagen vi = new VentanaInternaImagen(this);
            //Le agrego los manejadores
            vi.addInternalFrameListener(new ManejadorVentanaInterna());
            vi.getLienzo2D().addLienzoListener(new MiManejadorLienzo());
            //la añadimos al escritorio y la hacemos visible
            this.dEscritorio.add(vi);
            vi.setVisible(true);
            //Por último agregamos la imagen de la ventana actual a la nueva
            BufferedImage img = actual.getLienzo2D().getImagen(true);
            vi.getLienzo2D().setImagen(img);
        }
    }//GEN-LAST:event_miDuplicarActionPerformed

    private void miBandCombineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miBandCombineActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    float[][] matriz = {{1.0F, 0.0F, 0.0F},
                                        {0.0F, 0.0F, 1.0F},
                                        {0.0F, 1.0F, 0.0F}};
                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miBandCombineActionPerformed

    private void miColorConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miColorConvertActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_miColorConvertActionPerformed

    private void bBandasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBandasActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                for(int i = 0; i < img.getRaster().getNumBands(); i++){
                    BufferedImage imgbanda = this.getImageBand(img, i);
                    vi = new VentanaInternaImagen(this);
                    vi.getLienzo2D().setImagen(imgbanda);
                    this.dEscritorio.add(vi);
                    vi.setVisible(true);
                }
            }
        } 
    }//GEN-LAST:event_bBandasActionPerformed

    private void seleccionEspaciosColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionEspaciosColoresActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                ColorSpace cs = null;
                int seleccion = this.seleccionEspaciosColores.getSelectedIndex();
                switch (seleccion) {
                    case 0:
                        cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                        break;
                    case 1:
                        cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                        break;
                    case 2:
                        cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                        break;
                    default:
                        break;
                }
                
                try {
                    
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    //Creamos una ventana
                    VentanaInternaImagen newvi = new VentanaInternaImagen(this);
                    //Le agrego los manejadores
                    newvi.addInternalFrameListener(new ManejadorVentanaInterna());
                    newvi.getLienzo2D().addLienzoListener(new MiManejadorLienzo());
                    //la añadimos al escritorio y la hacemos visible
                    this.dEscritorio.add(newvi);
                    newvi.setVisible(true);
                    //Por último agregamos la imagen de la ventana actual a la nueva
                    newvi.getLienzo2D().setImagen(imgdest);
                    
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_seleccionEspaciosColoresActionPerformed

    private void bCombinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCombinarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    float[][] matriz = {{0.0F, 0.5F, 0.5F},
                                        {0.5F, 0.0F, 0.5F},
                                        {0.5F, 0.5F, 0.0F}};
                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_bCombinarActionPerformed

    private void bSepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSepiaActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    SepiaOp sepia = new SepiaOp();
                    sepia.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_bSepiaActionPerformed

    private void bEcualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEcualizarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    EqualizationOp ecualizacion = new EqualizationOp();
                    ecualizacion.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_bEcualizarActionPerformed

    private void sliderPosterizarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPosterizarFocusGained
        this.ganaFocus();
    }//GEN-LAST:event_sliderPosterizarFocusGained

    private void sliderPosterizarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPosterizarFocusLost
        imgFuente = null;
        this.sliderPosterizar.setValue(11);
    }//GEN-LAST:event_sliderPosterizarFocusLost

    private void sliderPosterizarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderPosterizarStateChanged
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    PosterizarOp op = new PosterizarOp(this.sliderPosterizar.getValue());
                    op.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderPosterizarStateChanged

    private void sliderColoresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderColoresFocusGained
        this.ganaFocus();
    }//GEN-LAST:event_sliderColoresFocusGained

    private void sliderColoresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderColoresFocusLost
        imgFuente = null;
        this.sliderColores.setValue(180);
    }//GEN-LAST:event_sliderColoresFocusLost

    private void sliderColoresStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderColoresStateChanged
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    TonoColoresOp op = new TonoColoresOp(this.sliderColores.getValue());
                    op.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderColoresStateChanged

    private void sliderMezclaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderMezclaStateChanged
        float alfa = (float) (this.sliderMezcla.getValue()/100.0);
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    Color seleccionado = vi.getLienzo2D().getColor();
                    TintOp tintado = new TintOp(seleccionado,alfa);
                    tintado.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }//GEN-LAST:event_sliderMezclaStateChanged

    private void bTintarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTintarActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            if (bTintar.isSelected()) {
                this.sliderMezcla.setEnabled(true);
                this.ganaFocus();
            } else {
                imgFuente = null;
                this.sliderMezcla.setValue(50);
                this.sliderMezcla.setEnabled(false);
            }
        }
    }//GEN-LAST:event_bTintarActionPerformed

    private void sliderUmbralStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderUmbralStateChanged
        int valor = this.sliderUmbral.getValue();
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    RojoOp op = new RojoOp(valor);
                    op.filter(imgFuente, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderUmbralStateChanged

    private void bTonoRojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTonoRojoActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            if (bTonoRojo.isSelected()) {
                this.sliderUmbral.setEnabled(true);
                this.ganaFocus();
            } else {
                imgFuente = null;
                this.sliderUmbral.setValue(50);
                this.sliderUmbral.setEnabled(false);
            }
        }
    }//GEN-LAST:event_bTonoRojoActionPerformed

    private void miAbrirAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAbrirAudioActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if(resp == JFileChooser.APPROVE_OPTION){
            try{
                //File f = dlg.getSelectedFile();
                File f = new File(dlg.getSelectedFile().getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                };
                this.listaReproduccion.addItem(f);
                this.listaReproduccion.setSelectedItem(f);
            }catch(Exception ex){
                System.err.println("Error al leer la imagen");
                JOptionPane.showMessageDialog(this, "No se pudo leer el audio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_miAbrirAudioActionPerformed

    private void bPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPlayActionPerformed
        VentanaInternaVideo vv = (VentanaInternaVideo)this.dEscritorio.getSelectedFrame();
        if(vv!=null){
            vv.play();
            vv.addMediaPlayerEventListener(new VideoListener());
        }else{
            File f = (File) listaReproduccion.getSelectedItem();
            if (f != null) {
                player = new SMClipPlayer(f);
                if (player != null) {
                    player.addLineListener(new ManejadorAudio());
                    player.play();
                }
            }
        }
    }//GEN-LAST:event_bPlayActionPerformed

    private void bPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPausaActionPerformed
        VentanaInternaVideo vv = (VentanaInternaVideo)this.dEscritorio.getSelectedFrame();
        if(vv!=null){
            vv.stop();
        }else{
            if (player != null) {
                player.stop();
                player = null;
            }
            if (recorder != null) {
                recorder.stop();
                recorder = null;
            }
        }
    }//GEN-LAST:event_bPausaActionPerformed

    private void bRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRecordActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showSaveDialog(this);
        if(resp == JFileChooser.APPROVE_OPTION){
            try{
                File f = dlg.getSelectedFile();
                recorder = new SMSoundRecorder(f);
                if (recorder != null) {
                    recorder.record();
                }
            }catch(Exception ex){
                System.err.println("Error al leer la imagen");
                JOptionPane.showMessageDialog(this, "No se pudo guardar el audio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_bRecordActionPerformed

    private void bCamaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCamaraActionPerformed
        VentanaInternaCamara vc = VentanaInternaCamara.getInstance();
        if(vc!=null){
            this.dEscritorio.add(vc);
            vc.setVisible(true);
        }
    }//GEN-LAST:event_bCamaraActionPerformed

    private void bCapturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCapturaActionPerformed
        VentanaInternaCamara vc = (VentanaInternaCamara)this.dEscritorio.getSelectedFrame();
        if(vc!=null){
            BufferedImage img = vc.getImage();
            VentanaInternaImagen vi = new VentanaInternaImagen(this);
            vi.getLienzo2D().setImagen(img);
            this.dEscritorio.add(vi);
            vi.setVisible(true);
            
        }
    }//GEN-LAST:event_bCapturaActionPerformed

    private void miAbrirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAbrirVideoActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if(resp == JFileChooser.APPROVE_OPTION){
            try{
                File f = dlg.getSelectedFile();
                VentanaInternaVideo vv = VentanaInternaVideo.getInstance(f);
                this.dEscritorio.add(vv);
                vv.setVisible(true);
            }catch(Exception ex){
                System.err.println("Error al abrir el video");
                JOptionPane.showMessageDialog(this, "No se pudo abrir el video", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_miAbrirVideoActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        VentanaInternaImagen vi = (VentanaInternaImagen)dEscritorio.getSelectedFrame();
        if (vi != null) {
            vi.getLienzo2D().setEstado(sm.mva.graficos.Estado.RectanguloR);
            vi.getLienzo2D().setEditar(false);
            vi.getLienzo2D().repaint();
            this.eEstado.setText("Estado seleccionado: Rectángulo Redondeado");
            vi.getLienzo2D().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed
    
    private BufferedImage getImageBand(BufferedImage img, int banda) {
        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,Transparency.OPAQUE,DataBuffer.TYPE_BYTE);
        //Creamos el nuevo raster a partir del raster de la imagen original
        int vband[] = {banda};
        WritableRaster bRaster = (WritableRaster) img.getRaster().createWritableChild(0, 0, img.getWidth(), img.getHeight(), 0, 0, vband);
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bRaster, false, null);
    }
    private LookupTable cuadratica(double m){
        double Max;
        if(m >= 128.0){
            Max = ((Math.pow(0.0-m,2.0))/100.0);
        }else{
            Max = ((Math.pow(255.0-m,2.0))/100.0);
        }
        
        double K = 255.0 / Max;
        byte lt[] = new byte[256];
            
        for (int l = 0; l < 256; l++) {
            lt[l] = (byte)(K*((Math.pow((float)l-m,2.0))/100.0));
        }
        
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }
    
    private LookupTable SplineLineaInflexion(double a, double b){
        double m = 0.0;
        //primero calculo m
        if(a!=255)
            m=(255.0-b)/(255.0-a);
        
        byte lt[] = new byte[256];
        for(int x = 0; x < 256; x++){
            if(x < a)
                lt[x] = (byte) ((b/a)*x);
            else
                lt[x] = (byte) (m*(x-a)+b);
        }
        
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        return slt;
    }
    
    private LookupTable negativo(){
        byte funcionT[] = new byte[256];
        for (int x = 0; x < 256; x++) {
            funcionT[x] = (byte) (255 - x); // Negativo
        }
        ByteLookupTable slt = new ByteLookupTable(0, funcionT);       
        return slt;
    }
    
    
    private void ganaFocus(){
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo2D().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo2D().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo2D().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }
    
    private void aplicarRescaleOp(RescaleOp rop){
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null && imgFuente != null) {
                try {
                    rop.filter(imgFuente, img);
                    this.dEscritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }
    
    private void aplicarEscalado(AffineTransform at){
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo2D().setImagen(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }
    private void aplicarLookup(LookupTable tabla){
        VentanaInternaImagen vi = (VentanaInternaImagen) (dEscritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImagen();
            if (img != null) {
                try {
                    LookupOp lop = new LookupOp(tabla, null);
                    lop.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }

            }
        }
    }
    
    private Kernel getKernel(int seleccion){
        Kernel k = null;
        switch(seleccion){
                case 0:
                    k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                    break;
                case 1:
                    k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                    break;
                case 2:
                    k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                    break;
                case 3:
                    k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);                    
                    break;
                case 4:
                    k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                    break;
                case 5:
                    // 1.0 / 25 = 0.04
                    float filtro[] = {0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                                       0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                                       0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                                       0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                                       0.04f, 0.04f, 0.04f, 0.04f, 0.04f};
                    k = new Kernel(5, 5, filtro);
                    break;
                case 6:
                    // 1.0 / 49 = 0.0204
                    float filtro2[] = {0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f,
                                       0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f, 0.0204f
                                       };
                    k = new Kernel(7, 7, filtro2);
                    break;
                case 7:
                    float filtro3[] = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
                    k = new Kernel(5, 1, filtro3);
                    break;
                case 8:
                    //1.0 / 7 = 0.1428;
                    float filtro4[] = {0.1428f, 0.1428f, 0.1428f, 0.1428f, 0.1428f, 0.1428f, 0.1428f};
                    k = new Kernel(7, 1, filtro4);
                    break;
                case 9:
                    float filtro5[] = {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f};
                    k = new Kernel(10, 1, filtro5);
                    break;
            }
        return k;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAbrir;
    private javax.swing.JToggleButton bAlisar;
    private javax.swing.JToggleButton bAmarillo;
    private javax.swing.JButton bAumentar;
    private javax.swing.JToggleButton bAzul;
    private javax.swing.JButton bBandas;
    private javax.swing.JButton bCamara;
    private javax.swing.JButton bCaptura;
    private javax.swing.JButton bCombinar;
    private javax.swing.JButton bContIlum;
    private javax.swing.JButton bContNormal;
    private javax.swing.JButton bContOsc;
    private javax.swing.JToggleButton bCuadratica;
    private javax.swing.JToggleButton bCurva;
    private javax.swing.JButton bDisminuir;
    private javax.swing.JButton bEcualizar;
    private javax.swing.JToggleButton bEditar;
    private javax.swing.JToggleButton bElipse;
    private javax.swing.JButton bGuardar;
    private javax.swing.JToolBar bHerramientas;
    private javax.swing.JToggleButton bLinea;
    private javax.swing.JButton bNegativo;
    private javax.swing.JToggleButton bNegro;
    private javax.swing.JButton bNuevo;
    private javax.swing.JButton bPausa;
    private javax.swing.JButton bPlay;
    private javax.swing.JButton bRecord;
    private javax.swing.JToggleButton bRectangulo;
    private javax.swing.JToggleButton bRellenar;
    private javax.swing.JToggleButton bRojo;
    private javax.swing.JButton bSepia;
    private javax.swing.JToggleButton bSmile;
    private javax.swing.JToggleButton bSpline;
    private javax.swing.JToggleButton bTintar;
    private javax.swing.JToggleButton bTonoRojo;
    private javax.swing.JToggleButton bTransp;
    private javax.swing.JToggleButton bTrazo;
    private javax.swing.JToggleButton bVerde;
    private javax.swing.JButton bVolcar;
    private javax.swing.JButton boton180;
    private javax.swing.JDesktopPane dEscritorio;
    private javax.swing.JLabel eCoordenadas;
    private javax.swing.JLabel eEstado;
    private javax.swing.JLabel eOtros;
    private javax.swing.JLabel eRGB;
    private javax.swing.ButtonGroup gColor;
    private javax.swing.ButtonGroup gForma;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JList<MiShape> lista;
    private javax.swing.JComboBox<File> listaReproduccion;
    private javax.swing.JMenu mArchivo;
    private javax.swing.JMenu mImagen;
    private javax.swing.JMenuBar mMenu;
    private javax.swing.JMenuItem miAbrir;
    private javax.swing.JMenuItem miAbrirAudio;
    private javax.swing.JMenuItem miAbrirVideo;
    private javax.swing.JMenuItem miAffineTransform;
    private javax.swing.JMenuItem miBandCombine;
    private javax.swing.JMenuItem miColorConvert;
    private javax.swing.JMenuItem miConvolve;
    private javax.swing.JMenuItem miDuplicar;
    private javax.swing.JMenuItem miGrabar;
    private javax.swing.JMenuItem miGuardar;
    private javax.swing.JMenuItem miLookupOp;
    private javax.swing.JMenuItem miNuevo;
    private javax.swing.JMenuItem miRescaleOp;
    private javax.swing.JPanel pBVolcar;
    private javax.swing.JPanel pBarraEstado;
    private javax.swing.JPanel pBarras;
    private javax.swing.JPanel pBrillo;
    private javax.swing.JPanel pColor;
    private javax.swing.JPanel pColores;
    private javax.swing.JPanel pFiltros;
    private javax.swing.JPanel pLista;
    private javax.swing.JPanel pRotEsc;
    private javax.swing.JPanel pSplineSliders;
    private javax.swing.JPanel pSur;
    private javax.swing.JPanel pTransformaciones;
    private javax.swing.JSpinner sGrosor;
    private javax.swing.JComboBox<String> seleccionEspaciosColores;
    private javax.swing.JComboBox<String> seleccionMascara;
    private javax.swing.JSlider sliderA;
    private javax.swing.JSlider sliderB;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderColores;
    private javax.swing.JSlider sliderContraste;
    private javax.swing.JSlider sliderCuadratica;
    private javax.swing.JSlider sliderMezcla;
    private javax.swing.JSlider sliderPosterizar;
    private javax.swing.JSlider sliderUmbral;
    // End of variables declaration//GEN-END:variables
}
