/* 
 * Copyright (C) 2015 Marco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package arduinoserialread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.time.Millisecond;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import org.zu.ardulink.Link;
import org.zu.ardulink.RawDataListener;
import org.zu.ardulink.gui.ConnectionStatus;
import org.zu.ardulink.gui.SerialConnectionPanel;

/**
 *
 * @author Marco on behalf of Physics Light
 */
public class SerialRead implements RawDataListener {

    public JFrame frame;
    private SerialConnectionPanel serialCOM;
    private JButton connect;
    private JButton disconnect;
    private JTextField textField;
    private TimeSeriesCollection dataset;
    private final Link link = Link.getDefaultInstance();
    public int dataNewPoint; 
    private String input_data;
    
    public SerialRead() {
        this.frame = new JFrame();
        initGUI();
        runApp();
    }

    private void initGUI() {
        
        // init the frame
        frame.setTitle("serial arduino input");
        frame.setBounds(100, 100, 600, 500);
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // init control panel
        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new FlowLayout());
        
        // init the serial connection panel
        serialCOM = new SerialConnectionPanel();
        ctrlPanel.add(serialCOM);
        frame.getContentPane().add(ctrlPanel, BorderLayout.SOUTH);
        
        // init the connection status
        ConnectionStatus connectionStatus = new ConnectionStatus();
        ctrlPanel.add(connectionStatus);
        
        
        //textfeild to show the real time values not fixed yet 
        textField= new JTextField(input_data);
        ctrlPanel.add(textField);
        
        
        // init connect and disconnect button
        connect = new JButton("connect");
        ctrlPanel.add(connect);
        disconnect = new JButton("disconnect");
        ctrlPanel.add(disconnect);
        
        // init real time chart
        TimeSeries series = new TimeSeries("DATA");
        DateAxis timeAxis = new DateAxis("Time");
        dataset = new TimeSeriesCollection(series);
        NumberAxis rangeAxis = new NumberAxis("npk reading");
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setAutoRange(true);
        XYPlot plot = new XYPlot(
                dataset, timeAxis, rangeAxis, new StandardXYItemRenderer()
        );
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.getRenderer().setSeriesPaint(0, new Color(0, 142, 192));
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setAutoRange(true);
        domainAxis.setFixedAutoRange(30000.0);  // 30 seconds
        // init the JFreeChart
        JFreeChart chart = new JFreeChart("Real-Time reading chart", plot);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        chart.removeLegend();
        // add real time chart to the frame
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        
    }

    private void runApp() {
        
        
        
        // Register a RawDataListener to receive data from Arduino
        link.addRawDataListener(this);
        
        // add an action listener to the connect button
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String comPort = serialCOM.getConnectionPort();
                String baudRateS = serialCOM.getBaudRate();
                if (comPort == null || "".equals(comPort)) {
                    JOptionPane.showMessageDialog(connect, "Invalid COM PORT setted.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (baudRateS == null || "".equals(baudRateS)) {
                    JOptionPane.showMessageDialog(connect, "Invalid baud rate setted. Advice: set " + Link.DEFAULT_BAUDRATE, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int baudRate = Integer.parseInt(baudRateS);
                        // init the connection between Arduino and PC
                        boolean connected = link.connect(comPort, baudRate);
                        if (connected) {
                            connect.setEnabled(false);
                            serialCOM.setEnabled(false);
                            disconnect.setEnabled(true);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        String message = ex.getMessage();
                        if (message == null || message.trim().equals("")) {
                            message = "Generic Error on connection";
                        }
                        JOptionPane.showMessageDialog(connect, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        
        // add an  action  listener to disconnect button 
        disconnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean disconnected = link.disconnect();
                if (disconnected) {
                    connect.setEnabled(true);
                    serialCOM.setEnabled(true);
                    disconnect.setEnabled(false);
                     input_data = Float.toString(dataNewPoint);
                    
                }
            }
        });
         
    }

    @Override
    public void parseInput(String id, int numBytes, int[] message) {
        StringBuilder build = new StringBuilder(numBytes + 1);
        for (int i = 0; i < numBytes; i++) {
            build.append((char) message[i]);
        }
        String msgString = build.toString();
        if (msgString.startsWith("SERIAL")) {
            msgString = msgString.substring("SERIAL".length());
            // print data to console
            System.out.println(msgString);
              dataNewPoint = Integer.parseInt(msgString);
            // add new data point to the real time chart
            dataset.getSeries(0).add(new Millisecond(), dataNewPoint);
           //input_data = Float.toString(dataNewPoint);
        }
    }

}
