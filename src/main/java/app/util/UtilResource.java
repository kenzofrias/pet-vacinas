package app.util;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

public class UtilResource {

    public static void showInfo(WindowBasedTextGUI gui, String title, String message) {
        MessageDialog.showMessageDialog(gui, title, message);
    }

    public static void showError(WindowBasedTextGUI gui, String title, String message) {
        MessageDialog.showMessageDialog(gui, title, message);
    }
}
