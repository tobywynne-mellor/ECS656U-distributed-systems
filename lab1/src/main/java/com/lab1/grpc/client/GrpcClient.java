package com.lab1.grpc.client;

import com.lab1.grpc.MatrixRequest;
import com.lab1.grpc.MatrixResponse;
import com.lab1.grpc.BlockMultServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;

public class GrpcClient {

	private static final int MAX = 4;
	private static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
		.usePlaintext()
		.build();

	private static BlockMultServiceGrpc.BlockMultServiceBlockingStub stub 
		= BlockMultServiceGrpc.newBlockingStub(channel);

	public static void main(String[] args) throws InterruptedException {

		// define matrices as in BlockMult.java
		int[][] A = { {1, 2, 3, 4},
		              {5, 6, 7, 8},
		              {9, 10, 11, 12},
		              {13, 14, 15, 16}};

		int[][] B = { {1, 2, 3, 4},
   			      {5, 6, 7, 8},
			      {9, 10, 11, 12},
			      {13, 14, 15, 16}};

		int[][] multipliedMatrixBlock = multiplyMatrixBlock(A, B);

		System.out.println("Matrix A: \n" + Arrays.deepToString(A) + "\nMatrix B:\n" + Arrays.deepToString(B));
		System.out.println("Multiplied: \n" + Arrays.deepToString(multipliedMatrixBlock));

		channel.shutdown();
	}

	/**
	 * Add integer matrices via gRPC
	 */
	private static int[][] addBlock(int A[][], int B[][]) {
		System.out.println("Calling addBlock on stub");
		MatrixRequest request = generateRequest(A, B);
		MatrixResponse matrixAddResponse = stub.addBlock(request);
		int[][] summedMatrix = decodeMatrix(matrixAddResponse.getMatrix());
		return summedMatrix;
	}

	/**
	 * Multiply integer matrices via gRPC
	 */
	private static int[][] multiplyBlock(int A[][], int B[][]) {
		System.out.println("Calling multiplyBlock on stub");
		MatrixRequest request = generateRequest(A, B);
		MatrixResponse matrixMultiplyResponse = stub.multiplyBlock(request);
		int[][] multipliedMatrix = decodeMatrix(matrixMultiplyResponse.getMatrix());
		return multipliedMatrix;
	}

	/**
	 *  encode the matrices and return a MatrixRequest object
	 */
	private static MatrixRequest generateRequest(int A[][], int B[][]) {
		String matrixA = encodeMatrix(A);
		String matrixB = encodeMatrix(B);

		MatrixRequest request = MatrixRequest.newBuilder()
			.setMatrixA(matrixA)
			.setMatrixB(matrixB)
			.build();

			return request;
	}

	/**
	 * Takes a matrix string turns it into a 2D integer array
	 */
	private static int[][] decodeMatrix(String matrixString) {
		return stringToDeep(matrixString); 
	}

	/**
	 * Takes a 2D integer array and turns it into an encoded string
	 */
	private static String encodeMatrix(int[][] matrix) {
		return Arrays.deepToString(matrix);
	}

	/**
	 * Turns array.toDeepString() back to array[][] 
	 * https://stackoverflow.com/questions/22377447/java-multidimensional-array-to-string-and-string-to-array/22428926#22428926
	 */
	private static int[][] stringToDeep(String str) {
		int row = 0;
		int col = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '[') {
				row++;
			}
		}
		row--;
		for (int i = 0;; i++) {
			if (str.charAt(i) == ',') {
				col++;
			}
			if (str.charAt(i) == ']') {
				break;
			}
		}
		col++;

		int[][] out = new int[row][col];

		str = str.replaceAll("\\[", "").replaceAll("\\]", "");

		String[] s1 = str.split(", ");

		int j = -1;
		for (int i = 0; i < s1.length; i++) {
			if (i % col == 0) {
				j++;
			}
			out[j][i % col] = Integer.parseInt(s1[i]);
		}
		return out;
	}

	/** 
	 * Multiplies matrices using addBlock and multiplyBlock 
	 * From BlockMult.java
	 */	
	private static int[][] multiplyMatrixBlock( int A[][], int B[][]) {
		int bSize=2;
		int[][] A1 = new int[MAX][MAX];
		int[][] A2 = new int[MAX][MAX];
		int[][] A3 = new int[MAX][MAX];
		int[][] B1 = new int[MAX][MAX];
		int[][] B2 = new int[MAX][MAX];
		int[][] B3 = new int[MAX][MAX];
		int[][] C1 = new int[MAX][MAX];
		int[][] C2 = new int[MAX][MAX];
		int[][] C3 = new int[MAX][MAX];
		int[][] D1 = new int[MAX][MAX];
		int[][] D2 = new int[MAX][MAX];
		int[][] D3 = new int[MAX][MAX];
		int[][] res= new int[MAX][MAX];
		for (int i = 0; i < bSize; i++) 
		{ 
			for (int j = 0; j < bSize; j++)
			{
				A1[i][j]=A[i][j];
				A2[i][j]=B[i][j];
			}
		}
		for (int i = 0; i < bSize; i++) 
		{ 
			for (int j = bSize; j < MAX; j++)
			{
				B1[i][j-bSize]=A[i][j];
				B2[i][j-bSize]=B[i][j];
			}
		}
		for (int i = bSize; i < MAX; i++) 
		{ 
			for (int j = 0; j < bSize; j++)
			{
				C1[i-bSize][j]=A[i][j];
				C2[i-bSize][j]=B[i][j];
			}
		} 
		for (int i = bSize; i < MAX; i++) 
		{ 
			for (int j = bSize; j < MAX; j++)
			{
				D1[i-bSize][j-bSize]=A[i][j];
				D2[i-bSize][j-bSize]=B[i][j];
			}
		}  
		A3=addBlock(multiplyBlock(A1,A2),multiplyBlock(B1,C2));
		B3=addBlock(multiplyBlock(A1,B2),multiplyBlock(B1,D2));
		C3=addBlock(multiplyBlock(C1,A2),multiplyBlock(D1,C2));
		D3=addBlock(multiplyBlock(C1,B2),multiplyBlock(D1,D2));
		for (int i = 0; i < bSize; i++) 
		{ 
			for (int j = 0; j < bSize; j++)
			{
				res[i][j]=A3[i][j];
			}
		}
		for (int i = 0; i < bSize; i++) 
		{ 
			for (int j = bSize; j < MAX; j++)
			{
				res[i][j]=B3[i][j-bSize];
			}
		}
		for (int i = bSize; i < MAX; i++) 
		{ 
			for (int j = 0; j < bSize; j++)
			{
				res[i][j]=C3[i-bSize][j];
			}
		} 
		for (int i = bSize; i < MAX; i++) 
		{ 
			for (int j = bSize; j < MAX; j++)
			{
				res[i][j]=D3[i-bSize][j-bSize];
			}
		} 
		/*
		for (int i=0; i<MAX; i++)
		{
			for (int j=0; j<MAX;j++)
			{
				System.out.print(res[i][j]+" ");
			}
			System.out.println("");
		}
		*/
		return res;
	}
}
