package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Document;
import com.diplom.sptor.domain.Equipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 10.06.2016.
 */
@Repository
@Transactional
public class DocumentDAO {


    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void saveDocument(Document document) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(document);
    }

    public List<Document> listDocument() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Document> downTimes = session.createQuery("from com.diplom.sptor.domain.Document ").list();
        return  downTimes;
    }

    public Document getDocument(Integer document_id) {
        Session session = this.sessionFactory.getCurrentSession();
        return  (Document) session.get(Document.class,document_id );
    }

    public List<Document> getDocumentsByEquipment(Equipment equipment){
        Session session = this.sessionFactory.getCurrentSession();
        List<Document> documentList = session.createQuery("select doc from com.diplom.sptor.domain.Document doc " +
                "where doc.equipment =:id order by doc.date_of_adding").setParameter("id", equipment).list();
        return documentList;
    }

    public void removeDocument(Integer document_id) {
        Session session = this.sessionFactory.getCurrentSession();
        Document document = (Document) session.get(Document.class, document_id);
        if(null != document){
            session.delete(document);
        }
    }

}
