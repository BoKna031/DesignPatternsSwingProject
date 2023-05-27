package dialogs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TemplateDialog extends AcceptDeclineDialog{
    private class ComponentTemplate{
        private final JLabel label;
        private final JComponent component;

        public ComponentTemplate(String label, JComponent component) {
            this.label = new JLabel(label);
            this.component = component;
        }
    }

    private ArrayList<ComponentTemplate> components = new ArrayList<>();

    public void addComponent(String label, JComponent component){
        components.add(new ComponentTemplate(label, component));
        updateContentPanel();
    }

    public void addComponent(int index, String label, JComponent component){
        components.add(index, new ComponentTemplate(label, component));
        updateContentPanel();
    }

    private void updateContentPanel(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0,2,5,5));
        for (ComponentTemplate ct : components){
            contentPanel.add(ct.label);
            contentPanel.add(ct.component);
        }
        addContentPanel(contentPanel);
    }
}
