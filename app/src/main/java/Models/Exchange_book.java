package Models;

/**
 * Created by Muhammad Musa on 4/29/2016.
 */
public class Exchange_book {

    Integer id,user_id,give_image_path,receive_image_path;
    String contact_name,contact_no,give_book_name,receive_book_name,give_category,receive_category;
    String created_at;

    public Exchange_book(Integer id, Integer user_id, Integer give_image_path, Integer receive_image_path, String contact_name, String contact_no, String give_book_name, String receive_book_name, String give_category, String receive_category, String created_at) {
        this.id = id;
        this.user_id = user_id;
        this.give_image_path = give_image_path;
        this.receive_image_path = receive_image_path;
        this.contact_name = contact_name;
        this.contact_no = contact_no;
        this.give_book_name = give_book_name;
        this.receive_book_name = receive_book_name;
        this.give_category = give_category;
        this.receive_category = receive_category;
        this.created_at = created_at;
    }

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

    public Integer getGive_image_path() {
        return give_image_path;
    }

    public void setGive_image_path(Integer give_image_path) {
        this.give_image_path = give_image_path;
    }

    public Integer getReceive_image_path() {
        return receive_image_path;
    }

    public void setReceive_image_path(Integer receive_image_path) {
        this.receive_image_path = receive_image_path;
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

    public String getGive_book_name() {
        return give_book_name;
    }

    public void setGive_book_name(String give_book_name) {
        this.give_book_name = give_book_name;
    }

    public String getReceive_book_name() {
        return receive_book_name;
    }

    public void setReceive_book_name(String receive_book_name) {
        this.receive_book_name = receive_book_name;
    }

    public String getGive_category() {
        return give_category;
    }

    public void setGive_category(String give_category) {
        this.give_category = give_category;
    }

    public String getReceive_category() {
        return receive_category;
    }

    public void setReceive_category(String receive_category) {
        this.receive_category = receive_category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
