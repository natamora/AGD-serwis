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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "visit_id")
    private int id;

    //tryb otrzymania sprzętu od klienta - czy 'przywiezienie', czy 'odbior'
    @Column(name = "receipt_type")
    private String receipt_type;

    //data przywiezienia lub odbioru sprzętu od klienta
    @Column(name = "receipt_date")
    private String receipt_date;

    //data odbioru naprawionego sprzętu (?? potrzebne ??)
    @Column(name = "pick_up_date")
    private String pick_up_date;

    //data naprawy sprzętu
    @Column(name = "repair_date")
    private String repair_date;

    //id produktu którego tyczy się wizyta
    @Column(name = "product_id")
    private int product_id;

    //id klienta dla którego tworzona jest wizyta
    @Column(name = "client_id")
    private int client_id;

    //id serwisanta który wykona usługę
    @Column(name = "user_id")
    private int serwisant_id;

    // notatka służbowa dla serwisanta
    @Column(name = "note")
    private String note;

    // cena całościowa wizyty
    @Column(name = "visit_costs")
    private String costs;

    // szacowany opis problemu (rejestrujacy)
    @Column(name = "description_estimated")
    private String estimated_description;

    // faktyczny opis problemu (serwisant)
    @Column(name = "description_actual")
    private String actual_description;

}
