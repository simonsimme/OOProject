# **Chat Application User Manual**

## **📘 Introduction**
This chat application allows users to:
- Create and join channels
- Send and receive messages
- Switch between channels

It supports multiple clients and provides a user-friendly interface for seamless communication.

---

## **✨ Features**
- 🛠️ **Create and join chat channels**
- 📩 **Send and receive messages**
- 🔄 **Switch between channels**
- **Encrypt messages**
- **Send emjoies**
- **Colour text messages**

---

## **⚙️ Prerequisites**
Before running the application, ensure you have:
1. **Java Development Kit (JDK)** installed
2. **Maven** installed
```bash
# Step 1: ensure you have JDK
java -version

# Step 2: ensure you have MVN
mvn -v
```


---

## **🚀 Running the Application**

### **Start Application**
Follow these steps to start the application:

```bash
# Step 1: Checkout to the main branch
git checkout main

# Step 2: Compile the project
mvn clean install -DskipTests

# Step 3: Navigate to the target directory
cd target

# Step 4: Run the Server in a new terminal window
java -jar chat-application-1.0-SNAPSHOT.jar /server

# Step 5: Run the client(s) in a new terminal window
java -jar chat-application-1.0-SNAPSHOT.jar /client
``` 
---
## **📖 UserGuide**


- 1️⃣ Starting the Program
  Two application windows will launch upon starting.
  Arrange them side by side for better visibility.
- 2️⃣ Creating a Channel
  Click Create Channel.
  Enter the desired channel name and password.
  Click Create to confirm.
- 3️⃣ Joining a Channel
  Click Join Channel.
  Enter the channel name and password.
  Click Join to enter the channel.
- 4️⃣ Sending Messages
  Type your message in the Input Field.
  Press Enter or click Send to send the message.
  By default your messages are encrypted client to client.
- 5️⃣ Switching Channels
  Select any channel from the Channel List.
  The Chat Area will update to show messages from the selected channel.
- 🛠️ Stress Testing
  Test by creating multiple channels and switching between them.
  Add multiple clients and simulate a high volume of messages.
---
## **🎯 Conclusion**


This chat application is a versatile tool for communication. Follow this guide to explore its features and enhance your experience.

💡 Feedback and improvements are always welcome!
