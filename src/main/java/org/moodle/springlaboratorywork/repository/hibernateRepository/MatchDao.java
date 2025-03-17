package org.moodle.springlaboratorywork.repository.hibernateRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moodle.springlaboratorywork.entity.Match;

import java.util.List;
import java.util.Optional;

public class MatchDao {

    public List<Match> findAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //todo write hoin for getting home and away teams
            //String hql = "select m from Match m join fetch l.teams";
            Query query = session.createQuery(hql, Match.class);

            List<Match> matches = query.getResultList();

            transaction.commit();
            return matches;
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Optional<Match> findById(Long id) {
        Match match = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            match = session.get(Match.class, id);

            transaction.commit();
            return Optional.ofNullable(match);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;

        }
    }

    public Match save(Match match) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(match);

            transaction.commit();
            return match;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Match update(Match match) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(match);

            transaction.commit();
            return match;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Match match){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.remove(match);

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
            String hql = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Match l WHERE l.name = :name";
            Query query = session.createQuery(hql).setParameter("name", name);
            transaction.commit();
            return (Boolean) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
