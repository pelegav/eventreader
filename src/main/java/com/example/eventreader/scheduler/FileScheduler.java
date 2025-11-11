package com.example.eventreader.scheduler;

import com.example.eventreader.model.Event;
import com.example.eventreader.model.Product;
import com.example.eventreader.model.RequestDetails;
import com.example.eventreader.service.EventService;
import com.example.eventreader.service.ProductService;
import com.example.eventreader.service.RequestDetailsService;
import com.example.eventreader.xmlelements.EventXml;
import com.example.eventreader.xmlelements.ProductXml;
import com.example.eventreader.xmlelements.RequestDetailsXml;
import com.example.eventreader.xmlelements.RootXml;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
public class FileScheduler {

    private ProductService productService;
    private EventService eventService;
    private RequestDetailsService requestDetailsService;

//    final String SOURCE = "input";
//    final String BACKUP = "backup";

    private Path sourcePath = Paths.get("input");
    private Path backupPath = Paths.get("backup");

    @Autowired
    public FileScheduler(ProductService productService,
                         EventService eventService,
                         RequestDetailsService requestDetailsService) {
        this.productService = productService;
        this.eventService = eventService;
        this.requestDetailsService = requestDetailsService;
    }


    // for testing
    public FileScheduler(ProductService productService,
                         EventService eventService,
                         RequestDetailsService requestDetailsService,
                         Path sourcePath,
                         Path backupPath) {
        this.productService = productService;
        this.eventService = eventService;
        this.requestDetailsService = requestDetailsService;
        this.sourcePath = sourcePath;
        this.backupPath = backupPath;
    }

    @Scheduled(fixedDelayString = "${app.fixed-delay-ms:600000}", initialDelay = 5000)
    public void scheduledTask() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath, "*.xml")) {
            for (Path entry : stream) {
                System.out.println("Processing file: " + entry.getFileName());
                RootXml rootXml = parseXml(entry);
                RequestDetails requestDetails = rootXml.getRequestDetails();
                requestDetailsService.saveRequestDetails(requestDetails);
                List<EventXml> events = rootXml.getEvents();
                for (EventXml eventXml : events) {
                    Event event = new Event();
                    event.setId(eventXml.getId());
                    event.setType(eventXml.getType());
                    event.setInsuredId(eventXml.getInsuredId());
                    event.setRequestDetails(requestDetails);
                    eventService.save(event);
                    List<ProductXml> products = eventXml.getProducts();
                    for (ProductXml productXml : products) {
                        Product product = new Product();
                        product.setType(productXml.getType());
                        product.setPrice(productXml.getPrice());
                        product.setStartDate(productXml.getStartDate());
                        product.setEndDate(productXml.getEndDate());
                        product.setEvent(event);
                        productService.save(product);
                    }
                }

                if (!Files.exists(backupPath)) {
                    Files.createDirectories(backupPath);
                } else if (!Files.isDirectory(backupPath)) {
                    throw new IOException("Backup path exists but is not a directory: " + backupPath);
                }
                Path targetFile = backupPath.resolve(entry.getFileName());
                Files.move(entry, targetFile, StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public RootXml parseXml(Path xmlFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(RootXml.class, RequestDetailsXml.class, EventXml.class, ProductXml.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (RootXml) unmarshaller.unmarshal(xmlFile.toFile());
    }
}
