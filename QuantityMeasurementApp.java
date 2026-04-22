public class QuantityMeasurementApp {

    // -------- ENUM FOR UNITS --------
    enum LengthUnit {
        FEET(1.0),        // base unit
        INCH(1.0 / 12.0); // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // -------- GENERIC QUANTITY CLASS --------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // Override equals() for comparison
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q5 = new Quantity(2.0, LengthUnit.FEET);

        // Cross-unit equality
        System.out.println("1 ft == 12 inch: " + q1.equals(q2)); // true

        // Same unit equality
        System.out.println("1 inch == 1 inch: " + q3.equals(q4)); // true

        // Different values
        System.out.println("1 ft == 2 ft: " + q1.equals(q5)); // false

        // Null comparison
        System.out.println("q1 == null: " + q1.equals(null)); // false

        // Same reference
        System.out.println("q1 == q1: " + q1.equals(q1)); // true
    }
}
