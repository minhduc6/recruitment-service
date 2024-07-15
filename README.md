# Recruitment System API

## Overview

The Recruitment System API project aims to design and implement a comprehensive API for managing the recruitment process. This system will facilitate the management of employers, job postings, job seekers, resumes, and job-related metadata, providing an efficient and user-friendly API service.

## Table of Contents

1. [Problem Statement](#problem-statement)
2. [Entities and Relationships](#entities-and-relationships)
3. [API Requirements](#api-requirements)
    - [EMPLOYER APIs](#employer-apis)
    - [JOB APIs](#job-apis)
    - [SEEKER APIs](#seeker-apis)
    - [RESUME APIs](#resume-apis)
4. [Installation](#installation)
5. [Usage](#usage)
6. [API Documentation](#api-documentation)
7. [Code Quality](#code-quality)
8. [Monitoring](#monitoring)
9. [Contributing](#contributing)
10. [License](#license)

## Problem Statement

### Input

- **Database and tables**: The project utilizes an existing database with predefined tables and initial data.
- **API descriptions**: Requirements provided in a non-technical format.

### Output

- **API design**: Design the APIs based on the requirements.
- **API implementation**: Implement the designed APIs.
- **API documentation**: Create comprehensive documentation for the APIs.
- **Linter**: Use a linter to ensure code quality and adherence to best practices.
- **Monitoring service**: Set up a monitoring service to track the performance and availability of the APIs.

## Entities and Relationships

The recruitment system involves the following entities and their relationships:

- **EMPLOYER**: Employers post job openings. They can have zero or more jobs.
- **JOB**: Contains information about job postings. A job belongs to one or more job fields and must be associated with one or more job provinces when posted.
- **SEEKER**: Job seekers looking for employment. They can create zero or more resumes and are looking for or currently working in a job province.
- **RESUME**: Job seekers' resumes. Each resume is owned by a single seeker and can belong to zero or more job fields and job provinces.
- **JOB_FIELD**: Contains information about job fields.
- **JOB_PROVINCE**: Contains information about provinces or regions.

Note: The entities job_field and job_province contain system metadata and do not require further manipulation.

## API Requirements

### EMPLOYER APIs

- **Create Employer**: Create a new employer.
    - Endpoint: `/employers`
    - Method: `POST`

- **Get Employer**: Retrieve employer details by ID.
    - Endpoint: `/employers/{id}`
    - Method: `GET`

- **Update Employer**: Update employer information.
    - Endpoint: `/employers/{id}`
    - Method: `PUT`

- **Delete Employer**: Delete an employer by ID.
    - Endpoint: `/employers/{id}`
    - Method: `DELETE`

### JOB APIs

- **Create Job**: Create a new job posting.
    - Endpoint: `/jobs`
    - Method: `POST`

- **Get Job**: Retrieve job details by ID.
    - Endpoint: `/jobs/{id}`
    - Method: `GET`

- **Get Job Infomation**: Get job information and people looking for the right fit.
  - Endpoint: `/jobs/job-information/{id}`
  - Method: `GET`

- **Update Job**: Update job information.
    - Endpoint: `/jobs/{id}`
    - Method: `PUT`

- **Delete Job**: Delete a job by ID.
    - Endpoint: `/jobs/{id}`
    - Method: `DELETE`

### SEEKER APIs

- **Create Seeker**: Create a new seeker.
    - Endpoint: `/seekers`
    - Method: `POST`

- **Get Seeker**: Retrieve seeker details by ID.
    - Endpoint: `/seekers/{id}`
    - Method: `GET`

- **Update Seeker**: Update seeker information.
    - Endpoint: `/seekers/{id}`
    - Method: `PUT`

- **Delete Seeker**: Delete a seeker by ID.
    - Endpoint: `/seekers/{id}`
    - Method: `DELETE`

### RESUME APIs

- **Create Resume**: Create a new resume.
    - Endpoint: `/resumes`
    - Method: `POST`

- **Get Resume**: Retrieve resume details by ID.
    - Endpoint: `/resumes/{id}`
    - Method: `GET`

- **Update Resume**: Update resume information.
    - Endpoint: `/resumes/{id}`
    - Method: `PUT`

- **Delete Resume**: Delete a resume by ID.
    - Endpoint: `/resumes/{id}`
    - Method: `DELETE`

### Statistic APIs
- **The System By Day**: Statistic System By Day
  - Endpoint: `/statistic/theSystemByDay?fromDate=123&toDate=123`
  - Method: `GET`

## Installation

To install and set up the project, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/minhduc6/recruitment-service.git
2. Run Project


Building and Running Spring Boot Application with Docker Compose

Before running Docker Compose, ensure you have built your Spring Boot application and generated the JAR file (`recruitment-service-0.0.1-SNAPSHOT.jar.jar` in this example).

1. **Navigate to your project directory**:
   ```bash
   cd /path/to/your/project
   docker-compose up -d

2. **Run App**.
   ```bash
   mvn clean package
   java -jar target/recruitment-service-0.0.1-SNAPSHOT.jar
