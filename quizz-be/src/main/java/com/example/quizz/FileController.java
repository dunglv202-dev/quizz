package com.example.quizz;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @PostMapping
    public void uploadFile(@ModelAttribute FileUploading fileUploading) {
        fileUploading.getFiles().forEach(file -> {
            if (!file.isEmpty() && file.getOriginalFilename() != null && file.getOriginalFilename().endsWith("docx")) {
                readWordFile(file);
            }
        });
    }

    private void readWordFile(MultipartFile file) {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            var paragraphs = document.getParagraphs();
            List<Question> questions = new ArrayList<>();

            Question currentQuestion = null;
            for (var para : paragraphs) {
                String content = para.getText();
                if (content.startsWith("Question")) {
                    currentQuestion = new Question();
                    String questionDetail = content.split(":", 2)[1];
                    currentQuestion.setQuestion(questionDetail.trim());
                    questions.add(currentQuestion);
                } else {
                    if (currentQuestion == null) {
                        throw new RuntimeException("Bad malformed doc");
                    }
                    currentQuestion.getAnswers().add(content.trim());
                }
            };

            System.out.println("Questions in " + file.getOriginalFilename());
            System.out.println(questions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
