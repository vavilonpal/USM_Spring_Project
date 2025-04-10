package org.moodle.springlaboratorywork.repository.hibernateRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moodle.springlaboratorywork.entity.Match;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class MatchDao {

    public List<Match> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                    select m from Match m
                    left join fetch m.league 
                    left join fetch m.homeTeam 
                    left join fetch m.awayTeam
                    """;
            Query<Match> query = session.createQuery(hql, Match.class);
            return query.getResultList();
        }
    }

    public Optional<Match> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = """
                    select m from Match m
                    left join fetch m.league 
                    left join fetch m.homeTeam 
                    left join fetch m.awayTeam
                    where m.id = :id
                    """;
            Query<Match> query = session.createQuery(hql, Match.class);
            query.setParameter("id", id);
            Match match = (Match) query.getSingleResult();
            return Optional.ofNullable(match);
        }
    }

    public Match save(Match match) {
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
