package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by user on 10.06.2016.
 */
@Entity
@Table(name = "toir.documents")
@Proxy(lazy=false)
public class Document {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int document_id;

    private String document_name;

    private String description;

    private byte [] content;

    private String content_type;

    private Date date_of_adding;

    private String path;

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public Date getDate_of_adding() {
        return date_of_adding;
    }

    public void setDate_of_adding(Date date_of_adding) {
        this.date_of_adding = date_of_adding;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Document() {
    }

    public Document(String document_name, String description, byte[] content,
                    String content_type, Date date_of_adding, String path) {
        this.document_name = document_name;
        this.description = description;
        this.content = content;
        this.content_type = content_type;
        this.date_of_adding = date_of_adding;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;

        Document document = (Document) o;

        if (getDocument_id() != document.getDocument_id()) return false;
        if (!getDocument_name().equals(document.getDocument_name())) return false;
        if (getDescription() != null ? !getDescription().equals(document.getDescription()) : document.getDescription() != null)
            return false;
        if (!getContent().equals(document.getContent())) return false;
        if (getContent_type() != null ? !getContent_type().equals(document.getContent_type()) : document.getContent_type() != null)
            return false;
        return getDate_of_adding().equals(document.getDate_of_adding());

    }

    @Override
    public int hashCode() {
        int result = getDocument_id();
        result = 31 * result + getDocument_name().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getContent().hashCode();
        result = 31 * result + (getContent_type() != null ? getContent_type().hashCode() : 0);
        result = 31 * result + getDate_of_adding().hashCode();
        return result;
    }
}
