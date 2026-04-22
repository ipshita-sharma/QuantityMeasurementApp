public class QuantityMeasurementApp {

    // -------- ENUM FOR UNITS --------
    enum LengthUnit {
        FEET(1.0),                // base unit
        INCH(1.0 / 12.0),         // 1 inch = 1/12 feet
        YARD(3.0),                // 1 yard = 3 feet
        CM(0.393701 / 12.0);      // 1 cm = 0.393701 inch → feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // -------- QUANTITY CLASS --------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to another unit (instance method)
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double baseValue = unit.toFeet(value);             // to base (feet)
            double converted = targetUnit.fromFeet(baseValue); // to target

            return new Quantity(converted, targetUnit);
        }

        // Equality (cross-unit)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisBase = unit.toFeet(value);
            double otherBase = other.unit.toFeet(other.value);

            return Double.compare(thisBase, otherBase) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // -------- STATIC CONVERSION API --------
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        double baseValue = source.toFeet(value);
        return target.fromFeet(baseValue);
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        // Static conversion API
        System.out.println("1 ft → inch: " + convert(1.0, LengthUnit.FEET, LengthUnit.INCH)); // 12
        System.out.println("3 yard → feet: " + convert(3.0, LengthUnit.YARD, LengthUnit.FEET)); // 9
        System.out.println("36 inch → yard: " + convert(36.0, LengthUnit.INCH, LengthUnit.YARD)); // 1
        System.out.println("1 cm → inch: " + convert(1.0, LengthUnit.CM, LengthUnit.INCH)); // ~0.393701

        // Instance conversion
        Quantity q = new Quantity(1.0, LengthUnit.FEET);
        System.out.println("1 ft → inch (instance): " + q.convertTo(LengthUnit.INCH));

        // Equality check
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("1 ft == 12 inch: " + q1.equals(q2)); // true

        // Edge cases
        System.out.println("0 ft → inch: " + convert(0.0, LengthUnit.FEET, LengthUnit.INCH)); // 0
        System.out.println("-1 ft → inch: " + convert(-1.0, LengthUnit.FEET, LengthUnit.INCH)); // -12
    }
}
