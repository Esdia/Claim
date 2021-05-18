package bobnard.claim.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListSkin {
    private JList list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;

    public ListSkin(String message, JList listToDisplay) {
        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();
    }

    public ListSkin(String title, String message, JList listToDisplay) {
        this(message, listToDisplay);
        dialog.setTitle(title);
    }

    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton});
        dialog = optionPane.createDialog("Skin select");
    }

    private void setupButtons() {
        okButton = new JButton("Ok");
        okButton.addActionListener(e -> handleOkButtonClick(e));


    }

    private JPanel layoutComponents() {
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }


    private void handleOkButtonClick(ActionEvent e) {
        if (okEvent != null) {
            okEvent.actionPerformed(e);
        }
        hide();
    }


    public void show(){ dialog.setVisible(true); }

    private void hide(){ dialog.setVisible(false); }

    public Object getSelectedItem(){ return list.getSelectedValue(); }

}