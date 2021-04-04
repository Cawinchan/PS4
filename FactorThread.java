
import java.math.BigInteger;
import java.util.ArrayList;

// Main thread will create k FactorPrimeThread -> start them -> join until finish


class FactorThread{
    private static boolean interrupt;
    public static void main(String[] args) throws Exception, InterruptedException {

        final int k = 4; //numnber of threads

        BigInteger initial_value = new BigInteger("239839672845043");
        //    BigInteger step_size; // T0: 2,3,4,...,N (step_size = 1)
        BigInteger step_size;

        BigInteger[] result = new BigInteger[k];

        final FactorNoInterrupt[] factorThread = new FactorNoInterrupt[k];

        step_size = new BigInteger(String.valueOf(k));
        for (int i = 0; i < k; i++){
            factorThread[i] = new FactorNoInterrupt(initial_value, i + 2, step_size);
            factorThread[i].start();
            System.out.println(factorThread[i]);
        }

        while (!interrupt){
            // System.out.println("Waiting for interrupt...");
        }
        
        System.out.println("interrupt happens!");

        int count = 0;

        for  (int i = 0; i < k; i++){
			if (factorThread[i].getResult() == null){
				factorThread[i].interrupt();
                count++;
                System.out.println(count);
			}
			else {
				continue;
			}
			System.out.println(result[i]);
        }

    }
    static class FactorNoInterrupt extends Thread {
        private BigInteger n, index, step_size;
        private BigInteger result;

        public FactorNoInterrupt(final BigInteger n, final int index, final BigInteger step_size) {
            this.n = n;
            this.index = BigInteger.valueOf(index);
            this.step_size = step_size;
        }

        public void run() {
            System.out.println("Thread is running...");
            result = factor(n);
            if (FactorThread.interrupt == true){
            }
        }

        public BigInteger getResult() {
            return result;
        }

        public BigInteger factor(BigInteger n) {
            BigInteger zero = new BigInteger("0");


            while (index.compareTo(n) < 0) {
        
                if (n.remainder(index).compareTo(zero) == 0) {
                    System.out.println("Factor is " + index);
                    FactorThread.interrupt = true;
                    result = index;
                    return index;
                }
                if (this.isInterrupted() || FactorThread.interrupt) {
                    System.out.println("Interrupted");
                    Thread.currentThread().interrupt();
                    return null;
                }

                index = index.add(step_size);
            }
            return null;
        }
    }
}
