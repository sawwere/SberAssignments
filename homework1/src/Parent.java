public abstract class Parent {
    private String name;

    static {
        System.out.println("Parent:static 1");
    }

    {
        System.out.println("Parent:instance 1");
    }

    static {
        System.out.println("Parent:static 2");
    }

    Parent() {
        System.out.println("Parent:constructor");
    }

    {
        System.out.println("Parent:instance 2");
    }

    Parent(String name) {
        System.out.println("Parent:name-constructor");
        this.name = name;
    }
}
