
/**
 *  Autori : Ragazzo Alessio 
 *           Antoniazzi Francesco Andrea
 * 
 *  Progetto : Verifica sperimentale dei sei gradi di separazione tramite
 *             l'algoritmmo di Floyd-Warshall
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Gradi_di_Separazione {

    final static int INF = 99999;
    final int V;
    
    int[][] distanze;
    
    
    public Gradi_di_Separazione(int numeroNodi,int[][] matriceAdiacenza){
        V = numeroNodi;
        this.distanze = floydWarshall(matriceAdiacenza);
        
    }

    public int getNodi() {
        return V;
    }
   
    int[][] floydWarshall(int[][] matriceAdicenza) {
        int[][] distanze = new int[V][V];
        int i, j, k;
        
        /*Copio la matrice delle adiacenze sul grafo delle distanze */
        for (i = 0; i < V; i++) 
            for (j = 0; j < V; j++)
                distanze[i][j] = matriceAdicenza[i][j];
        
        
        for (k = 0; k < V; k++) 
            for (i = 0; i < V; i++) 
                for (j = 0; j < V; j++) 
                    if (distanze[i][k] + distanze[k][j] < distanze[i][j]) 
                        distanze[i][j] = distanze[i][k] + distanze[k][j];     
        
        
        return distanze;
    }
    
    
    void stampaDistanze() {
        
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (distanze[i][j] == INF)
                    System.out.print("INF ");
                else 
                    System.out.print(distanze[i][j] + "   ");
            }
            System.out.println();
        }
    }
    
    double media(){
        double sum = 0;
        for(int i=0;i<V;i++)
            for(int j=0;j<V;j++)
                sum = sum + distanze[i][j];
        return sum / (Math.pow(V, 2) - V);     
    }
    
    int gradiSeparazione(){
        int max=0;
        int i=0;
        int j=0;
        
        while(max != INF && i<V){
            j=0;
            while(max != INF && j<i){
               if(max < distanze[i][j])
                    max = distanze[i][j]; 
               j++;
            }    
            i++;
        }  
        return max;
    }
    
    public static void main(String[] args) {
        int numeroNodi = 32;
        int graph[][] = new int[numeroNodi][numeroNodi];
        
        double media;
        
        // Inizializzo la matrice con tutti infinito 
        for (int i = 0; i < numeroNodi; i++) 
            for (int j = 0; j < numeroNodi; j++) 
                graph[i][j] = INF;
        
        // Ogni nodo ha come amici i suoi 2 vicini più stretti (si forma un cerchio)
        // NB. Il grafo creato non è orientato perchè sto creando la matrice simmetricamente
        
        for (int i = 0; i < numeroNodi; i++) {
            graph[i][i] = 0;
            
            graph[i][(i + 1) % numeroNodi] = 1;
            graph[(i + 1) % numeroNodi][i] = 1;
            
            graph[i][(i + (numeroNodi-1)) % numeroNodi] = 1;
            graph[(i + (numeroNodi-1)) % numeroNodi][i] = 1;
            
            graph[i][(i + 2) % numeroNodi] = 1;
            graph[(i + 2) % numeroNodi][i] = 1;
            
            graph[i][(i + (numeroNodi-2) ) % numeroNodi] = 1;
            graph[(i + (numeroNodi-2) ) % numeroNodi][i] = 1;
        }
        
        /**
         * Decommentare la parte successiva per aggiungere pochissimi 
         * archi casuali al grafo e vedere come i gradi di separazione diminuiscono
         * notevolmente !
        
        graph[5][25]=1;
        graph[25][5]=1;
        graph[8][30]=1;
        graph[30][8]=1;
        graph[15][22]=1;
        graph[22][15]=1;*/
        
        Gradi_di_Separazione a = new Gradi_di_Separazione(numeroNodi,graph);
        System.out.println("La matrice mostra le distanze fra ogni coppia di vertici :");
        a.stampaDistanze();
        
        
        media = a.media();
        System.out.println("La media è : " + media);
        int max = a.gradiSeparazione();
        
        if(max == INF)
            System.out.println("C'è un nodo isolato");
        else
            System.out.println("Il grado di separazione è : "+ max);    
    }
}
