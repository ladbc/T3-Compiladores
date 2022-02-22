package br.ufscar.dc.compiladores.lasemantico;

/**
 *
 * @author leadcunha
 */

import br.ufscar.dc.compiladores.lasemantico.TabeladeSimbolos.TipoLA;

public class Calculator extends LASemanticoBaseVisitor<Void> {

    TabeladeSimbolos tabela;

    @Override
    public Void visitPrograma(LASemanticoParser.ProgramaContext ctx) {
        tabela = new TabeladeSimbolos();
        return super.visitPrograma(ctx);
    }
    

    @Override
    public Void visitVariavel(LASemanticoParser.VariavelContext ctx) {
        var nomesVar = ctx.identificador();
        TipoLA tipoVar = TipoLA.INVALIDO;
        
        if(ctx.tipo().tipo_estendido() != null){
            if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
                String strTipoVar = ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText();
            
                switch (strTipoVar) {
                    case "inteiro":
                        tipoVar = TipoLA.INTEIRO;
                        break;
                    case "real":
                        tipoVar = TipoLA.REAL;
                        break;
                    case "literal":
                        tipoVar = TipoLA.LITERAL;
                        break;
                    case "logico":
                        tipoVar = TipoLA.LOGICO;
                        break;
                    default:
                        break;
                }
            }
        }
        
        if(ctx.tipo().registro() != null){
            
        }

        int index = 0;
        for(var nomeVar : nomesVar){
            String nome = nomeVar.getText();
            
            if (tabela.existe(nome)) {
            LASemanticoUtils.adicionarErroSemantico(
                    ctx.identificador().get(index).IDENT(0).getSymbol(), 
                    "identificador " + nome + " ja declarado anteriormente");
            } else {
                tabela.adicionar(nome, tipoVar);
            }
            index++;
        }

        return super.visitVariavel(ctx);
    }
    
    @Override
    public Void visitIdentificador(LASemanticoParser.IdentificadorContext ctx){
        var nome = ctx.IDENT(0).getText();
        if(!tabela.existe(nome)){
            LASemanticoUtils.adicionarErroSemantico(
                    ctx.IDENT(0).getSymbol(), 
                    "identificador " + nome + " nao declarado");
        }   
        
        return super.visitIdentificador(ctx);
    }
    
    @Override
    public Void visitTipo_basico_ident(LASemanticoParser.Tipo_basico_identContext ctx){
        
        if(ctx.IDENT() != null){
            var tipoIdent = ctx.IDENT().getText();
            if(!tabela.existe(tipoIdent)){
            LASemanticoUtils.adicionarErroSemantico(
                    ctx.IDENT().getSymbol(), 
                    "tipo " + tipoIdent + " nao declarado");
            } 
        }
        
        return super.visitTipo_basico_ident(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(LASemanticoParser.CmdAtribuicaoContext ctx) {
        var variavel = ctx.identificador().getText();
        TipoLA tipoVariavel = tabela.verificar(variavel);
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, ctx.expressao());

        if (tipoVariavel != tipoExpressao) {
            LASemanticoUtils.adicionarErroSemantico(
                ctx.identificador().IDENT(0).getSymbol(),
                    "atribuicao nao compativel para " + variavel);
        }
  
        return super.visitCmdAtribuicao(ctx);
    }
    
    @Override
    public Void visitCmdEnquanto(LASemanticoParser.CmdEnquantoContext ctx){
        var variavel = ctx.expressao().getText();
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, ctx.expressao());
  
        return super.visitCmdEnquanto(ctx);
    }    
    
}