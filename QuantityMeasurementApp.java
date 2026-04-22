public class QuantityMeasurementApp {

    // -------- ENUM FOR UNITS --------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.393701 / 12.0);

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
                throw new IllegalArgumentException("Invalid value");
            }
            this.value = value;
            this.unit = unit;
        }

        // -------- CONVERSION --------
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = unit.toFeet(value);
            double converted = targetUnit.fromFeet(base);
            return new Quantity(converted, targetUnit);
        }

        // -------- ADDITION (CORE UC6) --------
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            // convert both to base (feet)
            double base1 = this.unit.toFeet(this.value);
            double base2 = other.unit.toFeet(other.value);

            double sumBase = base1 + base2;

            // convert back to unit of first operand
            double result = this.unit.fromFeet(sumBase);

            return new Quantity(result, this.unit);
        }

        // -------- STATIC ADD (OPTIONAL OVERLOAD) --------
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base1 = q1.unit.toFeet(q1.value);
            double base2 = q2.unit.toFeet(q2.value);

            double sum = base1 + base2;
            double result = targetUnit.fromFeet(sum);

            return new Quantity(result, targetUnit);
        }

        // -------- EQUALITY --------
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double base1 = this.unit.toFeet(this.value);
            double base2 = other.unit.toFeet(other.value);

            return Double.compare(base1, base2) == 0;
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
            throw new IllegalArgumentException("Invalid value");
        }

        double base = source.toFeet(value);
        return target.fromFeet(base);
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Addition (instance method)
        System.out.println("1 ft + 12 inch = " + q1.add(q2)); // 2 ft

        // Reverse addition
        System.out.println("12 inch + 1 ft = " + q2.add(q1)); // 24 inch

        // Same unit
        System.out.println("1 ft + 2 ft = " +
                new Quantity(1, LengthUnit.FEET).add(new Quantity(2, LengthUnit.FEET)));

        // Yard + Feet
        System.out.println("1 yard + 3 ft = " +
                new Quantity(1, LengthUnit.YARD).add(new Quantity(3, LengthUnit.FEET)));

        // CM + Inch
        System.out.println("2.54 cm + 1 inch = " +
                new Quantity(2.54, LengthUnit.CM).add(new Quantity(1, LengthUnit.INCH)));

        // With zero
        System.out.println("5 ft + 0 inch = " +
                new Quantity(5, LengthUnit.FEET).add(new Quantity(0, LengthUnit.INCH)));

        // Negative values
        System.out.println("5 ft + (-2 ft) = " +
                new Quantity(5, LengthUnit.FEET).add(new Quantity(-2, LengthUnit.FEET)));
    }
}
