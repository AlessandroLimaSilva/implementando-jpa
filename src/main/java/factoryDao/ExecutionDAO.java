package factoryDao;

import model.Aluno;
import model.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ExecutionDAO {

    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlunoDAO2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Estado estadoParaAdicionar = new Estado("São Paulo","SP");
        Estado estadoParaAdicionar2 = new Estado("São Paulo","SP");
        Aluno alunoParaAdicionar = new Aluno("Jose Carlos",61, estadoParaAdicionar);
        Aluno alunoParaAdicionar2 = new Aluno("Rebecca de Lima",4, estadoParaAdicionar2);
        Aluno alunoParaAdicionar3 = new Aluno("Jose Carlos",61, estadoParaAdicionar);

        entityManager.getTransaction().begin();

        entityManager.persist(estadoParaAdicionar);
        entityManager.persist(alunoParaAdicionar);
        entityManager.persist(estadoParaAdicionar2);
        entityManager.persist(alunoParaAdicionar2);
        entityManager.persist(estadoParaAdicionar2);
        entityManager.persist(alunoParaAdicionar3);


        entityManager.getTransaction().commit();

        //Estado estadoEncontrado = entityManager.find(Estado.class,1);
        Aluno alunoEncontrado = entityManager.find(Aluno.class,1);
        //System.out.println(estadoEncontrado);
        System.out.println(alunoEncontrado);


        //Alterar Update
        entityManager.getTransaction().begin();
        alunoEncontrado.setNome("Jose Carlos da Silva");
        alunoEncontrado.setIdade(60);
        //alunoEncontrado.setEstado(new Estado("Lisboa","LB"));
        entityManager.getTransaction().commit();

        Aluno alunoEncontrado2 = entityManager.find(Aluno.class,1);
        System.out.println(alunoEncontrado2);

        //Remover um elemento do banco
        entityManager.getTransaction().begin();
        entityManager.remove(alunoEncontrado2);
        entityManager.getTransaction().commit();


        String nome = "Rebecca de Lima";
        //Executando Querys
        /*String nome = "carlos";
        String sql = "SELECT FROM * one.Aluno WHERE nome = :nome";
        Aluno alunoSQL = (Aluno) entityManager.
                createNamedQuery(sql,Aluno.class).setParameter("nome",nome).
                getSingleResult();
        */

        entityManager.getTransaction().begin();
        String sqlList = "SELECT * FROM one.Aluno;";
        List alunoSQLList = entityManager.
                createNativeQuery(sqlList,Aluno.class).
                getResultList();
        System.out.println("Lista String sql");
        for(Object lista: alunoSQLList){
            System.out.println(lista);
        }

       /* String jpql = "select a from Aluno a where a.nome = :nome";
        Aluno alunoJPQL = entityManager.
                createQuery(jpql,Aluno.class).
                setParameter("nome",nome).
                getSingleResult();
        */

        String jpqlList = "select a from Aluno a";
        List<Aluno> alunoJPQL = entityManager.createQuery(jpqlList,Aluno.class).getResultList();

        System.out.println("Lista JPQL sql");
        for(Object lista: alunoJPQL){
            System.out.println(lista);
        }

        CriteriaQuery<Aluno> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Aluno.class);
        Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
        CriteriaBuilder.In<String> inCause = entityManager.getCriteriaBuilder().in(alunoRoot.get(nome));
        inCause.value("nome");
        criteriaQuery.select(alunoRoot).where(inCause);
        Aluno alunoAPICriteria = entityManager.createQuery(criteriaQuery).getSingleResult();
        System.out.println("Criteria = nome = "+alunoAPICriteria);
        entityManager.close();
        entityManagerFactory.close();

    }


}
