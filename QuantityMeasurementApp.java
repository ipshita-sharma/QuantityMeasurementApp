// -------- STANDALONE ENUM (NOT PUBLIC) --------
enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert to base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (feet)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// -------- MAIN CLASS --------
public class QuantityMeasurementApp {

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // Conversion
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = unit.convertToBaseUnit(value);
            double result = targetUnit.convertFromBaseUnit(base);

            return new Quantity(result, targetUnit);
        }

        // Addition (UC6)
        public Quantity add(Quantity other) {
            if (other == null)
                throw new IllegalArgumentException("Other cannot be null");

            double sumBase =
                    unit.convertToBaseUnit(value) +
                    other.unit.convertToBaseUnit(other.value);

            double result = unit.convertFromBaseUnit(sumBase);

            return new Quantity(result, unit);
        }

        // Addition with target unit (UC7)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null)
                throw new IllegalArgumentException("Invalid input");

            double sumBase =
                    unit.convertToBaseUnit(value) +
                    other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity(result, targetUnit);
        }

        // Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double base1 = unit.convertToBaseUnit(value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            return Double.compare(base1, base2) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Conversion
        System.out.println("1 ft → inch: " + q1.convertTo(LengthUnit.INCH));

        // Equality
        System.out.println("1 ft == 12 inch: " + q1.equals(q2));

        // Addition
        System.out.println("1 ft + 12 inch: " + q1.add(q2));

        // Addition with target unit
        System.out.println("1 ft + 12 inch → yard: " + q1.add(q2, LengthUnit.YARD));
    }
}
