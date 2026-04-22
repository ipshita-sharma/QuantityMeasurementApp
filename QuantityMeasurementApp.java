public class QuantityMeasurementApp {

    // -------- ENUM FOR ALL UNITS --------
    enum LengthUnit {
        FEET(1.0),                // base unit
        INCH(1.0 / 12.0),         // 1 inch = 1/12 feet
        YARD(3.0),                // 1 yard = 3 feet
        CM(0.393701 / 12.0);      // 1 cm = 0.393701 inch → convert to feet

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

        // Equality check (cross-unit supported)
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

        Quantity q1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q2 = new Quantity(3.0, LengthUnit.FEET);
        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.CM);
        Quantity q5 = new Quantity(0.393701, LengthUnit.INCH);

        // Yard conversions
        System.out.println("1 yard == 3 feet: " + q1.equals(q2));   // true
        System.out.println("1 yard == 36 inch: " + q1.equals(q3));  // true

        // Same unit
        System.out.println("2 yard == 2 yard: " +
                new Quantity(2.0, LengthUnit.YARD).equals(new Quantity(2.0, LengthUnit.YARD))); // true

        // CM conversions
        System.out.println("1 cm == 0.393701 inch: " + q4.equals(q5)); // true

        // Different values
        System.out.println("1 yard == 2 feet: " +
                q1.equals(new Quantity(2.0, LengthUnit.FEET))); // false

        // Null check
        System.out.println("q1 == null: " + q1.equals(null)); // false

        // Same reference
        System.out.println("q1 == q1: " + q1.equals(q1)); // true
    }
}
