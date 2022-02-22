package br.ufscar.dc.compiladores.lasemantico;
import static br.ufscar.dc.compiladores.lasemantico.LASemanticoUtils.errosSemanticos;
import java.util.HashMap;
import java.util.List;

public class TabeladeSimbolos {
    private HashMap<String, EntradaTabeladeSimbolos> _tabelaSimbolos;
    
    public TabeladeSimbolos(){
        _tabelaSimbolos = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoLA tipo) {
        errosSemanticos.add(String.format("Adicionou na tabela %s: %s" + System.lineSeparator(), nome, tipo.toString()));
        _tabelaSimbolos.put(nome, new EntradaTabeladeSimbolos(nome, tipo));
    }
    
    public boolean existe(String nome) {
        return _tabelaSimbolos.containsKey(nome);
    }
    
    public TipoLA verificar(String nome) {
        return _tabelaSimbolos.get(nome)._tipo;
    }
    
    public enum TipoLA {
        INTEIRO,
        REAL,
        LITERAL,
        LOGICO,
        CADEIA,
        INVALIDO
    }
    
    public class EntradaTabeladeSimbolos {
        public String _nome;
        public TipoLA _tipo;

        private EntradaTabeladeSimbolos(
                String nome, 
                TipoLA tipo) {
            _nome = nome;
            _tipo = tipo;
        }
    }
}
