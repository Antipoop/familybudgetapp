package com.example.familybudget;

import javax.swing.*;
import java.awt.*;

public class ErrorMessage extends JDialog
{
    public ErrorMessage(JDialog owner, String msg)
    {
        super(owner, "ОШИБКА", true);
        setSize(new Dimension(500,200));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        prepareOKUI(msg);
        setLocationRelativeTo(owner);
    }
    private void prepareOKUI(String mes)
    {
        JOptionPane.showMessageDialog(ErrorMessage.this, mes, "ОШИБКА", JOptionPane.ERROR_MESSAGE);
    }
    public void ShowDailog(){
        setVisible(true);
    }
}