# Use the official Python image from Docker Hub
FROM python:3.10

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the requirements file and install dependencies
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

# Install additional dependencies for OpenCV and OpenGL
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    && rm -rf /var/lib/apt/lists/*

# Copy the rest of the application code
COPY . .

# Set environment variable for Python
ENV PYTHONUNBUFFERED=1

# Expose port 3000
EXPOSE 3000

# Command to start the server
CMD ["python", "app.py"]
