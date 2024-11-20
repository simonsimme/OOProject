package backend.Messages;

public interface Visitable {
    void accept(Visitor visitor);
}
