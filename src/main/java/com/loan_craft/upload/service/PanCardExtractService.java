package com.loan_craft.upload.service;

import com.google.cloud.documentai.v1.*;
import com.google.protobuf.ByteString;
import com.loan_craft.upload.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class PanCardExtractService {

    @Value("${gcp.project-id}")  private String projectId;
    @Value("${gcp.location}")    private String location;
    @Value("${gcp.pancard-processor-id}") private String processorId;

    public Map<String, String> processPdf(MultipartFile pancard, byte[] pancardBytes) throws Exception {
        // Read the multipart file directly into bytes
        // Build the client with the correct endpoint
        String endpoint = location + "-documentai.googleapis.com:443";
        try (DocumentProcessorServiceClient client =
             DocumentProcessorServiceClient.create(
                 DocumentProcessorServiceSettings.newBuilder()
                     .setEndpoint(endpoint)
                     .build())) {
            RawDocument rawDocument = RawDocument.newBuilder()
                    .setContent(ByteString.copyFrom(pancardBytes))
                    .setMimeType(pancard.getContentType() == null
                            ? "application/pdf"
                            : pancard.getContentType())
                    .build();
            ProcessRequest request = ProcessRequest.newBuilder()
                    .setName(String.format("projects/%s/locations/%s/processors/%s",
                            projectId, location, processorId))
                    .setRawDocument(rawDocument)
                    .build();
            Document doc = client.processDocument(request).getDocument();

            Map<String, String> extractedDetails = new HashMap<>();
            for(Document.Entity ele: doc.getEntitiesList()){
                extractedDetails.put(ele.getType(), ele.getMentionText());
            }
            return extractedDetails;
        }
        catch (Exception e){
            System.out.println("Pan card extraction failed" + e.getMessage());
            throw new CustomException("Pan card extraction failed. Please try again!");
        }
    }

}
