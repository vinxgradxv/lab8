package utils;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Asker {



    public String response = "";

    public Asker(){
    }

    public <T> T ask(Function<String, T> function,
                       Predicate<T> predicate,
                       String input,
                       boolean nullable) throws IOException {
        T value = null;
        response = "";
            try {
                if (input.equals("") && nullable) {
                    return null;
                }
                value = function.apply(input);
                if (predicate.test(value)){
                    return value;
                }
                response = "asdasd";
            }catch (IllegalArgumentException e){
                response = "Значение неверного формата";
            }
            catch (NullPointerException e){
                response = "Значение не может быть null";
            }
        return value;
    }
}
