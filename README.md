O professor nos passou esse pseudocódigo.

	Entrada: X,Y,m,a
	Inicializar W1,W2,b1,b2 aleatoriamente
	Enquanto(não convergir)
		j=0;
		dW1=0;
		dW2=0;
		b1=0;
		b2=0;
		para i=1 até m
			Z1[i] = W1 . X[i] + b1;
			a1[i] = g(Z1[i]); 
			Z2[i] = W2 . a1[i] + b2;
			a2[i] = sig(Z2[i]);

			g() pode ser tanh() ou reLU
	
			j += -[Y[i] . log(a2[i]) + (1 - Y[i]) . log(1-a2[i])];

			dZ2 = a2[i] - Y[i];
			dW2 += dZ2 . a1[i]^t;
			db2 += dZ2;

			dZ1 = W2^t . dZ2 * g1' . (Z1);
			dW1 += dZ1 . x[i]^t;
			db1 += dZ1; 
		j /= m;
		dW1 /= m;
		db1 /= m;
		dW2 /= m;
		db2 /= m;

		A = taxa de aprendizado

		W2 := 	W2 - A . dW2;
		b2 := b2 - A . db2;
		W1 := W1 - A . dW1;
		b1 := b1 - A ; db1;

	Saida: W1, W2, b1, b2

Nós tivemos que implementa-lo e dividir a base de dados em um esquema 70-30. 
podendo implementar o código em Java ou Python
