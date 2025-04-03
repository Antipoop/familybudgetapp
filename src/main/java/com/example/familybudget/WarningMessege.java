package com.example.familybudget;

import javax.swing.*;
import java.awt.*;
public class WarningMessege extends JDialog {

    public WarningMessege(JDialog owner, String msg)
    {
        super(owner, "Внимание!", true);
        setSize(new Dimension(500,200));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        prepareOKUI(msg);
        setLocationRelativeTo(owner);
    }
    private void prepareOKUI(String mes)
    {

        JOptionPane.showMessageDialog(WarningMessege.this, mes, "Внимание!", JOptionPane.WARNING_MESSAGE);
    }
    public void ShowDailog(){
        setVisible(true);
    }
}