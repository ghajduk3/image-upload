package si.fri.rso.image_upload.services.dao;



import com.arjuna.ats.jta.exceptions.NotImplementedException;
import jdk.jshell.spi.ExecutionControl;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.security.InvalidParameterException;
import java.util.List;

public abstract class GenericDAO<E,PK> {

    @Inject
    protected EntityManager em;

    protected Class<? extends E> entityClass;

    public GenericDAO() {

    }

    public GenericDAO(Class<? extends E> entityClass) {
        this.entityClass = entityClass;
    }

    public List<E> findAll() throws NotImplementedException {
        throw  new NotImplementedException();
    }

    public E findById(PK id) throws InvalidParameterException, EntityNotFoundException {
        E entity;

        try {
            entity = em.find(entityClass, id);
        } catch (EntityNotFoundException | NoResultException e) {
            throw new EntityNotFoundException("Entity was not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError();
        }
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    //TODO Create custom exceptions
    public E createNew(E instance) throws EntityExistsException,Exception {
        try{
            beginTx();
            em.persist(instance);
            em.flush();
            commitTx();
        }catch (Exception e){
            rollbackTx();
        }
        if(instance == null){
            throw new Exception("Null entity");
        }
        return instance;
    }

    public E update(E instance, PK id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public Boolean deleteById(PK id) throws NotImplementedException {
        E entity = findById(id);
        if(entity != null){
            try{
                beginTx();
                em.remove(entity);
                commitTx();
            }catch (Exception e){
                rollbackTx();
            }
        }else{
            return false;
        }

        return true;

    }


    protected void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    protected void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    protected void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

    }
}
