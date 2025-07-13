# 📚 PDF Flashcards Backend

A Spring Boot backend service that processes PDF documents and generates interactive flashcards using TensorFlow NLP models.

## 🚀 Features

- **📄 PDF Processing** - Extract text content from PDF files using Apache PDFBox
- **🤖 TensorFlow Integration** - Ready for custom NLP model integration
- **🃏 Flashcard Generation** - Generate question-answer pairs from PDF content
- **🌐 RESTful API** - Clean REST endpoints for frontend integration
- **📊 Health Monitoring** - Built-in health checks and metrics
- **🔒 File Validation** - Comprehensive file validation and error handling
- **⚡ Performance Optimized** - Efficient text processing and response handling

## 🏗️ Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Spring Boot    │    │   TensorFlow    │
│   (React)       │◄──►│   Backend        │◄──►│   NLP Model     │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌──────────────────┐
                       │   Apache PDFBox  │
                       │   (PDF Processing)│
                       └──────────────────┘
```

## 🛠️ Technology Stack

- **Java 17** - Modern Java with latest features
- **Spring Boot 3.2.0** - Rapid application development framework
- **Apache PDFBox** - PDF text extraction and processing
- **TensorFlow Java** - Machine learning model integration
- **Maven** - Dependency management and build tool
- **SLF4J** - Logging framework

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- TensorFlow model files (for production use)

## 🚀 Quick Start

### 1. Clone and Navigate
```bash
cd backend
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Process PDF and Generate Flashcards
```http
POST /api/process-pdf
Content-Type: multipart/form-data

pdf: [PDF file]
```

**Response:**
```json
{
  "flashcards": [
    {
      "question": "What is the main topic?",
      "answer": "The main topic is...",
      "explanation": "Generated from PDF content",
      "confidence": 0.85,
      "sourcePage": 1,
      "sourceText": "Original text snippet..."
    }
  ],
  "totalCards": 1,
  "processingTime": 1500,
  "status": "success",
  "message": "Successfully generated 1 flashcards"
}
```

### Advanced PDF Processing
```http
POST /api/process-pdf-advanced
Content-Type: multipart/form-data

pdf: [PDF file]
maxCards: 30
```

### Health Check
```http
GET /api/health
```

### Service Information
```http
GET /api/info
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# File Upload Limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# TensorFlow Configuration
tensorflow.model.path=models/
tensorflow.model.name=flashcard-generator

# Flashcard Generation
flashcard.default.max.cards=20
flashcard.max.cards.limit=100
```

## 🤖 TensorFlow Integration

### Current Implementation
The backend includes a placeholder TensorFlow service that generates basic flashcards from text content.

### Custom Model Integration
To integrate your custom TensorFlow model:

1. **Update TensorFlowService.java**:
   ```java
   // Load your model
   private SavedModelBundle model;
   
   public void initializeModel() {
       model = SavedModelBundle.load("path/to/your/model", "serve");
   }
   
   // Implement inference
   public List<Flashcard> generateFlashcards(String text, int maxCards) {
       // Your custom inference logic here
   }
   ```

2. **Add Model Files**:
   - Place your TensorFlow model in the `models/` directory
   - Update `tensorflow.model.path` in `application.properties`

3. **Environment Variables**:
   ```bash
   export TENSORFLOW_MODEL_PATH=/path/to/your/model
   ```

## 📁 Project Structure

```
src/main/java/com/pdfflashcards/backend/
├── PdfFlashcardsApplication.java    # Main application class
├── controller/
│   └── FlashcardController.java     # REST API endpoints
├── model/
│   ├── Flashcard.java              # Flashcard data model
│   └── FlashcardResponse.java      # API response model
└── service/
    ├── FlashcardService.java       # Main business logic
    ├── PdfProcessingService.java   # PDF text extraction
    └── TensorFlowService.java      # ML model integration
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Manual Testing with curl
```bash
# Health check
curl http://localhost:8080/api/health

# Process PDF (replace with actual file)
curl -X POST -F "pdf=@sample.pdf" http://localhost:8080/api/process-pdf
```

## 📊 Monitoring

### Health Endpoints
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Performance metrics

### Logging
Logs are configured to show:
- Application events
- PDF processing status
- TensorFlow model operations
- API request/response details

## 🔒 Security Considerations

- File size limits (10MB max)
- File type validation (PDF only)
- CORS configuration for frontend
- Input validation and sanitization
- Error handling without sensitive data exposure

## 🚀 Deployment

### Docker Deployment
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/pdf-flashcards-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Production Configuration
```properties
# Production settings
server.port=8080
logging.level.root=WARN
management.endpoints.web.exposure.include=health
```

## 🤝 Frontend Integration

The backend is designed to work seamlessly with the React frontend:

1. **CORS Configuration** - Allows requests from `http://localhost:3000`
2. **API Endpoints** - Matches frontend expectations
3. **Response Format** - JSON structure compatible with frontend components

## 📝 Development

### Adding New Features
1. Create service classes in `service/` package
2. Add model classes in `model/` package
3. Create controllers in `controller/` package
4. Update tests accordingly

### Code Style
- Follow Java naming conventions
- Use meaningful variable names
- Add comprehensive logging
- Include proper error handling

## 🐛 Troubleshooting

### Common Issues

1. **Port Already in Use**:
   ```bash
   # Change port in application.properties
   server.port=8081
   ```

2. **File Upload Errors**:
   - Check file size limits
   - Verify PDF file format
   - Check file permissions

3. **TensorFlow Issues**:
   - Verify model file paths
   - Check Java version compatibility
   - Review TensorFlow Java documentation

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

---

**Happy coding! 🎉** 