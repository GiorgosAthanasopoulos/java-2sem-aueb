/**
 * An interface for classes that can represent themselves as a string in a filesystem file
 */
public interface Serializable {
    /**
     * A method to retrieve the string representation of a class in a filesystem file
     * @return the string
     */
    String serialized();
}
