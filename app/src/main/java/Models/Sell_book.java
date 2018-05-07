package Models;

/**
 * Created by Muhammad Musa on 4/29/2016.
 */
public class Sell_book {

    Integer id,user_id,image_path;
    String contact_name,contact_no,book_name,price,category;
    String created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getImage_path() {
        return image_path;
    }

    public void setImage_path(Integer image_path) {
        this.image_path = image_path;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Sell_book(Integer id, Integer user_id, Integer image_path, String contact_name, String contact_no, String book_name, String price, String category, String created_at) {

        this.id = id;
        this.user_id = user_id;
        this.image_path = image_path;
        this.contact_name = contact_name;
        this.contact_no = contact_no;
        this.book_name = book_name;
        this.price = price;
        this.category = category;
        this.created_at = created_at;
    }
}
