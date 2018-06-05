package com.ksundaysky.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "visit")
public class Visit {


//    "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}", message="Ustaw prawidłową datę!")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "visit_id")
    private int id;

    //tryb otrzymania sprzętu od klienta - czy 'przywiezienie', czy 'odbior'
    @Column(name = "receipt_type")
    private String receipt_type;

    //data przywiezienia lub odbioru sprzętu od klienta
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}", message="Ustaw prawidłową datę! Wg wzoru: yyyy-MM-dd HH:mm")
    @NotEmpty(message = "Uzupełnij datę")
    @Column(name = "receipt_date_start")
    public String receipt_date_start;

    public String getReceipt_date_end() {
        return receipt_date_end;
    }

    public void setReceipt_date_end(String receipt_date_end) {
        this.receipt_date_end = receipt_date_end;
    }

    //data konca wizyty
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}", message="Ustaw prawidłową datę! Wg wzoru: yyyy-MM-dd HH:mm")
    @NotEmpty(message = "Uzupełnij datę")
    @Column(name = "receipt_date_end")
    public String receipt_date_end;


    //data odbioru naprawionego sprzętu (?? potrzebne ??)
    @Column(name = "pick_up_date")
    private String pick_up_date;

    //data naprawy sprzętu
    @Column(name = "repair_date")
    private String repair_date;

    //id produktu którego tyczy się wizyta
    @Column(name = "product_id")
    public int productId;

    //id klienta dla którego tworzona jest wizyta
    @Column(name = "client_id")
    private int client_id;

    //id serwisanta który wykona usługę
    @Column(name = "employer_id")
    private int serwisant_id;

    // notatka służbowa dla serwisanta
    @Column(name = "note")
    private String note;

    // cena całościowa wizyty
    @Column(name = "visit_costs")
    private String costs;

    // szacowany opis problemu (rejestrujacy)
    @Column(name = "description_estimated")
    @NotEmpty(message = "Uzupełnij opis")
    private String estimated_description;
    @Size(max=100, message="Zbyt dluga wiadomosc")

    // faktyczny opis problemu (serwisant)
    @Column(name = "description_actual")
    private String actual_description;

    @Column(name = "component_price")
    private String price;

    @Column(name = "component_name")
    private String component_name;

    public Visit(String receipt_type, String receipt_date_start, String receipt_date_end, String pick_up_date, String repair_date, int productId, int client_id, int serwisant_id, String note, String costs, String estimated_description, String actual_description, String clientNameSurname, String servisantSurname, String productName) {
        this.receipt_type = receipt_type;
        this.receipt_date_start = receipt_date_start;
        this.receipt_date_end = receipt_date_end;
        this.pick_up_date = pick_up_date;
        this.repair_date = repair_date;
        this.productId = productId;
        this.client_id = client_id;
        this.serwisant_id = serwisant_id;
        this.note = note;
        this.costs = costs;
        this.estimated_description = estimated_description;
        this.actual_description = actual_description;
        this.clientNameSurname = clientNameSurname;
        this.servisantSurname = servisantSurname;
        this.productName = productName;
    }


    public Visit(int visit_id, int client_id, int productId, String pick_up_date, String repair_date, String productName){
        this.id = visit_id;
        this.client_id = client_id;
        this.productId = productId;
        this.pick_up_date = pick_up_date;
        this.repair_date = repair_date;
        this.productName = productName;
    }

    public Visit() {
    }

    private String clientNameSurname;

    private String servisantSurname;

    private String productName;

    public String getServisantSurname() {
        return servisantSurname;
    }

    public void setServisantSurname(String servisantSurname) {
        this.servisantSurname = servisantSurname;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClientNameSurname() {
        return clientNameSurname;
    }

    public void setClientNameSurname(String clientNameSurname) {
        this.clientNameSurname = clientNameSurname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProduct_id() {
        return productId;
    }

    public void setProduct_id(int product_id) {
        this.productId = product_id;
    }

    public int getSerwisant_id() {
        return serwisant_id;
    }

    public void setSerwisant_id(int serwisant_id) {
        this.serwisant_id = serwisant_id;
    }

    public String getActual_description() {
        return actual_description;
    }

    public void setActual_description(String actual_description) {
        this.actual_description = actual_description;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getPick_up_date() {
        return pick_up_date;
    }

    public void setPick_up_date(String pick_up_date) {
        this.pick_up_date = pick_up_date;
    }

    public String getReceipt_date_start() {
        return receipt_date_start;
    }

    public void setReceipt_date_start(String receipt_date) {
        this.receipt_date_start = receipt_date;
    }

    public String getReceipt_type() {
        return receipt_type;
    }

    public void setReceipt_type(String receipt_type) {
        this.receipt_type = receipt_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEstimated_description() {
        return estimated_description;
    }

    public void setEstimated_description(String estimated_description) {
        this.estimated_description = estimated_description;
    }

    public String getRepair_date() {
        return repair_date;
    }

    public void setRepair_date(String repair_date) {
        this.repair_date = repair_date;
    }

    public String getComponent_name() {
        return component_name;
    }

    public String getPrice() {
        return price;
    }

}
