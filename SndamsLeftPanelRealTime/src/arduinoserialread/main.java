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

/**
 *
 * @author Marco
 */
import java.awt.EventQueue;
import javax.swing.UIManager;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;


public class main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(WindowsLookAndFeel.class.getCanonicalName());
                    // set the application 
                    SerialRead application = new SerialRead();
                    // show the frame
                    application.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

