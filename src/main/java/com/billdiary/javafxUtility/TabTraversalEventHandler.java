package com.billdiary.javafxUtility;

import com.sun.javafx.scene.control.skin.TextAreaSkin;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles tab/shift-tab keystrokes to navigate to other fields,
 * ctrl-tab to insert a tab character in the text area.
 */
public class TabTraversalEventHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.TAB)) {
            Node node = (Node) event.getSource();
            if (node instanceof TextArea) {
                TextAreaSkin skin = (TextAreaSkin) ((TextArea)node).getSkin();
                if (!event.isControlDown()) {
                    // Tab or shift-tab => navigational action
                    if (event.isShiftDown()) {
                        skin.getBehavior().traversePrevious();
                    } else {
                        skin.getBehavior().traverseNext();
                    }
                } else {
                    // Ctrl-Tab => insert a tab character in the text area
                    TextArea textArea = (TextArea) node;
                    textArea.replaceSelection("\t");
                }
                event.consume();
            }
        }
    }
}