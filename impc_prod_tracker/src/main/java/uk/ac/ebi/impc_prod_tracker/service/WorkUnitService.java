package uk.ac.ebi.impc_prod_tracker.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.impc_prod_tracker.data.organization.consortium.Consortium;
import uk.ac.ebi.impc_prod_tracker.data.organization.consortium.Consortium_;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_group.WorkGroup;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_unit.WorkUnit;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_unit.WorkUnitRepository;
import uk.ac.ebi.impc_prod_tracker.data.organization.work_unit.WorkUnit_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@Component
public class WorkUnitService {

    private WorkUnitRepository workUnitRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public WorkUnitService(WorkUnitRepository workUnitRepository){
        this.workUnitRepository = workUnitRepository;
    }

    public Set<WorkGroup> getWorkGroupsByWorkUnitName(String name)
    {
        WorkUnit workUnit = workUnitRepository.findWorkUnitByName(name);
        if (workUnit != null)
        {
            return workUnit.getWorkGroups();

        }
        return null;
    }

    @Transactional
    public WorkUnit getWorkUnitWithConsortia(Long id)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkUnit> query = cb.createQuery(WorkUnit.class);
        Root<WorkUnit> root = query.from(WorkUnit.class);
        query.select(root);
        root.fetch(WorkUnit_.consortia);
        query.where(cb.equal(root.get("id"), id));
        WorkUnit workUnit = null;
        try
        {
            workUnit = entityManager.createQuery(query).getSingleResult();
        }
        catch (NoResultException nre)
        {

        }

        return workUnit;
    }

}
