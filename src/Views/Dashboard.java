/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;


import hatedashboard.Spark;

import java.awt.*;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.swing.*; 
import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
/**
 *
 * @author jesus
 */
public class Dashboard extends javax.swing.JFrame {
       
  
    //Spark sp = new Spark();
   
    /**
     * Creates new form Home
     */
    public Dashboard() {
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
         setLocationTable();
        setTweetsTable();        
    }
    
    
    public void setReadTweets(){
        jLabelTweets.setText("");//Acá poner función lectura de líneas CSV
        
    }
    
    public void setLocationTable(){
        
        DefaultTableModel csv_data = new DefaultTableModel();
       
//        jPanel1.setLayout(new BorderLayout());
        
        
        //jPanel1.add(new PieChart("Ubicación", "Ubicación Tweets de Hate"));
    
    
    }
    
    public void setTweetsTable(){
    
          try{
//        CSVReader reader = new CSVReader(new FileReader("docs\\result.csv"));
//        String[] nextline;
//        
//        List myEntries = reader.readAll();
        
        File csv_file=new File("docs\\result.csv");     
        DefaultTableModel csv_data = new DefaultTableModel();
        
        try{

            int start=0;
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));
            CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
            for(CSVRecord csvRecord:csvParser){
                if(start==0){
                    start=1;
                    csv_data.addColumn(csvRecord.get(0));
                    csv_data.addColumn(csvRecord.get(1));
                    csv_data.addColumn(csvRecord.get(2));
                    csv_data.addColumn(csvRecord.get(3));
                }
                else{
                    Vector row=new Vector();
                    row.add(csvRecord.get(0));
                    row.add(csvRecord.get(1));
                    row.add(csvRecord.get(2));
                    row.add(csvRecord.get(3));
                    csv_data.addRow(row);
                }
            }

        }
        catch (Exception e){
            System.out.println("Error in Parsing CSV File");
        }

        
        JTable jTableTweet = new JTable();
        jTableTweet.setModel(csv_data);    
        jTableTweet.setEnabled(false);
        jScrollPane4.getViewport().add(jTableTweet);
        //jScrollPane1.add(jTableTweet);
        
        }
        catch(Exception e)
        {
            System.out.println(e);
        
        }
        //JTable table = new JTable(csvdemo.readCSVFile());
        
        //JTable1();
        
        
    }
    
//    public void setGridPanels(){
//        jPanelTweets.setLayout(new GridLayout(4,1,5,10)); 
//         
//        
//        JPanel panel = new JPanel();
//        panel.setBounds(40,80,200,200);    
//        panel.setBackground(Color.gray);  
//        JButton b1=new JButton("Button 1");     
//        b1.setBounds(50,100,80,30);    
//        b1.setBackground(Color.yellow);  
//        panel.add(b1); 
//        
//        JPanel panel2 = new JPanel();
//        panel2.setBounds(40,80,200,200);    
//        panel2.setBackground(Color.yellow);  
//        JButton b2=new JButton("Button 1");     
//        b1.setBounds(50,100,80,30);    
//        b1.setBackground(Color.yellow);  
//        panel2.add(b2); 
//        
//         JPanel panel3 = new JPanel();
//        panel3.setBounds(40,80,200,200);    
//        panel3.setBackground(Color.blue);  
//        JButton b3=new JButton("Button 1");     
//        b1.setBounds(50,100,80,30);    
//        b1.setBackground(Color.yellow);  
//        panel3.add(b3); 
//        
//        JPanel panel4 = new JPanel();
//        panel4.setBounds(40,80,200,200);    
//        panel4.setBackground(Color.red);  
//        JButton b4=new JButton("Button 1");     
//        b1.setBounds(50,100,80,30);    
//        b1.setBackground(Color.yellow);  
//        panel4.add(b4);  
//        
//         JPanel panel5 = new JPanel();
//        panel5.setBounds(40,80,200,200);    
//        panel5.setBackground(Color.gray);  
//        JButton b5=new JButton("Button 1");     
//        b5.setBounds(50,100,80,30);    
//        b5.setBackground(Color.yellow);  
//        panel5.add(b5); 
//        
//        JPanel panel6 = new JPanel();
//        panel6.setBounds(40,80,200,200);    
//        panel6.setBackground(Color.yellow);  
//        JButton b6=new JButton("Button 1");     
//        b6.setBounds(50,100,80,30);    
//        b6.setBackground(Color.yellow);  
//        panel6.add(b6); 
//        
//         JPanel panel7 = new JPanel();
//        panel7.setBounds(40,80,200,200);    
//        panel7.setBackground(Color.blue);  
//        JButton b7=new JButton("Button 1");     
//        b7.setBounds(50,100,80,30);    
//        b7.setBackground(Color.yellow);  
//        panel7.add(b7); 
//        
//        JPanel panel8 = new JPanel();
//        panel8.setBounds(80,80,200,200);    
//        panel8.setBackground(Color.red);  
//        JButton b8=new JButton("Button 1");     
//        b8.setBounds(50,100,80,30);    
//        b8.setBackground(Color.yellow);  
//        panel8.add(b8); 
//                
//        
//        
//        jPanelTweets.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//        jPanelTweets.add(panel);jScrollPane1.add(panel2);jPanelTweets.add(panel3);jPanelTweets.add(panel4);
//        jPanelTweets.add(panel5);jPanelTweets.add(panel6);jPanelTweets.add(panel7);jPanelTweets.add(panel8);
//        
//        
//       
//    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jHeaderBG = new javax.swing.JPanel();
        jPTAnalizados = new javax.swing.JPanel();
        jLabelTweets = new javax.swing.JLabel();
        jLabelTweets1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPBody = new javax.swing.JPanel();
        jPanelUbicacionChart = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(1400, 800));

        jHeaderBG.setBackground(new java.awt.Color(29, 161, 242));

        jPTAnalizados.setBackground(new java.awt.Color(29, 161, 242));
        jPTAnalizados.setFocusable(false);

        jLabelTweets.setBackground(new java.awt.Color(29, 161, 242));
        jLabelTweets.setFont(new java.awt.Font("Segoe UI Semibold", 1, 70)); // NOI18N
        jLabelTweets.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTweets.setText("4646848");
        jLabelTweets.setFocusable(false);

        jLabelTweets1.setBackground(new java.awt.Color(29, 161, 242));
        jLabelTweets1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 24)); // NOI18N
        jLabelTweets1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTweets1.setText("tweets analizados");
        jLabelTweets1.setFocusable(false);

        javax.swing.GroupLayout jPTAnalizadosLayout = new javax.swing.GroupLayout(jPTAnalizados);
        jPTAnalizados.setLayout(jPTAnalizadosLayout);
        jPTAnalizadosLayout.setHorizontalGroup(
            jPTAnalizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTAnalizadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTAnalizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTweets, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addGroup(jPTAnalizadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelTweets1)))
                .addContainerGap())
        );
        jPTAnalizadosLayout.setVerticalGroup(
            jPTAnalizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTAnalizadosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabelTweets, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTweets1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 80)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HATE SPEECH");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 60)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("analyzer");

        javax.swing.GroupLayout jHeaderBGLayout = new javax.swing.GroupLayout(jHeaderBG);
        jHeaderBG.setLayout(jHeaderBGLayout);
        jHeaderBGLayout.setHorizontalGroup(
            jHeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jHeaderBGLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jHeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPTAnalizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jHeaderBGLayout.setVerticalGroup(
            jHeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jHeaderBGLayout.createSequentialGroup()
                .addGroup(jHeaderBGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPTAnalizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jHeaderBGLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane2.setPreferredSize(new java.awt.Dimension(1400, 500));

        jPBody.setBackground(new java.awt.Color(255, 255, 255));

        jPanelUbicacionChart.setBackground(new java.awt.Color(255, 255, 255));
        jPanelUbicacionChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jPanelUbicacionChartLayout = new javax.swing.GroupLayout(jPanelUbicacionChart);
        jPanelUbicacionChart.setLayout(jPanelUbicacionChartLayout);
        jPanelUbicacionChartLayout.setHorizontalGroup(
            jPanelUbicacionChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );
        jPanelUbicacionChartLayout.setVerticalGroup(
            jPanelUbicacionChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPBodyLayout = new javax.swing.GroupLayout(jPBody);
        jPBody.setLayout(jPBodyLayout);
        jPBodyLayout.setHorizontalGroup(
            jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPBodyLayout.createSequentialGroup()
                        .addComponent(jPanelUbicacionChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(586, Short.MAX_VALUE))
        );
        jPBodyLayout.setVerticalGroup(
            jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBodyLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelUbicacionChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPBody);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jHeaderBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1301, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jHeaderBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Home().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jHeaderBG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTweets;
    private javax.swing.JLabel jLabelTweets1;
    private javax.swing.JPanel jPBody;
    private javax.swing.JPanel jPTAnalizados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelUbicacionChart;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
