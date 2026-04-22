public class QuantityMeasurementApp {

    // -------- FEET CLASS --------
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // -------- INCHES CLASS --------
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // -------- METHODS --------
    public static boolean compareFeet(double v1, double v2) {
        return new Feet(v1).equals(new Feet(v2));
    }

    public static boolean compareInches(double v1, double v2) {
        return new Inches(v1).equals(new Inches(v2));
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        System.out.println("Feet (1.0, 1.0): " + compareFeet(1.0, 1.0));   // true
        System.out.println("Feet (1.0, 2.0): " + compareFeet(1.0, 2.0));   // false

        System.out.println("Inches (1.0, 1.0): " + compareInches(1.0, 1.0)); // true
        System.out.println("Inches (1.0, 2.0): " + compareInches(1.0, 2.0)); // false

        Feet f = new Feet(1.0);
        System.out.println("Feet vs null: " + f.equals(null)); // false
    }
}
