# PulseDesk

PulseDesk is a simple ticketing and feedback management system designed classify comments into feedback and real issues. It uses Spring Boot for the backend, a H2 database for data storage, and a simple frontend interface for user interaction.

## Features

- **Feedback Submission**: Users can submit feedback or issues directly through the interface.
- **Automatic Ticket Creation**: Feedback is analyzed using Hugging Face's NLP model to classify and prioritize issues, automatically creating tickets for actionable feedback.
- **Category and Priority Detection**: Feedback is categorized (e.g., Bug, Billing, Feature) and prioritized (High, Medium, Low) based on its content.
- **Dashboard**: A clean and responsive dashboard to view tickets and comments.
- **RESTful API**: Endpoints for managing comments and tickets.

## Prerequisites

- Java 21
- Maven
- A Hugging Face account
- Docker (optional, for containerized deployment)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/KesTutisT/Pulsedesk
cd Pulsedesk
```

### 2. Configure Environment Variables
Create src/main/resources/application-local.properties and paste this text:
```bash
api.token=your_token_here
```
replace your_token_here with your actual token

### 3. Run the Application

#### Using Maven:

```bash
./mvnw spring-boot:run
```

### 4. Access the Application
- Open your browser and navigate to http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

## API Endpoints
### Comments
- POST /comments: Create a new comment.
- GET /comments: Retrieve all comments.
- GET /comments/{id}: Retrieve a specific comment by ID.

### Ticket
- GET /tickets: Retrieve all tickets.
- GET /tickets/{id}: Retrieve a specific ticket by ID.

## Technologies Used
- Backend: Spring Boot, Spring Data JPA, Spring Web
- Database: H2 (in-memory)
- Frontend: HTML, CSS, JavaScript
- AI Integration: Hugging Face API
