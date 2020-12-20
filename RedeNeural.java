/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RedeNeural{

    public static void main(String[] args) throws FileNotFoundException {
        //pegando os dados
        Leitor x = new Leitor();
                
        //array de arraylist, banco de dados extraído de https://archive.ics.uci.edu/ml/datasets/Tic-Tac-Toe+Endgame
        ArrayList<Leitor>[] bdd = new ArrayList[2]; 

        //separação 70% treino, 30% teste, respeitando a proporção de classe 1 e 0
        bdd[0] = x.lerBaseDeDados("treino.txt");
        bdd[1] = x.lerBaseDeDados("teste.txt");

        //X = bdd[i].get(i).dados (Arraylist<Double>)
        // 0 = b, 1 = x, 2 = o
        //Y = bdd[i].get(i).classe (Double, onde 0)
        // 0 = derrota, 1 = vitória (em relação a X)
        double [][] treinoX = new double[bdd[0].size()][9];
        double [] treinoY = new double[bdd[0].size()];
        
        for(int i=0;i<bdd[0].size();i++){
            treinoY[i]=bdd[0].get(i).classe;
            for(int j=0;j<bdd[0].get(i).dados.size();j++){
                treinoX[i][j]=bdd[0].get(i).dados.get(j);
            }
        }
        
        double [][] testeX = new double[bdd[1].size()][9];
        double [] testeY = new double[bdd[1].size()];
        
        for(int k=0;k<bdd[1].size();k++){
            testeY[k]=bdd[1].get(k).classe;
            for(int l=0;l<bdd[1].get(k).dados.size();l++){
                testeX[k][l]=bdd[1].get(k).dados.get(l);
            }
        }
        
        Modelo T = new Modelo(treinoX, treinoY, testeX, testeY);
        T.cria_redeNeural(0.01, 1000, 0);
        
    }
}
