public class Main {

    public static void main(String[] args) {
        System.out.println(test(4));

        String x = """
                        hello
                   """;
        System.out.println(x);

        Material.Plastic y;
    }

    public static int test(int x) {
        return switch (x){
            case 1: yield 1;
            case 2: yield 2;
            default: yield 3;
        };
    }
}

// fields are final, immutable
record lol(int x, String s) {
    // constructor in record definition upstairs^
}

abstract sealed class Material permits Material.Plastic {
    public non-sealed static class Plastic extends Material {

    }
}

class FlexablePlastic extends Material.Plastic {

}