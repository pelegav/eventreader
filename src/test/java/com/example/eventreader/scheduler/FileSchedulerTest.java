package com.example.eventreader.scheduler;

import com.example.eventreader.model.Event;
import com.example.eventreader.model.Product;
import com.example.eventreader.model.RequestDetails;
import com.example.eventreader.service.EventService;
import com.example.eventreader.service.ProductService;
import com.example.eventreader.service.RequestDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileSchedulerTest {

    @Mock
    private ProductService productService;

    @Mock
    private EventService eventService;

    @Mock
    private RequestDetailsService requestDetailsService;

    private FileScheduler fileScheduler;

    @TempDir
    Path tempDir; // תיקיה זמנית

    private Path inputDir;
    private Path backupDir;

    @BeforeEach
    void setup() throws Exception {
        inputDir = tempDir.resolve("input");
        backupDir = tempDir.resolve("backup");
        Files.createDirectories(inputDir);
        Files.createDirectories(backupDir);

        fileScheduler = new FileScheduler(
                productService,
                eventService,
                requestDetailsService,
                inputDir,
                backupDir
        );
    }

    @Test
    void testScheduledTaskProcessesXmlAndMovesFile() throws Exception {

        String xml = """
                <root>
                    <requestDetails>
                        <id>req1</id>
                        <acceptDate>2021-01-01</acceptDate>
                        <sourceCompany>TestCo</sourceCompany>
                    </requestDetails>
                    <events>
                        <event>
                            <id>ev1</id>
                            <type>A</type>
                            <insuredId>123</insuredId>
                            <products>
                                <product>
                                    <type>P1</type>
                                    <price>10</price>
                                </product>
                            </products>
                        </event>
                    </events>
                </root>
                """;

        Path xmlFile = inputDir.resolve("test.xml");
        Files.writeString(xmlFile, xml);

        fileScheduler.scheduledTask();

        verify(requestDetailsService, Mockito.times(1))
                .saveRequestDetails(Mockito.any(RequestDetails.class));

        verify(eventService, Mockito.times(1))
                .save(Mockito.any(Event.class));

        verify(productService, Mockito.times(1))
                .save(Mockito.any(Product.class));

        assertFalse(Files.exists(xmlFile));
        assertTrue(Files.exists(backupDir.resolve("test.xml")));
    }
}
