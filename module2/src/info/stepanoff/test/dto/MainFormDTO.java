package info.stepanoff.test.dto;

public class MainFormDTO {
    private int id;
    private String type;
    private String name;
    private String director;
    private String phone;
    private int rating;
    private int discount;

    // Конструктор, геттеры и сеттеры
    public MainFormDTO(int id, String type, String name, String director, String phone, int rating, int discount) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.director = director;
        this.phone = phone;
        this.rating = rating;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "MyTableDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", phone='" + phone + '\'' +
                ", rating=" + rating +
                ", discount=" + discount +
                '}';
    }
}