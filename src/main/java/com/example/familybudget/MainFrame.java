package com.example.familybudget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    private JList<String> familyMemberList;
    private DefaultListModel<String> listModel;
    private List<FamilyMember> familyMembers;
    private Connection connection;
    public final Color accentColor = new Color(177, 26, 237); // #B11AED
    public final Color secondaryBackground = new Color(244, 232, 211); // #F4E8D3

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        // Подключение к базе данных
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/family", "postgres", "1234321a");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setIconImage(new ImageIcon(getClass().getResource("/icon.ico")).getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setTitle("Family Budget Management");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel titleLabel = new JLabel("Family Budget");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(logoIcon);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);
        contentPane.add(logoPanel, BorderLayout.WEST);

        JPanel listPanel = new JPanel();
        listPanel.setBackground(secondaryBackground); // Дополнительный фон
        contentPane.add(listPanel, BorderLayout.CENTER);
        listPanel.setLayout(new BorderLayout(0, 0));

        listModel = new DefaultListModel<>();
        familyMemberList = new JList<>(listModel);
        familyMemberList.setFont(new Font("Calibri", Font.PLAIN, 14));
        listPanel.add(new JScrollPane(familyMemberList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(secondaryBackground); // Дополнительный фон
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add Member");
        addButton.setBackground(accentColor); // Цвет акцента
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Calibri", Font.BOLD, 14));
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Member");
        editButton.setBackground(accentColor); // Цвет акцента
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Calibri", Font.BOLD, 14));
        buttonPanel.add(editButton);

        loadFamilyMembers();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddMemberDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = familyMemberList.getSelectedIndex();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a member to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                FamilyMember selectedMember = familyMembers.get(selectedIndex);
                if (selectedMember != null)
                {
                    openEditMemberDialog(selectedMember);
                }
            }
        });
    }

    private void openAddMemberDialog() {
        FamilyMemberForm addMemberForm = new FamilyMemberForm(this, connection, (MainFrame.FamilyMember) null);
        addMemberForm.setVisible(true);
    }

    private void openEditMemberDialog(MainFrame.FamilyMember member) {
        FamilyMemberForm editMemberForm = new FamilyMemberForm(this, connection, member);
        editMemberForm.setVisible(true);
    }

    private void loadFamilyMembers() {
        listModel.clear();
        familyMembers = new ArrayList<>();

        try {
            String sql = "SELECT " +
                    "    FM.MemberID, " +
                    "    FullName, " +
                    "    BirthDate, " +
                    "    J.position, " +
                    "    J.organization, " +
                    "    J.Salary " +
                    "FROM " +
                    "    FamilyMember FM " +
                    "LEFT JOIN " +
                    "    Job J ON FM.MemberID = J.MemberID " +
                    "WHERE " +
                    "    J.StartDate = (SELECT MAX(StartDate) FROM Job WHERE MemberID = FM.MemberID) " +
                    "OR J.position IS NULL";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int memberID = resultSet.getInt("MemberID");
                String fullName = resultSet.getString("FullName");
                LocalDate birthDate = resultSet.getObject("BirthDate", LocalDate.class);
                String jobTitle = resultSet.getString("position");
                String companyName = resultSet.getString("organization");
                double salary = resultSet.getDouble("Salary");

                FamilyMember member = new FamilyMember(memberID, fullName, birthDate);
                member.setBudgetStatus(calculateBudgetStatus(memberID));
                familyMembers.add(member);

                int age = calculateAge(birthDate);

                String displayString = String.format(
                        "<html><body>%s (%s)<br>Возраст: %d<br>Должность: %s<br>Место работы: %s<br>Оклад: %.2f</body></html>",
                        fullName,
                        member.getBudgetStatus(),
                        age,
                        (jobTitle != null ? jobTitle : "Безработный"),
                        (companyName != null ? companyName : "-"),
                        salary
                );

                listModel.addElement(displayString);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading family members: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        Period diff = Period.between(birthDate, now);
        return diff.getYears();
    }


    public void refreshFamilyMemberList()
    {
        loadFamilyMembers();
    }

    private String calculateBudgetStatus(int memberId) {
        double income = getLastMonthIncome(memberId);
        double expenses = getLastMonthExpenses(memberId);

        if (income > expenses) {
            return "Профицит бюджета";
        } else {
            return "Дефицит бюджета";
        }
    }

    private double getLastMonthIncome(int memberId) {
        String sql = "SELECT Salary FROM Job " +
                "WHERE MemberID = ? AND StartDate <= CURRENT_DATE " +
                "ORDER BY StartDate DESC " +
                "LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("Salary");
            } else {
                return 0.0;
            }

        } catch (SQLException e) {
            System.err.println("Error getting last month income: " + e.getMessage());
            return 0.0;
        }
    }

    private double getLastMonthExpenses(int memberId) {
        String sql = "SELECT SUM(p.UnitPrice * e.Quantity) AS TotalExpenses " +
                "FROM Expense e " +
                "JOIN Product p ON e.ProductID = p.ProductID " +
                "WHERE e.MemberID = ? " +
                "AND e.ExpenseDate >= date_trunc('month', CURRENT_DATE)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("TotalExpenses");
            } else {
                return 0.0;
            }

        } catch (SQLException e) {
            System.err.println("Error getting last month expenses: " + e.getMessage());
            return 0.0;
        }
    }

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/family";
        String username = "postgres";
        String password = "1234321a";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static class FamilyMember {
        private final int memberID;
        private final String fullName;
        private final LocalDate birthDate;
        private String budgetStatus; // Добавляем поле для статуса бюджета

        public FamilyMember(int memberID, String fullName, LocalDate birthDate) {
            this.memberID = memberID;
            this.fullName = fullName;
            this.birthDate = birthDate;
        }

        public int getMemberID() {
            return memberID;
        }

        public String getFullName() {
            return fullName;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public String getBudgetStatus() { return budgetStatus; }

        public void setBudgetStatus(String budgetStatus) { this.budgetStatus = budgetStatus; }
    }
}