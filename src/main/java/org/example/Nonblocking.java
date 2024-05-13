package org.example;
import mpi.*;

import static java.lang.System.exit;

public class Nonblocking {
    private static int matrixSize = 1000;
    private static int MASTER = 0;
    private static int FROM_MASTER = 1;
    private static int FROM_WORKER = 1;

    public static void main(String[] args) throws MPIException {
        double[][] matrixA = Utils.generateMatrix(matrixSize);
        double[][] matrixB = Utils.generateMatrix(matrixSize);
        double[][] resultMatrix = new double[matrixSize][matrixSize];
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        MPI.Init(args);
        int size = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();

        int workersCount = size - 1;

        if(size < 2 || matrixSize < workersCount){
            System.out.println("Need at least two MPI tasks");
            MPI.COMM_WORLD.Abort(1);
            exit(1);
        }

        if(rank == MASTER){
            System.out.println("Matrix size " + matrixSize);
            System.out.println("Started with " + size + " tasks");
            int work = matrixSize / workersCount;
            int extra = matrixSize % workersCount;
            for(int worker = 1; worker <= workersCount; worker++){
                int rowStart = (worker - 1) * work;
                int rowEnd = rowStart + work;
                if(worker == workersCount){
                    rowEnd += extra;
                }
                Request req1 = MPI.COMM_WORLD.Isend(new int[]{rowStart}, 0,  1, MPI.INT, worker, FROM_MASTER);
                Request req2 =  MPI.COMM_WORLD.Isend(new int[]{rowEnd}, 0,  1, MPI.INT, worker, FROM_MASTER);

                double[][] blockA = Utils.extractBlock(matrixA, rowStart, rowEnd, matrixSize);

                Request req3 =  MPI.COMM_WORLD.Isend(blockA, 0, blockA.length, MPI.OBJECT, worker, FROM_MASTER);
                Request req4 = MPI.COMM_WORLD.Isend(matrixB, 0, matrixB.length, MPI.OBJECT, worker, FROM_MASTER);

                req1.Wait();
                req2.Wait();
                req3.Wait();
                req4.Wait();
            }

            for(int worker = 1; worker <= workersCount; worker++){
                int[] rowStart = new int[1];
                int[] rowEnd = new int[1];

                Request res1 = MPI.COMM_WORLD.Irecv(rowStart, 0, 1, MPI.INT, worker, FROM_WORKER);
                Request res2 = MPI.COMM_WORLD.Irecv(rowEnd, 0, 1, MPI.INT, worker, FROM_WORKER);

                res1.Wait();
                res2.Wait();

                double[][] openedResult = new double[rowEnd[0] - rowStart[0] + 1][matrixSize];

                Request res3 = MPI.COMM_WORLD.Irecv(openedResult, 0, openedResult.length, MPI.OBJECT, worker, FROM_WORKER);
                res3.Wait();

                Utils.addBlock(openedResult, resultMatrix, rowStart[0], rowEnd[0]);
            }
            endTime = System.currentTimeMillis();
            System.out.println("Result time: " +(endTime - startTime) + " ms");
        } else {
            int[] rowStart = new int[1];
            int[] rowEnd = new int[1];
            Request res1 = MPI.COMM_WORLD.Irecv(rowStart, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            Request res2 = MPI.COMM_WORLD.Irecv(rowEnd, 0,  1, MPI.INT, MASTER, FROM_MASTER);

            res1.Wait();
            res2.Wait();

            double[][] blockA = new double[rowEnd[0] - rowStart[0] + 1][matrixSize];
            double[][] updatedMatrixB = new double[matrixSize][matrixSize];

            Request res3 = MPI.COMM_WORLD.Irecv(blockA, 0, blockA.length, MPI.OBJECT, MASTER, FROM_MASTER);
            Request res4 = MPI.COMM_WORLD.Irecv(updatedMatrixB, 0, updatedMatrixB.length, MPI.OBJECT, MASTER, FROM_MASTER);

            res3.Wait();
            res4.Wait();
            System.out.println("Row start: " + rowStart[0] + " Row end: " + rowEnd[0] + " From task " + rank);

            double[][] subResult = Utils.multiply(blockA, updatedMatrixB);

            MPI.COMM_WORLD.Send(rowStart, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rowEnd, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(subResult, 0, subResult.length, MPI.OBJECT, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }
}