# Use a base image with Maven and Java
FROM maven:3.8.3-openjdk-11

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml file first
COPY pom.xml .

# Download dependencies to cache them
RUN mvn dependency:go-offline -B

# Copy the project files
COPY src ./src

# Run Maven to compile and install the project
CMD ["mvn", "clean", "install"]