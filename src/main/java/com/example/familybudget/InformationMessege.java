package com.example.familybudget;

import javax.swing.*;
import java.awt.*;

public class InformationMessege extends JDialog
{
    public InformationMessege(JDialog owner, String msg)
    {
        super(owner, "Message", true);
        setSize(new Dimension(500,200));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        prepareOKUI(msg);
        setLocationRelativeTo(owner);
    }
    private void prepareOKUI(String mes)
    {
        JOptionPane.showMessageDialog(InformationMessege.this, mes, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
    public void ShowDailog(){
        setVisible(true);
    }
}