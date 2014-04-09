package jframeexamen;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import JFrameExamen.SoundClip;
import java.util.LinkedList;

public class JFrameExamen extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // Se declaran las variables. 
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private SoundClip guile;    // Musica de fondo del juego
    private SoundClip tazdingo;
    private SoundClip coin;
    private int contbloques;    // Contador de bloques destruidos
    private Ball bola;          // Objeto bola.
    private Bloque pill;        // Objeto Bloque usado para inicializar las listas 1 y 3
    private BloqueR pillR;      // Objeto BloqueR usado para inicializar las listas 2 y 4
    private Barra1 bar;         // Objeto barra, es el movido por el jugador.
    private boolean musicafondo;// Boolean utilizado para correr o pausar la música de fondo
    private int vidas;          // Contador de vidas
    private Image game_over;    // Imagen de victoria
    private Image perder;       // Imagen de derrota
    private LinkedList<Image> listafondo;       // Imagen usada para la pausa
    private int direccion;      // Variable para la dirección del personaje
    private int score;          // Variable de puntuacion
    private boolean move;       // Variable utilizada para saber si el personaje se esta moviendo o no
    private boolean pausa;      // Booleano para pausar
    private boolean moverbola;  // Booleano que indica si la bola se esta moviendo
    private boolean instrucciones; // Booleano indicado para saber si se estan mostrando las instrucciones
    private boolean empezar;    // Booleano para comenzar el juego y quitar la pantalla de inicio
    private LinkedList<Bloque> lista; // Listas de bloques
    private LinkedList<BloqueR> lista2;
    private LinkedList<Bloque> lista3;
    private LinkedList<BloqueR> lista4;
    private Image fondo;        // Imagen de fondo
    private Image inicio;       // Imagen de inicio

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
        URL goURL = this.getClass().getResource("fondos/story1WB.png");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);
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

            if (!pausa && empezar) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
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
     * Metodo <I>keypPressed</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se actualiza la variable de dirección dependiendo de la
     * tecla que haya sido precionado El parámetro e se usará cpara obtener la
     * acción de la tecla que fue presionada.
     *
     */
    public void keyPressed(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la clase
     * <code>KeyEvent</code>.<P>
     * En este método se verifica si alguna tecla que haya sido presionada es
     * liberada. Si es liberada la booleana que controla el movimiento se
     * convierte en falsa.
     */
    public void keyReleased(KeyEvent e) {
        move = false;
        bar.setMoviendose(false);
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
    }

    /**
     * Metodo <I>MouseReleased</I> sobrescrito de la clase
     * <code>MouseEvent</code>.<P>
     * En este método se verifica si el click del mouse ha sido liberado, si sí
     * entonces la booleana que l ocontrola se hace falsa, para marcar que ya no
     * está siendo presionadao.
     */
    public void mouseReleased(MouseEvent e) {
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
     * @paramg es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        if (!empezar && inicio != null) {
            g.drawImage(inicio, 0, 0, this);
        } else if (vidas > 0) {
            g.drawImage(fondo, 0, 0, this);

                g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);//Pinta la bola
                g.drawImage(bar.getImagenI(), bar.getPosX(), bar.getPosY(), this);  //Pinta la Barra

                //    if (pausa) {
                //        g.setColor(Color.white);
                //        g.drawString(pill.getPausado(), pill.getPosX() + pill.getAncho() / 3, pill.getPosY() + pill.getAlto() / 2);
                //    }
                //if (pausa) {//Al pausar se despliega una imágen de pausa.
                //    g.drawImage(pause, 0, 20, this);
                //}
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        }

        }
    


