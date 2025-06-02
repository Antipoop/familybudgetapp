package info.stepanoff.test.dao;

import info.stepanoff.test.config.Props;
import info.stepanoff.test.dto.MainFormDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainFormDAO {

    // Метод для получения всех записей из таблицы
    public List<MainFormDTO> getAllRecords() {
        List<MainFormDTO> records = new ArrayList<>();

        // Подключение к базе данных
        Properties properties = Props.loadProperties();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        // Подключение к базе данных
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            // SQL-запрос
            String sql = """
                select id, partner_type, name, director, director_email, partner_phone, partner_legal_address, inn, rating,
                 CASE
                                 WHEN amount <= 10000 THEN 0
                                 WHEN amount > 10000 AND amount <= 50000 THEN 5
                                 WHEN amount > 50000 AND amount <= 300000 THEN 10
                                 WHEN amount > 300000 THEN 15
                             END AS discount
                             from 
                (SELECT id, partner_type, name, director, director_email, partner_phone, partner_legal_address, inn, rating, 
                 (select sum (quantity) from partner_production pp where p.id = pp.partner_id) as amount                 
                  FROM partner p) as source
            """;

            // Создание PreparedStatement
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Обработка результата
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String type = resultSet.getString("partner_type");
                    String name = resultSet.getString("name");
                    String director = resultSet.getString("director");
                    String phone = resultSet.getString("partner_phone");
                    Integer rating = resultSet.getInt("rating");
                    Integer discount = resultSet.getInt("discount");

                    // Создание DTO и добавление в список
                    MainFormDTO dto = new MainFormDTO(id, type, name, director, phone, rating, discount);
                    records.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }


}