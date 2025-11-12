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
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
@AllArgsConstructor
public class FileScheduler {

    private final ProductService productService;
    private final EventService eventService;
    private final RequestDetailsService requestDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(FileScheduler.class);

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

    @Scheduled(fixedDelayString = "${app.fixed-delay-ms:600000}", initialDelay = 5000)
    public void scheduledTask() {
        logger.info("Scheduled task started: scanning directory {}", sourcePath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath, "*.xml")) {
            for (Path entry : stream) {
                logger.info("Processing file: {}", entry.getFileName());
                RootXml rootXml = parseXml(entry);
                RequestDetails requestDetails = rootXml.getRequestDetails();
                requestDetailsService.saveRequestDetails(requestDetails);
                logger.debug("Saved RequestDetails with id: {}", requestDetails.getId());
                List<EventXml> events = rootXml.getEvents();
                for (EventXml eventXml : events) {
                    Event event = new Event();
                    event.setId(eventXml.getId());
                    event.setType(eventXml.getType());
                    event.setInsuredId(eventXml.getInsuredId());
                    event.setRequestDetails(requestDetails);
                    eventService.save(event);
                    logger.debug("Saved Event with id: {}", event.getId());
                    List<ProductXml> products = eventXml.getProducts();
                    for (ProductXml productXml : products) {
                        Product product = new Product();
                        product.setType(productXml.getType());
                        product.setPrice(productXml.getPrice());
                        product.setStartDate(productXml.getStartDate());
                        product.setEndDate(productXml.getEndDate());
                        product.setEvent(event);
                        productService.save(product);
                        logger.debug("Saved Product of type '{}' for Event id: {}", product.getType(), event.getId());
                    }
                }

                if (!Files.exists(backupPath)) {
                    logger.info("Backup directory does not exist, creating: {}", backupPath);
                    Files.createDirectories(backupPath);
                } else if (!Files.isDirectory(backupPath)) {
                    logger.error("Backup path exists but is not a directory: " + backupPath);
                    throw new IOException("Backup path exists but is not a directory: " + backupPath);
                }
                Path targetFile = backupPath.resolve(entry.getFileName());
                logger.info("Moving processed file {} to backup location {}", entry.getFileName(), targetFile);
                Files.move(entry, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | JAXBException e) {
            logger.error("Error processing scheduled task", e);
            throw new RuntimeException(e);
        }
        logger.info("Scheduled task completed");
    }

    public RootXml parseXml(Path xmlFile) throws JAXBException{
        try{
            logger.info("Starting XML parsing for file: {}", xmlFile.getFileName());
            JAXBContext context = JAXBContext.newInstance(RootXml.class, RequestDetailsXml.class, EventXml.class, ProductXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            RootXml rootXml = (RootXml) unmarshaller.unmarshal(xmlFile.toFile());
            logger.info("Successfully parsed XML file: {}", xmlFile.getFileName());
            return rootXml;
        } catch (JAXBException e){
            logger.error("Failed to parse XML file: {}", xmlFile.getFileName(), e);
            throw e;
        }
    }
}
