package model;

import com.mysql.cj.util.EscapeTokenizer;

import javax.persistence.*;

//@Entity anotação responsavel por transformar a classe em uma tabela do banco de dados
@Entity
public class Aluno {

    //Passa para o banco qual atributo é o id da tabela
    @Id
    //Atribui ao banco de dados a responsabilidade de gerar o numero do id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Mapeia o atributo como uma coluna do banco de dados
    @Column
    private String nome;

    //Mapeia o atributo como uma coluna no banco de dados
    @Column
    private int idade;

    //Relacionamento de aluno para estado//Um estado pode ter varios alunos
    //Muitos alunos podem morar em um estado
    //Mapeiamento Bidirecional
    //fetch EAGER(Ansiozo)A entidade mapeada com esse atributo sempre sera carregada na aplicação
    //quando a entidade que esta mapeando for consultada,mesmo que nunca seja utilizada
    //fetch LAZY(Preguiçoso)A entidade mapeada com esse atributo somente sera carregada na aplicação
    //quando esta for explicitamente consultada pela entidade que esta mapeando(Recomendada se houver
    //Grande quantiadade de copnsultas
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;

    public Aluno(){}

    public Aluno(String nome, int idade, Estado estado){
        this.nome = nome;
        this.idade = idade;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String toString(){
        String retorno = "\nId = "+getId()+", Nome = "+getNome()+", Idade = "+getIdade()+", Estado = "+getEstado()+"\n";
        return retorno;
    }


}
