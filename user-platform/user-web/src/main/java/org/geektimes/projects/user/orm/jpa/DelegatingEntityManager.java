package org.geektimes.projects.user.orm.jpa;

import org.geektimes.context.ComponentContext;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * DelegatingEntityManager
 *
 * @author qrXun on 2021/3/9
 */
public class DelegatingEntityManager implements EntityManager{

    private EntityManager entityManager;

    private String propertiesLocation;

    private String persistenceUnitName;

    @PostConstruct
    public void init(){
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(persistenceUnitName, loadProperties(propertiesLocation));
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    private Map loadProperties(String propertiesLocation) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertiesFileURL = classLoader.getResource(propertiesLocation);
        Properties properties = new Properties();
        try {
            properties.load(propertiesFileURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComponentContext componentContext = ComponentContext.getInstance();
        // 获取对象引用
        for (String propertyName : properties.stringPropertyNames()){
            String propertyValue = properties.getProperty(propertyName);
            if (propertyValue.startsWith("@")){
                // 包含需要进行对象引用的属性
                Object component = componentContext.getComponent(propertyValue.substring(1));
                properties.put(propertyName, component);
            }
        }
        return properties;
    }

    public void setPropertiesLocation(String propertiesLocation) {
        this.propertiesLocation = propertiesLocation;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public void persist(Object entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();

    }

    @Override
    public <T> T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void remove(Object entity) {
        entityManager.remove(entity);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        return entityManager.find(entityClass, primaryKey, properties);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        return entityManager.find(entityClass, primaryKey, lockMode);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        return entityManager.find(entityClass, primaryKey, lockMode, properties);
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        return entityManager.getReference(entityClass, primaryKey);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        entityManager.setFlushMode(flushMode);
    }

    @Override
    public FlushModeType getFlushMode() {
        return entityManager.getFlushMode();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        entityManager.lock(entity, lockMode);
    }

    @Override
    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        entityManager.lock(entity, lockMode, properties);
    }

    @Override
    public void refresh(Object entity) {
        entityManager.refresh(entity);
    }

    @Override
    public void refresh(Object entity, Map<String, Object> properties) {
        entityManager.refresh(entity, properties);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode) {
        entityManager.refresh(entity, lockMode);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        entityManager.refresh(entity, lockMode, properties);
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public void detach(Object entity) {
        entityManager.detach(entity);
    }

    @Override
    public boolean contains(Object entity) {
        return entityManager.contains(entity);
    }

    @Override
    public LockModeType getLockMode(Object entity) {
        return entityManager.getLockMode(entity);
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        entityManager.setProperty(propertyName, value);
    }

    @Override
    public Map<String, Object> getProperties() {
        return entityManager.getProperties();
    }

    @Override
    public Query createQuery(String qlString) {
        return entityManager.createQuery(qlString);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    @Override
    public Query createQuery(CriteriaUpdate updateQuery) {
        return entityManager.createQuery(updateQuery);
    }

    @Override
    public Query createQuery(CriteriaDelete deleteQuery) {
        return entityManager.createQuery(deleteQuery);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        return entityManager.createQuery(qlString, resultClass);
    }

    @Override
    public Query createNamedQuery(String name) {
        return entityManager.createNamedQuery(name);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
        return entityManager.createNamedQuery(name, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        return entityManager.createNativeQuery(sqlString);
    }

    @Override
    public Query createNativeQuery(String sqlString, Class resultClass) {
        return entityManager.createNativeQuery(sqlString, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        return entityManager.createNativeQuery(sqlString, resultSetMapping);
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
        return entityManager.createNamedStoredProcedureQuery(name);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
        return entityManager.createStoredProcedureQuery(procedureName);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
        return entityManager.createStoredProcedureQuery(procedureName, resultClasses);
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
        return entityManager.createStoredProcedureQuery(procedureName, resultSetMappings);
    }

    @Override
    public void joinTransaction() {
        entityManager.joinTransaction();
    }

    @Override
    public boolean isJoinedToTransaction() {
        return entityManager.isJoinedToTransaction();
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return entityManager.unwrap(cls);
    }

    @Override
    public Object getDelegate() {
        return entityManager.getDelegate();
    }

    @Override
    public void close() {
        entityManager.close();
    }

    @Override
    public boolean isOpen() {
        return entityManager.isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManager.getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return entityManager.getMetamodel();
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
        return entityManager.createEntityGraph(rootType);
    }

    @Override
    public EntityGraph<?> createEntityGraph(String graphName) {
        return entityManager.createEntityGraph(graphName);
    }

    @Override
    public EntityGraph<?> getEntityGraph(String graphName) {
        return entityManager.getEntityGraph(graphName);
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
        return entityManager.getEntityGraphs(entityClass);
    }


}
