package bobnard.claim.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UndoRedoMouseListener extends MouseAdapter {
    private final CFrame frame;
    private final UndoRedoPanel panel;

    private final boolean undo;

    public UndoRedoMouseListener(CFrame frame, UndoRedoPanel panel, boolean isUndo) {
        this.frame = frame;
        this.panel = panel;
        this.undo = isUndo;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.undo) {
            this.frame.undo();
        } else {
            this.frame.redo();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.panel.switchImage();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.panel.switchImage();
    }
}
