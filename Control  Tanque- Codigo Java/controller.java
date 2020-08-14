package controlPI;


      
import java.applet.Applet;//Elementos graficos,....es un objeto ventana
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;//arreglo de pixeles
import java.awt.image.BufferedImage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.util.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.net.*; 
import java.lang.*;
import javax.imageio.ImageIO;
import javax.swing.*;//INCLUYE EL PAQUETE GRAFICO/BIBLIOTECA CRAFICO javax.swing.JFrame


import javax.swing.JTextPane;
import javax.swing.ImageIcon;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BasicStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;




public class controller extends JApplet { //controller ES EL PROGRAMA PRINCIPAL
    public static ArrayList<OutputData> output_data = new ArrayList<OutputData>();
    public static int T = 1;//TIEMPO DE MUESTREO
    public static int T_log = 1;//TIEMPO DE LOGEO O TIEMPO DE HISTORIZACION
    public static double ref=0.0;
    public static int stateled;
    
	 public String host, miInURL,miOutURL;//VARIABLE TIPO HOST,...SI SE BORRA NO AFECTA EL PROGRAMA
	 public java.net.URL myServer = null;
    public java.net.URLConnection myConx = null;

    
    final int START = 1; // Starts operation (timer),....SON LOS BOTONES
    final int STOP = 2; // Stops operation (timer)

    final int SP = 4;//variables globales que se usan para todo el programa
    final int PB = 5;
    final int RESET = 6;
    //CREACION DE LAS VARIABLES GLOBALES
    final int ON = 7;
    final int OFF = 8;
    //
    final int NewVentana = 9;
    final int NewVentana2 = 10;
    final int Panel = 11;
    final int Planta = 12;
    final int BB = 13;//BOTON HISTORIAL
    final int IMA = 14;
    final int Picture = 15;
    

    public static String alarmam=" ";
    public static String state=" ";
    
    double level = 0.0; // Must be global,VARIABLES CON MAS PRECISION,32 BITS(DOUBLE,MEJOR RESOLUCION
    double valve_p = 0.0;
    double sp = 0.0; //seal de set point
    double pb = 0.0;
    double reset = 0.0;
    private static final long serialVersionUID = 1L;
    Timer timer = new Timer("ReadData");
    public static PI 		controlador = new PI();
    public static Planta 	planta  = new Planta();
    public static Log 		historial  = new Log();
    public static Log 		alarmas  = new Log();
    public static javax.swing.JTextArea Hist = new javax.swing.JTextArea(17,50);//(17, 50)TAMA DEL AREA DE TEXTO
   
   
    public static data_points points_xy = new data_points();
    public static Lectura lecturabinaria = new Lectura();
    public static Lectura2 lecturabinaria2 = new Lectura2();
    public static javax.swing.JTextPane Alarm = new javax.swing.JTextPane();
    
    public double valor2 = 0;
     
    public static javax.swing.JTextArea HistPanel = new javax.swing.JTextArea(20,50);
    
    public javax.swing.JPanel panelWindowN = new javax.swing.JPanel();
    public Graphics g = panelWindowN.getGraphics();
    
     
    public data_plot dataplot = new data_plot(panelWindowN);
    //VARIABLES ESTATICAS TIPO LABEL
    Image I;
    Image I2;

    
    static Label l1=new Label("SP"),l2=new Label("OUT"),l3=new Label("IN"),l4=new Label("ESTADO");
    static Label l1p = new Label("SP"),l2p = new Label("OUT"),l3p=new Label("IN"),l4p=new Label("ESTADO"); 
    
    
    java.awt.Container contenedor = getContentPane();
       
    public void init(){
        Toolkit tk = Toolkit.getDefaultToolkit();

        int xs = (int) tk.getScreenSize().getWidth() - 200; //probar con distintos valores,RESTA 200 PIXELES A CADA LADO
        int ys = (int) tk.getScreenSize().getHeight() - 200 ;
        System.out.println(xs + " " + ys);
        setSize(xs,ys);

        URL base;
        base = controller.class.getResource("/controlPI/image1.jpg");
                 
        I=getImage(controller.class.getResource("image1.jpg"));
        BackGroundPanel bgp = new BackGroundPanel();
        bgp.setLayout(null);
        bgp.setBackGroundImage(I);
        bgp.setBorder(javax.swing.BorderFactory.createTitledBorder("Image1"));
        
        //CREACION DE IMAGEN
        I2=getImage(controller.class.getResource("imagen3.jpg"));
          BackGroundPanel ima = new BackGroundPanel();
          ima.setLayout(null);
          ima.setBackGroundImage(I2);
          ima.setBorder(javax.swing.BorderFactory.createTitledBorder("Imagen3"));
        


        javax.swing.JPanel panelControl = new javax.swing.JPanel();
        panelControl.setBorder(javax.swing.BorderFactory.createTitledBorder("Control Del Panel"));//"ControlPanel"
        panelControl.setLayout( new FlowLayout());//LADO IZQUIERDO DE LA PANTALLA

        javax.swing.JButton startButton = new javax.swing.JButton("Start");
        
        TextField AAA = new TextField("AAASSSSSSSSSSSSSSSSSAA");

        javax.swing.JButton stopButton = new javax.swing.JButton("Stop");

        javax.swing.JLabel pidLabel = new javax.swing.JLabel(" || PID:");
        javax.swing.JButton pbButton = new javax.swing.JButton("PB");
        
        //CREACION BOTONES _ ON-OFF
        javax.swing.JButton onButton = new javax.swing.JButton("ON");
        javax.swing.JButton offButton = new javax.swing.JButton("OFF");
        javax.swing.JButton NewVentanaButton = new javax.swing.JButton("NewVentana");
        javax.swing.JButton PictureButton = new javax.swing.JButton("Picture");
        
        
        panelControl.add(startButton);
        panelControl.add(stopButton);
        
        panelControl.add(pidLabel);
        panelControl.add(pbButton);
         
         //
         panelControl.add(onButton);
         panelControl.add(offButton);
         //
         panelControl.add(NewVentanaButton);
         panelControl.add(PictureButton);
         
        
         


        startButton.addActionListener(new controller.ControlPanel(START));
        stopButton.addActionListener(new controller.ControlPanel(STOP));
        pbButton.addActionListener(new controller.ControlPanel(PB));
        
        
         //PARA EL ACCIONAMIENTO DEL BOTON
         onButton.addActionListener(new controller.ControlPanel(ON));
         offButton.addActionListener(new controller.ControlPanel(OFF));
         //
         NewVentanaButton.addActionListener(new controller.ControlPanel(NewVentana));
         PictureButton.addActionListener(new controller.ControlPanel(Picture)); //final int Picture = 15;


        contenedor.setLayout(new GridLayout(2,2));
        contenedor.add(panelControl);
//////////
        bgp.add(l1);
        bgp.add(l2);
        bgp.add(l3);
        
        //CREACION DEL BLOQUE_ESTADO LOGICO
        bgp.add(l4);
        l4.setLocation(20,288);
        l4.setSize(55,20);
        l4.setBackground(Color.orange);//COLOR DEL BLOQUE
        //
        
        //VISUALIZADORES
        l1.setLocation(670,48);//SP (678, 58)UBICACION D3 LAS V3NTANAS(VISUALIZADORES) DE INPUT,OUTPUT,CONTROLADOR
        l2.setLocation(670,78);//175,92(675,92)...PRACTICAR DE MANERA PERFECCIONISTA PARA SABER LA UBICACION EXACTA(3N EL C3NTRO POR 3JEMPLO)
        l3.setLocation(583,132);//(583,132)
        l1.setSize(40,18);
        l2.setSize(40,18);
        l3.setSize(40,18);
        //SOMBREO DE BLOQUES
        l1.setBackground(Color.white);
        l2.setBackground(Color.yellow);
        l3.setBackground(Color.pink);
        
        contenedor.add(bgp);
////////////
        javax.swing.JPanel panelHist = new javax.swing.JPanel();
        panelHist.setBorder(javax.swing.BorderFactory.createTitledBorder("Historia"));
        panelHist.setLayout( new FlowLayout());
        
        Hist.setEditable(false);
        Hist.setMargin(new Insets(5,5,5,5));
        
        
        JScrollPane logScrollPane = new JScrollPane(Hist);
        panelHist.add(logScrollPane);

        contenedor.add( panelHist );
        
/////Pantalla de Alarmas
        
        javax.swing.JPanel panelAlarm = new javax.swing.JPanel();
        panelAlarm.setBorder(javax.swing.BorderFactory.createTitledBorder("Alarmas"));
        panelAlarm.setLayout( new FlowLayout());
        
        
        
        Alarm.setEditable(false);
        Alarm.setMargin(new Insets(0,0,265,650));
        JScrollPane logScrollPaneA = new JScrollPane(Alarm);
        panelAlarm.add(logScrollPaneA);
 
        
        Alarm.setVisible(true);
        contenedor.add( panelAlarm );
        
        lecturabinaria = new Lectura();
        timer.schedule(lecturabinaria, 0, T*250);    
        
        lecturabinaria2 = new Lectura2();
        timer.schedule(lecturabinaria2, 0, T*250);

        }


    // Class to handle events on the control panel
    class ControlPanel implements java.awt.event.ActionListener  {
         FilePermission filePermission = new FilePermission("C:\\Users\\victo\\Desktop\\controlPI\\output.txt", "write");//"C:/Users/pc/Desktop/controlPI/output.tx"
         FilePermission filePermission2 = new FilePermission("C:\\Users\\victo\\Desktop\\controlPI\\input2.txt", "read");
        int buttonID;
        
        final double outs[] = {0.0, 0.0, 0.0};
    
            boolean bool1 = false, bool2 = false;
            boolean bool = true;
            int i=0;

        public double valor2;
        
        public ControlPanel(int  buttonID) {
            this.buttonID = buttonID;
        }

        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            
            
            
            switch (buttonID) {
            
            
            case START:    
            
                    // The name of the file to open.
                    String fileNameIn = "input.txt";
                    String fileNameIn2 = "input2.txt";
                    
                    String sp = null;
            
                    try {
                    
                        InputStream is = ControlPanel.class.getResourceAsStream(fileNameIn);

                        BufferedReader bufferedReaderIn = 
                            new BufferedReader(new InputStreamReader(is));
                                                                         
                        ref = 0.0;
                        
                        if((sp = bufferedReaderIn.readLine())!=null)
                            	ref = Double.valueOf(sp);   
                        System.out.format("\nSet point: %.2f\n",ref);
                        Hist.append(String.format("\nSet point: %.2f\n",ref));
                        HistPanel.append(String.format("\nSet point: %.2f\n",ref));
            
                        // Always close files.
                        bufferedReaderIn.close();         
                    }
                    catch(FileNotFoundException ex) {
                        System.out.println(
                            "Unable to open file '" + 
                            fileNameIn + "'");                
                    }
                    catch(IOException ex) {
                        System.out.println(
                            "Error reading file '" 
                            + fileNameIn + "'");    
                     }        
                    
                    // tareas
                	
                    controlador = new PI();
                    planta  = new Planta();
                    historial  = new Log();
                    
                    points_xy = new data_points();/////////
                    
                    
                    timer.schedule(planta, 0, T*1000);//TODOS LOS TIEMPOS ESTAN EN MILISEGUNDOS(POR ESO X1000)
                    timer.schedule(controlador, 0, T*1000);
                    timer.schedule(historial, 0, T_log*1000);
                    
                    timer.schedule(points_xy, 0, T*1000);///////////////          
                    
                    

    

                          
                   
            break;                       
                    
                    
           case STOP:
                    if (	planta.cancel() 	 &&
                            controlador.cancel() &&
                            historial.cancel() && 
                            points_xy.cancel() )
                    {
                        System.out.println("The Monitoring Process Timer was stopped!");
                        Hist.append("The Monitoring Process Timer was stopped!\n");
                        HistPanel.append("The Monitoring Process Timer was stopped!");
                        timer = new Timer("ReadData");
                    }else{
                        System.out.println("The Monitoring Process Timer was already stopped!");
                        Hist.append("The Monitoring Process Timer was already stopped!\n");
                        HistPanel.append("The Monitoring Process Timer was already stopped!");
                    }
             break;

             case PB:
                 
                String str = filePermission.getActions(); 
                 File myTestFile = new File("C:\\Users\\victo\\Desktop\\controlPI\\output.txt");
     
                 System.out.println(myTestFile.canWrite());
                 System.out.println("Actions with FP_obj1 : " + str);
              
                    
                 String fileNameOut = "output.txt";
                                     
                 try {
                     File file = new File("C:\\Users\\victo\\Desktop\\controlPI\\output.txt");
                     // creates the file
                     file.createNewFile();
                     FileWriter writer = new FileWriter(file); 
                     file.setWritable(true);
                 
                    for(int i=0;i<controller.output_data.size();++i){
                     
                            	OutputData data = controller.output_data.get(i);
                            	writer.write(String.format("%09f %09f %09f\n",data.t,data.u,data.yn));
                               writer.write("\r\n");                             
                              System.out.println(String.format("%.2f %.2f %.2f\n",data.t,data.u,data.yn));
                              writer.flush();
                              i++;
                       }      
                         System.out.println("\nLog data written in 'output' file.");
    
                     writer.close();
                     
                                 
                    }
                 catch(IOException ex1) {//SI ES QUE HAY ERROR
                     System.out.println(
                         "Error writing to file '"
                         + fileNameOut + "'");
                 } 
                 
                                        
                    
                    
            break;
                    
            case ON: 
            
                 bool1 = true;
                 l4.setBackground(Color.green);
                 l4p.setBackground(Color.green);
                 
                 l4.setText("1");
                 l4p.setText("1");
                                  
                 controller.state = controller.state+"1";
                 controller.alarmam=controller.alarmam + " Descripci�n:Motor , Estado:ON Tiempo: "  + (String) controller.getHoraActual() + "_horas"+'\n';
                 controller.appendToPane(Alarm,controller.alarmam ,0);

                 
                 break;
                 
            case OFF:
            
                 
                 bool1 = false;
                 l4.setBackground(Color.red);
                 l4p.setBackground(Color.red);
                 
                 l4.setText("0");
                 l4p.setText("0");
                 
                 controller.state = controller.state+"0";
                 controller.alarmam=controller.alarmam+" Descripci�n:Motor , Estado:OFF Tiempo: "  + (String) controller.getHoraActual() + "_horas"+'\n';
                 controller.appendToPane(Alarm,controller.alarmam ,0);
                 
                 break;
              
            case NewVentana:
            
    //CREACION DE VENTANA(v1)
        ventana v1 = new ventana();
        v1.setVisible(true);
                      
               javax.swing.JPanel panel1Control = new javax.swing.JPanel();
               panel1Control.setLayout( new BorderLayout());  
                                
               panelWindowN.setLayout( new FlowLayout());
               panelWindowN.setBackground(Color.pink);
               
               panel1Control.add(panelWindowN,BorderLayout.NORTH);
               
              
          //CREACION DE BOTONES DENTRO DEL PANEL DE CONTROL CREADO EN LA VENTANA
              javax.swing.JButton PanelButton = new javax.swing.JButton("Control Panel");
              panelWindowN.add(PanelButton);
             
              javax.swing.JButton PlantaButton = new javax.swing.JButton("Planta");
              panelWindowN.add(PlantaButton);
           
              javax.swing.JButton BBButton = new javax.swing.JButton("Historial");
              panelWindowN.add(BBButton);
              
              javax.swing.JButton IMAButton = new javax.swing.JButton("Grafica");
              panelWindowN.add(IMAButton);
              
              javax.swing.JPanel panelWindowC = new javax.swing.JPanel();
              panelWindowC.setLayout( new FlowLayout());  
              panelWindowC.setBackground(Color.yellow);
              
              panel1Control.add(panelWindowC,BorderLayout.CENTER);
                
              v1.add(panel1Control);
              
              ///
              JScrollPane logScrollPaneP = new JScrollPane(HistPanel);
              javax.swing.JPanel PanelP = new javax.swing.JPanel();
              ///
               PanelButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
               
                  panelWindowC.removeAll();
                  panel1Control.remove(logScrollPaneP);
                  panel1Control.remove(PanelP);
                  panel1Control.setBackground(Color.white);                    
                    //CREACION DEL BLOQUE_ESTADO LOGICO
                    panel1Control.add(l4p);
                    l4p.setBackground(Color.orange);    
                    l1p.setBackground(Color.white);
                    l2p.setBackground(Color.yellow);
                    l3p.setBackground(Color.pink);
                    BackGroundPanel bgp = new BackGroundPanel();
                    bgp.setBackGroundImage(I);
                    bgp.add(l1p);
                    bgp.add(l2p);
                    bgp.add(l3p);
                    bgp.add(l4p);
                    panel1Control.add(bgp,0);
                    panel1Control.revalidate();
                    panel1Control.repaint();
                  
                     if( bool1 == true ){
                        l4p.setBackground(Color.green);
                        l4p.setText("1");
                     }
                     else{
                        l4p.setBackground(Color.red);
                        l4p.setText("0");
                     } 
                  
                  }
               });
               

               
               
               PlantaButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 panelWindowC.removeAll();
                 panel1Control.remove(PanelP);
                 panel1Control.remove(logScrollPaneP);
                 panelWindowC.setBackground(Color.white);
                 panel1Control.setBackground(Color.white);      
                 bool2 = false;
                 BackGroundPanel bgp = new BackGroundPanel();
                 bgp.setBackGroundImage(I2);
                 panel1Control.add(bgp,0);
                 panel1Control.revalidate();
                 panel1Control.repaint();
                  }
               });
               
               
               
               BBButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 panelWindowC.removeAll();
                 panel1Control.remove(PanelP);
                 panel1Control.setBackground(Color.white);
                 HistPanel.setEditable(false);
                 HistPanel.setMargin(new Insets(5,5,5,5));
                 panel1Control.add(logScrollPaneP,0); 
                 panel1Control.revalidate();
                 panel1Control.repaint();
               }
               });
               
               IMAButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  System.out.println("Grafica");
                     
                      panelWindowC.removeAll();
                      panel1Control.remove(logScrollPaneP);
                      panel1Control.setBackground(Color.white);

                     int w = panelWindowN.getSize().width;
                     int h = panelWindowN.getSize().height;
                     
                     panelWindowC.setLayout(new BorderLayout());
                     
                     PanelP.setSize(150,150);
                     
                     PanelP.repaint();
                     panel1Control.add(PanelP,0);
                     panel1Control.revalidate();
                     panel1Control.repaint();          
                     dataplot = new data_plot(PanelP);
                     timer.schedule(dataplot,0,T*1000);  
                     
                     }
                  
               });
                              
                break;  
                
        case Picture :
   
          I2=getImage(controller.class.getResource("image2.jpg"));
          BackGroundPanel ima = new BackGroundPanel();
          ima.setLayout(null);
          ima.setBackGroundImage(I2);
          contenedor.add( ima );
                
        break; 
                    
            }
        }
    }


    public static String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
        return formateador.format(ahora);
    }
    
    public static void timea(){
      try{
         Thread.sleep(2500);
      }
      catch(Exception e2){
      }
    }
    
    
    
    
   private static void appendToPane(JTextPane tp, String msg, int  val)
       {
           int tam = msg.length();
           System.out.println(msg);
           StyleContext sc = new StyleContext();
           DefaultStyledDocument doc = new DefaultStyledDocument(sc);
           System.out.println(tam);
           tp.setDocument(doc);
           try {
               doc.insertString(0,msg,null);//imprime
   
           }catch (Exception ex) {
               System.out.println("Error: no se pudo establecer estilo de documento");
           }
           //estilos
           Style rojo = sc.addStyle("ConstantWidth", null);
           StyleConstants.setForeground(rojo, Color.red);
           Style verde = sc.addStyle("ConstantWidth", null);
           StyleConstants.setForeground(verde, Color.green);
           Style blanco = sc.addStyle("ConstantWidth", null);
           StyleConstants.setForeground(blanco, Color.white);
           //pintar
           int inicio_parcial=1,final_parcial=1,color_parcial=1;
           int cont=1;
           for(int i=0;i<tp.getDocument().getLength();i++){
               if(msg.charAt(i)=='\n'){
                  final_parcial=i;
                  if(controller.state.charAt(cont)=='1') {
                     doc.setCharacterAttributes(inicio_parcial,final_parcial, verde, true);
                  }
                  else {
                     color_parcial=1;
                     doc.setCharacterAttributes(inicio_parcial,final_parcial, rojo, true);
                  }
                  cont++;
                  inicio_parcial=final_parcial;
               }
           }
       }    
}


class data_plot extends TimerTask {
     private static double t,u,yn;
     private static int h = 474 , w = 471;
     private static int  pointx_ant = 20, pointy_ant = h-30;
     private static Punto2D pairxy0 = new Punto2D(pointx_ant,pointy_ant);
      
     private static ArrayList<Punto2D> listpairxy = new ArrayList<Punto2D>();
     private static boolean[] visitx = new boolean[w];
     private static boolean[] visity = new boolean[h];
     
     
     private JPanel panel = new JPanel();
     
     data_plot(JPanel _panel){
           this.panel = _panel;
       }
     
     public void run(){
        
        listpairxy = data_points.getCoorde();
        
        
        System.out.println(listpairxy.size());
        XYSeries scada = new XYSeries("Planta"); 
        
        for( int i = 0; i < listpairxy.size(); i++ ){
               scada.add(listpairxy.get(i).x,listpairxy.get(i).y);
        
        }
         
         XYSeriesCollection dataset = new XYSeriesCollection();
         dataset.addSeries(scada);
         JFreeChart chart_plot = ChartFactory.createXYLineChart(
                  "GRAFICA", "teimpo (S)", "y", dataset, PlotOrientation.VERTICAL, 
                  true, true, false   //false, false, false
                     
        );
                    
           XYPlot plot = chart_plot.getXYPlot();
           XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
           renderer.setSeriesPaint(0, Color.BLUE);
           renderer.setSeriesStroke(0, new BasicStroke(4.0f));
           plot.setRenderer(renderer);
           ChartPanel scadapanel = new ChartPanel(chart_plot);
           panel.removeAll();
           panel.revalidate();
           scadapanel.revalidate();
           panel.add(scadapanel);  
           
         
      }
    //sincronizacion de los subprocesos
    public synchronized static double getU(){
        return data_plot.u;
    }
    
    public synchronized static double getYn(){
        return data_plot.yn;
    }
    
    public synchronized static double getTime(){
        return data_plot.t;
    }
    
    public synchronized static ArrayList<Punto2D> getCoorde(){
        return data_plot.listpairxy;
    }
    
   
}

class data_points extends TimerTask {
      
     private static double t,u,yn;
     private static ArrayList<Punto2D> listpairxy = new ArrayList<Punto2D>();
    
     
     data_points(){
     }
     
     public void run(){

        yn = Planta.getYn();
        u = PI.getU();
        t = Planta.getTime();
        
        
            
            
        try {
         File datap = new File("C:\\Users\\victo\\Desktop\\controlPI\\file1.txt");
         datap.createNewFile();
         FileWriter writerp = new FileWriter(datap); 
         datap.setWritable(true);
         
         //writerp.write(Double.toString(t) + " " + Double.toString(u) + " " + Double.toString(yn) );       
         writerp.write(String.format("%09f %09f %09f\n",data_points.t,data_points.u,data_points.yn));
         System.out.println("\nESCRIBIR Datos.");
         writerp.close();
         }
         catch(IOException ex) {//SI ES QUE HAY ERROR
               System.out.println("Error writing to file ");    
           }
           
           
           
           
      }
    
    public synchronized static double getU(){
        return data_points.u;
    }
    
    public synchronized static double getYn(){
        return data_points.yn;
    }
    
    public synchronized static double getTime(){
        return data_points.t;
    }
    
    public synchronized static ArrayList<Punto2D> getCoorde(){
        return data_points.listpairxy;
    }       
   
    
}

class Punto2D{
    public double x,y;
    Punto2D(double _x,double _y){
        this.x = _x;
        this.y = _y;
    }
}

class PI extends TimerTask{//CONTROLADOR
    static int     times=0;
    static double  Yn;  // Referencia
    private static double  u=0.0, u_MAX=100.0, u_MIN=0.0;
    private static double  En, En_1=0.0, CE=0.0;
    static double  Kc = 3.07873122784567, tauI=27.07012858865571;
    static double  t=0.0;
    static int     T_sec = controller.T;
    public static int stateled;


    PI(){
    }

    public void run(){
        System.out.format("Ejecutando PI (%d) a horas %s\n",times++,controller.getHoraActual());
        controller.l1.setText(Double.toString(controller.ref));
        controller.l1p.setText(Double.toString(controller.ref));
        controller.Hist.append(String.format("Ejecutando PI (%d) a horas %s\n",times++,controller.getHoraActual()));
        controller.HistPanel.append(String.format("Ejecutando PI (%d) a horas %s\n",times-1,controller.getHoraActual()));
        // start time
        long startTime = System.currentTimeMillis();

        // e(k) = r(k) - y(k)
        En = controller.ref - Planta.getYn();

        updateU(En);

        //update values
        CE = En - En_1;
        En_1 = En;

        // generate cpu usage
        for(int i=0;i<10000000;++i);

        // end time
        long endTime = System.currentTimeMillis();

        double intervalTicks = (endTime - startTime)*1000.0;
        System.out.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks);
        controller.Hist.append(String.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks));
        controller.HistPanel.append(String.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks));
        
        double cpu_usage = intervalTicks * 100 / (T_sec*1000);
        System.out.format("   CPU Usage (PI): %.2f\n\n",cpu_usage);
        controller.Hist.append(String.format("   CPU Usage (PI): %.2f\n\n",cpu_usage));
        controller.HistPanel.append(String.format("   CPU Usage (PI): %.2f\n\n",cpu_usage));
        
        //System.out.format(String.format("%d ",stateled));
    }

    public synchronized void updateU(double En){
        // u(k) = u(k-1) + Kc*[e(k)-e(k-1)] + Kc*T/ti*e(k)
        u = u + Kc*CE + Kc*T_sec/tauI*En;//LEY DE CONTROL

        // saturadores
        u = Math.max(u_MIN,u);//OJO--->>>EL ACTUADOR NO DA MAS DE 100%, NI MENOS DE 0%
        u = Math.min(u_MAX,u);
    }

    public synchronized static double getU(){
        return PI.u;
    }
}

class Planta extends TimerTask {
    static int     times=0;
    private static double	      xn, xn_1, yyn_1, yyn;
    private static double	      Kp=0.81, tau=56.991;    // Process gain and constant timeprivate static double   t=0.0;         //  initial time (sec)
    private static double   t=0.0;         //  initial time (sec)
    private static double   Xn, Xn_1, Yn, Yn_1;   // n: Curent time, n_1: Previous past time
    private static int      T = controller.T; // Sampling time, s
    public double valor2;
    Planta(){
    }

    public void run(){
        System.out.format("Ejecutando Planta (%d) a horas %s\n",times++,controller.getHoraActual());
        controller.Hist.append(String.format("Ejecutando Planta (%d) a horas %s\n",times++,controller.getHoraActual()));
        controller.HistPanel.append(String.format("Ejecutando Planta (%d) a horas %s\n",times-1,controller.getHoraActual()));

        // start time
        long startTime = System.currentTimeMillis();

        // Planta
        xn = PI.getU();
        Xn =  xn - xn_1;
        updateYn(Xn);
        controller.l2.setText(Double.toString(getYn()));
        controller.l2p.setText(Double.toString(getYn()));
        yyn = yyn_1 + Yn;

        //update values
        yyn = yyn_1;
        Yn_1 = Yn;    //  xn = xn_1;    // when online
        t += T;

        // generate cpu usage
        for(int i=0;i<15000000;++i);

        // end time
        long endTime = System.currentTimeMillis();

        double intervalTicks = (endTime - startTime)*1000.0;
        System.out.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks);
        controller.Hist.append(String.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks));
        controller.HistPanel.append(String.format("   Execution time (PI): %.2f milisegundos\n",intervalTicks));
        
        double cpu_usage = intervalTicks * 100 / (T*1000);
        System.out.format("   CPU Usage (PI): %.2f\n\n",cpu_usage);
        controller.Hist.append(String.format("   CPU Usage (PI): %.2f\n\n",cpu_usage));
        controller.HistPanel.append(String.format("   CPU Usage (PI): %.2f\n\n",cpu_usage));
    }

    public synchronized void updateYn(double Xn){
        Yn = (tau*Yn_1 + Kp*T*Xn)/(tau + T);	
    }

    public synchronized static double getYn(){
        return Planta.Yn;
    }
    
    public synchronized static double getTime(){
        return Planta.t;
    }    
    
}

class Log extends TimerTask {
    private double  yn, u;
    private static double  t = 0.0;
    public int stateled;
    private int T_sec=controller.T;
    public double valor2;  
    Log(){}

    public void run(){
        yn = Planta.getYn();
        u = PI.getU();
        OutputData data = new OutputData(t,u,yn);
        controller.output_data.add(data);
        controller.l3.setText(Double.toString(u));
        controller.l3p.setText(Double.toString(u));

        System.out.format("Ejecutando Historial a horas %s\n",controller.getHoraActual());
        controller.Hist.append(String.format("Ejecutando Historial a horas %s\n",controller.getHoraActual()));
        controller.HistPanel.append(String.format("Ejecutando Historial a horas %s\n",controller.getHoraActual()));
        
        System.out.format("   t: %.4f | u: %.2f | yn: %.2f\n",t,u,yn);
        System.out.format("\naaaaaaaaaaaaaaaaaaaaaa %f \n",valor2);
        
        System.out.format("/nESTADO DEL LED %d",stateled);
        controller.Hist.append(String.format("   t: %.4f | u: %.2f | yn: %.2f\n",t,u,yn));
        controller.HistPanel.append(String.format("   t: %.4f | u: %.2f | yn: %.2f\n",t,u,yn));
        
        t += T_sec;
    }
}

class OutputData{
    public double t,u,yn;
    OutputData(double _t,double _u,double _yn){
        this.t = _t;
        this.u = _u;
        this.yn = _yn;
    }
}

class Lectura extends TimerTask{

    Lectura(){
    }

    public void run(){
        System.out.println("LEYENDO1111111111...");
        
        String sp = null;
        
        try {
            String fileNameIn2 = "input2.txt";
            InputStream is = Lectura.class.getResourceAsStream(fileNameIn2);
            BufferedReader bufferedReaderIn = new BufferedReader(new InputStreamReader(is));
                
            double InputBinario = 0.0;
            double valor = 0.0;                       
            
            if((sp = bufferedReaderIn.readLine())!=null){
                valor = Double.valueOf(sp);
                if(valor == 1){
                    controller.l4.setBackground(Color.green);
                    controller.l4.setText("1");
                    controller.l4p.setBackground(Color.green);
                    controller.l4p.setText("1");
                }else if(valor == 0){
                    controller.l4.setBackground(Color.red);
                    controller.l4.setText("0");
                    controller.l4p.setBackground(Color.red);
                    controller.l4p.setText("0");
                }
            } 
            
            // Always close files.
            bufferedReaderIn.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("error1");                
        }
        catch(IOException ex) {
            System.out.println("error2");
        }     
        
    }
}


class BackGroundPanel extends JPanel {
        Image backGround;

        BackGroundPanel() {
            super();
        }

        public void paint(Graphics g) {

            // get the size of this panel (which is the size of the applet),
            // and draw the image
            g.drawImage(getBackGroundImage(), 0, 0,
                    (int) getBounds().getWidth(), (int) getBounds().getHeight(), this);
        }

        public void setBackGroundImage(Image backGround) {
            this.backGround = backGround;
        }

        private Image getBackGroundImage() {
            return backGround;
        }
}
    
    
  //PARA CREACION VENTANA EN BLANCO 
 class ventana extends JFrame{
   public ventana(){
     this.setTitle("BARRA DE MENU");
     this.setSize(700,500);//tamao de la ventana
     this.setResizable(false); 
   }
   
 }
  
