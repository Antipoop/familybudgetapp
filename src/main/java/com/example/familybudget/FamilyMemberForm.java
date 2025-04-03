package com.example.familybudget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class FamilyMemberForm extends JDialog {

    private JTextField fullNameField;
    private JTextField birthDateField;
    private JTextField positionField;
    private JTextField jobPlaceField;
    private JTextField salaryField;
    private final MainFrame parentFrame;
    private final Connection connection;
    private MainFrame.FamilyMember memberToEdit;

    public final Color accentColor = new Color(177, 26, 237); // #B11AED

    public FamilyMemberForm(MainFrame owner, Connection dbConnection, MainFrame.FamilyMember member) {

        super(owner, "Add/Edit Family Member", true);
        parentFrame = owner;
        connection = dbConnection;
        memberToEdit = member;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(0, 2));

        prepareUI();
        if (memberToEdit != null)
        {
            loadData();
        }
        setLocationRelativeTo(owner);
    }

    private void prepareUI() {
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        birthDateField = new JTextField();
        JLabel positionLabel = new JLabel("Current Position:");
        positionField = new JTextField();
        JLabel jobPlaceLabel = new JLabel("Current Job Place:");
        jobPlaceField = new JTextField();
        JLabel salaryLabel = new JLabel("Current Monthly Income:");
        salaryField = new JTextField();

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(accentColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Calibri", Font.BOLD, 14));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFamilyMember();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(accentColor);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Calibri", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the form
            }
        });

        add(fullNameLabel);
        add(fullNameField);
        add(birthDateLabel);
        add(birthDateField);
        add(positionLabel);
        add(positionField);
        add(jobPlaceLabel);
        add(jobPlaceField);
        add(salaryLabel);
        add(salaryField);
        add(backButton);
        add(saveButton);
    }

    private void loadData() {
        if (memberToEdit != null) {
            String memberId = String.valueOf(memberToEdit.getMemberID());
            fullNameField.setText(memberToEdit.getFullName());
            birthDateField.setText(String.valueOf(memberToEdit.getBirthDate()));

            String sql = "SELECT Position, Organization, Salary FROM Job WHERE MemberID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int memberIdInt = Integer.parseInt(memberId); // Преобразуем String в int
                preparedStatement.setInt(1, memberIdInt); // Используем int для setInt
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    positionField.setText(resultSet.getString("Position"));
                    jobPlaceField.setText(resultSet.getString("Organization"));
                    salaryField.setText(String.valueOf(resultSet.getDouble("Salary")));
                }

            } catch (SQLException e) {
                System.err.println("Error loading job " + e.getMessage());
                new ErrorMessage(this, "Ошибка, что-то пошло не так. Проверьте, что поля заполненны корректо \n" + e.toString()).ShowDailog();
            }  catch (NumberFormatException e) {
                System.err.println("Ошибка преобразования MemberID в число: " + e.getMessage());
                new ErrorMessage(this, "Ошибка: Некорректный формат ID. ID должен быть числом.").ShowDailog();
            }
        }
    }

    private void saveFamilyMember() {
        String fullName = fullNameField.getText();
        String birthDateStr = birthDateField.getText();
        String position = positionField.getText();
        String jobPlace = jobPlaceField.getText();
        String salaryStr = salaryField.getText();

        if (fullName == null || fullName.trim().isEmpty()) {
            new WarningMessege(this, "ФИО  не может быть пустым").ShowDailog();
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
            if (salary < 0) {
                new WarningMessege(this, "Текущий месячный доход не может быть отрицательным числом").ShowDailog();
                return;
            }
        } catch (NumberFormatException e) {
            new WarningMessege(this, "Неверный формат дохода. Пожалуйста, введите число.").ShowDailog();
            return;
        }

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr);
        } catch (DateTimeParseException e) {
            new WarningMessege(this, "Неверный формат для даты рождения. Используйте формат ГГГГ-ММ-ДД.").ShowDailog();
            return;
        }

        try {
            if (memberToEdit == null) {
                String sql = "INSERT INTO FamilyMember (FamilyID, FullName, BirthDate) VALUES (1, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, fullName);
                    preparedStatement.setObject(2, birthDate);
                    preparedStatement.executeUpdate();

                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    int memberId = 0;
                    if (generatedKeys.next()) {
                        memberId = generatedKeys.getInt(1);
                    }

                    if (!UpdateJob(memberId, position, jobPlace, salary))
                    {
                        new ErrorMessage(this, "Проверьте, что база данных корректно настроена. Данные о работе НЕ сохранены").ShowDailog();
                    } else {
                        new InformationMessege(this, "Данные о работе сохранены").ShowDailog();
                    }
                }
            } else {
                String sql = "UPDATE FamilyMember SET FullName = ?, BirthDate = ? WHERE MemberID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, fullName);
                    preparedStatement.setObject(2, birthDate);
                    preparedStatement.setInt(3, memberToEdit.getMemberID());
                    preparedStatement.executeUpdate();

                    if (!UpdateJob(memberToEdit.getMemberID(), position, jobPlace, salary))
                    {
                        new ErrorMessage(this, "Проверьте, что база данных корректно настроена. Данные о работе НЕ обновлены").ShowDailog();
                    }else {
                        new InformationMessege(this, "Данные о работе обновлены").ShowDailog();
                    }
                }
            }

            parentFrame.refreshFamilyMemberList();

        } catch (SQLException e) {
            System.err.println("SQL-Error: " + e.getMessage());
            String mes = "SQL-Error\n" + e.toString();
            new ErrorMessage(this, "Ошибка при сохранении в базу данных: \n" + mes).ShowDailog();
            return;
        }
        dispose();
    }
    private Boolean UpdateJob(int memberId, String position, String jobPlace, double salary) {
        String sql = "SELECT * FROM Job WHERE MemberID = ?";
        int isExist = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                isExist++;
            }
        }catch (SQLException e)
        {
            System.err.println("SQL get data member:" + e.getMessage());
            new ErrorMessage(this, "Проблема с получением данных. Проверьте, что таблица настроена правильно\n" + e.toString()).ShowDailog();
            return false;
        }

        if (isExist>0)
        {
            String sql_ = "UPDATE Job SET Position = ?, Organization = ?, Salary = ? WHERE MemberID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql_)) {

                preparedStatement.setString(1, position);
                preparedStatement.setString(2, jobPlace);
                preparedStatement.setDouble(3, salary);
                preparedStatement.setInt(4, memberId);
                preparedStatement.executeUpdate();
                return true;
            }catch (SQLException e)
            {
                System.err.println("SQL-Error Update Job:Position = ?, Organization = ?, Salary = ? WHERE MemberID = ?" + e.getMessage());
                new ErrorMessage(this, "Ошибка при обновление данных о работе в базу данных:: \n" + e.toString()).ShowDailog();
                return false;
            }

        } else {
            String sql_ = "INSERT INTO Job (MemberID, Position, Organization, Salary, StartDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql_)) {

                preparedStatement.setInt(1, memberId);
                preparedStatement.setString(2, position);
                preparedStatement.setString(3, jobPlace);
                preparedStatement.setDouble(4, salary);
                preparedStatement.setObject(5,LocalDate.now());
                preparedStatement.executeUpdate();
                return true;
            }catch (SQLException e)
            {
                System.err.println("SQL-Error INSERT INTO Job (MemberID, Position, Organization, Salary, StartDate) VALUES (?, ?, ?, ?, ?): " + e.getMessage());
                new ErrorMessage(this, "Ошибка при добавление данных о работе в базу данных: \n" + e.toString()).ShowDailog();
                return false;
            }
        }

    }
}