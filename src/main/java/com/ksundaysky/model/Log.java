package com.ksundaysky.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private int id;

    @Column(name = "action_type")
    private String action_type;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "table_name")
    private String table_name;

    @Column(name = "author_id")
    private String author_email;

    @Column(name = "message")
    private String message;


//    @javax.persistence.Transient
//    private String authorRole;
//
//    public String getAuthorRole() {
//        return authorRole;
//    }
//
//    public void setAuthorRole(String authorRole) {
//        this.authorRole = authorRole;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction_type() {
        return action_type;
    }

    public String getMessage() {
        return message;
    }

    public String getTable_name() {
        return table_name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

   public String getAuthor_email() {
       return author_email;
   }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
