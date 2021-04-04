import java.math.BigInteger;

// Main thread will create k FactorPrimeThread -> start them -> join until finish


public class FactorThreadNoInterrupt{
    public static void main(String[] args) throws Exception {
        
        final int k = 4; //numnber of threads

        BigInteger initial_value = new BigInteger("239839672845043");
        //    BigInteger step_size; // T0: 2,3,4,...,N (step_size = 1)
        BigInteger step_size;

        final FactorNoInterrupt[] factorThread = new FactorNoInterrupt[k];

        step_size = new BigInteger(String.valueOf(k));
        for (int i = 0; i < k; i++){
            factorThread[i] = new FactorNoInterrupt(initial_value, i + 2, step_size);
            factorThread[i].start();
            System.out.println(factorThread[i]);
        }

        for  (int i = 0; i < k; i++){
            factorThread[i].join();
        }
    }

    static class FactorNoInterrupt extends Thread {
        private static BigInteger index;
        private static BigInteger step_size;
        private BigInteger n;
        private static BigInteger result;

        public FactorNoInterrupt(final BigInteger n, final int index, final BigInteger step_size) {
            this.n = n;
            FactorNoInterrupt.index = BigInteger.valueOf(index);
            FactorNoInterrupt.step_size = step_size;
        }

        public void run() {
            System.out.println("Thread is running...");
            result = factor(n);
        }

        public static BigInteger factor(BigInteger n) {
            BigInteger zero = new BigInteger("0");


            while (index.compareTo(n) < 0) {
                if (n.remainder(index).compareTo(zero) == 0) {
                    System.out.println("Factor is " + index);
                    result = index;
                    return index;
                }

                index = index.add(step_size);
            }
            return null;
        }
    }
}