package jframeexamen;

import JFrameExamen.SoundClip;

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
    private Image pause;        // Imagen usada para la pausa
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
        //acabarjuego = false;
        contbloques = 0;//oli
        lista = new LinkedList();
        lista2 = new LinkedList();
        lista3 = new LinkedList();
        lista4 = new LinkedList();
        pausa = false;
        move = false;
        moverbola = false;
        musicafondo = false;
        direccion = 0;
        score = 0;                    //puntaje inicial
        vidas = 3;                    //vidaas iniciales
        guile = new SoundClip("sounds/guile.wav");
        tazdingo = new SoundClip("sounds/tazdingo.wav");
        coin = new SoundClip("sounds/coin.wav");
        bar = new Barra1(getWidth() / 2, getHeight() - 30);
        bar.setPosX(getWidth() / 2 - bar.getAncho() / 2);
        setBackground(Color.black);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        bola = new Ball(bar.getPosX() + 25, bar.getPosY() - 25);
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pill = new Bloque(40, 60);
                lista.add(pill);
            } else {
                Bloque pillaux = (Bloque) lista.get(i - 2);
                pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                lista.add(pill);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pillR = new BloqueR(40, 120);
                lista2.add(pillR);
            } else {
                BloqueR pillaux = (BloqueR) lista2.get(i - 2);
                pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                lista2.add(pillR);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pill = new Bloque(40, 180);
                lista3.add(pill);
            } else {
                Bloque pillaux = (Bloque) lista3.get(i - 2);
                pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                lista3.add(pill);
            }

        }
        for (int i = 1; i < 15; i++) {
            if (i == 1) {
                pillR = new BloqueR(40, 240);
                lista4.add(pillR);
            } else {
                BloqueR pillaux = (BloqueR) lista4.get(i - 2);
                pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                lista4.add(pillR);
            }

        }

        URL goURL = this.getClass().getResource("barra/creditos.png");
        game_over = Toolkit.getDefaultToolkit().getImage(goURL);
        URL fURL = this.getClass().getResource("Fondo/FondoDos.jpg");
        fondo = Toolkit.getDefaultToolkit().getImage(fURL).getScaledInstance(getWidth(), getHeight(), 1);
        URL aURL = this.getClass().getResource("pill/gameover.jpg");
        perder = Toolkit.getDefaultToolkit().getImage(aURL);
        URL gURL = this.getClass().getResource("pill/imagenpausa.jpg");
        pause = Toolkit.getDefaultToolkit().getImage(gURL);

        instrucciones = false;
        empezar = false;
        URL emp = this.getClass().getResource("barra/Login.png");
        inicio = Toolkit.getDefaultToolkit().getImage(emp);

    }

    /**
     * Metodo <I>Start</I> sobrescrito de la clase <code>Thread</code>.<P>
     * Este metodo comienza la ejecucion del hilo. Esto llama al metodo
     * <code>run</code>
     */
    public void start() {
        // Declaras un hilo
        guile.setLooping(true);
        guile.play();
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

        while (true) {
            if (musicafondo) {
                guile.stop();
                guile.setLooping(false);
            } else {
                if (!guile.getLooping()) {
                    guile.setLooping(true);
                    guile.play();
                }
            }
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
    }

    /**
     * Metodo <I>actualiza</I>.
     * <P>
     * En este metodo se actualizan las posiciones de link como de la armadura,
     * ya sea por presionar una tecla o por moverlos con el mouse.
     */
    public void actualiza() {
        if (move) {
            bar.setMoviendose(true);
            switch (direccion) {
                case 3: {
                    bar.setPosX(bar.getPosX() - 6);
                    break; //se mueve hacia la izquierda
                }
                case 4: {

                    bar.setPosX(bar.getPosX() + 6);
                    break; //se mueve hacia la derecha
                }
            }
        }
        if (moverbola) {
            bola.setPosX(bola.getPosX() + bola.getVelX());
            bola.setPosY(bola.getPosY() + bola.getVelY());
        } else {
            bola.setPosX(bar.getPosX() + 20);
            bola.setPosY(bar.getPosY() - 30);
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto link con el objeto
     * armadura y además con las orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (bar.getPosX() + bar.getAncho() > getWidth()) {
            bar.setPosX(getWidth() - bar.getAncho());
        }
        if (bar.getPosX() < 0) {
            bar.setPosX(0);
        }
        for (Bloque i : lista) {
            if (bola.intersecta(i) && !i.getChoca()) {
                i.setChoca(true);
                if (i.getPosY() < bola.getPosY() + bola.getAlto() || i.getPosY() + i.getAlto() > bola.getPosY()) { //por arriba o por abajo
                    bola.setVelY(-bola.getVelY());
                } else {                                                                                           //por la izquierda o la derecha
                    bola.setVelX(-bola.getVelX());
                }
                i.addGolpe();
                if (i.getGolpes() == 3) {
                    lista.remove(i);
                    contbloques++;
                    score += 20;
                    if (!musicafondo) {
                        tazdingo.play();
                    }
                    break;
                }
                if (!musicafondo) {
                    coin.play();
                }
                i.cambiaimagen(i.getGolpes());
                score += 10;
            } else {
                i.setChoca(false);
            }
        }
        for (BloqueR i : lista2) {
            if (bola.intersecta(i) && !i.getChoca()) {
                i.setChoca(true);
                if (i.getPosY() < bola.getPosY() + bola.getAlto() || i.getPosY() + i.getAlto() > bola.getPosY()) { //por arriba o por abajo
                    bola.setVelY(-bola.getVelY());
                } else {                                                                                           //por la izquierda o la derecha
                    bola.setVelX(-bola.getVelX());
                }
                i.addGolpe();
                if (i.getGolpes() == 3) {
                    contbloques++;
                    lista2.remove(i);
                    score += 20;
                    if (!musicafondo) {
                        tazdingo.play();
                    }
                    break;
                }
                if (!musicafondo) {
                    coin.play();
                }
                i.cambiaimagen(i.getGolpes());
                score += 10;
            } else if (!bola.intersecta(i)) {
                i.setChoca(false);
            }
        }
        for (Bloque i : lista3) {
            if (bola.intersecta(i) && !i.getChoca()) {
                i.setChoca(true);
                if (i.getPosY() < bola.getPosY() + bola.getAlto() || i.getPosY() + i.getAlto() > bola.getPosY()) { //por arriba o por abajo
                    bola.setVelY(-bola.getVelY());

                } else if (!bola.intersecta(i)) {                                                                                           //por la izquierda o la derecha
                    bola.setVelX(-bola.getVelX());
                }
                i.addGolpe();
                if (i.getGolpes() == 3) {
                    contbloques++;
                    lista3.remove(i);
                    if (!musicafondo) {
                        tazdingo.play();
                    }
                    score += 20;
                    break;
                }
                if (!musicafondo) {
                    coin.play();
                }
                score += 10;
                i.cambiaimagen(i.getGolpes());
            } else {
                i.setChoca(false);
            }
        }
        for (BloqueR i : lista4) {
            if (bola.intersecta(i) && !i.getChoca()) {
                i.setChoca(true);
                if (i.getPosY() < bola.getPosY() + bola.getAlto() || i.getPosY() + i.getAlto() > bola.getPosY()) { //por arriba o por abajo
                    bola.setVelY(-bola.getVelY());

                } else {                                                                                           //por la izquierda o la derecha
                    bola.setVelX(-bola.getVelX());
                }
                i.addGolpe();
                if (i.getGolpes() == 3) {
                    contbloques++;
                    lista4.remove(i);
                    score += 20;
                    if (!musicafondo) {
                        tazdingo.play();
                    }
                    break;
                }
                if (!musicafondo) {
                    coin.play();
                }
                score += 10;
                i.cambiaimagen(i.getGolpes());
            } else if (!bola.intersecta(i)) {
                i.setChoca(false);
            }
        }

        if (bola.intersecta(bar)) {
            if (bola.getPosY() + bola.getAlto() / 2 < bar.getPosY() + bar.getAlto() / 2) {
                bola.setVelY(-bola.getVelY());
            }

            if (bola.getPosX() + bola.getAncho() / 2 > bar.getPosX() + bar.getAncho() / 2 && bola.getVelX() < 0) {
                bola.setVelX(-bola.getVelX());
            } else if (bola.getPosX() + bola.getAncho() / 2 < bar.getPosX() + bar.getAncho() / 2 && bola.getVelX() > 0) {
                bola.setVelX(-bola.getVelX());
            }

        }

        if (bola.getPosX() < 5) {
            bola.setVelX(-bola.getVelX());
        } else if (bola.getPosY() < 20) {
            bola.setVelY(-bola.getVelY());
        } else if (bola.getPosX() + bola.getAncho() > getWidth()) {
            bola.setVelX(-bola.getVelX());
        } else if (bola.getPosY() > getHeight()) {
            vidas--;
            moverbola = false;
            bola.setPosX(bar.getPosX() + 20);
            bola.setPosY(bar.getPosY() - 30);
            bola.setVelX(0);
            while (bola.getVelX() == 0) {
                bola.setVelX((int) (Math.random() * 10) - 5);
            }
            bola.setVelY(-4);
        }
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

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {//Al presionar la flecha izquierda se mueve a la izquierda
            direccion = 3;
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 4; //Al presionar la flecha derecha se mueve la barra a la derecha
            move = true;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {//Al presionar la P activa la Pausa del juego
            musicafondo = true;
            pausa = !pausa;
        } else if (e.getKeyCode() == KeyEvent.VK_E && !empezar) {//Al presionar la tecla E se empieza el juego
            empezar = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S && !pausa) { //Al presionar la tecla S se apaga/prende el sonido
            musicafondo = !musicafondo;

        } else if (e.getKeyCode() == KeyEvent.VK_I) { //Al presionar la tecla I se muestran/quitan las Instrucciones
            instrucciones = !instrucciones;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { //Al presionar la barra espaciadora lanza la pelota.
            if (!moverbola && empezar) {
                moverbola = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) { //Al presionar la tecla R reinicia el juego.
            musicafondo = false;
            pausa = false;
            moverbola =false;
            bar.setPosX(getWidth() / 2);
            bar.setPosY(getHeight() - 30);
            bola.setPosX(bar.getPosX() + 25);
            bola.setPosY(bar.getPosY() - 25);
            contbloques = 0;
            vidas = 3;
            score = 0;
            lista.clear();
            lista2.clear();
            lista3.clear();
            lista4.clear();
            for (int i = 1; i < 15; i++) {
                if (i == 1) {
                    pill = new Bloque(40, 70);
                    lista.add(pill);
                } else {
                    Bloque pillaux = (Bloque) lista.get(i - 2);
                    pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                    lista.add(pill);
                }

            }
            for (int i = 1; i < 15; i++) {
                if (i == 1) {
                    pillR = new BloqueR(40, 120);
                    lista2.add(pillR);
                } else {
                    BloqueR pillaux = (BloqueR) lista2.get(i - 2);
                    pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                    lista2.add(pillR);
                }

            }
            for (int i = 1; i < 15; i++) {
                if (i == 1) {
                    pill = new Bloque(40, 170);
                    lista3.add(pill);
                } else {
                    Bloque pillaux = (Bloque) lista3.get(i - 2);
                    pill = new Bloque(pillaux.getPosX() + 50, pillaux.getPosY());
                    lista3.add(pill);
                }

            }
            for (int i = 1; i < 15; i++) {
                if (i == 1) {
                    pillR = new BloqueR(40, 220);
                    lista4.add(pillR);
                } else {
                    BloqueR pillaux = (BloqueR) lista4.get(i - 2);
                    pillR = new BloqueR(pillaux.getPosX() + 50, pillaux.getPosY());
                    lista4.add(pillR);
                }

            }
        }
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
            if (lista != null && bar != null) {
                //Se Pintan todas las pildoras del juego
                for (Bloque i : lista) {
                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (BloqueR i : lista2) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (Bloque i : lista3) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }
                for (BloqueR i : lista4) {

                    g.drawImage(i.getImagenI(), i.getPosX(), i.getPosY(), this);
                }

                g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);//Pinta la bola
                g.drawImage(bar.getImagenI(), bar.getPosX(), bar.getPosY(), this);  //Pinta la Barra

                g.setColor(Color.black);//Despliega los puntos, las vidas y el comando de Instrucciones
                g.drawString("Puntos = " + score, 20, 50);
                g.drawString("Vidas = " + vidas, 20, 70);
                g.drawString("Presiona I para ver instrucciones.", getWidth() - 200, 50);
                g.drawString("Bloques destruidos: " + contbloques, 20, 90);
                //    if (pausa) {
                //        g.setColor(Color.white);
                //        g.drawString(pill.getPausado(), pill.getPosX() + pill.getAncho() / 3, pill.getPosY() + pill.getAlto() / 2);
                //    }
                if (pausa) {//Al pausar se despliega una imágen de pausa.
                    g.drawImage(pause, 0, 20, this);
                }
                if (instrucciones) {//Al presionar la I se muestran las instrucciones
                    g.drawString("Instrucciones:", 20, 90);
                    g.drawString("Para mover la barra, presiona las teclas de flecha izquierda o derecha.", 20, 110);
                    g.drawString("Presiona R para reiniciar la partida, P para pausar y S para detener la musica.", 20, 130);
                    g.drawString("Si la pastilla cae 3 veces pierdes el juego.", 20, 150);
                    g.drawString("La pastilla tiene que golpear tres veces a cada bloque para destruirlos.", 20, 170);
                    g.drawString("Ganas el juego al haber destruido todos los bloques.", 20, 190);
                }

            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }

        } else {
             g.drawImage(perder, 140, 20, this); //Cuando pierdes se despliega la pantalla de perder
             g.setColor(Color.white);
             g.drawString("Tu puntaje fue de: "+score,getWidth()/3+50,100);
        }
        if (contbloques >= 56) { //Cuando ganas se despliega la pantalla de creditos
            //acabarjuego = true;
            musicafondo = true;
            g.drawImage(game_over, 0, 20, this);

        }
    }

}
