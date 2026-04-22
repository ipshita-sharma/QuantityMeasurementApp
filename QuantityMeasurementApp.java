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
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        // -------- CONVERSION --------
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

            double base = unit.toFeet(value);
            double converted = targetUnit.fromFeet(base);

            return new Quantity(converted, targetUnit);
        }

        // -------- ADD (UC6 - default: first operand unit) --------
        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");

            double baseSum = this.unit.toFeet(this.value) + other.unit.toFeet(other.value);
            double result = this.unit.fromFeet(baseSum);

            return new Quantity(result, this.unit);
        }

        // -------- ADD WITH TARGET UNIT (UC7 CORE) --------
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double baseSum = this.unit.toFeet(this.value) + other.unit.toFeet(other.value);
            double result = targetUnit.fromFeet(baseSum);

            return new Quantity(result, targetUnit);
        }

        // -------- STATIC ADD (OPTIONAL API) --------
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = q1.unit.toFeet(q1.value) + q2.unit.toFeet(q2.value);
            double result = targetUnit.fromFeet(sum);

            return new Quantity(result, targetUnit);
        }

        // -------- EQUALITY --------
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double base1 = unit.toFeet(value);
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
        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        double base = source.toFeet(value);
        return target.fromFeet(base);
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        Quantity f = new Quantity(1.0, LengthUnit.FEET);
        Quantity i = new Quantity(12.0, LengthUnit.INCH);

        // UC7 examples
        System.out.println("Feet target: " + f.add(i, LengthUnit.FEET));   // 2 ft
        System.out.println("Inch target: " + f.add(i, LengthUnit.INCH));   // 24 inch
        System.out.println("Yard target: " + f.add(i, LengthUnit.YARD));   // ~0.667 yard

        // More cases
        System.out.println("Yard + Feet → Yard: " +
                new Quantity(1, LengthUnit.YARD).add(new Quantity(3, LengthUnit.FEET), LengthUnit.YARD));

        System.out.println("Inch + Yard → Feet: " +
                new Quantity(36, LengthUnit.INCH).add(new Quantity(1, LengthUnit.YARD), LengthUnit.FEET));

        System.out.println("CM + Inch → CM: " +
                new Quantity(2.54, LengthUnit.CM).add(new Quantity(1, LengthUnit.INCH), LengthUnit.CM));

        System.out.println("5 ft + 0 inch → yard: " +
                new Quantity(5, LengthUnit.FEET).add(new Quantity(0, LengthUnit.INCH), LengthUnit.YARD));

        System.out.println("5 ft + (-2 ft) → inch: " +
                new Quantity(5, LengthUnit.FEET).add(new Quantity(-2, LengthUnit.FEET), LengthUnit.INCH));
    }
}
