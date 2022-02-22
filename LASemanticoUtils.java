package br.ufscar.dc.compiladores.lasemantico;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;


//1. Identificador já declarado anteriormente no escopo
//2. Tipo não declarado
//3. Identificador (variável, constante, procedimento, função) não declarado
//4. Incompatibilidade entre argumentos e parâmetros formais (número, ordem e tipo) na
//chamada de um procedimento ou uma função
//5. Atribuição não compatível com o tipo declarado
//6. Uso do comando 'retorne' em um escopo não permitido

public class LASemanticoUtils {
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        errosSemanticos.add(String.format("Linha %d: %s" + System.lineSeparator(), linha, mensagem));
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.ExpressaoContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(!ctx.op_logico_1().isEmpty()){
            return TabeladeSimbolos.TipoLA.LOGICO;
        }
        
        for (var tl : ctx.termo_logico()) {
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, tl);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }

        return ret;
    }

    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Termo_logicoContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;

        if(!ctx.op_logico_2().isEmpty()){
            return TabeladeSimbolos.TipoLA.LOGICO;
        }
        
        for (var fl : ctx.fator_logico()) {
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, fl);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Fator_logicoContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.parcela_logica());
        if (ret == null) {
            ret = aux;
        } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
            ret = TabeladeSimbolos.TipoLA.INVALIDO;
        }
        
        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Parcela_logicaContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.exp_relacional() != null){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.exp_relacional());
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        return ret;
        }
        
        return TabeladeSimbolos.TipoLA.LOGICO;
    }
        
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Exp_relacionalContext ctx){
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.op_relacional() != null ){
            return TabeladeSimbolos.TipoLA.LOGICO;
        }
        
        
        for(var ea : ctx.exp_aritmetica()){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ea);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
     public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Exp_aritmeticaContext ctx){
        TabeladeSimbolos.TipoLA ret = null;
        
        for(var termo : ctx.termo()){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, termo);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.FatorContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
                
       for (var parcela : ctx.parcela()) {
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, parcela);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.ParcelaContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.parcela_unario() != null){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.parcela_unario());
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        else if(!ctx.parcela_nao_unario().isEmpty()){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.parcela_nao_unario());
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Parcela_unarioContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.IDENT() != null){
            ret = TabeladeSimbolos.TipoLA.LITERAL;
        }
        else if(ctx.NUM_INT() != null){
            ret = TabeladeSimbolos.TipoLA.INTEIRO;
        }
        else if(ctx.NUM_REAL() != null){
            ret = TabeladeSimbolos.TipoLA.REAL;
        }
        else if(ctx.identificador() != null){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.identificador().getText());
                if (ret == null) {
                    ret = aux;
                } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                    ret = TabeladeSimbolos.TipoLA.INVALIDO;
                }
        }
        else if(ctx.expressao() != null){
            for(var ex : ctx.expressao()){
                TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ex);
                if (ret == null) {
                    ret = aux;
                } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                    ret = TabeladeSimbolos.TipoLA.INVALIDO;
                }   
            }
        }

        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.Parcela_nao_unarioContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.CADEIA() != null){
            ret = TabeladeSimbolos.TipoLA.LITERAL;
        }
        else if(ctx.identificador() != null){
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, ctx.identificador().IDENT(0).getText());
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, LASemanticoParser.TermoContext ctx) {
        TabeladeSimbolos.TipoLA ret = null;
        
        if(ctx.getText() == "verdadeiro" || ctx.getText() == "falso"){
            return TabeladeSimbolos.TipoLA.LOGICO;
        }

       for (var fator : ctx.fator()) {
            TabeladeSimbolos.TipoLA aux = verificarTipo(tabela, fator);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabeladeSimbolos.TipoLA.INVALIDO) {
                ret = TabeladeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabeladeSimbolos.TipoLA verificarTipo(TabeladeSimbolos tabela, String nomeVar) {
        return tabela.verificar(nomeVar);
    }
}
