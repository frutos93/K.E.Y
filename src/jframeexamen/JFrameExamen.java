package jframeexamen;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class JFrameExamen extends JFrame implements Runnable, MouseListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private Image imagenVisible;       // Imagen de derrota
    private ArrayList<Image> listaFondo;       // Imagen usada para la pausa
    private Image inicio;       // Imagen de inicio
    private Image imagen2;
    private Image imagen3;
    private Image imagen4;
    private Image imagen5;
    private Image imagen6;
    private Image imagen7;
    private  int posImagen;

    /**
     * Constructor vacio de la clase <code>JFrameExamen</code>.
     */
    public JFrameExamen() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        setSize(800, 500);
        addMouseListener(this);
        listaFondo = new ArrayList<>();
        URL goURL = this.getClass().getResource("fondos/story1WB.png");
        inicio = Toolkit.getDefaultToolkit().getImage(goURL);
        listaFondo.add(inicio);
        
        URL goURL2 = this.getClass().getResource("fondos/screen2.png");
        imagen2 = Toolkit.getDefaultToolkit().getImage(goURL2);
        listaFondo.add(imagen2);
        
        URL goURL3 = this.getClass().getResource("fondos/screen3.png");
        imagen3 = Toolkit.getDefaultToolkit().getImage(goURL3);
        listaFondo.add(imagen3);
        
        URL goURL4 = this.getClass().getResource("fondos/screen4.png");
        imagen4 = Toolkit.getDefaultToolkit().getImage(goURL4);
        listaFondo.add(imagen4);
        
        URL goURL5 = this.getClass().getResource("fondos/screen5.png");
        imagen5 = Toolkit.getDefaultToolkit().getImage(goURL5);
        listaFondo.add(imagen5);
        
        URL goURL6 = this.getClass().getResource("fondos/screen6.png");
        imagen6 = Toolkit.getDefaultToolkit().getImage(goURL6);
        listaFondo.add(imagen6);
        
        URL goURL7 = this.getClass().getResource("fondos/screen7.png");
        imagen7 = Toolkit.getDefaultToolkit().getImage(goURL7);
        listaFondo.add(imagen7);
        
        posImagen = 0;
        imagenVisible = listaFondo.get(posImagen);
    }

    /**
     * Metodo <I>Start</I> sobrescrito de la clase <code>Thread</code>.<P>
     * Este metodo comienza la ejecucion del hilo. Esto llama al metodo
     * <code>run</code>
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {

            //actualiza();    
        //checaColision();
        while(true){
        repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
        try {
            // El thread se duerme.
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            System.out.println("Error en " + ex.toString());
        }
        }
    }

    /**
     * Metodo <I>actualiza</I>.
     * <P>
     * En este metodo se actualizan las posiciones de link como de la armadura,
     * ya sea por presionar una tecla o por moverlos con el mouse.
     */
    public void actualiza() {

    }

    /**
     * Metodo usado para checar las colisiones del objeto link con el objeto
     * armadura y además con las orillas del <code>Applet</code>.
     */
    public void checaColision() {
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la clase
     * <code>MouseEvent</code>.
     * <P>
     * Este metodo es invocado cuando se ha presionado un boton del mouse en un
     * componente.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * Metodo <I>MousePressed</I> sobrescrito de la clase
     * <code>mouseEvent</code>.<P>
     * En este metodo se verifica si el mouse ha dado click sobre la imágen. Al
     * verificar que haya dado un click se actualizan las coordenadas de 'x' y
     * 'y' para ajustar el desfase que puede tener la imagen con el click
     */
    public void mousePressed(MouseEvent e) {
        imagenVisible = (Image)listaFondo.get(posImagen);
        
        posImagen++;

        if (posImagen >= listaFondo.size()) {
            posImagen = 0;
            imagenVisible =(Image) listaFondo.get(posImagen);
        }
        System.out.print(posImagen);

    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando el cursor es movido dentro de un
     * componente sin presionar ningun boton.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * Este metodo es invocado cuando se presiona un boton en un componente, y
     * luego este es arrastrado.
     *
     * @param e es el evento generado al ocurrir lo descrito.
     */
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (imagenVisible != null) {
            g.drawImage((Image)listaFondo.get(posImagen), 0, 0, this);
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }

    }

    /**
     * Metodo <I>MouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * En este método se verifica si el click del mouse ha sido liberado, si sí
     * entonces la booleana que l ocontrola se hace falsa, para marcar que ya no
     * está siendo presionadao.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
    }

}
