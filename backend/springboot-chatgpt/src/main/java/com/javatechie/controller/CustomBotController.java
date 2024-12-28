package com.javatechie.controller;

import com.javatechie.dto.ChatGPTRequest;
import com.javatechie.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${python.script.path}")
    private String pythonScriptPath;

    @Value("${test.file.path}")
    private String testFilePath;

    @Autowired
    private RestTemplate template;



    @PostMapping("/generate-tests")
    public ResponseEntity<String> generateTests(@RequestParam String jsondata) {
        try {
            // Resolve paths dynamically


            // Step 1: Read file content
            String fileContent = new String(jsondata.getBytes());

            // Step 2: Prepare OpenAI prompt
            String fullPrompt = "Generate tests for the following classes with imports and " +
                    "public before each class using JUnit with package name of test classes " +
                    "based on the class packages.\n\nHere is the JSON:\n" + fileContent;

            ChatGPTRequest chatRequest = new ChatGPTRequest(model, fullPrompt);

            // Step 3: Call OpenAI API
            ChatGptResponse chatResponse = template.postForObject(apiURL, chatRequest, ChatGptResponse.class);

            if (chatResponse != null && chatResponse.getChoices() != null && !chatResponse.getChoices().isEmpty()) {
                String generatedTests = chatResponse.getChoices().get(0).getMessage().getContent();

                // Step 4: Save the tests to a file (UnitTests.txt)
                File testFile = new File(testFilePath + "\\UnitTests.txt"); // Ensure file name is "UnitTests.txt"
                testFile.getParentFile().mkdirs(); // Create directories if not exist

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
                    writer.write(generatedTests);
                }

                // Step 5: Call Python script to process the test file
                String outputDirectory = resolvePath("F:/FRO/all/hotel/src/test/java/ma/hotel"); // Adjust this as needed
                String pythonResponse = callPythonScript(testFilePath, outputDirectory, pythonScriptPath);

                return ResponseEntity.ok("Unit tests generated, saved, and processed:\n" + pythonResponse);
            } else {
                return ResponseEntity.badRequest().body("Failed to generate unit tests.");
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
        }
    }


    // Helper method to resolve paths dynamically
    private String resolvePath(String relativePath) {
        return Paths.get(relativePath).toAbsolutePath().toString();
    }


    @PostMapping("/generate-recommendations")
    public ResponseEntity<String> generateRecommendations(
            @RequestParam String jsonRecomendations) {
        try {
            // Step 1: Read the input JSON content
            String fileContent = new String(jsonRecomendations.getBytes());

            // Step 2: Prepare OpenAI prompt
            String fullPrompt = "generate the recommendations based on the code " +
                    "\n\nHere is the code analysis:\n" + fileContent +
                    "\n\nGenerate recommendations in JSON format with the following structure:\n" +
                    "{\n" +
                    "  \"General Code Quality Recommendations\": [],\n" +
                    "  \"Security Recommendations\": [],\n" +
                    "  \"Dependency Management\": [],\n" +
                    "  \"Clean Code Practices\": []\n" +
                    "}";

            ChatGPTRequest chatRequest = new ChatGPTRequest(model, fullPrompt);

            // Step 3: Call OpenAI API
            ChatGptResponse chatResponse = template.postForObject(apiURL, chatRequest, ChatGptResponse.class);

            // Step 4: Extract the recommendations JSON from the response
            if (chatResponse != null && chatResponse.getChoices() != null && !chatResponse.getChoices().isEmpty()) {
                String recommendationsJson = chatResponse.getChoices().get(0).getMessage().getContent();
                return ResponseEntity.ok(recommendationsJson);
            } else {
                return ResponseEntity.badRequest().body("Failed to generate recommendations.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error generating recommendations: " + e.getMessage());
        }
    }

    private String callPythonScript(String inputFilePath, String outputDirectory, String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "C:\\Users\\zakar\\AppData\\Local\\Programs\\Python\\Python312\\python.exe", // Adjust to your Python installation path
                    scriptPath,
                    inputFilePath,
                    outputDirectory
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Python script executed successfully:\n" + output;
            } else {
                return "Python script failed with exit code " + exitCode + ":\n" + output;
            }
        } catch (Exception e) {
            return "Error executing Python script: " + e.getMessage();
        }
    }
}
