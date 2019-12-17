package common

import com.google.common.io.CharSource
import com.google.common.io.Resources
import java.nio.charset.StandardCharsets

fun <T> getInput(clazz: Class<T>): CharSource {
    return Resources.asCharSource(
            Resources.getResource(clazz, "input.txt"),
            StandardCharsets.UTF_8);
}