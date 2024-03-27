package com.fypp.Ethics_Management_System.Parse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
@Service
public class ParseService {

    public ExpeditedEthicsApplication parsePDF(MultipartFile multipartFile) throws IOException {
        // First, convert MultipartFile to InputStream
        try (InputStream inputStream = multipartFile.getInputStream();

             PDDocument document = PDDocument.load(inputStream)) {
                PDFTextStripper pdfStripper = new PDFTextStripper();

                // Retrieve text from the PDF
                String text = pdfStripper.getText(document);

                //String[] lines = text.split("\n");

                ExpeditedEthicsApplication application = new ExpeditedEthicsApplication();

                //parsing methods
                ParseOther.parseGeneralInformation(text, application);
                ParseSignatureResearchInfo.parseResearchProjectInfo(text, application);
                ParseSignatureResearchInfo.parseSignatures(text,application);

                return application;
        }

    }

}