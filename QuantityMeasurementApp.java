// -------- LENGTH ENUM --------
enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CM(0.393701 / 12.0);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

// -------- WEIGHT ENUM --------
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}

// -------- MAIN CLASS --------
public class QuantityMeasurementApp {

    // -------- LENGTH CLASS --------
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value))
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            return new QuantityLength(target.fromBase(base), target);
        }

        public QuantityLength add(QuantityLength other) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityLength(unit.fromBase(sum), unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityLength(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength o = (QuantityLength) obj;
            return Double.compare(unit.toBase(value), o.unit.toBase(o.value)) == 0;
        }

        public String toString() {
            return value + " " + unit;
        }
    }

    // -------- WEIGHT CLASS --------
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value))
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.toBase(value);
            return new QuantityWeight(target.fromBase(base), target);
        }

        public QuantityWeight add(QuantityWeight other) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityWeight(unit.fromBase(sum), unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new QuantityWeight(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;

            QuantityWeight o = (QuantityWeight) obj;
            return Double.compare(unit.toBase(value), o.unit.toBase(o.value)) == 0;
        }

        public String toString() {
            return value + " " + unit;
        }
    }

    // -------- MAIN METHOD --------
    public static void main(String[] args) {

        // LENGTH TEST
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Length Equality: " + l1.equals(l2));
        System.out.println("Length Add: " + l1.add(l2));
        System.out.println("Length Convert: " + l1.convertTo(LengthUnit.INCH));

        // WEIGHT TEST
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println("Weight Equality (kg-g): " + w1.equals(w2)); // true
        System.out.println("Weight Equality (kg-lb): " + w1.equals(w3)); // true

        System.out.println("Weight Add: " + w1.add(w2)); // 2 kg
        System.out.println("Weight Convert: " + w1.convertTo(WeightUnit.POUND));

        // Category safety (different classes → false)
        System.out.println("Weight vs Length: " + w1.equals(l1)); // false
    }
}
