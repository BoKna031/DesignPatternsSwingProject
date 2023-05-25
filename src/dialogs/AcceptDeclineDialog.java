package dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public abstract class AcceptDeclineDialog extends JDialog {
    private boolean accepted;
    private JPanel buttonsPanel;
    public AcceptDeclineDialog(){
        accepted = false;
        setPreferredSize(new Dimension(300,300));
        buttonsPanel = createButtonPanel();

        setLayout(new BorderLayout());
        add(buttonsPanel,BorderLayout.PAGE_END);
        updateView();
    }

    public void addContentPanel(JPanel panel){
        this.add(panel, BorderLayout.CENTER);
        updateView();
    }

    private void updateView(){
        revalidate();
        repaint();
    }

    public boolean isAccepted() {
        return accepted;
    }


    private JPanel createButtonPanel(){
        JPanel result = new JPanel();
        result.setLayout(new GridLayout(1,2,5,5));
        JButton btnAccept = new JButton("Accept");
        btnAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accepted = true;
                dispose();
            }
        });
        JButton btnDecline = new JButton("Decline");
        btnDecline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        result.add(btnAccept);
        result.add(btnDecline);
        return result;
    }
}
