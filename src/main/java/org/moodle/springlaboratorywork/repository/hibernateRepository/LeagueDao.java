package org.moodle.springlaboratorywork.repository.hibernateRepository;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moodle.springlaboratorywork.entity.League;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LeagueDao {

    public List<League> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select l from League l join fetch l.teams";
            Query<League> query = session.createQuery(hql, League.class);
            return query.getResultList();
        }
    }

    public Optional<League> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            League league = session.get(League.class, id);

            return Optional.ofNullable(league);
        }
    }

    public League save(League league) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(league);

            transaction.commit();
            return league;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void delete(League league){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.remove(league);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Boolean existsByName(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM League l WHERE l.name = :name";
            Query query = session.createQuery(hql).setParameter("name", name);
            transaction.commit();
            return (Boolean) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

