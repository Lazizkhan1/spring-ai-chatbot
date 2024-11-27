package dev.danvega.streaming;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AppInit implements CommandLineRunner {

    private final VectorStore vectorStore;
    private static final Logger LOGGER = Logger.getLogger(AppInit.class.getName());


    public AppInit(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Value("classpath:/docs/pdf.pdf")
    private Resource pdfPath;

    @Override
    public void run(String... args) {

        TikaDocumentReader reader = new TikaDocumentReader(pdfPath);
        TextSplitter splitter = new TokenTextSplitter();
        vectorStore.accept(splitter.apply(reader.get()));
        LOGGER.info("Document vectorized");
    }
}
