public class Pizza implements Comparable<Pizza> {
    private String name;
    private boolean isVeg;

    public Pizza (String name, boolean isVeg) {
        this.name = name;
        this.isVeg= isVeg;
    }

    @Override
    public int compareTo(Pizza other) {
        return CharSequence.compare(this.name, other.name);
    }

    public void print() {
        System.out.print("name: " + this.name + ", ");
        System.out.println("isVeg: " + this.isVeg);
    }
}
