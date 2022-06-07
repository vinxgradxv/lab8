package utils;

public interface ConvertEnvVar<R>{
    R convert(String t, R defaultValue);
}
