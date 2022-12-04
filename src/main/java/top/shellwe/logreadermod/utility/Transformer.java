package top.shellwe.logreadermod.utility;



import java.io.*;
//import java.util.logging.Logger;
import ch.qos.logback.core.Appender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 此类用于类型转换
 */
public class Transformer extends OutputStream {
    private static Logger LOGGER = LogManager.getLogger(Transformer.class);
    private final Logger logger;
    private StringBuffer stringBuffer;


    public Transformer(final Logger logger) {
        this.logger = logger;
        stringBuffer = new StringBuffer();
    }

    @Override
    public void write(final int b) {
        if((char)b == '\n'){
            flush();
            return;
        }
        stringBuffer = stringBuffer.append((char)b);
    }

    @Override
    public void flush() {
        logger.info(stringBuffer.toString());
        stringBuffer = new StringBuffer();
    }

}
