package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private EntityManager em;

    public Page<Doctor> filterAndSortDoctors(String fullName, String sortField, String sortDir, Pageable pageable) {
        HibernateCriteriaBuilder cb = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);
        var doctorRoot = cq.from(Doctor.class);
        var user = doctorRoot.get("user");

        List<Predicate> predicates = new ArrayList<>();

        if (fullName != null && !fullName.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("fullName")), "%" + fullName.toLowerCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        if (sortField == null || sortField.trim().isEmpty()) {
            cq.orderBy(
                    cb.asc(user.get("fullName")),
                    cb.asc(user.get("enabled"))
            );
        } else if ("fullName".equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortDir)) {
                cq.orderBy(cb.desc(user.get("fullName")));
            } else {
                cq.orderBy(cb.asc(user.get("fullName")));
            }
        } else if ("enabled".equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortDir)) {
                cq.orderBy(cb.desc(user.get("enabled")));
            } else {
                cq.orderBy(cb.asc(user.get("enabled")));
            }
        } else {
            cq.orderBy(
                    cb.asc(user.get("fullName")),
                    cb.asc(user.get("enabled"))
            );
        }

        TypedQuery<Doctor> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return PageableExecutionUtils.getPage(
                query.getResultList(),
                pageable,
                () -> em.createQuery(cq.createCountQuery()).getSingleResult()
        );
    }

    public Page<Patient> filterAndSortPatients(String fullName, String sortField, String sortDir, Pageable pageable) {
        HibernateCriteriaBuilder cb = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
        var patientRoot = cq.from(Patient.class);
        var user = patientRoot.get("user");

        List<Predicate> predicates = new ArrayList<>();

        if (fullName != null && !fullName.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("fullName")), "%" + fullName.toLowerCase() + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        if (sortField == null || sortField.trim().isEmpty()) {
            cq.orderBy(
                    cb.asc(user.get("fullName")),
                    cb.asc(user.get("enabled"))
            );
        } else if ("fullName".equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortDir)) {
                cq.orderBy(cb.desc(user.get("fullName")));
            } else {
                cq.orderBy(cb.asc(user.get("fullName")));
            }
        } else if ("enabled".equalsIgnoreCase(sortField)) {
            if ("desc".equalsIgnoreCase(sortDir)) {
                cq.orderBy(cb.desc(user.get("enabled")));
            } else {
                cq.orderBy(cb.asc(user.get("enabled")));
            }
        } else {
            cq.orderBy(
                    cb.asc(user.get("fullName")),
                    cb.asc(user.get("enabled"))
            );
        }

        TypedQuery<Patient> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return PageableExecutionUtils.getPage(
                query.getResultList(),
                pageable,
                () -> em.createQuery(cq.createCountQuery()).getSingleResult()
        );
    }
}
