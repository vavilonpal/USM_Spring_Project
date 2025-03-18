package org.moodle.springlaboratorywork.repository.hibernateRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moodle.springlaboratorywork.entity.Team;
import org.moodle.springlaboratorywork.entity.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamDao {


    public Optional<Team> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select t from Team  t
                left join fetch t.coach
                left join fetch t.league
                left join fetch t.awayMatches
                left join fetch t.homeMatches
                left join fetch t.players
                where t.id = :id
                """;
            Query query = session.createQuery(hql, Team.class);
            query.setParameter("id", id);
            Team team = (Team) query.getSingleResult();

            return Optional.ofNullable(team);
        } catch (Exception e) {
            throw e;

        }
    }
    //todo realazie fetching
    public List<Team> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select t from Team  t
                left join fetch t.coach
                left join fetch t.league
                left join fetch t.awayMatches
                left join fetch t.homeMatches
                left join fetch t.players
                """;
            Query query = session.createQuery(hql, Team.class);

            List<Team> teams = query.getResultList();
            return teams;
        }catch (Exception e){
            throw e;
        }
    }

    public Team save(Team team) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(team);

            transaction.commit();
            return team;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Team update(Team team) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(team);

            transaction.commit();
            return team;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Team team){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.remove(team);

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
            String hql = "SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Team t WHERE t.name = :name";
            Query query = session.createQuery(hql).setParameter("name", name);
            transaction.commit();
            return (Boolean) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
