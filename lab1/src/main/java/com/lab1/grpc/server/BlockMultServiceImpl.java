package com.lab1.grpc.server;

import com.lab1.grpc.MatrixRequest;
import com.lab1.grpc.MatrixResponse;
import com.lab1.grpc.BlockMultServiceGrpc.BlockMultServiceImplBase;

import io.grpc.stub.StreamObserver;

import java.util.Arrays;

public class BlockMultServiceImpl extends BlockMultServiceImplBase {

	public static final int MAX = 4;

	@Override
	public void addBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {
		System.out.println("addBlock called");
		requestHandler(request, responseObserver, "add");
	}

	@Override
	public void multiplyBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {
		System.out.println("multiplyBlock called");
		requestHandler(request, responseObserver, "multiply");
	}

	/**
	 * Handles the gRPC request for both addBlock and multiplyBlock methods
	 */
	private void requestHandler(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver, String operation) {

		// decode matrixA and matrixB from the request
		int[][] decodedMatrixA = decodeMatrix(request.getMatrixA());
		int[][] decodedMatrixB = decodeMatrix(request.getMatrixB());

		// define the matrix operation result to be of size MAX, MAX
		int[][] result = new int[MAX][MAX];

		switch(operation) {
			case "add":
				result = addMatrices(decodedMatrixA, decodedMatrixB);
				break;
			case "multiply":
				result = multiplyMatrices(decodedMatrixA, decodedMatrixB);
				break;
			default:
				System.out.println("Cannot recognise operation: " + operation);
				return;
		}

		// encode the resultant matrix as a string
		String encodedMatrix = encodeMatrix(result);

		// generate the matrix response object
		MatrixResponse response = MatrixResponse.newBuilder()
			.setMatrix(encodedMatrix)
			.build();

		// send response of gRPC
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	/**
	 * Sums two matrices
	 */
	private static int[][] addMatrices(int[][] matrixA, int[][]matrixB) {

		int[][] result = new int[MAX][MAX];

		for (int i=0; i<result.length; i++) {
			for (int j=0; j < result.length; j++) {
				result[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}
		return result; 
	}

	/**
	 * Multiply two matrices together
	 */
	private static int[][] multiplyMatrices(int A[][], int B[][]) {

		int C[][]= new int[MAX][MAX];

		C[0][0]=A[0][0]*B[0][0]+A[0][1]*B[1][0];
		C[0][1]=A[0][0]*B[0][1]+A[0][1]*B[1][1];
		C[1][0]=A[1][0]*B[0][0]+A[1][1]*B[1][0];
		C[1][1]=A[1][0]*B[0][1]+A[1][1]*B[1][1];

		return C;
	}

	/**
	 * Takes a matrix string turns it into a 2D integer array
	 */
	private int[][] decodeMatrix(String matrixString) {
		return stringToDeep(matrixString); 
	}

	/**
	 * Takes a 2D integer array and turns it into an encoded string
	 */
	private String encodeMatrix(int[][] matrix) {
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

}
