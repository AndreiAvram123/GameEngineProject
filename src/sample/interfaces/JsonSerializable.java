package sample.interfaces;

import java.io.IOException;

public interface JsonSerializable {
    String toJsonObject() throws IOException;
}
