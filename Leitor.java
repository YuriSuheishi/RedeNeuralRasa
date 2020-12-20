/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Yuri
 */

public class Leitor {
    double classe;
    ArrayList<Double> dados;
    
     /**
     * @param classe
     * @param dados
     * usado no lerBaseDeDados para criar leitor para depois adicionar
     * na arraylist de leitor
     */
    public Leitor(double classe, ArrayList<Double> dados){
        this.classe = classe;
        this.dados = dados;
    }

    /**
     * @param usado para declarar o leitor no main
     */
    public Leitor(){
        
    }
    
    /**
     * @param nome, nome do aqruivo que vai ler
     * @return arraylist com classe + dados lidos
     * 
     */
    public ArrayList<Leitor> lerBaseDeDados(String nome) throws FileNotFoundException {
        ArrayList<Leitor> BD= new ArrayList<>();
        File file = new File(nome);
        Scanner bd;
        bd = new Scanner(file);
        double[] teste = new double[9];
        while (bd.hasNextLine()){
            
            String linha = bd.nextLine();
            String[] dados = linha.split(",");
                
            if("positive".equals(dados[dados.length - 1])){
                classe = 1.0;
            }
            else{
                classe = 0.0;
            }    
            
            
            
            
            ArrayList<Double> conteudo = new ArrayList<>();
            //transforma o resto dos dados em um arraylist de double, que são os dados do giroscopio
            for (int i=0; i<dados.length - 1; i++) {
                if("b".equals(dados[i])){
                    conteudo.add(0.0);
                }
                else if("x".equals(dados[i])){
                    conteudo.add(1.0);
                }    
                else{
                    conteudo.add(2.0);
                }    
            }
            
            //cria um leitor, para depois adicionar ao arraylist de leitor
            Leitor L = new Leitor(classe, conteudo);
            BD.add(L);
            }
        bd.close();
        return BD;
    }
    
}