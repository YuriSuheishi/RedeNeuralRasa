/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que implementa a rede neural para classificação binária, 
 * considerando modelo com 4 neurônios, 3 para camada interna, 
 * e um pra saída.
 * 
 * @author Yuri
 */
public class Modelo {

    private int tipo;           // define tipo da camada interna, 0 para tanh() e 1 para ReLU
    private double[][] W1;       //matriz de dimensões (n_neuroniosCamada1,n_x), peso neurônios
    private double[] W2;         //vetor de dimensão (n_neuroniosCamada1), peso neurônios
    private double[] b1;         //vetor de dimensão (n_neuroniosCamada1), offset neurônios
    private double b2;            //unitário pois só temos um neurônios na segunda camada, offset neurônios
    private final double[][] X_treino; //matriz de treinamento, de dimensao (n_treino, n_x)
    private final double[] Y_treino;   //vetor com , de dimensao n_treino
    private final double[][] X_teste;  //matriz de teste, de dimensao (n_teste, n_x)
    private final double[] Y_teste;    //vetor com rotulos das instancias, de dimensao n_teste

    public Modelo(double[][] X_treino, double[] Y_treino, double[][] X_teste, double[] Y_teste) {
        this.X_treino = X_treino;
        this.X_teste = X_teste;
        this.Y_treino = Y_treino;
        this.Y_teste = Y_teste;
       
        //no modelo utilizado usamos 3 neurônios na primeira camada
        int n_neuroniosCamada1 = 3;
        //inicializa W1,W2,b1,b2
        W1 = new double[n_neuroniosCamada1][X_treino[0].length];
        W2 = new double[n_neuroniosCamada1];
        b1 = new double[n_neuroniosCamada1];
        for (double[] row: W1){
            Arrays.fill(row, Math.random());
        }
        for(int i=0; i<W2.length;i++){
            for(int j=0; j<W1[0].length;j++){
                W1[i][j]=Math.random();
            }
        }
        for(int i=0; i<W2.length;i++){
            W2[i]=Math.random();
        }
        for(int i=0; i<b1.length;i++){
            b1[i]=(Math.random());
        }
        b2 = Math.random();
    }

    /**
     * Neurônio utilizando função de ativação tangente hiperbolica 
     * Atua na primeira camada, conjunto de n_neuroniosCamada1  
     * @param x são os valores de entrada
     * @return a1, saida da primeira camada do neurônio 
     */
    private double[] tanh(double[] x,int n_neuroniosCamada1){
        double mult[] = new double[n_neuroniosCamada1];

        //multiplicação de matriz W1(3,9) . x(9,1) = mult(3,1)
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            for (int j=0; j < x.length; j++){
                mult[i] = W1[i][j] * x[j];
            }
        }
        double z1[] = new double[n_neuroniosCamada1];
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            z1[i] = mult[i] + b1[i];
        }
        //calculando funcao tangente hiperbolica 
        double tanh[] = new double[n_neuroniosCamada1];
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            tanh[i] = (Math.exp(z1[i]) - Math.exp(-z1[i])) / (Math.exp(z1[i]) + Math.exp(z1[i])); 


        }
        return tanh; //retorna a1

    }

    
    /**
     * Neurônio utilizando função de ativação ReLU
     * Atua na primeira camada, conjunto de n_neuroniosCamada1  
     * @param x são os valores de entrada
     * @return a1, saida da primeira camada do neurônio 
     */
    private double[] relu(double[] x,int n_neuroniosCamada1){
        double mult[] = new double[n_neuroniosCamada1];

        //multiplicação de matriz W1(3,9) . x(9,1) = mult(3,1)
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            for (int j=0; j < x.length; j++){
                mult[i] += x[j] * W1[i][j];
            }
        }
        double z1[] = new double[n_neuroniosCamada1];
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            z1[i] = mult[i] + b1[i];
        }
        //calculando funcao Retified Linear Unit 
        double relu[] = new double[n_neuroniosCamada1];
        for (int i = 0; i < n_neuroniosCamada1; i++) {
            if (relu[i] < 0) {
                relu[i] = 0;
            }
        }
        return relu; //retorna a1

    }
    
    
    /**
     * Neurônio utilizando função de ativação sigmoide  
     * Atua na segunda camada, neurônio unico
     * @param a1 é um vetor com as saídas dos neurônios da primeira camada
     * @return a2, saida da segunda camada do neurônio
     */
    private double sigmoide(double[] a1) {
        double prodE = 0;

        //multiplicação de matriz W2(1,3) . a1(3,1) = prodE(1,1)
        for (int i = 0; i < a1.length; i++) {
            prodE += a1[i] * W2[i];
        }
        double z2 = prodE + b2;

        //calculando funcao sigmoide
        double sigmoide = 1 / (1 + Math.exp(-z2));
        return sigmoide; //retorna a2
    }


    /**
     * Constroi a rede Neural com base nas funcoes anteriores.
     * @param taxa_aprendizado taxa de aprendizado
     * @param num_iteracoes numero de interacoes para aprendizado do modelo
     * @param tipo_camada 0 se for tanh e 1 para ReLU
     */
    public void cria_redeNeural(double taxa_aprendizado, double num_iteracoes, int tipo_camada) {

        //treina a Rede Neural
        treina(taxa_aprendizado, num_iteracoes,tipo_camada);

        //prediz rotulos de treinamento
        double[] Y_predicao_treino = predicao(X_treino,tipo_camada);

        
//        //apenas para verificacao, comentar depois
//        for (int i = 0; i < Y_predicao_treino.length; i++) {
//            System.out.println(Y_predicao_treino[i]);
//        }

        //prediz rotulos de teste
        double[] Y_predicao_teste = predicao(X_teste,tipo_camada);

        //Exibe a taxa de acuracia do treino e do teste
        //Taxa de acuracia = 100 - MAE*100
        //Erro medio absoluto (MAE) ~~> MAE = 1/m * soma(|y_predito^(i) - y^(i)|)
        System.out.println("Acuracia de treino " + (100 - calculaMAE(Y_predicao_treino, Y_treino) * 100) + "%");
        System.out.println("Erros absolutos "+ (calculaMAE(Y_predicao_treino, Y_treino) * Y_treino.length) + " de " + Y_treino.length);
        System.out.println("Acuracia de teste " + (100 - calculaMAE(Y_predicao_teste, Y_teste) * 100) + "%"); 
        System.out.println("Erros absolutos "+ (calculaMAE(Y_predicao_teste, Y_teste) * Y_teste.length) + " de " + Y_teste.length);
//        for(int i=0;i<Y_treino.length;i++){
//            int acerto = 0;
//            if( Y_predicao_treino[i] == Y_treino[i] ){
//                acerto += 1;
//            }
//            if( Y_predicao_treino[i] == 0){
//                System.out.println("Deu zero");
//            }
//            System.out.println("Teste " + i + " predição = " + Y_predicao_treino[i] + " valor real = " + Y_treino[i] + " resultado:" + acerto + " acertos"); 
//            
//        }
    }

    /**
     * Treina a rede neural.
     * @param taxa_aprendizado taxa de aprendizado
     * @param num_iteracoes numero de interacoes para aprendizado do modelo
     * @param tipo_camada 0 se for tanh e 1 para ReLU
     */
    public void treina(double taxa_aprendizado, double num_iteracoes, int tipo){
        for (int abc = 0; abc < num_iteracoes; abc++){
        // tem q colocar aqui condição de parada, num_iterações
        double custo = 0;
        double[][] dW1 = new double[W2.length][Y_treino.length];
        double[] dW2 = new double[W2.length];
        double[] db1 = new double[b1.length];
        for (double[] row: dW1){
            Arrays.fill(row, 0);
        }
        Arrays.fill(dW2, 0);
        Arrays.fill(db1, 0);
        double db2 = 0;
        double dZ2 = 0;
        double dZ1 = 0;
        for (int i = 0; i < Y_treino.length; i++){
            //propagacao pra frente
            double a1[]= new double[b1.length]; 
            double a2;
            if(tipo == 0){
                a1 = tanh(X_treino[i],b1.length);
            }
            else{
                a1 = relu(X_treino[i],b1.length);
            }
            a2 = sigmoide(a1);
            custo += -(Y_treino[i] * Math.log(a2) + (1 - Y_treino[i]) * Math.log(1 - a2)); 
            
            //propagação pra trás
            dZ2 = a2 - Y_treino[i];
            db2 += dZ2;
            for(int k=0;k<a1.length;k++){
                dW2[k] += dZ2 * a1[k];
                dZ1 = W2[k] * dZ2 * derivada(a1[k],tipo);
                db1[k] += dZ1; 
                for(int g=0;g<X_treino[0].length;g++){
                    dW1[k][g] += dZ1 * X_treino[i][g];
                }
            }    
            
                
        }
        
        //atualiza os pesos e calcula os valores médios
        custo /= Y_treino.length;
        for(int k=0;k<dW2.length;k++){
            dW2[k] /= Y_treino.length;
            db1[k] /= Y_treino.length;
            W2[k] = W2[k] - taxa_aprendizado * dW2[k];
            b1[k] = b1[k] - taxa_aprendizado * db1[k];
            for(int g=0;g<X_treino[0].length;g++){
                dW1[k][g] /= Y_treino.length;
                W1[k][g] = W1[k][g] - taxa_aprendizado * dW1[k][g];
            }
        }
	db2 /= Y_treino.length;
        b2 = b2 - taxa_aprendizado * db2;
    } // fim do treino
}
    
    /**
     * Prediz se uma instancia é 0 ou 1, utilizando os parametros w e b treinados atraves de
     * da rede neural
     * "Treino é treino, jogo é jogo" - Não sei
     * @param X conjunto de dados de dimensoes (n_treino, n_x)
     * @param tipo 0 se for tanh e 1 para ReLU
     * @return Y, um vetor com predicoes de dimensao n_teste
     */
    private double[] predicao(double[][] X, int tipo) {
        //literalmente copiei a propagacao pra frente, alias, parando pra pensar, eu devia ter feito um método propagação
        //mas acabei deixando um pouco pra ultima hora, admito.
        double[] Y = new double[X.length];
        double a1[]= new double[b1.length]; 
        double a2;

        //para cada instancia
        for (int i = 0; i < X.length; i++) {
            if(tipo == 0){
                a1 = tanh(X_treino[i],b1.length);
            }
            else{
                a1 = relu(X_treino[i],b1.length);
            }
            a2 = sigmoide(a1);

            //converte probabilidades em predicoes
            if (a2 > 0.5) {
                Y[i] = 1;
            } else {
                Y[i] = 0;
            }
        }

        return Y;
    }
    
    /**
     * Calcula a derivada da função tanh e relu, 
     * como não sabia se iria usar a derivada da sigmoide enquanto estava fazendo
     * eu coloquei por precaução
     *
     * @param double a, saida do neurônio
     * @param int tipo da função, 0 tanh, 1 relu, qualquer outro sigmoide
     * @return o valor do erro medio absoluto
     */
    private double derivada(double a,int tipo){
        if(tipo == 0){
                return 1 - a * a;
            }
        if(tipo == 1){
                if(a>0){
                    return 1;
                }    
                else{
                    return 0;
                }    
            }
        return a * (1-a);
    }
    
    /**
     * Calcula o erro medio absoluto (MAE).
     * também peguei da regressão logistica, já que o calculo é o mesmo
     *
     * @param Y_predicao vetor com as predicoes, dimensao m
     * @param Y vetor com os rotulos reais, dimensao m
     * @return o valor do erro medio absoluto
     */
    private double calculaMAE(double[] Y_predicao, double[] Y) {
        double mae = 0;
        for (int i = 0; i < Y.length; i++) {
            mae += Math.abs(Y_predicao[i] - Y[i]);
        }
        mae /= Y.length;

        return mae;
    }
}















